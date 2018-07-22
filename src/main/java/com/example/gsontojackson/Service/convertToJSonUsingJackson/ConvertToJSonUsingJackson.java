package com.example.gsontojackson.Service.convertToJSonUsingJackson;

import com.example.gsontojackson.Domain.User;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.util.JSONWrappedObject;

import java.util.Objects;
import java.util.logging.Logger;

public class ConvertToJSonUsingJackson {

  public static Logger logger = Logger.getLogger(ConvertToJSonUsingJackson.class.getName());

  public static String doConversion(User userValue) {
    User user = Objects.requireNonNull(userValue);
    logger.info("Converting POJOs into JSON");

    String jsonInString = null;
    ObjectMapper mapper = new ObjectMapper();
    try {
      jsonInString = mapper.writeValueAsString(user);
    } catch (JsonProcessingException e) {
      e.printStackTrace();
    }
    return jsonInString;
  }
}
