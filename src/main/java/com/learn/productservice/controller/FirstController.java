package com.learn.productservice.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FirstController {
	
	@GetMapping("/")
	public String  home() {
		return "Welcome to Spring Boot Application";
	}

	 
}
