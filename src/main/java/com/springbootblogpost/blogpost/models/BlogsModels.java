package com.springbootblogpost.blogpost.models;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "posts_table")
public class BlogsModels {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name= "post_id")
    private int postId;
    @Column(name= "post_context")
    private String postContext;
    @Column(name= "posted_date")
    private Date postedDate;
    @Column(name= "post_author")
    private String postAuthor;
}
