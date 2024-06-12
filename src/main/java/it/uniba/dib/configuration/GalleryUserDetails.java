package it.uniba.dib.configuration;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import it.uniba.dib.model.Customer;
import it.uniba.dib.repository.CustomerRepository;

@Service
public class GalleryUserDetails implements UserDetailsService{

	@Autowired
	CustomerRepository customerRepository;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		String userName, password = null;
		List<GrantedAuthority> authorities = null;
		List<Customer> customer = customerRepository.findByEmail(username); // Spring DATA JPA esegue la query
		
		if(customer.size() == 0) {
			throw new UsernameNotFoundException("User details not found for the user :"+username);
		} else if (customer.get(0).getPwd() == null) {
			throw new UsernameNotFoundException("User details not found for the user :"+username);
		} else {
			userName = customer.get(0).getEmail();
			password = customer.get(0).getPwd();
			
			authorities = new ArrayList<>();
			authorities.add(new SimpleGrantedAuthority(customer.get(0).getRole()));
			//System.out.println("authorities: " + authorities);
		}
		// Queste info sono inserite nell'oggetto User cosi' possono essere restituite al provider di autenticazione DAO
		// IL DAO confronterà la password recuperata dal DB con la password inserita dall'utente
		return new User(username, password, authorities);
	}

}
