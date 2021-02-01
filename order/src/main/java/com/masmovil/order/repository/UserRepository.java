package com.masmovil.order.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.masmovil.order.model.User;

public interface UserRepository extends MongoRepository<User, String>{

}
