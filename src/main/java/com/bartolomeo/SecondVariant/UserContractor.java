package com.bartolomeo.SecondVariant;

import com.bartolomeo.SecondVariant.model.Post;
import com.bartolomeo.SecondVariant.model.User;
import com.bartolomeo.SecondVariant.service.PostsClient;
import com.bartolomeo.SecondVariant.service.UsersClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class UserContractor {


    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    @Autowired
    private UsersClient usersClient;

//    @Autowired
//    private PostsClient postsClient;

    @RequestMapping(value = "/userList", method = RequestMethod.GET, produces = "application/json")
    public List<User> allUsers() {
        List<User> users = usersClient.users();
        return users;
    }

//    @RequestMapping(value = "/postsClient", method = RequestMethod.GET, produces = "application/json")
//    public List<User> postsClient() {
//        List<User> users = usersClient.users();
//        return users;
//    }





}
