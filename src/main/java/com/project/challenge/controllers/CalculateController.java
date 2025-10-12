package com.project.challenge.controllers;

import com.project.challenge.services.CalculateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class CalculateController {

    @Autowired
    CalculateService calculateService;

    @GetMapping("/calculate")
    public ResponseEntity<Object> calculate(@RequestParam int num1 , @RequestParam int num2) {
        //Calculate the response calculate service
        double result = calculateService.calculate(num1, num2);

        return ResponseEntity
                .ok(Map.of("result", String.valueOf(result)));
    }
}
