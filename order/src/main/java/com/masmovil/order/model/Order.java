package com.masmovil.order.model;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Data
@NoArgsConstructor
@RequiredArgsConstructor
@AllArgsConstructor
public class Order {
	
	@NonNull	
	private String userId;
	@NonNull
	private List<String> phones; 

	private Double prize;
}
