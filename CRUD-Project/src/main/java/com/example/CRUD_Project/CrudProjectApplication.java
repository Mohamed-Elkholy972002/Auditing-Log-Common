package com.example.CRUD_Project;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;


@SpringBootApplication
@ComponentScan(basePackages = {
		"com.example.CRUD_Project",
		"com.example.practise_filters"
})
@EnableJpaRepositories(basePackages = {
		"com.example.CRUD_Project.product",
		"com.example.practise_filters.model"
})
@EntityScan(basePackages = {
		"com.example.CRUD_Project.product",
		"com.example.practise_filters.model"
})
public class CrudProjectApplication {

	public static void main(String[] args) {
		SpringApplication.run(CrudProjectApplication.class, args);
	}

}
