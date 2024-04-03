package com.springbootblogpost.blogpost.controllers;

import com.springbootblogpost.blogpost.models.UserModels;
import com.springbootblogpost.blogpost.repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/users")
public class UsersController {
    @Autowired
    private UsersRepository usersRepository;

    // Creat an user REST API
    @PostMapping()
    public UserModels createNewUser(@RequestBody  UserModels user){
        return usersRepository.save(user);
    }

}
