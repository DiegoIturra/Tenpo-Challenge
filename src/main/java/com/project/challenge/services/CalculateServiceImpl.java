package com.project.challenge.services;

import com.project.challenge.models.entities.History;
import com.project.challenge.services.interfaces.CalculateService;
import com.project.challenge.services.interfaces.ExternalPercentageService;
import com.project.challenge.services.interfaces.HistoryAsyncService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.CacheManager;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;


@RequiredArgsConstructor
@Service
public class CalculateServiceImpl implements CalculateService {

    private static final Logger log = LoggerFactory.getLogger(CalculateServiceImpl.class);


    private final ExternalPercentageService percentageService;
    private final HistoryAsyncService historyAsyncService;
    private final CacheManager cacheManager;


    @Override
    public Double calculate(int num1, int num2) {
        String endpoint = buildEndpoint(num1, num2);
        String parameters = buildParameters(num1, num2);

        try {
            Double result = doCalculate(num1, num2);
            saveSuccessRecord(endpoint, parameters, result);
            return result;
        } catch (Exception exception) {
            saveErrorRecord(endpoint, parameters, exception);
            throw exception;
        }
    }

    private Double fetchPercentage(){
        try {
            Double percentage = percentageService.getPercentage();

            Optional.ofNullable(cacheManager.getCache("percentageCache"))
                    .ifPresent(cache -> cache.put("percentage", percentage));
            return percentage;
        } catch (Exception e) {
            return Optional.ofNullable(cacheManager.getCache("percentageCache"))
                    .map(cache -> cache.get("percentage", Double.class))
                    .orElseThrow(() -> new ResponseStatusException(
                            HttpStatus.INTERNAL_SERVER_ERROR, "Error getting percentage from external service and cache is null"
                    ));
        }
    }

    private String buildEndpoint(int num1, int num2) {
        return "/calculate?num1=".concat(String.valueOf(num1))
                .concat("&num2=")
                .concat(String.valueOf(num2));
    }

    private String buildParameters(int num1, int num2) {
        return "{ num1: " + num1 + ", num2: " + num2 + " }";
    }

    private Double doCalculate(int num1, int num2) {
        Double percentage = fetchPercentage();
        return (num1 + num2) + percentage;
    }

    private void saveSuccessRecord(String endpoint, String parameters, Double result) {
        History record = new History();
        record.setEndpoint(endpoint);
        record.setError(null);
        record.setParameters(parameters);
        record.setResponse("{ result: " + result + " }");
        historyAsyncService.saveHistoryAsync(record);
        log.info("SAVING A RECORD WITHOUT ERROR: " + record);
    }

    private void saveErrorRecord(String endpoint, String parameters, Exception exception){
        History record = new History();
        record.setEndpoint(endpoint);
        record.setError(exception.getMessage());
        record.setParameters(parameters);
        record.setResponse(null);
        historyAsyncService.saveHistoryAsync(record);
        log.info("SAVING A RECORD WITH AN ERROR: " + exception.getMessage());
    }
}
