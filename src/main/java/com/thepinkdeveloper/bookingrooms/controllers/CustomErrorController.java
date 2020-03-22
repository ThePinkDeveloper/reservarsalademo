package com.thepinkdeveloper.bookingrooms.controllers;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CustomErrorController implements ErrorController {
	
	@RequestMapping(value = "/error")
	public String error() {
		return "Se ha producido un error, posiblemente alguno de los parámetros introducidos son incorrectos.";
	}

	@Override
	public String getErrorPath() {
		return "/error";
	}
	
}