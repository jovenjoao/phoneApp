package com.masmovil.phone.service;

import java.util.List;

import javax.management.InstanceNotFoundException;

import com.masmovil.phone.model.Phone;

public interface PhoneService {

	List<Phone> getPhones ();

	Phone getPhone (String phoneId) throws InstanceNotFoundException;
	
}
	