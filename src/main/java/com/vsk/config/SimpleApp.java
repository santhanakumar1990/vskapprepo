package com.vsk.config;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class SimpleApp {

	public static void main(String[] args) {

		SpringApplication.run(SimpleApp.class, args);

	}

	@GetMapping(path = "/get/{string}")
	public String getTestString(@PathVariable("string")String value) {
		return "Instance running in aws, input is "+value;
	}

	@GetMapping(path = "/info")
	public String getTestString() {
		return "Instance running in aws, new update 2.0";
	}
	

}
