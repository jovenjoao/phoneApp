package com.masmovil.user.model.controller;

import javax.management.InstanceAlreadyExistsException;
import javax.management.InstanceNotFoundException;
import javax.websocket.server.PathParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.masmovil.user.model.User;
import com.masmovil.user.service.UserService;

@RestController
@RequestMapping("/user")
public class UserController {

	@Autowired
	private UserService userService;

	
	@PostMapping 
	public User createUser(@RequestBody User user) throws InstanceAlreadyExistsException {

		return userService.saveUser(user);

	}

	@GetMapping("/{email}")
	public User getUser(@PathVariable("email") String userEmail) throws InstanceNotFoundException {

		return userService.getUser(userEmail);
		
	}


	
}
