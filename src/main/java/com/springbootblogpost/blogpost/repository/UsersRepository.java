package com.springbootblogpost.blogpost.repository;

import com.springbootblogpost.blogpost.models.UserModels;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UsersRepository extends JpaRepository<UserModels,Integer> {
    Optional<UserModels> findAllByEmail(String email);
    Optional<UserModels> findByUsernameOrEmail(String username,String email);
    Optional<UserModels> findAllByUsername(String username);
    Boolean existsByUsername(String username);
    Boolean existsByEmail(String email);


}
