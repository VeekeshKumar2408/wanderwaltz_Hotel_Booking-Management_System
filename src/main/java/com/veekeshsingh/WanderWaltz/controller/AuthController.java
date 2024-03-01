package com.veekeshsingh.WanderWaltz.controller;

import com.veekeshsingh.WanderWaltz.exception.UserAlreadyExistsException;
import com.veekeshsingh.WanderWaltz.model.User;
import com.veekeshsingh.WanderWaltz.service.IUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;

@RequiredArgsConstructor
public class AuthController {
    private final IUserService userService;

    @PostMapping("/register-user")
    public ResponseEntity<?> registerUser(User user){
        try{
            userService.registerUser(user);
            return ResponseEntity.ok("Registration Successful");
        } catch (UserAlreadyExistsException e){
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        }
    }

}
