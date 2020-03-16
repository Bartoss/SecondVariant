package com.bartolomeo.SecondVariant.service;

import com.bartolomeo.SecondVariant.model.User;

import java.util.List;

public interface ShowDataService {
    void printUsersWithPosts(List<User> users);
    void  printUserPosts(User user);
}
