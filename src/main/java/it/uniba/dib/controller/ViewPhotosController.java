package it.uniba.dib.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ViewPhotosController {

	@GetMapping("/viewphotos")
    public String viewPhotos(Model model) {
		// restituisce il nome della vista per la pagina view-photos
        return "view-photos";
    }
}
