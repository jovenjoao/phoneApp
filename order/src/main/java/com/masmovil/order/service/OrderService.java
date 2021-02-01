package com.masmovil.order.service;

import java.util.List;
import java.util.Map;

import com.masmovil.order.model.Order;

public interface OrderService {

	Map<String,String> validateOrder (List<String> phones,String email, String userName, String surname);
	
	Order saveOrder (String userId, List<String> iphone);
	
}
