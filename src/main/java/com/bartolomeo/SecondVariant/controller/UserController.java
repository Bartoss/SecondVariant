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

import javax.sound.midi.SysexMessage;
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
            System.out.println(user.getUsername() + " napisał(a) " + user.getCountPosts() + " postów");
            response.setId(user.getId());
            response.setUser(user.getUsername());
            response.setMessageOne("napisal(a)");
            response.setPostCount(user.getCountPosts());
            response.setMessageTwo("postow");
            responseList.add(response);
        }

        return responseList;
    }

    @RequestMapping(value = "/checkUniqePosts", method = RequestMethod.GET)
    public HashMap<Integer, ArrayList<String>> checkUniquePosts() throws IOException, InterruptedException, ExecutionException, UserNotFoundException {
        Future<List<User>> users = fetchJsonDataImpl.getAllUsers();
        HashSet<String> repetedSet = new HashSet<>();

        for (int i = 1; i < users.get().size() + 1; i++) {
            Future<Optional<User>> futureUser = fetchJsonDataImpl.getUser(i);
            Future<List<Post>> futurePosts = fetchJsonDataImpl.getPostByUser(i);
            ArrayList<String> listOfPostsUnique = new ArrayList<>();
            ArrayList<String> listOfPostsRepeted = new ArrayList<>();
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
        return repeatedPostsMap;
    }

    @RequestMapping(value = "/nearestUser", method = RequestMethod.GET)
    public Long nearestUser() throws
            IOException, InterruptedException, ExecutionException, UserNotFoundException {
        Future<List<User>> users = fetchJsonDataImpl.getAllUsers();
        Future<Optional<User>> futureUser;
        Future<Optional<User>> futureUser2;
        long distance = 1000000000;
        for (int i = 1; i < users.get().size() + 1; i++) {
            distance = 1000000000;
            futureUser = fetchJsonDataImpl.getUser(i);

            Optional<User> optionalUser = futureUser.get();
            if (!optionalUser.isPresent()) {
                throw new UserNotFoundException();
            }

            User user = optionalUser.get();
            double lat1 = user.getAddress().getGeo().getLat();
            double lng1 = user.getAddress().getGeo().getLng();

            for (int j = 1; j < users.get().size() + 1; j++) {
                futureUser2 = fetchJsonDataImpl.getUser(j);

                Optional<User> optionalUser2 = futureUser2.get();
                if (!optionalUser2.isPresent()) {
                    throw new UserNotFoundException();
                }

                User user2 = optionalUser2.get();
                if (user.getId() != user2.getId()) {
                    double lat2 = user2.getAddress().getGeo().getLat();
                    double lng2 = user2.getAddress().getGeo().getLng();
                    long temp = haversineModell.calculateRound(lat1, lng1, lat2, lng2);
                    if (distance > temp) {
                        distance = temp;
                        user.setLowestDistance(distance);
                        user.setLowestDistanceNeighbourName(user2.getUsername());
                    }
                }
            }
            System.out.println("User " + user.getUsername() + " best distance: " + user.getLowestDistance() + " with user: " + user.getLowestDistanceNeighbourName());
        }
        return distance;
    }
}
