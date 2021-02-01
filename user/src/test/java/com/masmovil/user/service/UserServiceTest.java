package com.masmovil.user.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import java.util.Optional;

import javax.management.InstanceAlreadyExistsException;
import javax.management.InstanceNotFoundException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.test.util.ReflectionTestUtils;

import com.masmovil.user.model.User;
import com.masmovil.user.repository.UserRepository;

public class UserServiceTest {

	private UserService userService;
	private UserRepository userRepository;
	
	@BeforeEach
	public void prepareTest () {
		userService = new UserServiceImpl();
		userRepository = Mockito.mock(UserRepository.class);
		ReflectionTestUtils.setField(userService, "userRepository", userRepository);
		
	}
	
	@Test 
	public void getUser_whenUserExists_shouldReturnUser () throws InstanceNotFoundException {
		User u = new User ();
		
		Mockito.when(userRepository.findById("a@a.es")).thenReturn(Optional.of(u));
		
		User result = this.userService.getUser("a@a.es");
		assertEquals(u, result);
	} 
	
	
	@Test 
	public void getUser_whenUserNotExists_shouldReturnException () throws InstanceNotFoundException {
		User u = new User ();
		
		Mockito.when(userRepository.findById("a@a.es")).thenReturn(Optional.empty());
		
		try {
			this.userService.getUser("a@a.es");
			fail();
		}catch (InstanceNotFoundException e) {
		
		}
	} 
	
	@Test 
	public void saveUser_whenUserNotExists_shouldSaveUser () throws InstanceAlreadyExistsException {
		
		User u = new User ();
		u.setEmail("a@a.es");
		
		Mockito.when(userRepository.findById("a@a.es")).thenReturn(Optional.empty());
		
		this.userService.saveUser(u);
		
		Mockito.verify(userRepository).save(u);
		
	}
	
	
	@Test 
	public void saveUser_whenUserExists_shouldThrowException () throws InstanceAlreadyExistsException {

		User u = new User ();
		u.setEmail("a@a.es");
		
		Mockito.when(userRepository.findById("a@a.es")).thenReturn(Optional.of(u));
		
		try {
			this.userService.saveUser(u);
			fail();
		}catch (InstanceAlreadyExistsException e) {
		
		}
	
	}

	
}
