package com.bartolomeo.SecondVariant.service.impl;

import com.bartolomeo.SecondVariant.UserContractor;
import com.bartolomeo.SecondVariant.model.User;
import com.bartolomeo.SecondVariant.service.RetreatService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
public class RetreatServiceImpl implements RetreatService {

    private final UserContractor userContractor;
    private final ShowDataServiceImpl showDataServiceImpl;

    public RetreatServiceImpl(UserContractor userContractor, ShowDataServiceImpl showDataServiceImpl) {
        this.userContractor = userContractor;
        this.showDataServiceImpl = showDataServiceImpl;
    }

    private List<User> rollbackUsers = null;


    @Override
    public void rollbackUsers() {

    }

    @Override
    public void logCachedResults() {

    }
}
