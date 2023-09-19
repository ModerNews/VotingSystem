package org.alo.votingsystem;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class VotingSystemApplication {

	// TODO: Content-Type: application/x-www-form-urlencoded doesn't work in whole application
	// Additionally on POST /login:
	// Resolved [org.springframework.web.HttpMediaTypeNotSupportedException: Content-Type 'application/x-www-form-urlencoded;charset=UTF-8' is not supported]
	public static void main(String[] args) {
		SpringApplication.run(VotingSystemApplication.class, args);
	}

}