package com.example.gsontojackson.Service;

import com.example.gsontojackson.DAO.UserDAO;
import com.example.gsontojackson.Domain.User;
import com.example.gsontojackson.Repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;
import java.util.logging.Logger;

@Service
public class UserService {

  private final UserRepository userRepository;
  private Logger logger = Logger.getLogger(UserService.class.getName());

  public UserService(UserRepository userRepository) {
    this.userRepository = Objects.requireNonNull(userRepository);
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
}
