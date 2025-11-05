package com.example.ecommerce.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AuthResponse {
    @SuppressWarnings("unused")
	private String token;
    @SuppressWarnings("unused")
	private String email;
    @SuppressWarnings("unused")
	private String name;
    @SuppressWarnings("unused")
	private String role;
}
