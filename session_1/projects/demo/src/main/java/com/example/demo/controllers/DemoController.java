package com.example.demo.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.entities.DemoEntity;

@RestController
public class DemoController {

	@GetMapping("/hello")
	public String hello() {
		
		return "Hello world";
		
	}
	
	@GetMapping("/hello/{name}")
	public String hello(@PathVariable("name") String name) {
		return String.format("Hello %s", name);
	}
	
	@GetMapping("/hello/id/{id}")
	public String hello(@PathVariable("id") int id) {
		return String.format("Hello [%04d]", id);
	}
	
	@PostMapping("/hello/add")
	public DemoEntity helloAdd(@RequestBody DemoEntity demo) {
		
		System.out.printf("[%d] %s %B\n", demo.getId(), demo.getMessage(), demo.isActive());
		
		demo.setId(1);
		
		return demo;
		
	}
}
