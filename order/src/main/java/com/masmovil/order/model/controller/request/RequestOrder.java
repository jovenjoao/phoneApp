package com.masmovil.order.model.controller.request;

import java.util.List;

import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RequestOrder {

	
	@NotNull
	private String name;
	@NotNull
	private String surname;
	@NotNull
	private String email;
	@NotNull
	private List<String> phones; 
	
}
