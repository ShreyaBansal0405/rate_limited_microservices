package com.shreya.auth_service.controller;

import com.shreya.auth_service.model.errorResponse;
import com.shreya.auth_service.model.loginFormat;
import com.shreya.auth_service.model.loginResponse;
import com.shreya.auth_service.service.authService;
import com.shreya.auth_service.service.jwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
public class AuthController {

    @Autowired
    private authService authOb;
    @Autowired
    private jwtService jwtOb;

    @PostMapping("/login")
    public ResponseEntity<?> loginFunction(@RequestBody loginFormat body) {
        Optional<String> token = authOb.login_success(body.getUsername(), body.getPassword());
        if (token.isEmpty()) {
            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .body(
                            new errorResponse(
                                    "Invalid credentials"
                            )
                    );
        } else {
            loginResponse response =
                    new loginResponse(
                            token.get(),
                            "Bearer",
                            86400L
                    );

            return ResponseEntity.ok(response);

        }
    }
    @GetMapping("/validate")
    public String validate(@RequestHeader("Authorization") String authHeader){

            String token =authHeader.substring(7);
            boolean valid = jwtOb.isValid(token);
            return valid?"valid":"invalid";





    }

}
