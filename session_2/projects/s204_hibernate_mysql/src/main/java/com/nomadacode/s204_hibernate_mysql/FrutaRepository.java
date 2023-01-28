package com.nomadacode.s204_hibernate_mysql;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import jakarta.transaction.Transactional;

@Transactional
public interface FrutaRepository extends CrudRepository<Fruta, Integer> {

	// Crear queries personalizados
	@Query(value = "SELECT * FROM frutas WHERE price >= :minPrice", nativeQuery = true)
	public Iterable<Fruta> getByPrecioGreatherThan(@Param("minPrice") double minPrice);
	
}
