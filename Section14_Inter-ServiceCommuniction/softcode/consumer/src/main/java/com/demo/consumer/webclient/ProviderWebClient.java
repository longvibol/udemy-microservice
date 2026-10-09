package com.demo.consumer.webclient;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class ProviderWebClient {	
	private final WebClient webClient;	
	public Mono<String> getInstanceInfo() {
		
		return webClient.get()
			.uri("/instance-info")
			.retrieve()
			.bodyToMono(String.class);
	}
}
