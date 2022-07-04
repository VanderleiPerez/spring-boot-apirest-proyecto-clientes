package com.empresa.springboot.apirest;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
//public class SpringBootBackendApirestApplication implements CommandLineRunner{
public class SpringBootBackendApirestApplication{


	@Autowired
	//private BCryptPasswordEncoder passwordEncoded;
	
	public static void main(String[] args) {
		SpringApplication.run(SpringBootBackendApirestApplication.class, args);
	}

	/*@Override
	public void run(String... args) throws Exception {
		String password = "12345";
		for(int i=0;i<4;i++) {
			String passwordBcrypt = passwordEncoded.encode(password);
			System.out.println(passwordBcrypt);
		}
		
	}*/

}
