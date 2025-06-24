package com.progressoft.assignment.model.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class DealRequest {
    @NotBlank
    private String id;

    @Pattern(regexp = "^[A-Z]{3}$", message = "Currency code must be a 3-letter ISO code")
    @NotNull
    private String fromCurrency;

    @Pattern(regexp = "^[A-Z]{3}$", message = "Currency code must be a 3-letter ISO code")
    @NotNull
    private String toCurrency;

    @NotNull
    private LocalDateTime timestamp;

    @NotNull
    @Positive
    private BigDecimal amount;
}