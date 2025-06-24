package com.progressoft.assignment.model.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BatchProcessingSummary {
    private int totalRequests;
    private int successfulDeals;
    private int failedDeals;
    private List<DealResponse> successfulResults;
    private List<String> errorMessages;
}