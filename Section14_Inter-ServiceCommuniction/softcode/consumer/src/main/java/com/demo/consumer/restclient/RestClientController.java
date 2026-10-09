package com.demo.consumer.restclient;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestClient;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/rest-client")
public class RestClientController {
	
	private final ProviderRestClient providerRestClient;

	@GetMapping("/instance")
	public String getInstance() {
//		RestClient restClient = RestClient.create();
//		
//		String response = restClient.get()
//					.uri("http://localhost:9091/instance-info") // end url
//					.retrieve() // request 
//					.body(String.class); // convert to our class
//		
//		return response;
		return providerRestClient.getInstanceInfo();
	}
}
