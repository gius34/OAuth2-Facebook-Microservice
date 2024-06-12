package it.uniba.dib.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.access.AccessDeniedHandlerImpl;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.csrf.CsrfTokenRequestAttributeHandler;
import org.springframework.security.web.session.HttpSessionEventPublisher;

import it.uniba.dib.filter.CsrfCookieFilter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.web.OAuth2LoginAuthenticationFilter;

import static org.springframework.security.config.Customizer.withDefaults;

import java.io.IOException;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {
	
	@Autowired
	private CustomOAuth2UserService oauth2UserService;
	
	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		
		// Questa classe rende disponibile il CsrfToken ed e' responsabile di aggiungere e 
		// verificare i token CSRF nelle richieste HTTP, proteggendo l'applicazione da attacchi CSRF.
		CsrfTokenRequestAttributeHandler requestHandler = new CsrfTokenRequestAttributeHandler();

		// set il nome dell'attributo su cui verrà popolato CsrfToken
		requestHandler.setCsrfRequestAttributeName("_csrf");
		
		http
		.authorizeHttpRequests((requests) -> requests
			.requestMatchers("/managephotos", "/api/managephotos/**").hasRole("ADMIN")
			.requestMatchers("/home","/viewphotos","/api/viewphotos/**", "/img/**").hasAnyRole("USER", "ADMIN")
			.requestMatchers("/", "/access-denied", "/favicon.ico", "/login", "/login/**", "/register").permitAll()
			
		)
		.oauth2Login(oauth2 -> oauth2
				// Configuro la pagina di login per l'autenticazione OAuth2
				.loginPage("/login")
				// Specifico URL di reindirizzamento dopo il login success con OAuth2
				.defaultSuccessUrl("/home")
				// Configuro un gestore custom per gestire i fallimenti dell'autenticazione OAuth2
				.failureHandler(new CustomAuthenticationFailureHandler())
				// Configurazione per ottenere le informazioni dell'utente autenticato con OAuth2
				.userInfoEndpoint(userInfo -> userInfo
						// Specifico il servizio utilizzato per recuperare le informazioni dell'utente
						.userService(oauth2UserService))
				)
		
		.formLogin((form) -> form
				// Configuro la pagina di login per l'autenticazione tramite form (basic)
				.loginPage("/login")
				// Consento a tutti di accedere alla pagina di login
				.permitAll()
				// Specifico l'URL al quale devono essere inviate le credenziali di login
				.loginProcessingUrl("/login")
				// Specifico l'URL di reindirizzamento dopo il login success tramite form
                .defaultSuccessUrl("/home", true))
		
		// Abilito l'autenticazione HTTP Basic
		.httpBasic(withDefaults())
		
		// Abilita la protezione CSRF e inizia la configurazione dei vari aspetti della protezione del CSRF
		.csrf((csrf) -> csrf
				// Specifico un gestore personalizzato per la gestione del token CSRF
				.csrfTokenRequestHandler(requestHandler)
				// Questo metodo consente di escludere specifici percorsi URL dalla protezione CSRF
				.ignoringRequestMatchers("/register")
				// Configuro il repository che memorizzera' il token CSRF. In questo caso viene memorizzato nel cookie
				// Con la funzione withHttpOnlyFalse, andiamo a creare un cookie CSRF che non ha l'attributo HttpOnly 
				// impostato su true. Impostandolo su false, il valore del cookie potrà essere letto dal codice JavaScript 
				// dell'applicazione,
				.csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse()))
		
		// Specifico che il filtro CsrfCookieFilter deve essere eseguito dopo il filtro BasicAuthenticationFilter
		// Appena l'operazione di login è completata, il CsrfToken sara' generato
		// Il token verra' inserito nella response mediante CsrfCookieFilter
		.addFilterAfter(new CsrfCookieFilter(), BasicAuthenticationFilter.class)
		
		// Specifico che il filtro CsrfCookieFilter deve essere eseguito dopo il filtro OAuth2LoginAuthenticationFilter
		// Appena l'operazione di login è completata, il CsrfToken sara' generato
		// Il token verra' inserito nella response mediante CsrfCookieFilter
		.addFilterAfter(new CsrfCookieFilter(), OAuth2LoginAuthenticationFilter.class)
		
		// Stiamo dicendo a Spring Security di creare il JSessionID dopo che il login iniziale e' stato completato
		// Questo JSessionID potra' essere utilizzato dal client per le diverse richieste
		// Impostando il metodo requireExplicitSave a False, il framework avra' la responsabilita' di generare il
		// JSESSIONID e di memorizzare i dettagli dell'autenticazione del SecurityContextHolder
		.securityContext((securityContext) -> securityContext.requireExplicitSave(false))
		.sessionManagement((session) -> session
				.sessionCreationPolicy(SessionCreationPolicy.ALWAYS)
				.maximumSessions(2))
		.logout((logout) -> logout
				// Configuro l'URL di reindirizzamento dopo un logout riuscito
				.logoutSuccessUrl("/")
				// Specifico che il cookie di sessione deve essere eliminato al logout
				.deleteCookies("JSESSIONID")
				// Invalido la sessione HTTP corrente al momento del logout
				.invalidateHttpSession(true)
				.permitAll())
		.exceptionHandling(exceptionHandling ->
			// Configuro un gestore personalizzato per gli accessi negati
    		exceptionHandling.accessDeniedHandler(accessDeniedHandler()));
	
		return http.build();
	}
	
	
	@Bean
	public HttpSessionEventPublisher httpSessionEventPublisher() {
		return new HttpSessionEventPublisher();
	}
	
	@Bean
	public static PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Bean
	public AccessDeniedHandler accessDeniedHandler() {
		
	    AccessDeniedHandlerImpl accessDeniedHandler = new AccessDeniedHandlerImpl();
	    accessDeniedHandler.setErrorPage("/access-denied");
	    return accessDeniedHandler;
	}
	
	
	// Obiettivo: gestire i fallimenti di autenticazione in modo personalizzato
	private static class CustomAuthenticationFailureHandler extends SimpleUrlAuthenticationFailureHandler {
        @Override
        public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) 
        		throws IOException, ServletException {
            	
            	// In caso di eccezione, il metodo onAuthenticationFailure della classe SimpleUrlAuthenticationFailureHandler viene chiamato. 
            	// Gestione del fallimento dell'autenticazione in modo standard, con il reindirizzamento dell'utente a una pagina di errore di login.
                super.onAuthenticationFailure(request, response, exception);
            
        }
    }
}
