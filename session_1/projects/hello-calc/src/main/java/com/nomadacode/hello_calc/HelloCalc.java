package com.nomadacode.hello_calc;

import java.util.ArrayList;
import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloCalc {

	String display = "[0]";
	List<Integer> digits = new ArrayList<Integer>();
	
	void updateDisplay() {
		display = "";
		
		if (digits.size() == 0) {
			display = "[0]";
		}
		
		for (int digit : digits) {
			display = display + "[" + digit + "]";
		}
	}
	
	@GetMapping("/api/calc/display")
	public String getDisplay() {
		return display;
	}
	
	@PostMapping("/api/calc/push/button/{number}")
	public String pushButton(@PathVariable("number") int digit) {
		if (digit >= 0 && digit <= 9) {
			digits.add(digit);
		}
		
		updateDisplay();
		
		return display;
	}
	
	/*@PostMapping("/api/calc/push/button/0")
	public String pushButton0() {
		digits.add(0);
		
		updateDisplay();
		
		return display;
	}
	
	@PostMapping("/api/calc/push/button/1")
	public String pushButton1() {
		digits.add(1);
		
		updateDisplay();
		
		return display;
	}
	
	@PostMapping("/api/calc/push/button/2")
	public String pushButton2() {
		digits.add(2);
		
		updateDisplay();
		
		return display;
	}*/
	
	/// ... demás botones
	
	@DeleteMapping("/api/calc/push/clear")
	public String pushButtonClear() {
		digits.clear();
		
		updateDisplay();
		
		return display;
	}
	
}
