package com.example.gsontojackson.Repository;

import com.example.gsontojackson.Domain.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends MongoRepository <User, String>{}
