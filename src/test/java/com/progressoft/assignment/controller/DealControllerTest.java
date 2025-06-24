package com.progressoft.assignment.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.progressoft.assignment.model.dto.BatchProcessingSummary;
import com.progressoft.assignment.model.dto.DealRequest;
import com.progressoft.assignment.model.dto.DealResponse;
import com.progressoft.assignment.service.DealService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.Currency;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(DealController.class)
class DealControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private DealService dealService;

    @Autowired
    private ObjectMapper objectMapper;

    private DealRequest buildValidRequest() {
        DealRequest req = new DealRequest();
        req.setId("FX001");
        req.setFromCurrency("USD");
        req.setToCurrency("EUR");
        req.setTimestamp(LocalDateTime.now());
        req.setAmount(BigDecimal.valueOf(1000));
        return req;
    }

    private DealResponse buildValidResponse() {
        DealResponse resp = new DealResponse();
        resp.setId("FX001");
        resp.setFromCurrency(Currency.getInstance("USD"));
        resp.setToCurrency(Currency.getInstance("EUR"));
        resp.setTimestamp(LocalDateTime.now());
        resp.setAmount(BigDecimal.valueOf(1000));
        return resp;
    }

    @Test
    @DisplayName("POST /api/deals - succès")
    void createDeal_Success() throws Exception {
        DealRequest req = buildValidRequest();
        DealResponse resp = buildValidResponse();
        Mockito.when(dealService.create(any(DealRequest.class))).thenReturn(resp);

        mockMvc.perform(post("/api/deals")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(req)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value("FX001"));
    }

    @Test
    @DisplayName("POST /api/deals - requête invalide")
    void createDeal_InvalidRequest() throws Exception {
        DealRequest req = new DealRequest();
        req.setId(""); // id vide
        mockMvc.perform(post("/api/deals")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(req)))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("POST /api/deals/batch - succès")
    void createBatch_Success() throws Exception {
        DealRequest req = buildValidRequest();
        DealResponse resp = buildValidResponse();
        BatchProcessingSummary summary = new BatchProcessingSummary(1, 1, 0, List.of(resp), Collections.emptyList());
        Mockito.when(dealService.createBatch(anyList())).thenReturn(summary);

        mockMvc.perform(post("/api/deals/batch")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(List.of(req))))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.totalRequests").value(1))
                .andExpect(jsonPath("$.successfulDeals").value(1));
    }

    @Test
    @DisplayName("GET /api/deals - succès")
    void getAllDeals_Success() throws Exception {
        DealResponse resp = buildValidResponse();
        Mockito.when(dealService.getAllDeals()).thenReturn(Arrays.asList(resp));
        mockMvc.perform(get("/api/deals"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value("FX001"));
    }

    @Test
    @DisplayName("POST /api/deals - erreur métier (conflit)")
    void createDeal_Conflict() throws Exception {
        DealRequest req = buildValidRequest();
        Mockito.when(dealService.create(any(DealRequest.class)))
                .thenThrow(new com.progressoft.assignment.exception.DealIdAlreadyExistsException("Deal ID already exists."));
        mockMvc.perform(post("/api/deals")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(req)))
                .andExpect(status().isConflict());
    }
}


