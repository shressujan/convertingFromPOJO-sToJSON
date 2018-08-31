package com.example.gsontojackson.validation;

import com.example.gsontojackson.DAO.UserDAO;
import com.example.gsontojackson.Domain.User;
import com.example.gsontojackson.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UserConstraintValidator implements ConstraintValidator<ValidUser, UserDAO> {

  private UserRepository userRepository;

  @Autowired
  public UserConstraintValidator(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  @Override
  public void initialize(ValidUser constraintAnnotation) {

  }

  @Override
  public boolean isValid(UserDAO userDAO, ConstraintValidatorContext context) {
    boolean valid = true;
    valid &= validateEmail(userDAO, context);
    valid &= validatePassword(userDAO, context);
    valid &= validateDoesntExistYet(userDAO, context);

    return valid;
  }

  private boolean validateEmail(UserDAO userDAO, ConstraintValidatorContext context) {
    String emailPattern ="^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?(?:\\.[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?)*$";
    Pattern pattern = Pattern.compile(emailPattern);
    Matcher matcher = pattern.matcher(userDAO.getEmail());
    boolean valid = matcher.matches();
    if(!valid) {
      context.buildConstraintViolationWithTemplate("Invalid Email");
    }
    return valid;
  }

  private boolean validatePassword(UserDAO userDAO, ConstraintValidatorContext context) {
    String passwordPattern = "(?=.*[A-Z])(?=.*[a-z])(?=.*[\\d])(?=.*[,~,!,@,#,$,%,^,&,*,(,),-,_,=,+,[,{,],},|,;,:,<,>,/,?])(?!.*\\s).{8,}";
    Pattern pattern = Pattern.compile(passwordPattern);
    Matcher matcher = pattern.matcher(userDAO.getPassword());

    boolean valid = matcher.matches();
    if(!valid) {
      context.buildConstraintViolationWithTemplate("Invalid Password");
    }
    return valid;
  }

  private boolean validateDoesntExistYet(UserDAO userDAO, ConstraintValidatorContext context) {
    boolean valid = true;
    String id = userDAO.getEmail();
    Optional<User> optionalUserDAO = userRepository.findById(id);
    if(optionalUserDAO.isPresent()) {
      valid = false;
      context.buildConstraintViolationWithTemplate("Already exists");
    }
    return valid;
  }
}
