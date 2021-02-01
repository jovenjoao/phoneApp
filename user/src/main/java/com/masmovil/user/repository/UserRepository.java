package com.masmovil.user.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.masmovil.user.model.User;

public interface UserRepository extends MongoRepository<User, String>{

}
