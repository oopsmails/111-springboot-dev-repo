package com.oopsmails.springboot.jwt.controllers;

import com.oopsmails.springboot.jwt.entity.User;
import com.oopsmails.springboot.jwt.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController // Marks the class a rest controller
@RequestMapping("/api/user") // Requests made to /api/auth/anything will be handles by this class
public class UserController {

    // Injecting Dependencies
    @Autowired private UserRepo userRepo;

    // Defining the function to handle the GET route to fetch user information of the authenticated user
    @GetMapping("/info")
    public User getUserDetails(){
        // Retrieve email from the Security Context
        String email = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        // Fetch and return user details
        return userRepo.findByEmail(email).get();
    }


}
