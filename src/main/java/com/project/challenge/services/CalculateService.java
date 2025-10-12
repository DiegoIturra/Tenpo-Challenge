package com.project.challenge.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@Service
public class CalculateService {

    @Autowired
    ExternalPercentageService percentageService;

    @Autowired
    CacheManager cacheManager;

    private Double fetchPercentage(){
        try {
            return percentageService.getPercentage();
        } catch (Exception e) {
            return Optional.ofNullable(cacheManager.getCache("percentageCache"))
                    .map(cache -> cache.get("percentage", Double.class))
                    .orElseThrow(() -> new ResponseStatusException(
                            HttpStatus.INTERNAL_SERVER_ERROR, "Error getting percentage from external service and cache is null"
                    ));
        }
    }

    public Double calculate(int num1, int num2) {
        Double percentage = fetchPercentage();
        return (num1 + num2) + percentage;
    }

}
