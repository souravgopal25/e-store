package com.example.userservice.entity;



import jakarta.persistence.*;
import lombok.*;


@Getter
@Builder
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "user")
public class User {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  private String email;
  private String password;
  private String firstName;
  private String lastName;
  private String dateOfBirth;
}
