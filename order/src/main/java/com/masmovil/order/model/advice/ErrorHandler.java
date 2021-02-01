package com.masmovil.order.model.advice;

import java.util.HashMap;
import java.util.Map;

import javax.management.InstanceAlreadyExistsException;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.client.RestClientResponseException;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;

@ControllerAdvice
@Slf4j
public class ErrorHandler {

	@Autowired
	private ObjectMapper objectMapper;
	
	@ExceptionHandler(value = Exception.class)
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	@ResponseBody
	public Map<String,String> generalExceptions(HttpServletRequest request, Exception e) {
		log.error("Exception: {}", e.getMessage());
		e.printStackTrace();
		Map<String,String> bodyMap = new HashMap<>();
		bodyMap.put("message", "internal.error");
		
		return bodyMap;
	}
	
	
	@ExceptionHandler(value = InstanceAlreadyExistsException.class)
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	@ResponseBody
	public Map<String,String> duplicateField(HttpServletRequest request, Exception e) {
		log.error("Exception: {}", e);
		Map<String,String> bodyMap = new HashMap<>();
		bodyMap.put("message", "duplicateField");
		
		return bodyMap;
	}
	
	
	@ExceptionHandler(value = RestClientResponseException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ResponseBody
	public Map<String,String> badRequest(HttpServletRequest request, RestClientResponseException e) throws JsonMappingException, JsonProcessingException {
		log.error("Exception: {}", e);
		Map<String,String> bodyMap = objectMapper.readValue(e.getResponseBodyAsString(), Map.class); 
		
		return bodyMap;
	}
	
	
	@ExceptionHandler(value = MethodArgumentNotValidException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ResponseBody
	public Map<String,String> invalidRquest(HttpServletRequest request, MethodArgumentNotValidException e) throws JsonMappingException, JsonProcessingException {
		log.error("Exception: {}",e);
		Map<String,String> bodyMap = new HashMap<>();
		
		e.getAllErrors().stream().forEach(error -> {
			bodyMap.put(error.getObjectName(), String.join(",", error.getCodes()));
		});
		
		return bodyMap;
	}
	
	
}
