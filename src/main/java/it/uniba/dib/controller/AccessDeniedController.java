package it.uniba.dib.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class AccessDeniedController {

	@GetMapping("/access-denied")
	public String accessDenied() {
		// Restituisce il nome della vista per la pagina access-denied
		return "access-denied";
	}
	
	@PostMapping("/access-denied")
	public String accessDeniedPost() {
		// Restituisce il nome della vista per la pagina access-denied
		return "access-denied";
	}
}
