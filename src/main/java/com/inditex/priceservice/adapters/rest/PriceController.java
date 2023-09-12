package com.inditex.priceservice.adapters.rest;

import com.inditex.priceservice.adapters.exceptions.PriceNotFoundException;
import com.inditex.priceservice.domain.model.Price;
import com.inditex.priceservice.domain.service.PriceService;
import io.swagger.v3.oas.annotations.Parameter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/api")
public class PriceController {
    @Autowired
    private PriceService priceService;

    @GetMapping("/getPrice")
    public Price getPrice(@Parameter(description = "Application Date", example = "2020-06-15T00:00:01")
                                          @RequestParam(value = "applicationDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime applicationDate,
                          @Parameter(description = "Product ID", example = "35455")
                                          @RequestParam(value = "productId") Long productId,
                          @Parameter(description = "Brand ID", example = "1")
                                          @RequestParam(value = "brandId") Long identifier) {
        return priceService.getPriceByProductAndDate(productId, applicationDate, identifier)
                .orElseThrow(() -> new PriceNotFoundException("No records matching the input parameters found."));
    }
    @GetMapping("/")
    public ResponseEntity<String> healthcheck() {
        return ResponseEntity.ok("OK");

    }
}