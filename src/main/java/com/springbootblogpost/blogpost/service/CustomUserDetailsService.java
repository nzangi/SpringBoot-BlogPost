package com.springbootblogpost.blogpost.service;

import com.springbootblogpost.blogpost.models.UserModels;
import com.springbootblogpost.blogpost.repository.UsersRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


import java.util.Set;
import java.util.stream.Collectors;

@Service
public class CustomUserDetailsService implements UserDetailsService {
    private UsersRepository usersRepository;
    public CustomUserDetailsService (UsersRepository usersRepository){
        this.usersRepository = usersRepository;

    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserModels user = usersRepository.findAllByUsername(username).orElseThrow(
                () ->new UsernameNotFoundException(username+ " username was not found")

        );

        Set<GrantedAuthority> authorities = user
                .getRoles()
                .stream()
                .map((role) ->new SimpleGrantedAuthority(role.getRoleName())).collect(Collectors.toSet());

        return new org.springframework.security.core.userdetails.User(
                user.getUsername(),user.getPassword(),authorities
        );
    }

}
