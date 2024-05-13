package com.bessonov.musicappserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.bessonov.musicappserver")
@EnableAutoConfiguration
public class MusicAppServerApplication {
	public static void main(String[] args) {
		SpringApplication.run(MusicAppServerApplication.class, args);
	}
}
