package com.springbootblogpost.blogpost.controllers;

import com.springbootblogpost.blogpost.DTOModels.LoginDTO;
import com.springbootblogpost.blogpost.DTOModels.SignUpDTO;
import com.springbootblogpost.blogpost.models.UserModels;
import com.springbootblogpost.blogpost.models.UserRole;
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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.Authenticator;
import java.util.Set;

@RestController
@RequestMapping("api/v1/users/auth")
public class UsersAuthController {
    @Autowired
    private UsersRepository usersRepository;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private PasswordEncoder passwordEncoder;

    // Creat an user REST API
    @PostMapping("/login")
    public ResponseEntity<String> authenticateUser(@RequestBody LoginDTO loginDTO){
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginDTO.getUsername(),loginDTO.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        return new ResponseEntity<>("User was signed in successfully!", HttpStatus.OK);
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
        user.setRoles(Set.of(UserRole.ROLE_USER)); // Assign ROLE_USER to the user


        usersRepository.save(user);

        return new ResponseEntity<>("User registered successfully!",HttpStatus.CREATED);

    }

}
