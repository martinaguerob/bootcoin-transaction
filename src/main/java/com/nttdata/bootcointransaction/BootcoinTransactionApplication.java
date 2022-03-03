package com.nttdata.bootcointransaction;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@EnableEurekaClient
@SpringBootApplication
public class BootcoinTransactionApplication {

	public static void main(String[] args) {
		SpringApplication.run(BootcoinTransactionApplication.class, args);
	}

}
