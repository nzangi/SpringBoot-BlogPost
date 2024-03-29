package com.springbootblogpost.blogpost.repository;

import com.springbootblogpost.blogpost.models.BlogsModels;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BlogsRepository extends JpaRepository<BlogsModels, Integer> {
}
