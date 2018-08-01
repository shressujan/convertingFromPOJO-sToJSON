package com.example.gsontojackson.Domain;

public class DefaultUser {

  public static User getDefaultUser() {
   User user = new User();
    user.setEmail("snoop_abc@yahoo.com");
    user.setPassword("abcabc");
    return user;
  }
}
