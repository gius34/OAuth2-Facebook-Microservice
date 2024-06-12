package it.uniba.dib.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginController {
	
	@GetMapping("/login")
	public String login() {
		// restituisce il nome della vista per la pagina login
		return "login";
	}

}
