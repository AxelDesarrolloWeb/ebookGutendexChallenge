package com.alvax.ebookGutendex;

import com.alvax.ebookGutendex.model.DatosResultados;
import com.alvax.ebookGutendex.principal.Principal;
import com.alvax.ebookGutendex.service.ConsumoAPI;
import com.alvax.ebookGutendex.service.ConvierteDatos;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class EbookGutendexApplication {
	public static void main(String[] args) {
		SpringApplication.run(EbookGutendexApplication.class, args);
	}
	@Bean
	public CommandLineRunner run(Principal principal) {
		return args -> {
			principal.muestraElMenu();
		};
	}
}