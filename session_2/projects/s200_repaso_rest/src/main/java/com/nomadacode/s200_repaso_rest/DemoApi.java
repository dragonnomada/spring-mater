package com.nomadacode.s200_repaso_rest;

import java.util.ArrayList;
import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DemoApi {

	@GetMapping("/api/demo/test") // GET /api/demo/test -> test() -> String
	public String test(
			@RequestParam(
					name = "nombre",
					required = true, 
					defaultValue = "Anónimo"
			) 
			String nombre, 
			@RequestParam("edad") 
			Integer edad
	) {
		
		System.out.println(nombre);
		
		return String.format("Hola %s, tienes %d años", nombre, edad);
		
	}
	
	@GetMapping("/api/demo/saludar")
	public List<String> saludar(
			@RequestParam(
					name = "mensaje",
					defaultValue = "Sin mensaje"
			)
			String mensaje,
			@RequestParam(
					name = "veces", 
					defaultValue = "1"
			)
			int veces
	) {
		
		List<String> saludos = new ArrayList<>();
		
		for (int i = 1; i <= veces; i++) {
			saludos.add(String.format("Hola, tu mensaje es: %s", mensaje));
		}
		
		return saludos;
		
	}
	
}
