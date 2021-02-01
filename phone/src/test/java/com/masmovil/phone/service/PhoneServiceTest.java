package com.masmovil.phone.service;

import static org.junit.jupiter.api.Assertions.fail;

import java.util.List;
import java.util.Optional;

import javax.management.InstanceNotFoundException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.test.util.ReflectionTestUtils;

import com.masmovil.phone.model.Phone;
import com.masmovil.phone.repository.PhoneRepository;


public class PhoneServiceTest {

	private PhoneService phoneService;
	
	private PhoneRepository phoneRepository;
	
	@BeforeEach
	public void prepareTest () {
		phoneService = new PhoneServiceImpl();
		phoneRepository = Mockito.mock(PhoneRepository.class);
		ReflectionTestUtils.setField(phoneService, "phoneRepository", phoneRepository);
	}
	
	@Test
	public void getPhonesTest () {
		phoneService.getPhones();
		Mockito.verify(phoneRepository).findAll();
	}
	
	@Test
	public void getPhone_whenPhoneExists_shouldReturnPhoneTest () throws InstanceNotFoundException {
		Mockito.when(phoneRepository.findById("P1")).thenReturn(Optional.of(new Phone()));
		phoneService.getPhone("P1");
		Mockito.verify(phoneRepository).findById("P1");
	}
	
	@Test 
	public void getPhone_whenPhoneNotExists_shouldThrowExceptionTest () {
		Mockito.when(phoneRepository.findById("P1")).thenReturn(Optional.empty());
		try {
			phoneService.getPhone("P1");
			fail();
		} catch (InstanceNotFoundException e) {
			Mockito.verify(phoneRepository).findById("P1");	
		}
		
	}
	
	
}
