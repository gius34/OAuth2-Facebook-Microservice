package it.uniba.dib.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import it.uniba.dib.model.Customer;

@Repository
public interface CustomerRepository extends CrudRepository<Customer,Integer>{

	// Il nome del metodo e' findByEmail : 
	// con findBy stiamo dicendo a Spring Data JPA di recuperare i dati con l'aiuto della query
	// con Email stiamo dicendo a Spring Data JPA di recuperare i dati inserendo nella query la condizione Email
	List<Customer> findByEmail(String email);
}
