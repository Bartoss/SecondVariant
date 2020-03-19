package com.bartolomeo.SecondVariant.service;

import com.bartolomeo.SecondVariant.model.Post;
import com.bartolomeo.SecondVariant.model.User;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

import java.util.List;

public interface FetchJsonData {

    @GET("/users/{id}")
    Call<User> getUser(@Path("id") int id);

    @GET("posts")
    Call<List<Post>> getPostByUser(@Query("userId") int userId);

    @GET("/users")
    Call<List<User>> getAllUsers();
}
