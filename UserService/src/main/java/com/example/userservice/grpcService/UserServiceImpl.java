package com.example.userservice.grpcService;

import com.example.userService.*;
import com.example.userservice.entity.User;
import com.example.userservice.exceptions.UserNotFoundException;
import com.example.userservice.service.UserService;
import io.grpc.Status;
import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;

import java.util.List;

@GrpcService
public class UserServiceImpl extends UserServiceGrpc.UserServiceImplBase {
  UserService userService;

  public UserServiceImpl(UserService userService) {
    this.userService = userService;
  }

  public User makeUserFromUserDto(UserDTO userDTO) {
    return User.builder()
        .firstName(userDTO.getFirstName())
        .lastName(userDTO.getLastName())
        .email(userDTO.getEmail())
        .password(userDTO.getPassword())
        .dateOfBirth(userDTO.getDateOfBirth())
        .build();
  }

  public UserDTO makeUserDtoFromUser(User user) {
    return UserDTO.newBuilder()
        .setId(user.getId())
        .setFirstName(user.getFirstName())
        .setLastName(user.getLastName())
        .setEmail(user.getEmail())
        .setDateOfBirth(user.getDateOfBirth())
        .build();
  }

  @Override
  public void getAllUsers(EmptyRequest request, StreamObserver<UserDTO> responseObserver) {
    try {
      List<User> userList = userService.getAllUsers();
      userList.stream()
          .forEach(
              user -> {
                UserDTO userDTO = makeUserDtoFromUser(user);
                responseObserver.onNext(userDTO);
              });
    } catch (Exception e) {
      responseObserver.onError(Status.INTERNAL.withDescription(e.getMessage()).asException());
    } finally {
      responseObserver.onCompleted();
    }
  }

  @Override
  public void signup(UserDTO request, StreamObserver<SignupResponse> responseObserver) {
    User user = makeUserFromUserDto(request);
    try {
      user = userService.createUser(user).getBody();
      UserDTO responseUserDto = makeUserDtoFromUser(user);
      SignupResponse signupResponse =
          SignupResponse.newBuilder()
              .setUser(responseUserDto)
              .setSuccess(true)
              .setMessage("User Creation Success")
              .build();
      responseObserver.onNext(signupResponse);
    } catch (Exception e) {
      responseObserver.onError(Status.INTERNAL.withDescription(e.getMessage()).asException());
    } finally {
      responseObserver.onCompleted();
    }
  }

  @Override
  public void deleteUser(
      DeleteUserRequest request, StreamObserver<DeleteUserResponse> responseObserver) {
    try {
      User user = userService.deleteUser(request.getId()).getBody();
      DeleteUserResponse deleteUserResponse =
          DeleteUserResponse.newBuilder()
              .setSuccess(true)
              .setMessage("User Deleted Success")
              .build();
      responseObserver.onNext(deleteUserResponse);
    } catch (Exception e) {
      responseObserver.onError(Status.INTERNAL.withDescription(e.getMessage()).asException());
    } finally {
      responseObserver.onCompleted();
    }
  }

  @Override
  public void updateUser(
      UpdateUserRequest request, StreamObserver<UpdateUserResponse> responseObserver) {
    try {
      User updateRequest = makeUserFromUserDto(request.getUser());
      User updatedUser = userService.updateUser(request.getId(), updateRequest).getBody();
      UserDTO updatedResponse = makeUserDtoFromUser(updatedUser);
      UpdateUserResponse response =
          UpdateUserResponse.newBuilder()
              .setSuccess(true)
              .setMessage("User Updated Successfully")
              .setUser(updatedResponse)
              .build();
      responseObserver.onNext(response);
    } catch (Exception e) {
      responseObserver.onError(Status.INTERNAL.withDescription(e.getMessage()).asException());
    } finally {
      responseObserver.onCompleted();
    }
  }

  @Override
  public void getUser(GetUserRequest request, StreamObserver<UserDTO> responseObserver) {
    try {
      User user = userService.getUserById(request.getId()).getBody();
      UserDTO userDTO = makeUserDtoFromUser(user);
      responseObserver.onNext(userDTO);
    } catch (UserNotFoundException e) {
      responseObserver.onError(Status.NOT_FOUND.withDescription(e.getMessage()).asException());
    } finally {
      responseObserver.onCompleted();
    }
  }
}
