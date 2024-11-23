package com.api.digital.security.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class IdNotFoudException extends RuntimeException {
	
	public IdNotFoudException() {
		super("Dispositivo/Vulnerabilidade não existe ou foi excluido!");
	}
	
	public IdNotFoudException(String message) {
		super(message);
	}

}
