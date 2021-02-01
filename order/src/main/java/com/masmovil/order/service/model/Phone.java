package com.masmovil.order.service.model;

import java.math.BigDecimal;
import java.util.List;

import lombok.Data;

@Data
public class Phone {

	private String id;
	
	private List<String> images;
	
	private String name;
	private String description;
	private Double price;
	
}
