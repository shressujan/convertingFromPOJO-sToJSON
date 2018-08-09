package com.example.gsontojackson.Service;

import com.example.gsontojackson.DAO.UserDAO;
import com.example.gsontojackson.Domain.User;
import com.example.gsontojackson.Exception.CustomException;
import com.example.gsontojackson.Repository.UserRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import java.io.IOException;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.initMocks;

public class UserServiceTest {

  @Mock
  private UserRepository userRepository;

  @Mock
  private ObjectMapper objectMapper;

  private UserService userService;

  @Before
  public void createMocks() {
    initMocks(this);
    userService = spy(new UserService(userRepository, objectMapper));
  }
  @Test
  public void createUserTest_Sucess() {
    UserDAO userDAO = new UserDAO();
    userDAO.setEmail("snoop_suzan@yahoo.com");
    userDAO.setPassword("asdfqweir2314");

    User user = new User();
    user.setEmail(userDAO.getEmail());
    user.setPassword(userDAO.getPassword());
    when(this.userRepository.save(any(User.class))).thenReturn(user);

    User user1 = userService.createUser(userDAO);

    Assert.assertEquals(user1.getEmail(), userDAO.getEmail());

  }

  @Test(expected = NullPointerException.class )
  public void createUserTest_Fail() throws NullPointerException{
    UserDAO userDAO = new UserDAO();

    when(this.userRepository.save(any(User.class))).thenThrow(NullPointerException.class);

    userService.createUser(userDAO);

  }

  @Test
  public void getUserTest_Success() {
    String userEmail = "snoop_suzan@yahoo.com";
    User user = new User();
    user.setEmail(userEmail);

    when(userRepository.findById(userEmail)).thenReturn(Optional.ofNullable(user));

    Optional<User> user1 = userService.getUser(userEmail);

    Assert.assertEquals(user1.get().getEmail(), userEmail);
  }


  @Test(expected = NullPointerException.class)
  public void getUserTest_Fail() throws NullPointerException {
    String userEmail = null;

    when(userRepository.findById(userEmail)).thenThrow(NullPointerException.class);

    Optional<User> user1 = userService.getUser(userEmail);

    Assert.assertEquals(user1.get().getEmail(), userEmail);
  }

  @Test
  public void convertPOJOsToJsonStringTest_Success() throws JsonProcessingException {
    String userEmail = "snoop_suzan@yahoo.com";
    String password = "abcAC12!@#";

    User user = new User();
    user.setEmail(userEmail);
    user.setPassword(password);

    when(userService.getUser(userEmail)).thenReturn(Optional.ofNullable(user));
    when(objectMapper.writeValueAsString(user)).thenReturn(anyString());

    String result = userService.convertPOJOsToJsonString(userEmail);

    Assert.assertEquals("{\"email\":\"snoop_suzan@yahoo.com\",\"password\":\"abcAC12!@#\"}", result);
  }

  @Test(expected = NullPointerException.class)
  public void convertPOJOsToJsonStringTest_Fail() throws NullPointerException{

    when(userService.getUser(null)).thenThrow(NullPointerException.class);

    userService.convertPOJOsToJsonString(null);
  }

  @Test(expected = CustomException.class)
  public void convertPOJOsToJsonStringTest_Fail_mapping() throws CustomException, JsonProcessingException {
    String userEmail = "snoop_suzan@yahoo.com";
    String password = "abcAC12!@#";

    User user = new User();
    user.setEmail(userEmail);
    user.setPassword(password);

    when(userService.getUser(userEmail)).thenReturn(Optional.ofNullable(user));
    when(objectMapper.writeValueAsString(user)).thenThrow(JsonProcessingException.class);

    userService.convertPOJOsToJsonString(userEmail);

    }

    @Test
    public void convertJsonStringToPOJOsTest_Success() throws IOException {
      String jsonString = "{\"email\":\"snoop_suzan@yahoo.com\",\"password\":\"abcAC12!@#\"}";

      UserDAO userDAO = new UserDAO();

      when(objectMapper.readValue(jsonString, UserDAO.class)).thenReturn(userDAO);

      String result = userService.convertJsonStringToPOJOS(jsonString);

      Assert.assertEquals("UserDAO{email='snoop_suzan@yahoo.com', password='abcAC12!@#'}", result);
    }

    /*Why not working?*/
  @Test(expected = CustomException.class)
  public void convertJsonStringToPOJOsTest_Fail() throws CustomException, IOException {
    String jsonString = "{\"email\":\"snoop_suzan@yahoo.com\",\"password\":\"abcAC12!@#\"}";

    when(objectMapper.readValue(jsonString, UserDAO.class)).thenThrow(IOException.class);

    userService.convertJsonStringToPOJOS(jsonString);

  }
}
