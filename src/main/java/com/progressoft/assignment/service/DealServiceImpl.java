package com.progressoft.assignment.service;

import java.util.Currency;
import java.util.List;

import org.springframework.stereotype.Service;

import com.progressoft.assignment.exception.DealIdAlreadyExistsException;
import com.progressoft.assignment.exception.UnsupportedCurrencyCodeException;
import com.progressoft.assignment.mapper.DealMapper;
import com.progressoft.assignment.model.dto.BatchProcessingSummary;
import com.progressoft.assignment.model.dto.DealRequest;
import com.progressoft.assignment.model.dto.DealResponse;
import com.progressoft.assignment.domaine.Deal;
import com.progressoft.assignment.repository.DealRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class DealServiceImpl implements DealService {

    private final DealRepository dealRepository;
    private final DealMapper dealMapper;

    @Override
    public DealResponse create(final DealRequest dealRequestDto) {
        log.info("Attempting to create deal with ID: {}", dealRequestDto.getId());

        validateCurrencyCodes(dealRequestDto);

        if (dealRepository.existsById(dealRequestDto.getId())) {
            log.warn("Duplicate deal ID detected: {}", dealRequestDto.getId());
            throw new DealIdAlreadyExistsException("Deal ID already exists.");
        }

        Deal deal = dealMapper.toEntity(dealRequestDto);
        Deal savedDeal = dealRepository.save(deal);

        log.info("Deal created successfully with ID: {}", savedDeal.getId());
        return dealMapper.toResponseDto(savedDeal);
    }

    @Override
    public BatchProcessingSummary createBatch(final List<DealRequest> dealRequests) {
        log.info("Starting batch processing for {} deals", dealRequests.size());

        record Result(DealResponse response, String error) {}

        List<Result> results = dealRequests.stream()
            .map(dealRequest -> {
                try {
                    DealResponse response = create(dealRequest);
                    log.debug("Successfully processed deal: {}", dealRequest.getId());
                    return new Result(response, null);
                } catch (Exception e) {
                    String errorMsg = String.format("Failed to process deal %s: %s",
                            dealRequest.getId(), e.getMessage());
                    log.warn("Failed to process deal: {}", errorMsg);
                    return new Result(null, errorMsg);
                }
            })
            .toList();

        List<DealResponse> successfulResults = results.stream()
            .filter(r -> r.response() != null)
            .map(Result::response)
            .toList();

        List<String> errorMessages = results.stream()
            .filter(r -> r.error() != null)
            .map(Result::error)
            .toList();

        BatchProcessingSummary result = new BatchProcessingSummary(
                dealRequests.size(),
                successfulResults.size(),
                errorMessages.size(),
                successfulResults,
                errorMessages
        );

        log.info("Batch processing completed - Total: {}, Success: {}, Failed: {}",
                result.getTotalRequests(), result.getSuccessfulDeals(), result.getFailedDeals());

        return result;
    }

    @Override
    public List<DealResponse> getAllDeals() {
        log.info("Retrieving all deals from database");
        List<Deal> deals = dealRepository.findAll();
        List<DealResponse> dealDtos = dealMapper.toResponseDtoList(deals);
        log.info("Retrieved {} deals", dealDtos.size());
        return dealDtos;
    }

    private void validateCurrencyCodes(DealRequest dto) {
        boolean fromCurrencyValid = Currency.getAvailableCurrencies().stream()
            .anyMatch(c -> c.getCurrencyCode().equals(dto.getFromCurrency()));
        boolean toCurrencyValid = Currency.getAvailableCurrencies().stream()
            .anyMatch(c -> c.getCurrencyCode().equals(dto.getToCurrency()));

        if (!fromCurrencyValid || !toCurrencyValid) {
            log.error("Invalid currency code provided: from={}, to={}", dto.getFromCurrency(), dto.getToCurrency());
            throw new UnsupportedCurrencyCodeException("Invalid currency code.");
        }
    }
}