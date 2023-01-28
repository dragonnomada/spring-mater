package com.nomadacode.p203_reporte_texto_guia;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
public class ReporteApi {

	@PostMapping("/reporte")
	public ResponseEntity<Resource> reportar(@RequestParam("file") MultipartFile file) {
		
		Scanner scanner;
		
		try {
			scanner = new Scanner(file.getInputStream());
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
		
		String html = "";
		
		html += "<ul>";
		
		while (scanner.hasNextLine()) {
			
			String lineNombre = scanner.nextLine();
			
			if (lineNombre.isEmpty() || lineNombre.isBlank()) {
				continue;
			}
			
			String linePrecio = scanner.nextLine();
			String lineCantidad = scanner.nextLine();
			
			Matcher matcherNombre = Pattern.compile("Fruta: (\\w+)").matcher(lineNombre);
			
			String nombre = "SIN NOMBRE";
			
			if (matcherNombre.find()) {
				nombre = matcherNombre.group(1);
			}
			
			Matcher matcherPrecio = Pattern.compile("Precio: \\$(\\d+\\.?\\d*)").matcher(linePrecio);
			
			double precio = 0;
			
			if (matcherPrecio.find()) {
				precio = Double.parseDouble(matcherPrecio.group(1));
			}
			
			Matcher matcherCantidad = Pattern.compile("Cantidad: (\\d+\\.?\\d*)").matcher(lineCantidad);
			
			double cantidad = 0;
			
			if (matcherCantidad.find()) {
				cantidad = Double.parseDouble(matcherCantidad.group(1));
			}
			
			System.out.println(nombre);
			System.out.println(precio);
			System.out.println(cantidad);
			
			html += "<li>";
			html += String.format("<strong style='color:" + (precio > 30 ? "red" : "green") + ";'>%s</strong> "
					+ "<em>%.2f</em> "
					+ "<button>%.2f</button>", nombre, precio, cantidad);
			html += "</li>";
			
			//System.out.printf("%s [$%.2f MXN] (%.2f KG) %n", nombre, precio, cantidad);
			
		}
		
		html += "</ul>";
		
		System.out.println(html);
		
		byte[] bytes = html.getBytes();
		
		InputStream inputStream = new ByteArrayInputStream(bytes);
		
		InputStreamResource resource = new InputStreamResource(inputStream);
		
		return ResponseEntity.ok()
				.header("content-disposition", "attachment; filename=report.html")
				.header("content-type", "application/octect-stream")
				.contentLength(bytes.length)
				.body(resource);
		
	}
	
}
