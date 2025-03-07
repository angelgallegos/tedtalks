package com.iodigital.tedtalks;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@ComponentScan("com.iodigital.tedtalks")
@EnableJpaRepositories(
	basePackages = "com.iodigital.tedtalks.repositories", repositoryImplementationPostfix = "Impl"
)
public class TedtalksApplication {

	public static void main(String[] args) {
		SpringApplication.run(TedtalksApplication.class, args);
	}

}
