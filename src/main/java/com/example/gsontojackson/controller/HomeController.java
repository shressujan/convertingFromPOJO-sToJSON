package com.example.gsontojackson.controller;

import com.example.gsontojackson.DAO.UserDAO;
import com.example.gsontojackson.Domain.User;
import com.example.gsontojackson.Service.UserService;
import com.example.gsontojackson.Service.convertToJSonUsingJackson.ConvertToJSonUsingJackson;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;
import java.util.Optional;
import java.util.logging.Logger;

@Controller
@RequestMapping("/")
public class HomeController {

  private final UserService userService;
  private final Logger logger;

  public HomeController(UserService userService) {
    this.userService = Objects.requireNonNull(userService);
    logger = Logger.getLogger( HomeController.class.getName() );
  }

  @GetMapping("/index")
  public String getIndex() {
    logger.info("inside getIndex()");
    return "index";
  }

  @PostMapping("/index")
  public String writeIndex(UserDAO userDAO) {
    User user = this.userService.createUser(userDAO);
    logger.info("inside writeIndex()");
    logger.info(user.toString());
    return "redirect:/JSon/" + user.getEmail();
  }

  @GetMapping("/JSon/{userEmail}")
  @ResponseBody
  public String getJSonObject(@PathVariable(value = "userEmail", required = true) String userEmail) {
    logger.info("inside getJSonObject() before conversion");
    Optional<User> user = this.userService.getUser(userEmail);
    String jsonResponse = ConvertToJSonUsingJackson.doConversion(user.get());
    logger.info("inside getJSonObject()");
    return jsonResponse;
  }
}
