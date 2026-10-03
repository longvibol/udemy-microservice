package com.demo.provider;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class InstanceController {

	@Value("${server.port}")
	private String port;
	
	private String instance = java.util.UUID.randomUUID().toString();

	@GetMapping("/instance-info")
	public String getInstanceInfo() {

		System.out.println("Request received instance at instance running on port: " + port);

		return "Instanc server by Port: " + port + ". Intansce ID: " + instance ;

	}

}
