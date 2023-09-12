package com.inditex.priceservice.domain.service;

import com.inditex.priceservice.adapters.exceptions.PriceNotFoundException;
import com.inditex.priceservice.domain.model.Price;
import com.inditex.priceservice.domain.repository.PriceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class PriceService {
    @Autowired
    private PriceRepository priceRepository;

    public Optional<Price> getPriceByProductAndDate(Long productId, LocalDateTime applicationDate, Long brandId) throws PriceNotFoundException {
        return Optional.ofNullable(priceRepository.findFirstByProductIdAndBrandIdAndStartDateLessThanEqualAndEndDateGreaterThanOrderByPriorityDesc(
                        productId, brandId, applicationDate, applicationDate))
                .orElseThrow(() -> new PriceNotFoundException("No records matching the input parameters found."));
    }

    public void save(Price priceEntity) {
        priceRepository.save(priceEntity);
    }
}