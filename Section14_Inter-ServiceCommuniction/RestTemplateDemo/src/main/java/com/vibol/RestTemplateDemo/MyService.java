package com.vibol.RestTemplateDemo;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class MyService {

    private final RestTemplate restTemplate;

    public MyService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public String fetchData(Long id) {
    	String url = "https://jsonplaceholder.typicode.com/todos/"+id;
        return restTemplate.getForObject(url, String.class);
    }
    
    public MyRespond fetchDataWithRespon(Long id) {
    	String url = "https://jsonplaceholder.typicode.com/todos/"+id;
        return restTemplate.getForObject(url, MyRespond.class);
    }
}