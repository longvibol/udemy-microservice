package com.demo.consumer.feign;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/feign")
@RequiredArgsConstructor
public class FeignController {
	
	private final ProviderFeignClient feignClient;
	
	@GetMapping("/instance")
	public String getInstance() {
		return feignClient.getInstanceInfo();
	}

}
