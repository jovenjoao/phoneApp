package com.masmovil.phone.controller;

import javax.management.InstanceNotFoundException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.masmovil.phone.model.Phone;
import com.masmovil.phone.service.PhoneService;

public class PhoneControllerTest {

	private PhoneController phoneController;
	
	private PhoneService phoneService;
	
	@BeforeEach
	public void prepareTest () {
		phoneController = new PhoneController();
		phoneService = Mockito.mock(PhoneService.class);
		ReflectionTestUtils.setField(phoneController, "phoneService", phoneService);
	}

	
	@Test 
	public void getAllPhonesTest (){
		phoneController.getAllPhones();
		Mockito.verify(phoneService).getPhones();
	}
	
	@Test 
	public void getPhoneDataTest () throws InstanceNotFoundException {
		phoneController.getPhoneData("P1");
		Mockito.verify(phoneService).getPhone("P1");
	}
	

	
}
