package com.shreya.auth_service.service;

import com.shreya.auth_service.model.User;
import com.shreya.auth_service.repo.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class authService {

    private final UserRepository repoOb;
    private final jwtService jwtOb;
    public Optional<String> login_success(String username, String password ){
//        System.out.println(username);
//        System.out.println(password);
//        System.out.println("In db");

        User user= repoOb.findByUsername(username).orElse(null);
        if(user==null)return Optional.empty();
        //System.out.println("password"+user.getPassword());
        if(user.getPassword().equals(password))return Optional.of(jwtOb.generateToken(user));
        else return Optional.empty();


    }

}
