package com.masmovil.order.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.masmovil.order.model.Order;

public interface OrderRepository extends MongoRepository<Order, String>{

}
