package com.example.gsontojackson.DAO;

import com.example.gsontojackson.validation.ValidUser;
import org.hibernate.validator.constraints.UniqueElements;
import org.springframework.data.annotation.Id;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

@ValidUser
public class UserDAO {

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
    return "UserDAO{" +
        "email='" + email + '\'' +
        ", password='" + password + '\'' +
        '}';
  }
}
