package com.springbootblogpost.blogpost.service;

import com.springbootblogpost.blogpost.models.UserModels;
import com.springbootblogpost.blogpost.models.UserRole;
import com.springbootblogpost.blogpost.repository.UsersRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.management.relation.Role;
import java.util.ArrayList;
import java.util.List;
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
        List<GrantedAuthority> authorities = getUserAuthorities(user);

        return new org.springframework.security.core.userdetails.User(
                user.getUsername(),user.getPassword(),authorities
        );
    }

    private List<GrantedAuthority> getUserAuthorities(UserModels user){
        Set<UserRole> roles = user.getRoles();
        return roles.stream()
                .map(role -> new SimpleGrantedAuthority(role.toString()))
                .collect(Collectors.toList());
    }
}
