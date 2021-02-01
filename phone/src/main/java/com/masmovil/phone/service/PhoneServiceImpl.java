package com.masmovil.phone.service;

import java.util.List;

import javax.management.InstanceNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.masmovil.phone.model.Phone;
import com.masmovil.phone.repository.PhoneRepository;

@Service
public class PhoneServiceImpl implements PhoneService{
	
	@Autowired
	private PhoneRepository phoneRepository;
	
	public List<Phone> getPhones (){
		return phoneRepository.findAll ();
	}

	public Phone getPhone (String phoneId) throws InstanceNotFoundException {
		return phoneRepository.findById (phoneId).orElseThrow(() ->new InstanceNotFoundException ());
	}

}
