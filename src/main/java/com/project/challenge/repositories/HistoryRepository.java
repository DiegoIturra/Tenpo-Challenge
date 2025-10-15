package com.project.challenge.repositories;

import com.project.challenge.models.entities.History;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HistoryRepository extends JpaRepository<History, Long> { }
