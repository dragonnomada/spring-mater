package com.nomadacode.s200_repaso_rest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
//import org.springframework.context.annotation.PropertySource;
//import org.springframework.context.annotation.PropertySources;

@SpringBootApplication
/*@PropertySources(value = {
@PropertySource(ignoreResourceNotFound = true, value = "classpath:resources/application.properties")
})*/
public class S200RepasoRestApplication {

	public static void main(String[] args) {
		SpringApplication.run(S200RepasoRestApplication.class, args);
	}

}
