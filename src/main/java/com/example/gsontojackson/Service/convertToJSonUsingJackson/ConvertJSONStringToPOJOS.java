package com.example.gsontojackson.Service.convertToJSonUsingJackson;

import com.example.gsontojackson.DAO.UserDAO;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.logging.Logger;

public class ConvertJSONStringToPOJOS {

  public static Logger logger = Logger.getLogger(ConvertJSONStringToPOJOS.class.getName());

  public static UserDAO doConversion(String jSONString) {
    logger.info("Converting JSONString {" + jSONString +"} to POJOS");
    ObjectMapper objectMapper = new ObjectMapper();
    UserDAO userDAO = null;
    try {
      userDAO = objectMapper.readValue(jSONString, UserDAO.class);
    } catch (IOException e) {
      e.printStackTrace();
    }
    return userDAO;
  }

}
