package com.springbootblogpost.blogpost.models;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

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
    @Column(nullable = false,name= "posted_date",columnDefinition = "TIMESTAMP")
    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime postedDate;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "post_author",referencedColumnName = "user_id")
    private UserModels post_author;
}
