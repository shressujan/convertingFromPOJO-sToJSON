package com.example.gsontojackson.controller;

import com.example.gsontojackson.DAO.UserDAO;
import com.example.gsontojackson.Domain.DefaultUser;
import com.example.gsontojackson.Domain.User;
import com.example.gsontojackson.Service.UserService;
import com.example.gsontojackson.Service.convertToJSonUsingJackson.ConvertJSONStringToPOJOS;
import com.example.gsontojackson.Service.convertToJSonUsingJackson.ConvertPOJOSTOJSonString;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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
    logger = Logger.getLogger(this.getClass().getName() );
  }

  @GetMapping("/index")
  public String getIndex() {
    logger.info("inside getIndex()");
    return "index";
  }

  @PostMapping("/index")
  public String writeIndex(UserDAO userDAO) {
    User user = this.userService.createUser(userDAO);
    return "redirect:/JSon/" + user.getEmail();
  }

  @GetMapping("/JSon/{userEmail}")
  public String getJSonObject(@PathVariable(value = "userEmail", required = true) String userEmail, Model model) {
    logger.info("inside getJSonObject() method");
    User user = this.userService.getUser(userEmail).orElseGet(() -> DefaultUser.getDefaultUser());
    String jsonResponse = ConvertPOJOSTOJSonString.doConversion(user);
    model.addAttribute("JSONString", jsonResponse);
    return "jsonString";
  }

  @PostMapping("/POJOS")
  @ResponseBody
  public String getPOJOS(@RequestParam String jSONString) {
    logger.info("inside getPOJOS() method");
    return ConvertJSONStringToPOJOS.doConversion(jSONString).toString();
  }
}
