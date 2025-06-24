package com.progressoft.assignment.mapper;

import java.util.Currency;
import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import com.progressoft.assignment.model.dto.DealRequest;
import com.progressoft.assignment.model.dto.DealResponse;
import com.progressoft.assignment.domaine.Deal;

@Mapper(componentModel = "spring")
public interface DealMapper {

    @Mapping(target = "fromCurrency", source = "fromCurrency", qualifiedByName = "stringToCurrency")
    @Mapping(target = "toCurrency", source = "toCurrency", qualifiedByName = "stringToCurrency")
    Deal toEntity(DealRequest dealDtoReq);

    @Mapping(target = "fromCurrency", source = "fromCurrency", qualifiedByName = "currencyToString")
    @Mapping(target = "toCurrency", source = "toCurrency", qualifiedByName = "currencyToString")
    DealResponse toResponseDto(Deal deal);

    List<DealResponse> toResponseDtoList(List<Deal> deals);

    @Named("stringToCurrency")
    default Currency stringToCurrency(String currencyCode) {
        return currencyCode != null ? Currency.getInstance(currencyCode) : null;
    }

    @Named("currencyToString")
    default String currencyToString(Currency currency) {
        return currency != null ? currency.getCurrencyCode() : null;
    }
}