package com.api.digital.security.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.api.digital.security.dto.DeviceDataTransferObject;

@RestControllerAdvice
public class GlobalExceptionHandler {
	
	@ExceptionHandler(IdNotFoudException.class)
	public ResponseEntity<String> handleIdNotFoundException(IdNotFoudException ex){
		System.out.println("IdNotException interceptada: " + ex.getMessage());
		
		//DeviceDataTransferObject errorResponse = new DeviceDataTransferObject();
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
	}
	
	@ExceptionHandler(Exception.class)
	public ResponseEntity<String> handleGeneralException(Exception ex){
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro interno do servidor");
	}
}