package com.demo.consumer.restclient;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProviderRestClient {
	
	private final RestClient restClient;
	
	public String getInstanceInfo() {
		return restClient.get()
				.uri("/instance-info")
				.retrieve()
				.body(String.class);
	}

}
