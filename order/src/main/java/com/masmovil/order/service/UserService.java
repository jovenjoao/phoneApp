package com.masmovil.order.service;

import javax.management.InstanceAlreadyExistsException;
import javax.management.InstanceNotFoundException;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.masmovil.order.model.User;

@FeignClient(url ="http://user:8080", name="user")
public interface UserService {

	@PostMapping ("/user")
	public User createUser(@RequestBody User user) throws InstanceAlreadyExistsException;


	@GetMapping("/user/{email}")
	public User getUser(@PathVariable("email") String userEmail) throws InstanceNotFoundException;


}
