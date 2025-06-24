package com.progressoft.assignment.service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Currency;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.mockito.ArgumentMatchers.any;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;

import com.progressoft.assignment.exception.DealIdAlreadyExistsException;
import com.progressoft.assignment.exception.UnsupportedCurrencyCodeException;
import com.progressoft.assignment.mapper.DealMapper;
import com.progressoft.assignment.model.dto.DealRequest;
import com.progressoft.assignment.model.dto.DealResponse;
import com.progressoft.assignment.domaine.Deal;
import com.progressoft.assignment.repository.DealRepository;

@ExtendWith(MockitoExtension.class)
class DealServiceImplTest {

    @Mock
    private DealRepository dealRepository;

    @Mock
    private DealMapper dealMapper;

    @InjectMocks
    private DealServiceImpl dealService;

    private DealRequest validRequest;
    private Deal dealEntity;
    private DealResponse expectedResponse;

    @BeforeEach
    void setup() {
        validRequest = new DealRequest();
        validRequest.setId("D123");
        validRequest.setFromCurrency("USD");
        validRequest.setToCurrency("EUR");
        validRequest.setTimestamp(LocalDateTime.now());
        validRequest.setAmount(BigDecimal.valueOf(1000));

        dealEntity = new Deal(
            "D123",
            Currency.getInstance("USD"),
            Currency.getInstance("EUR"),
            validRequest.getTimestamp(),
            validRequest.getAmount()
        );

        expectedResponse = new DealResponse();
        expectedResponse.setId("D123");
        expectedResponse.setFromCurrency(Currency.getInstance("USD"));
        expectedResponse.setToCurrency(Currency.getInstance("EUR"));
        expectedResponse.setTimestamp(validRequest.getTimestamp());
        expectedResponse.setAmount(validRequest.getAmount());
    }

    @Test
    void createDeal_Success() {
        when(dealRepository.existsById("D123")).thenReturn(false);
        when(dealMapper.toEntity(validRequest)).thenReturn(dealEntity);
        when(dealRepository.save(any(Deal.class))).thenReturn(dealEntity);
        when(dealMapper.toResponseDto(dealEntity)).thenReturn(expectedResponse);

        DealResponse actual = dealService.create(validRequest);

        assertEquals(expectedResponse, actual);
    }

    @Test
    void createDeal_DuplicateId_ThrowsException() {
        when(dealRepository.existsById("D123")).thenReturn(true);

        DealIdAlreadyExistsException ex = assertThrows(DealIdAlreadyExistsException.class,
                () -> dealService.create(validRequest));
        assertEquals("Deal ID already exists.", ex.getMessage()); // Updated expected message
    }

    @Test
    void createDeal_InvalidCurrency_ThrowsException() {
        validRequest.setFromCurrency("INVALID");

        UnsupportedCurrencyCodeException ex = assertThrows(UnsupportedCurrencyCodeException.class,
            () -> dealService.create(validRequest));
        assertTrue(ex.getMessage().contains("Invalid currency code"));
    }
}