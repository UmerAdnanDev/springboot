package com.example.topic1;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication //this annotation sets up everything automatically and finds all components  
public class Application {
	public static void main(String[] args) {
		// SpringApplication.run(Application.class, args);
		System.out.println("\n Spring Boot Project Created From Spring Initializer\n");
		// This starts Spring's container
        ApplicationContext context = SpringApplication.run(Application.class, args);

        // WAY 1: MANUAL - You create the object yourself
        ManualComponent mycomp = new ManualComponent();
        mycomp.Msg();
	}

}
