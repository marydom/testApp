package com.etnetera.hr;

import java.time.LocalDate;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.etnetera.hr.data.JavaScriptFramework;
import com.etnetera.hr.repository.JavaScriptFrameworkRepository;

/**
 * Spring Boot application class.
 * 
 * @author Etnetera
 *
 */
@SpringBootApplication
public class Application {
	private static final Logger log = LoggerFactory.getLogger(Application.class);

	public static void main(String[] args) {
		SpringApplication.run(Application.class);
	}

	// @Bean
	public CommandLineRunner demo(JavaScriptFrameworkRepository repository) {

		return (args) -> {
			// save a few
			repository.save(new JavaScriptFramework("Vue.js", "3.2", LocalDate.of(2021, 12, 31), 1));
			repository.save(new JavaScriptFramework("Vue.js", "3.1", LocalDate.of(2020, 12, 31), 100));
			repository.save(new JavaScriptFramework("Angular", "12", LocalDate.of(2023, 12, 31), 0));
			repository.save(new JavaScriptFramework("Angular", "11", LocalDate.of(2000, 12, 31), 1));
			repository.save(new JavaScriptFramework("Angular", "11-patch27", LocalDate.of(2021, 12, 31), 1));
			repository.save(new JavaScriptFramework("jQuery", "3.6.0", LocalDate.of(2021, 12, 31), 67));

			// fetch all
			log.info("Frameworks found with findAll():");
			log.info("-------------------------------");
			for (JavaScriptFramework framework : repository.findAll()) {
				log.info(framework.toString());
			}
			log.info("");

			// fetch an individual framework by ID
			JavaScriptFramework framework = repository.findById(1L).get();
			log.info("JavaScriptFramework found with findById(1L):");
			log.info("--------------------------------");
			log.info(framework.toString());
			log.info("");

			// fetch frameworks by name
			log.info("JavaScriptFramework found with findByName('Angular'):");
			log.info("--------------------------------------------");
			repository.findByName("Angular").forEach(f -> {
				log.info(f.toString());
			});
		};
	}

}
