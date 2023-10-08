package com.dnb.accountservice.advice;

import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;


@ControllerAdvice
public class CustomExceptionHandler extends ResponseEntityExceptionHandler {

	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatusCode status, WebRequest request) {
		Map<String,Object> responseBody=new LinkedHashMap<>();
		responseBody.put("timestamp", LocalDateTime.now());
		responseBody.put("status", status.value());
		
		List<String>errorKeys=ex.getBindingResult().getFieldErrors()
				.stream()
				.map(x->x.getField())
				.collect(Collectors.toList());
		
		List<String>errorMessages=ex.getBindingResult().getFieldErrors()
				.stream()
				.map(x->x.getDefaultMessage())
				.collect(Collectors.toList());
		
		Map<String, String>result=IntStream.range(0, errorKeys.size())
		  .boxed()
		  .collect(Collectors.toMap(errorKeys::get, errorMessages::get));
		responseBody.put("errors", result);
		
		
		return new ResponseEntity<>(responseBody,headers,status);
	}
}
