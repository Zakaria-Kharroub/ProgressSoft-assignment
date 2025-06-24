package com.progressoft.assignment.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.progressoft.assignment.model.dto.BatchProcessingSummary;
import com.progressoft.assignment.model.dto.DealRequest;
import com.progressoft.assignment.model.dto.DealResponse;
import com.progressoft.assignment.service.DealService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@RestController
@RequestMapping(path = "/api/deals")
@Tag(name = "FX Deals", description = "Foreign Exchange Deals Management")
public class DealController {

    private final DealService dealService;

    @Operation(summary = "Create a single FX deal", description = "Creates and persists a single foreign exchange deal")
    @PostMapping
    public ResponseEntity<DealResponse> create(@Valid @RequestBody DealRequest dealDtoReq) {
        return new ResponseEntity<>(
            dealService.create(dealDtoReq),
            HttpStatus.CREATED
        );
    }

    @Operation(summary = "Batch process multiple FX deals", description = "Processes multiple deals at once with no rollback policy")
    @PostMapping("/batch")
    public ResponseEntity<BatchProcessingSummary> createBatch(@Valid @RequestBody List<DealRequest> dealRequests) {
        return new ResponseEntity<>(
            dealService.createBatch(dealRequests),
            HttpStatus.OK
        );
    }

    @Operation(summary = "Get all deals", description = "Retrieves all persisted FX deals")
    @GetMapping
    public ResponseEntity<List<DealResponse>> getAllDeals() {
        return ResponseEntity.ok(dealService.getAllDeals());
    }
}