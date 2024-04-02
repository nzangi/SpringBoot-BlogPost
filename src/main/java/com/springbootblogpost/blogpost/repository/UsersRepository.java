package com.springbootblogpost.blogpost.repository;

import com.springbootblogpost.blogpost.models.UserModels;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsersRepository extends JpaRepository<UserModels,Integer> {

}
