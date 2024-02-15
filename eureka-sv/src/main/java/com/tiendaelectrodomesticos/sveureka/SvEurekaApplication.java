package com.tiendaelectrodomesticos.sveureka;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableEurekaServer
public class SvEurekaApplication {

	public static void main(String[] args) {
		SpringApplication.run(SvEurekaApplication.class, args);
	}

}
