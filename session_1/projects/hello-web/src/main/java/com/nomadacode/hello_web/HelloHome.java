package com.nomadacode.hello_web;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloHome {

	@GetMapping("/")
	public String home() {
		return "Bienvenido";
	}
	
	@GetMapping("/reporte")
	public int reporte() {
		return Integer.MAX_VALUE;
	}
	
	@GetMapping("/login")
	public String formLogin() {
		return "<form action='/login/procesar' method='POST'>" + "\n" +
				"<input type='text' placeholder='Ej. pepe'><br>" + "\n" +
				"<input type='password' placeholder='Ej. *****'><br>" + "\n" +
				"<button>enviar</button>" + "\n" +
				"</form>";
	}
	
}
