package com.bartolomeo.SecondVariant.controller;

import com.bartolomeo.SecondVariant.model.Post;
import com.bartolomeo.SecondVariant.model.User;
import com.bartolomeo.SecondVariant.service.FetchJsonDataImpl;
import com.bartolomeo.SecondVariant.service.UserNotFoundException;
import okhttp3.OkHttpClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

@RestController
public class UserController {

    private final FetchJsonDataImpl fetchJsonDataImpl;

    @Autowired
    public UserController(FetchJsonDataImpl fetchJsonDataImpl){
        this.fetchJsonDataImpl = fetchJsonDataImpl;
    }

    @RequestMapping(value = "/countUsersPosts", method = RequestMethod.GET)
    public String countUsersPosts() throws IOException, InterruptedException, ExecutionException, UserNotFoundException {
        String value = new String();
        Future<List<User>> users = fetchJsonDataImpl.getAllUsers();

//        users.get().forEach(user -> {
//            Future<Optional<User>> futureUsers = fetchJsonDataImpl.getUser(user.getId());
//        });

        for(int i = 1; i < users.get().size(); i++){
            Future<Optional<User>> futureUser = fetchJsonDataImpl.getUser(i);
            Future<List<Post>> futurePosts = fetchJsonDataImpl.getPostByUser(i);

            Optional<User> optionalUser = futureUser.get();
            if (!optionalUser.isPresent()) {
                throw new UserNotFoundException();
            }

            List<Post> posts = futurePosts.get();
            int postCount = posts.size();
            User user = optionalUser.get();
            user.setPosts(posts);
            user.setCountPosts(postCount);
            System.out.println("User: " + user.getUsername() + " wrote count(" + user.getCountPosts() + ") posts ");
            value += "User: " + user.getUsername() + " wrote count(" + user.getCountPosts() + ") posts \n";
        }

        return value;
    }
}
