package com.nomadacode.s202_download_server;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.UUID;

import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DownloadApi {

	@GetMapping("/download/{filename}")
	public ResponseEntity<Resource> download(@PathVariable("filename") String filename) {
		
		System.out.println("Descargando el archivo: " + filename);
		
		// TODO: 1. Abrir el archivo con ese nombre
		
		File file = new File("D:\\tmp\\" + filename);
		
		FileInputStream fileInputStream;
		
		try {
			fileInputStream = new FileInputStream(file);
		} catch (Exception e) {
			String error = "No se encuentró el archivo 🤡🤡🤡";
			
			byte[] bytes = error.getBytes();
			
			InputStream inputStream = new ByteArrayInputStream(bytes);
			
			InputStreamResource resource = new InputStreamResource(inputStream);
			
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.header("content-disposition", "attachment; filename=error.txt")
					.header("content-type", "application/octet-stream")
					.contentLength(bytes.length)
					.body(resource);
		}
		
		// TODO: 2. Convertir el archivo en recurso
		
		InputStreamResource resource = new InputStreamResource(fileInputStream);
		
		// TODO: 3. Devolver el recurso
		
		String basename = filename.substring(UUID.randomUUID().toString().length() + 1);
		
		return ResponseEntity.ok()
				.header("content-disposition", "attachment; filename=" + basename)
				.header("content-type", "application/octet-stream")
				.contentLength(file.length())
				.body(resource);
		
	}
	
}
