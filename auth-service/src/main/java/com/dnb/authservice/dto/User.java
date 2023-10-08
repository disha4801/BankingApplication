package com.dnb.authservice.dto;

import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "Users")
@Data
@NoArgsConstructor
public class User extends IdBasedEntity {

	@Column(unique = true, nullable = false)
    private String username;
	
	@NotBlank(message = "Email cannot be Empty")
    @Column(unique = true, nullable = false)
    private String email;

	@NotBlank
	@Size(min = 8, message = "should be minimum 8 characters..")
	private String password;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles = new HashSet<>();

}
