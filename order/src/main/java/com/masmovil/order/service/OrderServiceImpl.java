package com.masmovil.order.service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.management.InstanceAlreadyExistsException;
import javax.management.InstanceNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.masmovil.order.model.Order;
import com.masmovil.order.model.User;
import com.masmovil.order.repository.OrderRepository;
import com.masmovil.order.service.model.Phone;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class OrderServiceImpl implements OrderService {

	@Autowired
	private OrderRepository orderRepository;

	@Autowired
	private PhoneService phoneService;

	@Autowired
	private UserService userService;

	@Override
	public Order saveOrder(String userId, List<String> phoneList) {

		Double prizes = phoneList.stream().map(phone -> {
			try {
				return this.phoneService.getPhoneData(phone).getPrice();
			} catch (InstanceNotFoundException e) {
				return 0d;
			}
		}).reduce(0d, (a, b) -> a + b);

		Order buildOrder = new Order(userId, phoneList, prizes);

		return orderRepository.save(buildOrder);

	}

	@Override
	public Map<String, String> validateOrder(List<String> phones, String email, String userName, String surname) {

		Map<String, String> errors = phones.stream().map(phoneId -> {
			try {
				log.info("check phone: {}", phoneId);
				this.phoneService.getPhoneData(phoneId);
				return null;
			} catch (Exception e) {
				return phoneId;
			}
		}).filter(p -> p != null).collect(Collectors.toMap(p -> p, p -> "invalid.phone"));

		try {
			log.info("check user: {}", email);
			User user = this.userService.getUser(email);
			if (user.getName().compareTo(userName) != 0 || user.getSurname().compareTo(surname) != 0) {
				errors.put("user", "invalid.user");
			}
		} catch (InstanceNotFoundException e) {
			User user = new User(email, userName, surname);
			try {
				log.info("save user: {}", user);
				this.userService.createUser(user);
			} catch (InstanceAlreadyExistsException e1) {
				log.error("Unexecpted conflict error");

			}

		}

		return errors;
	}

}
