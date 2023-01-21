package com.nomadacode.hello_web;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/// Este es el punto de entrada 
/// para una aplicación de Java Spring Web

/// El proyecto automáticamente detectará una clase de
/// @SpringBootApplication
/// por lo que lo ejecutará en modo Spring Boot
@SpringBootApplication
public class HelloWebApplication {

	/// El método de entrada ejecutará nuestra clase principal
	public static void main(String[] args) {
		/// Ejecuta nuestra clase como una aplicación de Spring
		SpringApplication.run(HelloWebApplication.class, args);
	}

}
