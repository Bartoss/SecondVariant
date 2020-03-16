package com.bartolomeo.SecondVariant.service;

import com.bartolomeo.SecondVariant.model.Post;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(name = "postsClient", url = "https://jsonplaceholder.typicode.com")
public interface PostsClient{
    @GetMapping("/posts")
    List<Post> getPostsBy(@RequestParam("userId") Integer userId);
}
