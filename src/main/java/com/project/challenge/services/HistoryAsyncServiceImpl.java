package com.project.challenge.services;

import com.project.challenge.models.entities.History;
import com.project.challenge.services.interfaces.HistoryAsyncService;
import com.project.challenge.services.interfaces.HistoryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class HistoryAsyncServiceImpl implements HistoryAsyncService {

    private static final Logger log = LoggerFactory.getLogger(HistoryAsyncServiceImpl.class);

    @Autowired
    private HistoryService historyService;

    @Async
    @Override
    public void saveHistoryAsync(History record) {
        try {
            historyService.save(record);
        } catch (Exception ex) {
            log.error("Error saving record: " + ex.getMessage());
        }
    }
}
