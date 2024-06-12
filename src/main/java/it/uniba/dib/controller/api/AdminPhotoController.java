package it.uniba.dib.controller.api;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import it.uniba.dib.model.Photo;
import it.uniba.dib.service.IPhotoService;

@RestController
public class AdminPhotoController {

	@Autowired
	@Qualifier("mainPhotoService")
	private IPhotoService photoService;
	
	public AdminPhotoController() {

	}

	@RequestMapping("/api/viewphotos")
	public Iterable<Photo> getAll() {
		
		return photoService.getAll();
	}
	
	@RequestMapping("/api/viewphotos/{id}")
	public Photo getById(@PathVariable int id) {
		
		Optional<Photo> photo = photoService.getById(id);
		
		if(photo.isEmpty()) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "item not found");
		}
		
		return photo.get();
	}
	
	@RequestMapping(value = "/api/managephotos", method= RequestMethod.POST)
	public Photo create(@RequestBody Photo photo) {
		
		return photoService.create(photo);
	}
	
	@RequestMapping(value = "/api/managephotos/{id}", method= RequestMethod.DELETE)
	public void delete(@PathVariable int id) {
		
		Boolean isDelete = photoService.delete(id);
		
		if(!isDelete) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "item not found");
		}

	}
}
