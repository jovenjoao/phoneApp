package com.masmovil.user.model.advice;

import java.util.HashMap;
import java.util.Map;

import javax.management.InstanceAlreadyExistsException;
import javax.management.InstanceNotFoundException;
import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import lombok.extern.slf4j.Slf4j;

@ControllerAdvice
@Slf4j
public class ErrorHandler {

	@ExceptionHandler(value = Exception.class)
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	@ResponseBody
	public Map<String,String> generalExceptions(HttpServletRequest request, Exception e) {
		log.error("Exception: {}", e.getMessage());
		Map<String,String> bodyMap = new HashMap<>();
		bodyMap.put("message", "internal.error");
		
		return bodyMap;
	}
	
	
	@ExceptionHandler(value = InstanceAlreadyExistsException.class)
	@ResponseStatus(HttpStatus.CONFLICT)
	@ResponseBody
	public Map<String,String> duplicateField(HttpServletRequest request, Exception e) {
		log.error("Exception: {}", e);
		Map<String,String> bodyMap = new HashMap<>();
		bodyMap.put("message", "duplicate.user");
		
		return bodyMap;
	}
	
	@ExceptionHandler(value = InstanceNotFoundException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	@ResponseBody
	public Map<String,String> notFoundExceptions(HttpServletRequest request, Exception e) {
		log.error("Exception: {}", e.getMessage());
		e.printStackTrace();
		Map<String,String> bodyMap = new HashMap<>();
		bodyMap.put("message", "object.not.found");
		
		return bodyMap;
	}
	
}
