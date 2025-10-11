package com.project.challenge.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class CalculateController {

    @GetMapping("/calculate")
    public ResponseEntity<Object> calculate(@RequestParam Integer num1 , @RequestParam Integer num2) {
        return ResponseEntity
                .ok(Map.of("message", "Here goes the result"));
    }


}
