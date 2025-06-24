package com.progressoft.assignment.domaine;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Currency;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Entity
@Table(name = "deals")
@Data
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class Deal {

    @Id
    private String id;

    @NotNull(message = "The source currency must not be null")
    private Currency fromCurrency;

    @NotNull(message = "The target currency must not be null")
    private Currency toCurrency;

    @NotNull(message = "The transaction timestamp must not be null")
    private LocalDateTime timestamp;

    @NotNull(message = "The transaction amount must not be null")
    @DecimalMin(value = "0.0", inclusive = false, message = "The transaction amount must be greater than zero")
    private BigDecimal amount;
}