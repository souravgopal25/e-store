package com.example.userservice;

import com.example.userservice.grpcService.HelloServiceImpl;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;

@SpringBootApplication
public class UserServiceApplication {
  static HelloServiceImpl helloService;

  public UserServiceApplication(HelloServiceImpl helloService) {
    this.helloService = helloService;
  }

  public static void main(String[] args) throws IOException, InterruptedException {
    SpringApplication.run(UserServiceApplication.class, args);
  }
}
