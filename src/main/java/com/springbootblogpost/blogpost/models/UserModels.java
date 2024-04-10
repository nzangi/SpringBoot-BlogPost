package com.springbootblogpost.blogpost.models;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;
import java.util.Set;

@Data
@Entity
@Table(name = "users",uniqueConstraints = {
    @UniqueConstraint(columnNames = {"username"}),
        @UniqueConstraint(columnNames = {"email"}),
})

public class UserModels {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    @Column(name= "user_id")
    private int id;
    @Column(name= "first_name")
    private String firstName;
    @Column(name= "last_name")
    private String lastName;
    @Column(name= "username")
    private String username;
    @Column(name= "email")
    private String email;
    @Column(name= "password")
    private String password;

    @ElementCollection(targetClass = UserRole.class)
    @CollectionTable(name = "user_roles", joinColumns = @JoinColumn(name = "user_id"))
    @Enumerated(EnumType.STRING)
    private Set<UserRole> roles;

    @OneToMany(mappedBy="post_author",cascade = CascadeType.REMOVE)
    private List<BlogsModels> blogsModel;
}
