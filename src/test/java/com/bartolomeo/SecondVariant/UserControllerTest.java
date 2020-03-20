package com.bartolomeo.SecondVariant;


import com.bartolomeo.SecondVariant.controller.UserController;
import com.bartolomeo.SecondVariant.model.*;
import com.bartolomeo.SecondVariant.service.FetchJsonDataImpl;
import org.junit.Before;
import org.junit.Test;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.Future;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class UserControllerTest {

    private UserController userController;
    private FetchJsonDataImpl fetchJsonDataImpl;

    private static final int USER_ID = 1;
    @Before
    public void before(){
        fetchJsonDataImpl = mock(FetchJsonDataImpl.class);
        userController = new UserController(fetchJsonDataImpl);
    }

    @Test
    public void checkCountUsersPosts() throws Exception{
        User user = setNewUser(USER_ID, "Bartolomeo");
        Future<Optional<User>> userFuture = new CheckFutureTest<>(Optional.of(user));
        when(fetchJsonDataImpl.getUser(USER_ID)).thenReturn(userFuture);

        List<Post> postList = new ArrayList<>();
        postList.add(setNewPost(USER_ID,1,"Number One"));
        postList.add(setNewPost(USER_ID,2,"Number Two"));
        postList.add(setNewPost(USER_ID,3,"Number Three"));

        Future<List<Post>> postFuture = new CheckFutureTest<>(postList);
        when(fetchJsonDataImpl.getPostByUser(USER_ID)).thenReturn(postFuture);

        ArrayList<User> allUsers = new ArrayList<>();
        allUsers.add(setNewUser(user.getId(),user.getUsername()));
        Future<List<User>> usersFuture = new CheckFutureTest<>(allUsers);
        when(fetchJsonDataImpl.getAllUsers()).thenReturn(usersFuture);

        List<Response> produce = userController.countUsersPosts();

        Response res;
        System.out.println(produce.get(0));
        //res = produce.get(0);

        //assertThat(res.getId(),is(1));
        //assertThat(res.getPostCount(),is(3));
    }

    @Test
    public void checkUniquePosts() throws Exception{
        User user = setNewUser(USER_ID, "Bartolomeo");
        Future<Optional<User>> userFuture = new CheckFutureTest<>(Optional.of(user));
        when(fetchJsonDataImpl.getUser(USER_ID)).thenReturn(userFuture);

        List<Post> postList = new ArrayList<>();
        postList.add(setNewPost(USER_ID,1,"Number One"));
        postList.add(setNewPost(USER_ID,2,"Number Two"));
        postList.add(setNewPost(USER_ID,3,"Number Two"));
        postList.add(setNewPost(USER_ID,4,"Number Four"));
        postList.add(setNewPost(USER_ID,5,"Number Four"));

        Future<List<Post>> postFuture = new CheckFutureTest<>(postList);
        when(fetchJsonDataImpl.getPostByUser(USER_ID)).thenReturn(postFuture);

        ArrayList<User> allUsers = new ArrayList<>();
        allUsers.add(setNewUser(user.getId(),user.getUsername()));
        Future<List<User>> usersFuture = new CheckFutureTest<>(allUsers);
        when(fetchJsonDataImpl.getAllUsers()).thenReturn(usersFuture);

        ArrayList<String> repetedTitle = new ArrayList<>();
        repetedTitle.add("Number Two");
        repetedTitle.add("Numer Four");
        HashMap<Integer, ArrayList<String>> produce = userController.checkUniquePosts();

        //assertThat(produce.get(USER_ID), is(USER_ID));
        assertThat(produce.get(USER_ID).size(),is(2));

    }

    //method to create new user in test class
    private User setNewUser(int userId, String username){
        User user = new User();
//        Address address = new Address();
//        Geo geo = new Geo();
//        address.setGeo(geo);
//        geo.setLat(lat);
//        geo.setLng(lng);
        user.setId(userId);
        user.setUsername(username);
        //user.setAddress(address);
        return user;
    }

    //method to create new post in test class
    private Post setNewPost(int userId, int id, String title){
        Post post = new Post();
        post.setUserId(userId);
        post.setId(id);
        post.setTitle(title);
        return post;
    }



}
