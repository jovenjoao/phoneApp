package com.masmovil.phone.model;

import java.util.List;

import org.springframework.data.annotation.Id;

import lombok.Data;

@Data
public class Phone {

	@Id
	private String id;
	
	private List<String> images;
	
	private String name;
	private String description;
	private Double price;
	
}


