package com.masmovil.order.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.util.Map;

import javax.management.InstanceAlreadyExistsException;
import javax.management.InstanceNotFoundException;

import org.assertj.core.util.Lists;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.AdditionalAnswers;
import org.mockito.Mockito;
import org.springframework.test.util.ReflectionTestUtils;

import com.masmovil.order.model.Order;
import com.masmovil.order.model.User;
import com.masmovil.order.repository.OrderRepository;
import com.masmovil.order.service.model.Phone;

public class OrderServiceTest {

	private OrderService orderService;
	

	private OrderRepository orderRepository;
	private PhoneService phoneService;
	private UserService userService;

	
	@BeforeEach
	public void prepareTest () {
		orderService = new OrderServiceImpl();
		orderRepository = Mockito.mock(OrderRepository.class);
		phoneService = Mockito.mock(PhoneService.class);
		userService = Mockito.mock(UserService.class);
		ReflectionTestUtils.setField(orderService, "orderRepository", orderRepository);
		ReflectionTestUtils.setField(orderService, "phoneService", phoneService);
		ReflectionTestUtils.setField(orderService, "userService", userService);
	}
	
	@Test
	public void validateOrderTest_existsUserAndAllPhones_shouldReturnEmptyErrors () throws InstanceNotFoundException {
		
		Mockito.when(phoneService.getPhoneData(Mockito.anyString())).thenReturn(new Phone ());
		User user = new User ("a@a.es", "a", "b");
		Mockito.when(userService.getUser("a@a.es")).thenReturn(user);
		
		Map<String,String> errors = orderService.validateOrder(Lists.newArrayList("p1","p2"), "a@a.es", "a", "b");
		assertTrue(errors.isEmpty());
		
	}
	
	@Test
	public void validateOrderTest_existsUserAndSomePhones_shouldReturnEmptyErrors () throws InstanceNotFoundException {
		
		Mockito.when(phoneService.getPhoneData("p1")).thenReturn(new Phone ());
		Mockito.when(phoneService.getPhoneData("p2")).thenThrow(InstanceNotFoundException.class);
		User user = new User ("a@a.es", "a", "b");
		Mockito.when(userService.getUser("a@a.es")).thenReturn(user);
		
		Map<String,String> errors = orderService.validateOrder(Lists.newArrayList("p1","p2"), "a@a.es", "a", "b");
		assertEquals(1, errors.size());
		assertEquals("invalid.phone", errors.get("p2"));
		
	}
	
	@Test
	public void validateOrderTest_existsUserWrongAndAllPhonesExists_shouldReturnEmptyErrors () throws InstanceNotFoundException {
		
		Mockito.when(phoneService.getPhoneData(Mockito.anyString())).thenReturn(new Phone ());
		User user = new User ("a@a.es", "a", "c");
		Mockito.when(userService.getUser("a@a.es")).thenReturn(user);
		
		Map<String,String> errors = orderService.validateOrder(Lists.newArrayList("p1","p2"), "a@a.es", "a", "b");
		assertEquals(1, errors.size());
		assertEquals("invalid.user", errors.get("user"));
		
	}
	
	@Test
	public void validateOrderTest_existsNotUserAndAllPhonesExists_shouldReturnEmptyErrors () throws InstanceNotFoundException, InstanceAlreadyExistsException {
		
		Mockito.when(phoneService.getPhoneData(Mockito.anyString())).thenReturn(new Phone ());
		Mockito.when(userService.getUser("a@a.es")).thenThrow(InstanceNotFoundException.class);
		
		Map<String,String> errors = orderService.validateOrder(Lists.newArrayList("p1","p2"), "a@a.es", "a", "b");
		assertEquals(0, errors.size());
		Mockito.verify(userService).createUser(Mockito.any());
		
	}
	
	@Test
	public void saveOrderTest () throws InstanceNotFoundException {
		
		Phone p1 = new Phone ();
		p1.setPrice(5d);
		Mockito.when(this.phoneService.getPhoneData("p1")).thenReturn(p1);
		
		Phone p2 = new Phone ();
		p2.setPrice(2d);
		Mockito.when(this.phoneService.getPhoneData("p2")).thenReturn(p2);
		
		Mockito.when(orderRepository.save(Mockito.any())).thenAnswer(AdditionalAnswers.returnsFirstArg());
		
		Order order = this.orderService.saveOrder("a@a.es", Lists.newArrayList("p1","p2"));
		
		assertEquals(7d, order.getPrize());
		Mockito.verify(orderRepository).save(Mockito.any());
		
		
	}
	
}
