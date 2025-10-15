package com.project.challenge.services;

import com.project.challenge.models.entities.History;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface HistoryService {
    List<History> findAll();
    Page<History> findPaginated(Pageable pageable);
    History save(History record);


}
