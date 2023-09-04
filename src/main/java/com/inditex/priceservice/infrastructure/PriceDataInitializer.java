package com.inditex.priceservice.infrastructure;

import com.inditex.priceservice.domain.model.Price;
import com.inditex.priceservice.domain.repository.PriceRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Component
public class PriceDataInitializer implements CommandLineRunner {
    private final PriceRepository priceRepository;
    public PriceDataInitializer(PriceRepository priceRepository) {
        this.priceRepository = priceRepository;
    }

    @Override
    public void run(String... args) {
        // Create and save Price entities with example data using builder methods
        priceRepository.save(
                Price.builder()
                        .brandId(1L)
                        .startDate(LocalDateTime.parse("2020-06-14T00:00:00"))
                        .endDate(LocalDateTime.parse("2020-12-31T23:59:59"))
                        .priceList(1L)
                        .productId(35455L)
                        .priority(0)
                        .price(new BigDecimal("35.50"))
                        .curr("EUR")
                        .build()
        );

        priceRepository.save(
                Price.builder()
                        .brandId(1L)
                        .startDate(LocalDateTime.parse("2020-06-14T15:00:00"))
                        .endDate(LocalDateTime.parse("2020-06-14T18:30:00"))
                        .priceList(2L)
                        .productId(35455L)
                        .priority(1)
                        .price(new BigDecimal("25.45"))
                        .curr("EUR")
                        .build()
        );

        priceRepository.save(
                Price.builder()
                        .brandId(1L)
                        .startDate(LocalDateTime.parse("2020-06-15T00:00:00"))
                        .endDate(LocalDateTime.parse("2020-06-15T11:00:00"))
                        .priceList(3L)
                        .productId(35455L)
                        .priority(1)
                        .price(new BigDecimal("30.50"))
                        .curr("EUR")
                        .build()
        );

        priceRepository.save(
                Price.builder()
                        .brandId(1L)
                        .startDate(LocalDateTime.parse("2020-06-15T16:00:00"))
                        .endDate(LocalDateTime.parse("2020-12-31T23:59:59"))
                        .priceList(4L)
                        .productId(35455L)
                        .priority(1)
                        .price(new BigDecimal("38.95"))
                        .curr("EUR")
                        .build()
        );
    }
}
