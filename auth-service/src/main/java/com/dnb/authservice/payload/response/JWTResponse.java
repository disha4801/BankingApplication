package com.dnb.authservice.payload.response;

import java.util.List;

import lombok.Data;

@Data
public class JWTResponse {
    private String token;
    private String type = "Bearer";
    private String refreshToken;
    private int id;
    private String username;
    private String email;
    private List<String> roles;
}
