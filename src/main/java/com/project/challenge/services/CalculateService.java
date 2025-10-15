package com.project.challenge.services;

import com.project.challenge.models.entities.History;
import org.springframework.beans.factory.annotation.Autowired;
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
    HistoryAsyncService historyAsyncService;

    @Autowired
    CacheManager cacheManager;

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
            return percentageService.getPercentage();
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
        System.out.println("SAVING A RECORD WITHOUT ERROR: " + record);
    }

    private void saveErrorRecord(String endpoint, String parameters, Exception exception){
        History record = new History();
        record.setEndpoint(endpoint);
        record.setError(exception.getMessage());
        record.setParameters(parameters);
        record.setResponse(null);
        historyAsyncService.saveHistoryAsync(record);
        System.out.println("SAVING A RECORD WITH AN ERROR: " + exception.getMessage());
    }
}
