package com.masmovil.order.controller;

import static org.junit.jupiter.api.Assertions.fail;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.management.InstanceAlreadyExistsException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.client.RestClientResponseException;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.masmovil.order.model.controller.OrderController;
import com.masmovil.order.model.controller.request.RequestOrder;
import com.masmovil.order.service.OrderService;

public class OrderControllerTest {

	private OrderController orderController;

	private OrderService orderService;

	@BeforeEach
	public void prepareTest() {
		orderController = new OrderController();
		orderService = Mockito.mock(OrderService.class);
		ReflectionTestUtils.setField(orderController, "orderService", orderService);
	}

	@Test
	public void saveOrderTest_whenValidateIsOk_shouldSaveOrder()
			throws InstanceAlreadyExistsException, JsonProcessingException {

		List<String> phones = new ArrayList<>();
		phones.add("p1");
		phones.add("p2");

		Mockito.when(orderService.validateOrder(phones, "a@a.es", "a", "b")).thenReturn(new HashMap<>());

		orderController.saveOrder(new RequestOrder("a", "b", "a@a.es", phones));

		Mockito.verify(orderService).saveOrder("a@a.es", phones);

	}

	@Test
	public void saveOrderTest_whenValidateIsKO_shouldRaiseException()
			throws InstanceAlreadyExistsException, JsonProcessingException {

		List<String> phones = new ArrayList<>();
		phones.add("p1");
		phones.add("p2");

		Map<String, String> errors = new HashMap<>();
		errors.put("p1", "not.found");

		Mockito.when(orderService.validateOrder(phones, "a@a.es", "a", "b")).thenReturn(errors);

		try {
			orderController.saveOrder(new RequestOrder("a", "b", "a@a.es", phones));
			fail();
		} catch (RestClientResponseException e) {

		} catch (Exception e) {
			fail();
		}

	}

	
	@Test
	public void validateOrder_whenValidateIsOK_shouldDoNothing()
			throws InstanceAlreadyExistsException, JsonProcessingException {

		List<String> phones = new ArrayList<>();
		phones.add("p1");
		phones.add("p2");

		Map<String, String> errors = new HashMap<>();
		errors.put("p1", "not.found");

		Mockito.when(orderService.validateOrder(phones, "a@a.es", "a", "b")).thenReturn(errors);


		try {
			orderController.validateOrder(new RequestOrder("a", "b", "a@a.es", phones));
			fail();
		} catch (RestClientResponseException e) {

		} catch (Exception e) {
			fail();
		}

		Mockito.verify(orderService).validateOrder(phones, "a@a.es", "a", "b");
		
	}
	
	@Test
	public void validateOrder_whenValidateIsKO_shouldThrowException()
			throws InstanceAlreadyExistsException, JsonProcessingException {

		List<String> phones = new ArrayList<>();
		phones.add("p1");
		phones.add("p2");

		Mockito.when(orderService.validateOrder(phones, "a@a.es", "a", "b")).thenReturn(new HashMap<>());

		try {
			orderController.validateOrder(new RequestOrder("a", "b", "a@a.es", phones));
		} catch (RestClientResponseException e) {
			fail();
		} catch (Exception e) {
			fail();
		}

		Mockito.verify(orderService).validateOrder(phones, "a@a.es", "a", "b");
		
	}
	
}
