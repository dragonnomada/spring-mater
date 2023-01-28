package com.nomadacode.s204_hibernate_mysql;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FrutaService {

	@Autowired
	FrutaRepository frutaRepository;
	
}
