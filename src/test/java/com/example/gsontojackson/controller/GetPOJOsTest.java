package com.example.gsontojackson.controller;

import com.example.gsontojackson.Service.UserService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.springframework.ui.Model;

import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

public class GetPOJOsTest {
  
  @Mock
  private UserService userService;

  @Mock
  private Model model;
  
  private HomeController homeController;
  @Before
  public void CreateMocks() {
    initMocks(this);
    this.homeController = spy(new HomeController(userService));
  }
  
  @Test
  public void getPOJOsTest_Success() {
    String jsonString = "{\"email\":\"snoop_suzan@yahoo.com\",\"password\":\"SAra123!@#\"}";

    String pojosToString ="UserDAO{email='snoop_suzan@yahoo.com', password='SAra123!@#'}";
    when(userService.convertJsonStringToPOJOS(jsonString)).thenReturn(pojosToString);
    
    String result = homeController.getPOJOS(jsonString);

    Assert.assertEquals(pojosToString, result);
  }

  @Test(expected = NullPointerException.class)
  public void getPOJOsTest_Fail() throws NullPointerException{
    String jsonString = null;

    when(userService.convertJsonStringToPOJOS(jsonString)).thenThrow(NullPointerException.class);

    homeController.getPOJOS(jsonString);
  }


}
