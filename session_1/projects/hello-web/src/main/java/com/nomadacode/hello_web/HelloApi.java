package com.nomadacode.hello_web;

import java.util.ArrayList;
import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloApi {

	List<String> historial = new ArrayList<String>();

	@GetMapping("/historial")
	public String getVistaHistorial() {
		String html = "";
		
		html += "<ul>";
		
		for (String registro : historial) {
			String color = "red";
			
			if (registro.endsWith("ENTRA")) {
				color = "green";
			}
			
			html += String.format("<li style='color:%s;'>%s</li>", color, registro);
		}
		
		html += "</ul>";
		
		return html;
	}
	
	@GetMapping("/api/historial")
	public List<String> getHistorial() {
		return historial;
	}
	
	@GetMapping("/api/entrada")
	public String doEntrada() {
		historial.add("EMPLEADO ENTRA");
		return "ok [entra]";
	}
	
	@GetMapping("/api/salida")
	public String doSalida() {
		historial.add("EMPLEADO SALE");
		return "ok [sale]";
	}
	
}
