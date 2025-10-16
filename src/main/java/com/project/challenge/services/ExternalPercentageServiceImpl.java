package com.project.challenge.services;

import com.project.challenge.services.interfaces.ExternalPercentageService;
import org.springframework.stereotype.Service;

@Service
public class ExternalPercentageServiceImpl implements ExternalPercentageService {

    @Override
    public Double getPercentage(){
        Double randomNumber = Math.random() * 100;
        return Math.round(randomNumber * 100.0) / 100.0;
    }
}
