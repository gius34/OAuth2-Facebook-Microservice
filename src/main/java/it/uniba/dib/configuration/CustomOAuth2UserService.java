package it.uniba.dib.configuration;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import it.uniba.dib.model.Customer;
import it.uniba.dib.repository.CustomerRepository;

@Service
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

	@Autowired
	CustomerRepository customerRepository;
	
	@Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) {
        OAuth2User user =  super.loadUser(userRequest);
        
        // Estraggo le informazioni sull'utente
        Map<String, Object> attributes = user.getAttributes();

        Set<SimpleGrantedAuthority> authorities = new HashSet<>();
        
        if (attributes.containsKey("email")) {
        	// Spring DATA JPA esegue la query
        	List<Customer> customerRoleDB = customerRepository.findByEmail(attributes.get("email").toString());
        	if (customerRoleDB.isEmpty()) {
                
        		Customer customer = new Customer();
        		final String roleUser = "ROLE_USER";
        		final String roleAdmin = "ROLE_ADMIN";
        		
        		customer.setEmail(attributes.get("email").toString());
        		
        		// Assegno il ruolo in base al count degli utenti nel DB
        		String role = (customerRepository.count() == 0) ? roleAdmin : roleUser;
        		customer.setRole(role);
    			
    			// Salvo l'utente nel DB
    			customerRepository.save(customer);
    			authorities.add(new SimpleGrantedAuthority(role));

            } else {
            	authorities.add(new SimpleGrantedAuthority(customerRoleDB.get(0).getRole()));
            }
        	
        }

        // Costruisci e restituisci l'utente OAuth2 personalizzato con i ruoli mappati
        return new DefaultOAuth2User(authorities, attributes, "name");
    }
}
