package com.nomadacode.hello_persona;

import java.util.ArrayList;
import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PersonaApi {

	List<PersonaEntity> personas = new ArrayList<>();
	
	/// CONSULTAR
	
	@GetMapping("/api/personas")
	public List<PersonaEntity> getPersonas() {
		return personas;
	}
	
	@GetMapping("/api/personas/porNombre/{nombre}")
	public PersonaEntity getPersonaPorNombre(@PathVariable("nombre") String nombre) {
		
		PersonaEntity personaEncontrada = null;
		
		for (PersonaEntity persona : personas) {
			if (persona.getNombre().indexOf(nombre) >= 0) {
				personaEncontrada = persona;
			}
		}
		
		return personaEncontrada;
		
	}
	
	/// INSERTAR
	
	@PutMapping("/api/personas/agregar")
	public PersonaEntity createPersona(@RequestBody PersonaEntity persona) {
		persona.setId(personas.size());
		
		personas.add(persona);
		
		return persona;
	}
	
}
