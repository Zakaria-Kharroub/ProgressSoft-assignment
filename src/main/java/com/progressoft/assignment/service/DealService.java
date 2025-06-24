package com.progressoft.assignment.service;

import java.util.List;

import com.progressoft.assignment.model.dto.BatchProcessingSummary;
import com.progressoft.assignment.model.dto.DealRequest;
import com.progressoft.assignment.model.dto.DealResponse;

public interface DealService {

    DealResponse create(final DealRequest dealRequestDto);

    BatchProcessingSummary createBatch(final List<DealRequest> dealRequests);

    List<DealResponse> getAllDeals();
}