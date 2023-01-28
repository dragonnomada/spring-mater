package com.nomadacode.s204_hibernate_mysql;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FrutaApi {

	@Autowired
	FrutaService frutaService;
	
	@GetMapping("/api/frutas")
	public Iterable<Fruta> getAllFrutas() {
		
		return frutaService.frutaRepository.findAll();
		
	}
	
	@PutMapping("/api/frutas/nueva")
	public Fruta createFruta(@RequestBody Fruta fruta) {
		
		return frutaService.frutaRepository.save(fruta);
		
	}
	
	@GetMapping("/api/frutas/{id}")
	public Optional<Fruta> getFrutaById(@PathVariable("id") int id) {
		
		return frutaService.frutaRepository.findById(id);
		
	}
	
	@GetMapping("/api/frutas/precio")
	public Iterable<Fruta> getFrutaByIdWithPrecio(
			@RequestParam(name = "min", defaultValue = "0") double minPrecio
	) {
		
		return frutaService.frutaRepository.getByPrecioGreatherThan(minPrecio);
		
	}
	
}
