package com.example.gsontojackson.Service;

import com.example.gsontojackson.DAO.UserDAO;
import com.example.gsontojackson.Domain.DefaultUser;
import com.example.gsontojackson.Domain.User;
import com.example.gsontojackson.Exception.CustomException;
import com.example.gsontojackson.Repository.UserRepository;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Objects;
import java.util.Optional;
import java.util.logging.Logger;

@Service
public class UserService {

  private final UserRepository userRepository;
  private final ObjectMapper objectMapper;
  private Logger logger = Logger.getLogger(UserService.class.getName());

  public UserService(UserRepository userRepository, ObjectMapper objectMapper) {
    this.userRepository = Objects.requireNonNull(userRepository);
    this.objectMapper = new ObjectMapper();
  }

  public User createUser(UserDAO userDAO) {
    User user = new User();
    user.setEmail(userDAO.getEmail());
    user.setPassword(userDAO.getPassword());
    userRepository.save(user);
    logger.info("Created " + user);
    return user;
  }

  public Optional<User> getUser(String userEmail) {
    return this.userRepository.findById(userEmail);
  }


  public String convertPOJOsToJsonString(String userEmail) throws CustomException {

    User user = getUser(userEmail).orElseGet(() -> DefaultUser.getDefaultUser());
    logger.info("Converting POJOs " + user.toString() + " into JSONString");

    String jsonInString;
    try {
      jsonInString = objectMapper.writeValueAsString(user);
    } catch (JsonProcessingException e) {
      throw new CustomException();
    }
    logger.info("JSONString = "+ jsonInString);

    return jsonInString;
  }


  public String convertJsonStringToPOJOS(String jsonString) throws CustomException {
    logger.info("Converting JSONString {" + jsonString +"} to POJOS");
    UserDAO userDAO;
    try {
      userDAO = objectMapper.readValue(jsonString, UserDAO.class);
    } catch (IOException e) {
      logger.info("IOException thrown");
      throw new CustomException();
    }
    return userDAO.toString();
  }
}
