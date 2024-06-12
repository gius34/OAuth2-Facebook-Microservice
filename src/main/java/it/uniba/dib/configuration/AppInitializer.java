package it.uniba.dib.configuration;

import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.filter.DelegatingFilterProxy;
import jakarta.servlet.ServletContext;

public class AppInitializer implements WebApplicationInitializer {

	@Override
	public void onStartup(ServletContext sc) {
		// Metodo chiamato all'avvio dell'applicazione
		
		// Creo un nuovo contesto dell'applicazione web basato su annotazioni
		AnnotationConfigWebApplicationContext root = new AnnotationConfigWebApplicationContext();
		
		// Registro la configurazione di sicurezza (SecurityConfiguration.class) nel contesto
		root.register(SecurityConfiguration.class);
		
		// Aggiungo un listener per il caricamento del contesto creato
		sc.addListener(new ContextLoaderListener(root));
		
		// Il filtro di sicurezza "DelegatingFilterProxy" viene aggiunto al contesto
		sc.addFilter("securityFilter", new DelegatingFilterProxy("springSecurityFilterChain"))
			.addMappingForUrlPatterns(null, false, "/*");
		
	}
}
