package com.example.topic4a;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
		System.out.println("Banking API demonsterating use of data tranfer object layer to remove over fetching of data and improving security along with performance,mannual mapper layer a pipeline between entity and dto , used validation dependency for edge cases,loombok for reducing boileplate code and MySQl for database ");
	}

}
