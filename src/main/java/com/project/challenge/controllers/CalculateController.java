package com.project.challenge.controllers;

import com.project.challenge.services.CalculateServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequiredArgsConstructor
@Tag(name = "Calculation", description = "Operations for dynamic calculation using an external percentage.")
public class CalculateController {

    private final CalculateServiceImpl calculateService;

    @Operation(
            summary = "Calculate the sum of two numbers plus a dynamic external percentage.",
            description = "Returns (num1 + num2) + percentage. The percentage is fetched from an external service or a 30-minute cache.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Calculation performed successfully.",
                            content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(type = "number", format = "double", example = "35.0"))
                    ),
                    @ApiResponse(
                            responseCode = "500",
                            description = "External service failed and no value was found in the cache."
                    )
            }
    )
    @GetMapping("/calculate")
    public ResponseEntity<Object> calculate(@RequestParam int num1 , @RequestParam int num2) {
        //Calculate the response calculate service
        Double result = calculateService.calculate(num1, num2);

        return ResponseEntity
                .ok(Map.of("result", String.valueOf(result)));
    }
}
