package com.comnet.CNProject;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

@SpringBootApplication
@RestController
public class CnProjectApplication {

	public static void main(String[] args) {
		SpringApplication.run(CnProjectApplication.class, args);
	}

	@GetMapping("/")
	public String hello() throws IOException {
		HtmlReader reader = new HtmlReader("index.html");

		return reader.toString();
	}
}
