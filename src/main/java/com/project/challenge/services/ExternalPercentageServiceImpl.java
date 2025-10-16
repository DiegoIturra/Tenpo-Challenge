package com.project.challenge.services;

import com.project.challenge.services.interfaces.ExternalPercentageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class ExternalPercentageServiceImpl implements ExternalPercentageService {

    private static final Logger log = LoggerFactory.getLogger(ExternalPercentageServiceImpl.class);

    @Override
    public Double getPercentage(){

        double failureChance = Math.random();

        if(failureChance < 0.5) {
            log.error("External service failure");
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "External service failure");
        }

        log.info("External service success");
        Double randomNumber = Math.random() * 100;
        return Math.round(randomNumber * 100.0) / 100.0;
    }
}
