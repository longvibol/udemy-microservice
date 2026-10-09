package com.demo.consumer.resttemplate;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RestTemplateClient {
	
	private final RestTemplate restTemplate;
	
	private static final String PROVIDER_URL = "http://localhost:9091";
	
	public String getInstanceInfo() {
	return restTemplate.getForObject(PROVIDER_URL+"/instance-info", String.class);
	}

}
