package com.project.challenge.services;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
public class ExternalPercentageService {

    @Cacheable(value = "percentageCache", key = "'percentage'")
    public Double getPercentage(){
        Double randomNumber = Math.random() * 100;
        return Math.round(randomNumber * 100.0) / 100.0;
    }
}
