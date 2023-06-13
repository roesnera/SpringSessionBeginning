package com.adamaroesner.springsessionattempt.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class HelloController {

    @GetMapping("/hello")
    public String sayHi(){
        return "Hiya!";
    }

    @GetMapping("/secured/hello")
    public String securedHi(){
        return "Hello from our secured endpoint";
    }
}
