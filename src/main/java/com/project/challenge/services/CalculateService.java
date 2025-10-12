package com.project.challenge.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CalculateService {

    @Autowired
    ExternalPercentageService percentageService;
    public double calculate(int num1, int num2){
        double percentage = percentageService.getPercentage();
        return (num1 + num2) * percentage;
    }
}
