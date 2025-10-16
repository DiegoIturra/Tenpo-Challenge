package com.project.challenge.services;

import com.project.challenge.models.entities.History;
import com.project.challenge.repositories.HistoryRepository;
import com.project.challenge.services.interfaces.HistoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;


@RequiredArgsConstructor
@Service
public class HistoryServiceImpl implements HistoryService {

    private final HistoryRepository historyRepository;

    @Override
    @Transactional(readOnly = true)
    public List<History> findAll() {
        return historyRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Page<History> findPaginated(Pageable pageable) {
        return historyRepository.findAll(pageable);
    }

    @Override
    @Transactional
    public History save(History record) {
        record.setDate(LocalDateTime.now());
        return historyRepository.save(record);
    }
}
