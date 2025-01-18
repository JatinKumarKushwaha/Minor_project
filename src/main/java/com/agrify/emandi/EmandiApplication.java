package com.agrify.emandi;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.boot.SpringApplication;

@Configuration
@ComponentScan
@EnableAutoConfiguration
public class EmandiApplication {

	public static void main(String[] args) {
		SpringApplication.run(EmandiApplication.class, args);
	}
}

@RestController
class GreetingController {

	@GetMapping("/hello/{name}")
	String hello(@PathVariable String name) {
		return "Hello, " + name + "!";
	}
}