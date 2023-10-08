package com.dnb.authservice.controller;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dnb.authservice.dto.RefreshToken;
import com.dnb.authservice.dto.Role;
import com.dnb.authservice.dto.User;
import com.dnb.authservice.enums.ERole;
import com.dnb.authservice.exceptions.RefreshTokenException;
import com.dnb.authservice.exceptions.RoleException;
import com.dnb.authservice.jwt.JwtUtils;
import com.dnb.authservice.payload.request.LoginRequest;
import com.dnb.authservice.payload.request.SignUpRequest;
import com.dnb.authservice.payload.request.TokenRefreshRequest;
import com.dnb.authservice.payload.response.JWTResponse;
import com.dnb.authservice.payload.response.MessageResponse;
import com.dnb.authservice.payload.response.TokenRefreshResponse;
import com.dnb.authservice.security.CustomUserDetails;
import com.dnb.authservice.service.RefreshTokenService;
import com.dnb.authservice.service.RoleService;
import com.dnb.authservice.service.UserServiceImpl;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/authenticate")
@RequiredArgsConstructor
public class AuthController {

	private final UserServiceImpl userService;

	private final RoleService roleService;

	private final RefreshTokenService refreshTokenService;

	private final AuthenticationManager authenticationManager;

	private final PasswordEncoder encoder;

	private final JwtUtils jwtUtils;

	@PostMapping("/signup")
	public ResponseEntity<?> registerUser(@Valid @RequestBody SignUpRequest signUpRequest) {

		String username = signUpRequest.getUsername();
		String email = signUpRequest.getEmail();
		String password = signUpRequest.getPassword();
		Set<String> strRoles = signUpRequest.getRoles();
		Set<Role> roles = new HashSet<>();

		if (userService.existsByUsername(username)) {
			return ResponseEntity.badRequest().body(new MessageResponse("Error: Username is already taken!"));
		}

		if (userService.existsByEmail(email)) {
			return ResponseEntity.badRequest().body(new MessageResponse("Error: Email is already taken!"));
		}

		User user = new User();
		user.setEmail(email);
		user.setUsername(username);
		user.setPassword(encoder.encode(password));

		if (strRoles != null) {
			strRoles.forEach(role -> {
				switch (role) {
				case "ROLE_ADMIN":
					Role adminRole = null;

					if (roleService.findByName(ERole.ROLE_ADMIN).isEmpty()) {
						adminRole = new Role(ERole.ROLE_ADMIN);
					} else {
						adminRole = roleService.findByName(ERole.ROLE_ADMIN)
								.orElseThrow(() -> new RoleException("Error: Admin Role is not found."));
					}

					roles.add(adminRole);

					break;
				default:
					Role userRole = null;

					if (roleService.findByName(ERole.ROLE_USER).isEmpty()) {
						userRole = new Role(ERole.ROLE_USER);
					} else {
						userRole = roleService.findByName(ERole.ROLE_USER)
								.orElseThrow(() -> new RoleException("Error: User Role is not found."));
					}

					roles.add(userRole);
				}
			});
		} else {
			roleService.findByName(ERole.ROLE_USER).ifPresentOrElse(roles::add,
					() -> roles.add(new Role(ERole.ROLE_USER)));
		}

		user.setRoles(roles);
		userService.saveUser(user);

		return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
	}

	@PostMapping("/login")
	public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
		String username = loginRequest.getUsername();
		String password = loginRequest.getPassword();

		UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
				username, password);

		Authentication authentication = authenticationManager.authenticate(usernamePasswordAuthenticationToken);
		SecurityContextHolder.getContext().setAuthentication(authentication);
		CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
		String jwt = jwtUtils.generateJwtToken(userDetails.getUsername());

		List<String> roles = userDetails.getAuthorities().stream().map(item -> item.getAuthority())
				.collect(Collectors.toList());

		RefreshToken refreshToken = refreshTokenService.createRefreshToken(userDetails.getId());

		JWTResponse jwtResponse = new JWTResponse();
		jwtResponse.setEmail(userDetails.getEmail());
		jwtResponse.setUsername(userDetails.getUsername());
		jwtResponse.setId(userDetails.getId());
		jwtResponse.setToken(jwt);
		jwtResponse.setRefreshToken(refreshToken.getToken());
		jwtResponse.setRoles(roles);

		return ResponseEntity.ok(jwtResponse);
	}

	@PostMapping("/refreshtoken")
	public ResponseEntity<?> refreshtoken(@RequestBody TokenRefreshRequest request) {

		String requestRefreshToken = request.getRefreshToken();

		RefreshToken token = refreshTokenService.findByToken(requestRefreshToken).orElseThrow(
				() -> new RefreshTokenException(requestRefreshToken + "Refresh token is not in database!"));

		RefreshToken deletedToken = refreshTokenService.verifyExpiration(token);

		User userRefreshToken = deletedToken.getUser();

		String newToken = jwtUtils.generateTokenFromUsername(userRefreshToken.getUsername());

		return ResponseEntity.ok(new TokenRefreshResponse(newToken, requestRefreshToken));
	}

	@PostMapping("/validate/token")
	public ResponseEntity<?> validateToken(@RequestBody String token) {
		
		boolean valid = this.jwtUtils.validateJwtToken(token);
		
		if(valid) return ResponseEntity.ok(valid);
		else return new ResponseEntity(false,HttpStatus.NOT_ACCEPTABLE);
	}
}
