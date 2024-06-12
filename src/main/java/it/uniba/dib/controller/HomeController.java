package it.uniba.dib.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HomeController {

	@RequestMapping("/home")
	public String home() {
		// Restituisce il nome della vista per la pagina home
		return "home";
	}
	
	@GetMapping("/logout")
	public String logoutPage() {
		// Reindirizzo l'utente all'URL '/'
		return "redirect:/";
	}
	
}
