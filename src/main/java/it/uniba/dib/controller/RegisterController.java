package it.uniba.dib.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import it.uniba.dib.model.Customer;
import it.uniba.dib.repository.CustomerRepository;

@Controller
public class RegisterController {
	
	@Autowired
	private CustomerRepository customerRepository;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@GetMapping("/register")
    public String registerForm(Model model) {
		model.addAttribute("customer", new Customer());
        return "register"; // restituisce il nome della vista per la pagina di registrazione
    }
	
	@PostMapping("/register")
	public String processRegister(Customer customer){
		Customer savedCustomer = null;
		final String roleUser = "ROLE_USER";
		final String roleAdmin = "ROLE_ADMIN";
		try {
			
			// Verifico se l'email già esiste nel DB
			if(!customerRepository.findByEmail(customer.getEmail()).isEmpty()) {
				// restituisce il nome della vista per la pagina register-error
				return "register-error";
				
			}
			
			// Verifico se esiste almeno un utente nel DB
			if(customerRepository.count() == 0) {
				// Assegno il ruolo ADMIN
				customer.setRole(roleAdmin);
				
			} else {
				// Assegno il ruolo USER
				customer.setRole(roleUser);
			}
			
			// Creo l'hash della password in chiaro
			String hashPwd = passwordEncoder.encode(customer.getPwd());
			customer.setPwd(hashPwd);
			
			// Salvo l'utente nel DB
			savedCustomer = customerRepository.save(customer);
			if(savedCustomer.getId() > 0) {
				// restituisce il nome della vista per la pagina register-success
				return "register-success";
			}
			
		} catch (Exception ex) {
			// restituisce il nome della vista per la pagina register-error
			return "register-error";
		}
		
		// restituisce il nome della vista per la pagina register-error
		return "register-error";
	}
	
}
