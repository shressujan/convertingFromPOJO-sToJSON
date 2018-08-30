package com.example.gsontojackson.Domain;

import org.springframework.data.annotation.Id;

import javax.validation.constraints.NotNull;

public class User {

  @NotNull
  @Id
  public String email;

  @NotNull
  public String password;

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  @Override
  public String toString() {
    return "User{" +
        "email='" + email + '\'' +
        ", password='" + password + '\'' +
        '}';
  }
}
