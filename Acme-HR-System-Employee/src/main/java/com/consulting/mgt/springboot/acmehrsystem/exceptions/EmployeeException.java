package com.consulting.mgt.springboot.acmehrsystem.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST)
public class EmployeeException extends RuntimeException {

	private static final long serialVersionUID = 8632362940032329348L;

	public EmployeeException(String message) {
		super(message);
	}

}
