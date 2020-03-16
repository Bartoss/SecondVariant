package com.bartolomeo.SecondVariant.service;

import com.bartolomeo.SecondVariant.model.User;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@FeignClient(name = "usersClient", url = "https://jsonplaceholder.typicode.com")
public interface UsersClient{
    @GetMapping
    List<User> users();
}