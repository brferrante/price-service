package com.inditex.priceservice;

import com.inditex.priceservice.domain.model.Price;
import com.inditex.priceservice.domain.service.PriceService;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Collections;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class PriceControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PriceService priceService;

    @ParameterizedTest
    @CsvSource({
            "2020-06-14T10:00:00,35455,1,35.50,EUR", // Test Scenario 1
            "2020-06-14T16:00:00,35455,1,25.45,EUR", // Test Scenario 2
            "2020-06-14T21:00:00,35455,1,35.50,EUR", // Test Scenario 3
            "2020-06-15T10:00:00,35455,1,30.50,EUR", // Test Scenario 4
            "2020-06-16T21:00:00,35455,1,38.95,EUR"  // Test Scenario 5
    })
    public void testGetPrice(String requestDateTime, Long productId, Long brandId, String expectedPrice, String expectedCurrency) throws Exception {
        LocalDateTime requestDateTimeParsed = LocalDateTime.parse(requestDateTime);

        Price price = Price.builder()
                .brandId(brandId)
                .startDate(LocalDateTime.parse("2020-06-14T00:00:00"))
                .endDate(LocalDateTime.parse("2020-12-31T23:59:59"))
                .priceList(1L)
                .productId(productId)
                .priority(0)
                .price(expectedPrice != null ? new BigDecimal(expectedPrice) : null)
                .curr(expectedCurrency)
                .build();

        when(priceService.getPriceByProductAndDate(productId, requestDateTimeParsed)).thenReturn(Collections.singletonList(price));

        ResultActions resultActions = mockMvc.perform(get("/api/getPrice")
                        .param("applicationDate", requestDateTime)
                        .param("productId", productId.toString())
                        .param("brandId", brandId.toString())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));

        if (expectedPrice != null && expectedCurrency != null) {
            resultActions.andExpect(jsonPath("$.brandId").value(brandId))
                    .andExpect(jsonPath("$.startDate").value("2020-06-14T00:00:00"))
                    .andExpect(jsonPath("$.endDate").value("2020-12-31T23:59:59"))
                    .andExpect(jsonPath("$.priceList").value(1))
                    .andExpect(jsonPath("$.productId").value(productId))
                    .andExpect(jsonPath("$.priority").value(0))
                    .andExpect(jsonPath("$.price").value(expectedPrice))
                    .andExpect(jsonPath("$.curr").value(expectedCurrency));
        } else {
            resultActions.andExpect(content().string(""));
        }
    }
}