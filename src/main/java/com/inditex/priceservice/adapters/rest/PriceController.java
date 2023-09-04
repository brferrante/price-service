package com.inditex.priceservice.adapters.rest;

import com.inditex.priceservice.domain.model.Price;
import com.inditex.priceservice.domain.service.PriceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api")
public class PriceController {
    @Autowired
    private PriceService priceService;

    @GetMapping("/getPrice")
    public ResponseEntity<Price> getPrice(@RequestParam("applicationDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime applicationDate,
                                          @RequestParam("productId") Long productId) {
        List<Price> prices = priceService.getPriceByProductAndDate(productId, applicationDate);
        if (!prices.isEmpty()) {
            return ResponseEntity.ok(prices.get(0));
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}