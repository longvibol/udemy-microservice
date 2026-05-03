package com.vibol.FirstHelloWorl.FirstController;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloWorld {

    @GetMapping("/hello")
    public String helloWorld(){
        return "Hello World";
    }

    @PostMapping("hello")
    public String PostWelcome(@RequestBody String name){
        return "Hello " + name + " In Spring Boot";
    }
}
