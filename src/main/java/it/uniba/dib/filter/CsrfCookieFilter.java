package it.uniba.dib.filter;

import java.io.IOException;

import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

// La classe CsrfCookieFilter estende OncePerRequestFilter, che garantisce che il filtro 
// venga eseguito una volta per ogni richiesta HTTP
public class CsrfCookieFilter extends OncePerRequestFilter{

	// Il metodo doFilterInternal viene sovrascritto per implementare la logica del filtro
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		
		// Recupero il token CSRF dall'attributo della request. Nota: Spring Security aggiunge alla request il token CSRF
		CsrfToken csrfToken = (CsrfToken) request.getAttribute(CsrfToken.class.getName());
		
		// Controllo se il nome dell'header del token CSRF non è null. Questo serve per verificare se il token è presente
		if(null != csrfToken.getHeaderName()) {
			
			// Configuro l'header della risposta con il nome e il valore del token CSRF. 
			// Questo permette al client di conoscere il token CSRF corrente
			response.setHeader(csrfToken.getHeaderName(), csrfToken.getToken());
		}
		
		// Passa la request e la response al filtro successivo nella catena
		filterChain.doFilter(request, response);
		
		// In questo modo, ogni volta che verra' inviata la risposta al client, il valore CsrfToken sara' presente
		// nell'intestazione.
	}

}
