package com.project.challenge.controllers;

import com.project.challenge.models.entities.History;
import com.project.challenge.services.HistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/history")
public class HistoryController {

    @Autowired
    private HistoryService historyService;

    @GetMapping("/all")
    public List<History> findAll() {
        return historyService.findAll();
    }

    @PostMapping("/save")
    public History save(@RequestBody History record) {
        return historyService.save(record);
    }
}
