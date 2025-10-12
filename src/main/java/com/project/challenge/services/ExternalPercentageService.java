package com.project.challenge.services;

import org.springframework.stereotype.Service;

@Service
public class ExternalPercentageService {

    public double getPercentage(){
        double randomNumber = Math.random() * 100;
        return Math.round(randomNumber * 100.0) / 100.0;
    }
}
