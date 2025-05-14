package com.example.practise_filters.api;

import com.example.practise_filters.dto.SomeObject;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {
    @GetMapping("/hi")
    public String sayHello() {
        return "Welcome, onboard";
    }

    @GetMapping("/addObject")
    public String addObject(@RequestBody SomeObject someObject) {
        return "Object : " + someObject.getName() ;
    }
}
