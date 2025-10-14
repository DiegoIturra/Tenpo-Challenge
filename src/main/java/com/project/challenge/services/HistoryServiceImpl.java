package com.project.challenge.services;

import com.project.challenge.models.entities.History;
import com.project.challenge.repositories.HistoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class HistoryServiceImpl implements HistoryService{

    @Autowired
    private HistoryRepository historyRepository;

    @Override
    @Transactional(readOnly = true)
    public List<History> findAll() {
        return (List<History>) historyRepository.findAll();
    }

    @Override
    @Transactional
    public History save(History record) {
        record.setDate(LocalDateTime.now());
        return historyRepository.save(record);
    }
}
