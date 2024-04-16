package com.springbootblogpost.blogpost.repository;

import com.springbootblogpost.blogpost.models.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<UserRole,Integer> {
    Optional<UserRole> findByRoleName(String roleName);
}
