package com.nomadacode.hello_persona;

import java.util.ArrayList;
import java.util.List;

public class PersonaService {

	private List<PersonaEntity> personas = new ArrayList<>();
	
	public List<PersonaEntity> getPersonas() {
		return personas;
	}
	
	public PersonaEntity getPersonaById(int id) {
		PersonaEntity personaEncontrada = null;
		
		for (PersonaEntity persona : personas) {
			if (persona.getId() == id) {
				personaEncontrada = persona;
			}
		}
		
		return personaEncontrada;
	}
	
	public PersonaEntity addPersona(String nombre, String apellidoPaterno, String apellidoMaterno) {
		
		PersonaEntity persona = new PersonaEntity();
		
		persona.setId(personas.size() + 1);
		
		persona.setNombre(nombre);
		persona.setApellidoPaterno(apellidoPaterno);
		persona.setApellidoMaterno(apellidoMaterno);
		
		personas.add(persona);
		
		return persona;
		
	}
	
	public PersonaEntity editPersonaEdad(int id, int edad) {
		PersonaEntity persona = getPersonaById(id);
		
		if (persona != null) {
			persona.setEdad(edad);
		}
		
		return persona;
	}
	
	public PersonaEntity editPersonaPeso(int id, double peso) {
		PersonaEntity persona = getPersonaById(id);
		
		if (persona != null) {
			persona.setPeso(peso);
		}
		
		return persona;
	}
	
	public PersonaEntity editPersonaCasar(int id) {
		PersonaEntity persona = getPersonaById(id);
		
		if (persona != null) {
			persona.setCasado(true);
		}
		
		return persona;
	}
	
	public PersonaEntity editPersonaDivorciar(int id) {
		PersonaEntity persona = getPersonaById(id);
		
		if (persona != null) {
			persona.setCasado(false);
		}
		
		return persona;
	}
	
}
