package com.masmovil.user.service;

import javax.management.InstanceAlreadyExistsException;
import javax.management.InstanceNotFoundException;

import com.masmovil.user.model.User;

public interface UserService {

	User getUser (String mail) throws InstanceNotFoundException;
	
	User saveUser (User user) throws InstanceAlreadyExistsException;
	
}
