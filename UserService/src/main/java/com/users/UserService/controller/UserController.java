package com.users.UserService.controller;

import com.users.UserService.entities.User;
import com.users.UserService.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/users")
public class UserController {
        @Autowired
        UserService userService;

        // create a user
        @PostMapping
        public ResponseEntity<User> createUser(@RequestBody User user)
        {
            User user1 = userService.saveUser(user);
            return ResponseEntity.status(HttpStatus.CREATED).body(user1);
        }

        //get a single user
        @GetMapping("/{userId}")
        public ResponseEntity<User> getUserById(@PathVariable String userId){
            User user = userService.getUser(userId);
            return ResponseEntity.ok(user);
        }

        // get all users
        @GetMapping
        public ResponseEntity<List<User>> getallUsers(){
            List<User> allUsers = userService.getAllUsers();
            return ResponseEntity.ok(allUsers);
        }

}
