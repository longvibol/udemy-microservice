package com.vibol.RestTemplateDemo;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MyController {

    private final MyService myService;

    // Constructor injection for the service
    public MyController(MyService myService) {
        this.myService = myService;
    }

    @GetMapping("/get-external-data/{id}")
    public String getExternalData(@PathVariable Long id) {
        // Calls the service method that uses RestTemplate
        return myService.fetchData(id);
    }
    
    @GetMapping("/get-external-data1/{id}")
    public MyRespond getExternalDataWithRespond(@PathVariable Long id) {
        // Calls the service method that uses RestTemplate
        return myService.fetchDataWithRespon(id);
    }
}