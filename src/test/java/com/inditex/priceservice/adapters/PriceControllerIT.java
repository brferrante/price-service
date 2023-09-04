package com.inditex.priceservice.adapters;

import com.inditex.priceservice.adapters.rest.PriceController;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
@SpringBootTest
@AutoConfigureMockMvc
public class PriceControllerIT {

    @Autowired
    private MockMvc mockMvc;

    @ParameterizedTest
    @CsvSource({"2020-06-14T10:00:00,35455,0,35.50,EUR",
            "2020-06-14T16:00:00,35455,1,25.45,EUR",
            "2020-06-14T21:00:00,35455,1,35.50,EUR",
            "2020-06-15T10:00:00,35455,1,30.50,EUR",
            "2020-06-16T21:00:00,35455,1,38.95,EUR"})
    void testIntegration(String applicationDate, Long productId, int chain, String expectedPrice, String currency) throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/getPrice")
                        .param("applicationDate", applicationDate)
                        .param("productId", String.valueOf(productId)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.productId").value(productId))
                .andExpect(MockMvcResultMatchers.jsonPath("$.brandId").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$.rateToApply").value(1)) // Assuming rate is always 1 for these tests
                .andExpect(MockMvcResultMatchers.jsonPath("$.applicationStartDate").value("2020-06-14T00:00:00"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.applicationEndDate").value("2020-12-31T23:59:59"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.finalPriceToApply").value(expectedPrice))
                .andExpect(MockMvcResultMatchers.jsonPath("$.currency").value(currency));
    }
}