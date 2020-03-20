package com.bartolomeo.SecondVariant.controller;

import com.bartolomeo.SecondVariant.model.Post;
import com.bartolomeo.SecondVariant.model.Response;
import com.bartolomeo.SecondVariant.model.User;
import com.bartolomeo.SecondVariant.service.FetchJsonDataImpl;
import com.bartolomeo.SecondVariant.service.HaversineModell;
import com.bartolomeo.SecondVariant.service.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


import java.io.IOException;
import java.util.*;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

@RestController
public class UserController {

    private final FetchJsonDataImpl fetchJsonDataImpl;
    private HashMap<Integer, ArrayList<String>> repeatedPostsMap = new HashMap<>();
    private HaversineModell haversineModell = new HaversineModell();

    @Autowired
    public UserController(FetchJsonDataImpl fetchJsonDataImpl) {
        this.fetchJsonDataImpl = fetchJsonDataImpl;
    }

    @RequestMapping(value = "/countUsersPosts", method = RequestMethod.GET)
    public List<Response> countUsersPosts() throws IOException, InterruptedException, ExecutionException, UserNotFoundException {

        List<Response> responseList = new ArrayList<>();
        Future<List<User>> users = fetchJsonDataImpl.getAllUsers();

        for (int i = 1; i < users.get().size() + 1; i++) {
            Future<Optional<User>> futureUser = fetchJsonDataImpl.getUser(i);
            Future<List<Post>> futurePosts = fetchJsonDataImpl.getPostByUser(i);
            Response response = new Response();
            Optional<User> optionalUser = futureUser.get();
            if (!optionalUser.isPresent()) {
                throw new UserNotFoundException();
            }

            List<Post> posts = futurePosts.get();
            int postCount = posts.size();
            User user = optionalUser.get();
            user.setPosts(posts);
            user.setCountPosts(postCount);
            response.setId(user.getId());
            response.setUser(user.getUsername());
            response.setMessageOne("napisal(a)");
            response.setPostCount(user.getCountPosts());
            response.setMessageTwo("postow");
            responseList.add(response);
            System.out.println(response.getUser() + " " + response.getMessageOne() + " " + user.getCountPosts() + " " + response.getMessageTwo());
        }

        return responseList;
    }

    @RequestMapping(value = "/checkUniquePosts", method = RequestMethod.GET)
    public HashMap<Integer, ArrayList<String>> checkUniquePosts() throws IOException, InterruptedException, ExecutionException, UserNotFoundException {
        Future<List<User>> users = fetchJsonDataImpl.getAllUsers();
        HashSet<String> repetedSet = new HashSet<>();
        ArrayList<String> listOfPostsRepeted = new ArrayList<>();

        for (int i = 1; i < users.get().size() + 1; i++) {
            Future<Optional<User>> futureUser = fetchJsonDataImpl.getUser(i);
            Future<List<Post>> futurePosts = fetchJsonDataImpl.getPostByUser(i);

            Optional<User> optionalUser = futureUser.get();
            if (!optionalUser.isPresent()) {
                throw new UserNotFoundException();
            }

            List<Post> posts = futurePosts.get();
            User user = optionalUser.get();
            user.setPosts(posts);
            for (Post post : posts) {
                if (repetedSet.add(post.getTitle()) == false) {
                    listOfPostsRepeted.add(post.getTitle());
                }
            }

            repeatedPostsMap.put(user.getId(), listOfPostsRepeted);

        }
        System.out.println("Repeted Title");
        repeatedPostsMap.forEach((k, v) -> {
            System.out.println("key: " + k + " value: " + v);
        });
        return repeatedPostsMap;
    }

    @RequestMapping(value = "/nearestUser", method = RequestMethod.GET)
    public List<User> nearestUser() throws
            IOException, InterruptedException, ExecutionException, UserNotFoundException {
        Future<List<User>> users = fetchJsonDataImpl.getAllUsers();
        Future<Optional<User>> futureUser;
        Future<Optional<User>> futureUser2;
        List<User> usersList = new ArrayList<>();
        var ref = new Object() {
            long distance;
        };
        for (int i = 1; i < users.get().size() + 1; i++) {
            ref.distance = 1000000000;
            futureUser = fetchJsonDataImpl.getUser(i);

            Optional<User> optionalUser = futureUser.get();
            if (!optionalUser.isPresent()) {
                throw new UserNotFoundException();
            }

            User user = optionalUser.get();
            double lat1 = user.getAddress().getGeo().getLat();
            double lng1 = user.getAddress().getGeo().getLng();
            //System.out.println("lng1: " + lng1 + " lat1:" + lat1);
            users.get().forEach(person ->{
                if(user.getId() != person.getId()){
                    double lat2 = person.getAddress().getGeo().getLat();
                    double lng2 = person.getAddress().getGeo().getLng();
                    //System.out.println("lng2: " + lng2 + " lat2:" + lat2);
                    long temp = haversineModell.calculateRound(lat1, lng1, lat2, lng2);
                    if (ref.distance > temp) {
                        ref.distance = temp;
                        user.setLowestDistance(ref.distance);
                        user.setLowestDistanceNeighbourName(person.getUsername());
                    }
                }
            });
            usersList.add(user);
            System.out.println("User " + user.getUsername() + " best distance: " + user.getLowestDistance() + " with user: " + user.getLowestDistanceNeighbourName());
        }
        return usersList;
    }
}
