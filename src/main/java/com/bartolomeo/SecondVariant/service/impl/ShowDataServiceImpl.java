package com.bartolomeo.SecondVariant.service.impl;

import com.bartolomeo.SecondVariant.model.User;
import com.bartolomeo.SecondVariant.service.ShowDataService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;
import java.util.List;

@Service
@Slf4j
public class ShowDataServiceImpl implements ShowDataService {

    @Override
    public void printUsersWithPosts(List<User> users) {
        users.stream().forEach(user -> {
            log.info(MessageFormat.format("User #{0} ({1})",user.getId(),user.getUsername()));
        });
    }

    @Override
    public void printUserPosts(User user) {
        user.getPostList().stream().map(post -> MessageFormat.format("\t[{0}] task: {1}])", post.getBody(),post.getTitle())).forEach(log::info);
    }
}
