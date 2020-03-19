package com.bartolomeo.SecondVariant.service;

import com.bartolomeo.SecondVariant.model.Post;
import com.bartolomeo.SecondVariant.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Service;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.Future;

@Service
public class FetchJsonDataImpl {

    private final FetchJsonData fetchJsonData;

    @Autowired
    public FetchJsonDataImpl(@Value("http://jsonplaceholder.typicode.com") String url){
        Retrofit retrofit = new Retrofit.Builder().baseUrl(url).addConverterFactory(GsonConverterFactory.create()).build();
        fetchJsonData = retrofit.create(FetchJsonData.class);
    }

    @Async
    public Future<Optional<User>> getUser(int id) throws IOException{
        User user = fetchJsonData.getUser(id).execute().body();
        Optional<User> result = Optional.ofNullable(user);
        return new AsyncResult<>(result);
    }

    @Async
    public Future<List<Post>> getPostByUser(int userId) throws IOException{
        List<Post> posts = fetchJsonData.getPostByUser(userId).execute().body();
        return new AsyncResult<>(posts);
    }

    @Async
    public Future<List<User>> getAllUsers() throws IOException{
        List<User> users = fetchJsonData.getAllUsers().execute().body();
        return new AsyncResult<>(users);
    }



}
