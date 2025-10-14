package com.project.challenge.services;

import com.project.challenge.models.entities.History;

import java.util.List;

public interface HistoryService {
    List<History> findAll();
    History save(History record);
}
