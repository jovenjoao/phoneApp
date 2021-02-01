package com.masmovil.order.service;

import javax.management.InstanceNotFoundException;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.masmovil.order.service.model.Phone;

@FeignClient(url =  "http://phone:8080", name="phone")
public interface PhoneService {

	@GetMapping ("/phone/{phone-id}")
	Phone getPhoneData (@PathVariable ("phone-id") String phoneId) throws InstanceNotFoundException;
	
	
}
