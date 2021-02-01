package com.masmovil.user.controller;

import javax.management.InstanceAlreadyExistsException;
import javax.management.InstanceNotFoundException;
import javax.websocket.server.PathParam;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.MediaType;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.masmovil.user.model.User;
import com.masmovil.user.model.controller.UserController;
import com.masmovil.user.service.UserService;

public class UserControllerTest {

	private UserController userController;
	private UserService userService;

	@BeforeEach
	public void prepareTest () {
		userController = new UserController();
		userService = Mockito.mock(UserService.class);
		ReflectionTestUtils.setField(userController, "userService", userService);
	}
	
	@Test
	public void createUserTest () throws InstanceAlreadyExistsException{
		
		User user = new User ();
		
		this.userController.createUser(user);
		
		Mockito.verify(userService).saveUser(user);
	}
	
	@Test
	public void validateOrderTest () throws InstanceNotFoundException{
		
		this.userController.getUser("a@a.es");
		
		Mockito.verify(userService).getUser("a@a.es");
	}
	
	

	
}
