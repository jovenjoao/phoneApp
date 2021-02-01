package com.masmovil.user.service;

import java.util.Optional;

import javax.management.InstanceAlreadyExistsException;
import javax.management.InstanceNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.masmovil.user.model.User;
import com.masmovil.user.repository.UserRepository;

@Service
public class UserServiceImpl implements UserService{

	@Autowired
	private UserRepository userRepository;
	
	@Override
	public User getUser(String mail) throws InstanceNotFoundException {
		return userRepository.findById(mail).orElseThrow(()-> new InstanceNotFoundException());
		
	}

	@Override
	public User saveUser(User user) throws InstanceAlreadyExistsException {
		Optional<User> userOptional = userRepository.findById(user.getEmail());
		
		if (userOptional.isPresent()) {
			throw new InstanceAlreadyExistsException ();
		}else {
			return userRepository.save(user);
		}
	}


}
