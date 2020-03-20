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
        User user = setNewUser(USER_ID, "Bartolomeo",0,0);
        Address address = new Address();
        Geo geo = new Geo();
        geo.setLat(0);
        geo.setLng(0);
        address.setGeo(geo);
        user.setAddress(address);
        Future<Optional<User>> userFuture = new CheckFutureTest<>(Optional.of(user));
        when(fetchJsonDataImpl.getUser(USER_ID)).thenReturn(userFuture);

        List<Post> postList = new ArrayList<>();
        postList.add(setNewPost(USER_ID,1,"Number One"));
        postList.add(setNewPost(USER_ID,2,"Number Two"));
        postList.add(setNewPost(USER_ID,3,"Number Three"));

        Future<List<Post>> postFuture = new CheckFutureTest<>(postList);
        when(fetchJsonDataImpl.getPostByUser(USER_ID)).thenReturn(postFuture);

        ArrayList<User> allUsers = new ArrayList<>();
        allUsers.add(setNewUser(user.getId(),user.getUsername(),user.getAddress().getGeo().getLat(),user.getAddress().getGeo().getLng()));
        Future<List<User>> usersFuture = new CheckFutureTest<>(allUsers);
        when(fetchJsonDataImpl.getAllUsers()).thenReturn(usersFuture);

        List<Response> produce = userController.countUsersPosts();

        Response res;
        res = produce.get(0);

        assertThat(res.getId(),is(1));
        assertThat(res.getPostCount(),is(3));
    }

    @Test
    public void checkUniquePosts() throws Exception{
        User user = setNewUser(USER_ID, "Bartolomeo",0,0);
        Address address = new Address();
        Geo geo = new Geo();
        geo.setLat(0);
        geo.setLng(0);
        address.setGeo(geo);
        user.setAddress(address);
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
        allUsers.add(setNewUser(user.getId(),user.getUsername(),user.getAddress().getGeo().getLat(),user.getAddress().getGeo().getLng()));
        Future<List<User>> usersFuture = new CheckFutureTest<>(allUsers);
        when(fetchJsonDataImpl.getAllUsers()).thenReturn(usersFuture);

        ArrayList<String> repetedTitle = new ArrayList<>();
        repetedTitle.add("Number Two");
        repetedTitle.add("Numer Four");
        HashMap<Integer, ArrayList<String>> produce = userController.checkUniquePosts();

        assertThat(produce.get(USER_ID).size(),is(2));

    }

    @Test
    public void checkNearestUser() throws Exception{
        User user1 = setNewUser(USER_ID, "Bartolomeo",0,0);
        Address address1 = new Address();
        Geo geo1 = new Geo();

        geo1.setLat(50.064030);
        geo1.setLng(19.942126);
        address1.setGeo(geo1);
        user1.setAddress(address1);

        User user2 = setNewUser(USER_ID, "Weronika",0,0);
        Address address2 = new Address();
        Geo geo2 = new Geo();

        geo2.setLat(50.069126);
        geo2.setLng(19.905222);
        address2.setGeo(geo2);
        user2.setAddress(address2);

        User user3 = setNewUser(USER_ID, "Arturo",0,0);
        Address address3 = new Address();
        Geo geo3 = new Geo();

        geo3.setLat(52.233816);
        geo3.setLng(21.007445);
        address3.setGeo(geo3);
        user3.setAddress(address3);

        Future<Optional<User>> userFuture = new CheckFutureTest<>(Optional.of(user1));
        when(fetchJsonDataImpl.getUser(USER_ID)).thenReturn(userFuture);
        Future<Optional<User>> userFuture2 = new CheckFutureTest<>(Optional.of(user2));
        when(fetchJsonDataImpl.getUser(2)).thenReturn(userFuture2);
        Future<Optional<User>> userFuture3 = new CheckFutureTest<>(Optional.of(user3));
        when(fetchJsonDataImpl.getUser(3)).thenReturn(userFuture3);

        ArrayList<User> allUsers = new ArrayList<>();
        allUsers.add(setNewUser(user1.getId(),user1.getUsername(),user1.getAddress().getGeo().getLat(),user1.getAddress().getGeo().getLng()));
        allUsers.add(setNewUser(user2.getId(),user2.getUsername(),user2.getAddress().getGeo().getLat(),user2.getAddress().getGeo().getLng()));
        allUsers.add(setNewUser(user3.getId(),user3.getUsername(),user3.getAddress().getGeo().getLat(),user3.getAddress().getGeo().getLng()));
        Future<List<User>> usersFuture = new CheckFutureTest<>(allUsers);
        when(fetchJsonDataImpl.getAllUsers()).thenReturn(usersFuture);

        List<User> produce = userController.nearestUser();
        System.out.println(produce.size());


    }

    //method to create new user in test class
    private User setNewUser(int userId, String username, double lat, double lng){
        User user = new User();
        Address address = new Address();
        Geo geo = new Geo();
        address.setGeo(geo);
        geo.setLat(lat);
        geo.setLng(lng);
        user.setId(userId);
        user.setUsername(username);
        user.setAddress(address);
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
