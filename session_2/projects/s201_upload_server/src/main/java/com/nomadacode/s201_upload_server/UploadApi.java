package com.nomadacode.s201_upload_server;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
public class UploadApi {

	int quota = 0;
	int max_quota = 1 * 1024 * 1024 * 40; // 40MB 
	
	@PostMapping("/upload")
	public ResponseEntity<String> upload(
			@RequestParam("file") MultipartFile file, 
			@RequestParam(name = "token", defaultValue = "") String token
	) {
		
		if (!token.equals("abc123")) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
					.body("Acceso denegado: Token no válido"); // UNAUTHORIZED 401 -> String
		}
		
		if (quota > max_quota) {
			return ResponseEntity.status(HttpStatus.INSUFFICIENT_STORAGE)
					.body("El límite de cuota se ha superado (" + quota + ")"); // INSUFFICIENT STORAGE 507 -> String
		}
		
		System.out.printf("Archivo recibido: %s %n", file.getOriginalFilename());
		
		String uuid = UUID.randomUUID().toString();
		
		String filename = uuid + "-" + file.getOriginalFilename();
		
		String path = "D:\\tmp\\" + filename;
		
		System.out.println("El archivo se guardará en la ruta: " + path);
		
		try {
			
			file.transferTo(new File(path));
			
			quota += file.getSize();
			
			return ResponseEntity.ok(filename); // OK 200 -> String
			
		} catch (IOException e) {
			return ResponseEntity.status(HttpStatus.FORBIDDEN)
					.body("No hay acceso al recurso"); // FORBIDDEN 403 -> String
		} catch (Exception e) {
			
			System.out.println("Hubo un error: " + e.getMessage());
			
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body(e.getMessage()); // INTERNAL SERVER ERROR 500 -> String
			
		}
		
	}
	
}
