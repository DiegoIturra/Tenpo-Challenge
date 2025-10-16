package com.project.challenge.controllers;

import com.project.challenge.models.entities.History;
import com.project.challenge.services.interfaces.HistoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/history")
@RequiredArgsConstructor
@Tag(name = "History", description = "Operations related to the asynchronous history of calculations.")
public class HistoryController {

    private final HistoryService historyService;

    @Operation(summary = "Get all history records (non-paginated)",
            description = "Returns the complete list of history records. Use the /paginated endpoint for large datasets.")
    @GetMapping("/all")
    public List<History> findAll() {
        return historyService.findAll();
    }

    @Operation(summary = "Get paginated history records",
            description = "Returns a paginated and sortable list of calculation history records.")
    @GetMapping("/paginated")
    public Page<History> findPaginated(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "date") String sortBy
    ) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy).ascending());
        return historyService.findPaginated(pageable);
    }
}
