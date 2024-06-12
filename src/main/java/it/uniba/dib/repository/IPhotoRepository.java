package it.uniba.dib.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import it.uniba.dib.model.Photo;

@Repository
public interface IPhotoRepository extends CrudRepository<Photo, Integer>{

}
