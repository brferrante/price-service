package com.inditex.priceservice.domain.service;

import com.inditex.priceservice.domain.model.Price;
import com.inditex.priceservice.domain.repository.PriceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class PriceService {
    @Autowired
    private PriceRepository priceRepository;

    public List<Price> getPriceByProductAndDate(Long productId, LocalDateTime applicationDate) {
        return priceRepository.findByProductIdAndStartDateLessThanEqualAndEndDateGreaterThanEqualOrderByPriorityDesc(
                productId, applicationDate, applicationDate);
    }
}