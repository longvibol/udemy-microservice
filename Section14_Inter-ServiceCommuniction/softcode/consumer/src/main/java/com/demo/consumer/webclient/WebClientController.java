package com.demo.consumer.webclient;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/web-client")
@RequiredArgsConstructor
public class WebClientController {

	private final ProviderWebClient providerWebClient;
	
	@GetMapping("/instance")
	public Mono<String> getInstance(){
//		WebClient webClient = WebClient.create();
//		
//		Mono<String> response = webClient.get()
//			.uri("http://localhost:9091/instance-info")
//			.retrieve()
//			.bodyToMono(String.class);
//		
//		return response;
		return providerWebClient.getInstanceInfo();
	}
}
