package com.example.gsontojackson.controller;

import com.example.gsontojackson.DAO.UserDAO;
import com.example.gsontojackson.Domain.User;
import com.example.gsontojackson.Service.UserService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.slf4j.Logger;

import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

public class writeIndexTest {

  @Mock
  private UserService userService;
  @Mock
  private Logger logger;

  private HomeController homeController;

  @Before
  public void createMocks() {
    initMocks(this);
    homeController = spy(new HomeController(userService));
  }

  @Test
  public void writeIndex_Succes() {
    UserDAO userDAO = new UserDAO();
    userDAO.setEmail("snoop_suzan@yahoo.com");
    User user = new User();
    user.setEmail(userDAO.getEmail());

    when(userService.createUser(userDAO)).thenReturn(user);

    String result = homeController.writeIndex(userDAO);

    Assert.assertEquals("redirect:/JSon/" + userDAO.getEmail(), result);
  }


  @Test(expected = NullPointerException.class)
  public void writeIndex_Fail() {
    UserDAO userDAO = null;

    when(userService.createUser(userDAO)).thenThrow(NullPointerException.class);
    homeController.writeIndex(userDAO);

  }

}
