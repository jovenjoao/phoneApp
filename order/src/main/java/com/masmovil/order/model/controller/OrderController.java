package com.masmovil.order.model.controller;

import java.nio.charset.Charset;
import java.util.Map;

import javax.management.InstanceAlreadyExistsException;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestClientResponseException;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.masmovil.order.model.Order;
import com.masmovil.order.model.controller.request.RequestOrder;
import com.masmovil.order.service.OrderService;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/order")
@Slf4j
public class OrderController {

	@Autowired
	private OrderService orderService;

	private ObjectMapper objectMapper = new ObjectMapper();

	@PostMapping(consumes = { MediaType.APPLICATION_JSON_VALUE })
	public Order saveOrder(@Valid @RequestBody RequestOrder requestOrder)
			throws InstanceAlreadyExistsException, JsonProcessingException {

		Map<String, String> wrongsPhone = orderService.validateOrder(requestOrder.getPhones(), requestOrder.getEmail(),
				requestOrder.getName(), requestOrder.getSurname());
		
		
		if (wrongsPhone.isEmpty()) {
			Order order = this.orderService.saveOrder(requestOrder.getEmail(), requestOrder.getPhones());
			log.info("view Order: {} ",objectMapper.writeValueAsString(order));
			return order;
		} else {
			log.info ("WRONG ORDER {}",wrongsPhone);
			throw buildOrderException(wrongsPhone);
		}

	}

	@PostMapping("/check")
	@ResponseStatus(HttpStatus.ACCEPTED)
	public void validateOrder(@Valid @RequestBody RequestOrder requestOrder) throws JsonProcessingException {

		Map<String, String> errors = orderService.validateOrder(requestOrder.getPhones(), requestOrder.getEmail(),
				requestOrder.getName(), requestOrder.getSurname());

		if (!errors.isEmpty()) {
			log.info ("WRONG ORDER {}",errors);
			throw buildOrderException(errors);
		}

	}

	private RestClientResponseException buildOrderException(Map<String, String> errors) throws JsonProcessingException {
		HttpHeaders responseHeaders = new HttpHeaders();
		responseHeaders.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON.getType());
		return new RestClientResponseException("Invalid Order", HttpStatus.BAD_REQUEST.value(),
				HttpStatus.BAD_REQUEST.getReasonPhrase(), responseHeaders,
				objectMapper.writeValueAsString(errors).getBytes(), Charset.defaultCharset());
	}

}
