package com.springbootblogpost.blogpost.controllers;

import com.springbootblogpost.blogpost.DTOModels.LoginDTO;
import com.springbootblogpost.blogpost.DTOModels.SignUpDTO;
import com.springbootblogpost.blogpost.models.UserModels;
import com.springbootblogpost.blogpost.models.UserRole;
import com.springbootblogpost.blogpost.repository.RoleRepository;
import com.springbootblogpost.blogpost.repository.UsersRepository;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.net.Authenticator;
import java.util.Collection;
import java.util.Collections;
import java.util.Set;

@RestController
@RequestMapping("/api/v1/users/auth")
public class UsersAuthController {
    @Autowired
    private UsersRepository usersRepository;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private RoleRepository roleRepository;

    private boolean isUserLoggedOut = false; // Flag to track logout status
    // Creat an user REST API

    @PostMapping("/login")
    public ResponseEntity<String> authenticateUser(@RequestBody LoginDTO loginDTO){
        try {
            Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginDTO.getUsername(),loginDTO.getPassword()));
            System.out.println("User authenticated successfully: " + loginDTO.getUsername());
            SecurityContextHolder.getContext().setAuthentication(authentication);
            return new ResponseEntity<>("User was signed in successfully!", HttpStatus.OK);
        }catch (Exception e){
            System.out.println("Authentication failed: " + e.getMessage());
            return  new ResponseEntity<>("Wrong Username or Password. Authentication failed!",HttpStatus.UNAUTHORIZED);
        }

    }

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody SignUpDTO signUpDTO){
        //check if user exist in the DB
        if (usersRepository.existsByUsername(signUpDTO.getUsername())){
            return  new ResponseEntity<>("Username is already taken!",HttpStatus.BAD_REQUEST);
        }

        //check if email is in db
        if (usersRepository.existsByEmail(signUpDTO.getEmail())){
            return  new ResponseEntity<>("Email is already taken!",HttpStatus.BAD_REQUEST);
        }
        //create user object
        UserModels user = new UserModels();
        user.setFirstName(signUpDTO.getFirstName());
        user.setLastName(signUpDTO.getLastName());
        user.setUsername(signUpDTO.getUsername());
        user.setEmail(signUpDTO.getEmail());
        user.setPassword(passwordEncoder.encode(signUpDTO.getPassword()));
//        user.setRoles(Set.of(UserRole.ROLE_USER)); // Assign ROLE_USER to the user
        UserRole userRole = roleRepository.findByRoleName("ROLE_USER").get();
        user.setRoles(Collections.singleton(userRole));

        usersRepository.save(user);

        return new ResponseEntity<>("User registered successfully!",HttpStatus.OK);
    }

    @DeleteMapping("/logout")
    public ResponseEntity<String> logoutUser(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(authentication != null && !isUserLoggedOut){
            SecurityContextHolder.clearContext();
            isUserLoggedOut=true;
            return new ResponseEntity<>("User logged out successfully!",HttpStatus.OK);
        }
        return new ResponseEntity<>("No user is currently logged in!",HttpStatus.BAD_REQUEST);
    }


}
