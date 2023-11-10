package com.emtech.bookinventory.controllers;

import com.emtech.bookinventory.services.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

@RestController
@AllArgsConstructor
@RequestMapping("api/v1/account")
public class UserController {

    private final UserService userService;

    @PostMapping("/register")
    public HashMap createAccount(@RequestBody HashMap params){
        return userService.validateUser(params);
    }

    @PostMapping("/login")
    public HashMap loginUser(@RequestBody HashMap params){
        return userService.loginUser(params);
    }
}
