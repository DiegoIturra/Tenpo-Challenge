package com.project.challenge.services;

import com.project.challenge.models.entities.History;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class HistoryAsyncService {

    @Autowired
    private HistoryService historyService;

    @Async
    public void saveHistoryAsync(History record) {
        try {
            historyService.save(record);
        } catch (Exception ex) {
            System.out.println("Error saving record: " + ex.getMessage());
        }
    }
}
