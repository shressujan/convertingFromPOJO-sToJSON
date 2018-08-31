package com.example.gsontojackson.validation;

import com.example.gsontojackson.DAO.UserDAO;
import com.example.gsontojackson.Domain.User;
import com.example.gsontojackson.Repository.UserRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import javax.validation.ClockProvider;
import javax.validation.ConstraintValidatorContext;
import javax.validation.Validator;
import java.util.Optional;

import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;


public class UserConstraintValidatorTest {


 @Mock private UserRepository userRepository;
  private Validator validator;
  private UserConstraintValidator userConstraintValidator;

  @Before
  public void createMocks() {
    initMocks(this);
    userConstraintValidator = spy(new UserConstraintValidator(userRepository));

    /*Different validator*/
/*    ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
    validator = validatorFactory.getValidator();*/
  }

  @Test
  public void testUser_Valid() {
    UserDAO userDAO = new UserDAO();
    userDAO.setEmail("hari_abc@yahoo.com");
    userDAO.setPassword("ABCqwe!@#1123");

    Assert.assertTrue(userConstraintValidator.isValid(userDAO, constraintValidatorContext()));

   /* Set<ConstraintViolation<UserDAO>> violations = validator.validate(userDAO);
    Assert.assertEquals(0, violations.size());*/
  }

  @Test
  public void testUser_InValidEmail() {
    UserDAO userDAO = new UserDAO();
    userDAO.setEmail("snoop_abc");
    userDAO.setPassword("ABCqwe!@#1123");

    Assert.assertFalse(userConstraintValidator.isValid(userDAO, constraintValidatorContext()));
/*    Set<ConstraintViolation<UserDAO>> violations = validator.validate(userDAO);
    Assert.assertEquals(1, violations.size());*/
  }

  @Test
  public void testUser_InValidPassword() {
    UserDAO userDAO = new UserDAO();
    userDAO.setEmail("snoop_abc@yahoo.com");
    userDAO.setPassword("!@#1123");

    Assert.assertFalse(userConstraintValidator.isValid(userDAO, constraintValidatorContext()));
   /* Set<ConstraintViolation<UserDAO>> violations = validator.validate(userDAO);
    Assert.assertEquals(1, violations.size());*/
  }

  @Test
  public void testUser_NotExistYet() {
    UserDAO userDAO = new UserDAO();
    userDAO.setEmail("snoop_suzan@yahoo.com");
    userDAO.setPassword("ADSFasdfa123!@#");

    User user = new User();
    user.setEmail(userDAO.getEmail());
    user.setPassword(userDAO.getPassword());

    userRepository.save(user);


    Optional<User> optionalUser = Optional.of(user);

    when(userRepository.findById(userDAO.getEmail())).thenReturn(optionalUser);

    Assert.assertFalse(userConstraintValidator.isValid(userDAO, constraintValidatorContext()));

    /*This validator didn't work!!!*/
 /*   Set<ConstraintViolation<UserDAO>> violations = validator.validate(userDAO);
    Assert.assertEquals(1, violations.size());*/
  }


  public ConstraintValidatorContext constraintValidatorContext() {

    return new ConstraintValidatorContext() {
      @Override
      public void disableDefaultConstraintViolation() {

      }

      @Override
      public String getDefaultConstraintMessageTemplate() {
        return null;
      }

      @Override
      public ClockProvider getClockProvider() {
        return null;
      }

      @Override
      public ConstraintViolationBuilder buildConstraintViolationWithTemplate(String messageTemplate) {
        return null;
      }

      @Override
      public <T> T unwrap(Class<T> type) {
        return null;
      }
    };
  }
}
