package com.dnb.accountservice.advice;
import java.io.IOException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.TypeMismatchException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import com.dnb.accountservice.exceptions.DataNotFoundException;
import com.dnb.accountservice.exceptions.IdNotFoundException;
import com.dnb.accountservice.exceptions.InsufficientBalanceException;



@ControllerAdvice
public class AppAdvice {
	
	@ResponseStatus(value = HttpStatus.NOT_FOUND,reason="invalid id provided")
	@ExceptionHandler(IdNotFoundException.class)
	public ResponseEntity<?> idNotFoundExceptionHandler(IdNotFoundException e){
		Map<String, String>map=new HashMap<>();
		map.put("message", "Id not found");
		map.put("HttpStatus", HttpStatus.NOT_FOUND+"");
		return new ResponseEntity(map,HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler(DataNotFoundException.class)
	public ResponseEntity<?> dataNotFoundExceptionHandler(DataNotFoundException e){
		Map<String, String>map=new HashMap<>();
		map.put("message", "data not found");
		map.put("HttpStatus", HttpStatus.NOT_FOUND+"");
		return new ResponseEntity(map,HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler(InsufficientBalanceException.class)
	public ResponseEntity<?> insufficientBalanceExceptionHandler(InsufficientBalanceException e){
		Map<String, String>map=new HashMap<>();
		map.put("message", "Insufficient balance to perform withdrawal");
		map.put("HttpStatus", HttpStatus.BAD_REQUEST+"");
		return new ResponseEntity(map,HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(HttpRequestMethodNotSupportedException.class)
	public ResponseEntity<Map<String,String>> handleException(HttpRequestMethodNotSupportedException e)throws IOException{
		String provided = e.getMethod();
		List<String> supported= List.of(e.getSupportedMethods());
		String error=provided+ " is not one of the HTTP supported methods ("+String.join(", ", supported)+")";
		Map<String, String>errorResponse=Map.of("error",error,
				"message",e.getLocalizedMessage(),
				"status",HttpStatus.METHOD_NOT_ALLOWED.toString()
				);
		return new ResponseEntity<>(errorResponse,HttpStatus.METHOD_NOT_ALLOWED);
	}
	
	@ExceptionHandler({MethodArgumentTypeMismatchException.class,
		TypeMismatchException.class})
	public ResponseEntity<Map<String,String>>handleException(TypeMismatchException e){
		Map<String, String>map=Map.of(
		"message", e.getLocalizedMessage(),
		"HttpStatus", HttpStatus.BAD_REQUEST.toString());
		return new ResponseEntity<>(map,HttpStatus.BAD_REQUEST);
	}
}
	