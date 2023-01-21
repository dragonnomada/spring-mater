package com.nomadacode.hello_persona;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PersonaApi {

	PersonaService servicio = new PersonaService();
	
	@GetMapping("/api/personas")
	public List<PersonaEntity> getPersonas() {
		return servicio.getPersonas();
	}
	
	
	@PutMapping("/api/personas/agregar")
	public PersonaEntity addPersona(@RequestBody PersonaEntity persona) {
		
		PersonaEntity personaCreada = servicio.addPersona(persona.nombre, persona.apellidoPaterno, persona.apellidoMaterno);
		
		servicio.editPersonaEdad(personaCreada.getId(), persona.edad);
		servicio.editPersonaPeso(personaCreada.getId(), persona.peso);
		
		return servicio.getPersonaById(personaCreada.getId());
	}
	
}
