package com.shreya.auth_service.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class loginResponse {

    private String token;
    private String tokenType;
    private Long expiresIn;
}