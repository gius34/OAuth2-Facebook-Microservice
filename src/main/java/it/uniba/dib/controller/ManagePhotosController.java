package it.uniba.dib.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ManagePhotosController {

	@GetMapping("/managephotos")
    public String managePhotos() {
		// restituisce il nome della vista per la pagina manage-photos
        return "manage-photos";
    }
}
