package com.example.userservice.service;

import com.example.userservice.entity.User;
import com.example.userservice.exceptions.UserAlreadyExist;
import com.example.userservice.exceptions.UserNotFoundException;

import com.example.userservice.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
  UserRepository userRepository;

  public UserService(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  public ResponseEntity<User> getUserById(Long id) {
    Optional<User> optionalUser = userRepository.findById(id);
    if (optionalUser.isPresent()) {
      return new ResponseEntity<>(optionalUser.get(), HttpStatus.OK);
    } else throw new UserNotFoundException("User not found with ID : " + id);
  }

  public ResponseEntity<User> createUser(User user) {
    Optional<User> optionalUser = userRepository.findById(user.getId());
    if (optionalUser.isPresent()) {
      throw new UserAlreadyExist("User Already Exists with UserId: " + optionalUser.get().getId());
    }
    User responseUser = userRepository.save(user);
    return new ResponseEntity<>(responseUser, HttpStatus.OK);
  }

  public ResponseEntity<User> updateUser(Long userId, User user) {
    Optional<User> optionalUser = userRepository.findById(userId);
    if (optionalUser.isEmpty()) {
      throw new UserNotFoundException("User not found with UserId: " + userId);
    }
    User updatedUser = userRepository.save(user);
    return new ResponseEntity<>(updatedUser, HttpStatus.OK);
  }

  public ResponseEntity<User> deleteUser(Long userId) {
    Optional<User> optionalUser = userRepository.findById(userId);
    if (optionalUser.isEmpty()) {
      throw new UserNotFoundException("User not found with UserId: " + userId);
    }
    userRepository.delete(optionalUser.get());
    return new ResponseEntity<>(optionalUser.get(), HttpStatus.OK);
  }

  public List<User> getAllUsers() {
    return userRepository.findAll();
  }
}
