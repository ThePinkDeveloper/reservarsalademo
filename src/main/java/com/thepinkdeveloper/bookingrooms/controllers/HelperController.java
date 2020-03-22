package com.thepinkdeveloper.bookingrooms.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.thepinkdeveloper.bookingrooms.services.impl.HelperServiceImpl;

@RestController
@RequestMapping("/init")
public class HelperController {
	
	@Autowired
	@Qualifier("helperServiceImpl")
	private HelperServiceImpl helperServiceImpl;

	@RequestMapping("/application")
	public void generateDatainDB() {
		helperServiceImpl.createInitialRooms();
		helperServiceImpl.createInitialBooks();
	}
}
