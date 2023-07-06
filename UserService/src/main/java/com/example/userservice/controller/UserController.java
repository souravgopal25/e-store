package com.example.userservice.controller;

import com.example.userservice.entity.User;
import com.example.userservice.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {

  UserService userService;

  public UserController(UserService userService) {
    this.userService = userService;
  }

  @PostMapping("")
  public ResponseEntity<User> createUser(@RequestBody User user) {
    ResponseEntity<User> createdUser = userService.createUser(user);
    return createdUser;
  }

  @GetMapping("/{id}")
  public ResponseEntity<User> getUserById(@PathVariable Long id) {
    ResponseEntity<User> response = userService.getUserById(id);
    return response;
  }
}
