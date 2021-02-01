package com.masmovil.phone.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.masmovil.phone.model.Phone;

public interface PhoneRepository extends MongoRepository<Phone, String>{

	
	
}
