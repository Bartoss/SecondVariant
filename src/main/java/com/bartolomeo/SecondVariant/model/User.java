package com.bartolomeo.SecondVariant.model;

import lombok.Data;

import java.util.List;

@Data
public class User {
    Integer id;
    String name;
    String username;
    String email;

    List<Post> postList;
}
