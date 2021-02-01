package com.masmovil.phone.controller;

import java.util.List;

import javax.management.InstanceNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.masmovil.phone.model.Phone;
import com.masmovil.phone.service.PhoneService;

@RestController
@RequestMapping("/phone")
public class PhoneController {

	@Autowired
	private PhoneService phoneService;
	
	@GetMapping 
	public List<Phone> getAllPhones (){
		return phoneService.getPhones();
	}
	
	@GetMapping ("/{phone-id}")
	public Phone getPhoneData (@PathVariable ("phone-id") String phoneId) throws InstanceNotFoundException {
		return phoneService.getPhone(phoneId);
	} 
	
	
}
