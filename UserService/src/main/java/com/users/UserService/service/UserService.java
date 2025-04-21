package com.users.UserService.service;

import com.users.UserService.entities.User;

import java.util.List;


public interface UserService {

    // operations based on user details.
    //create a user
    User saveUser(User user);

    //fetch all users
    List<User> getAllUsers();

    //fetch user byID
    User getUser(String userId);
}
