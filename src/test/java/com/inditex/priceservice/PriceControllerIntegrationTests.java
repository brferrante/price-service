package com.inditex.priceservice;

import com.inditex.priceservice.domain.model.Price;
import com.inditex.priceservice.domain.service.PriceService;
import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.function.Executable;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.NoSuchElementException;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
public class PriceControllerIntegrationTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private PriceService priceService;

    @ParameterizedTest
    @CsvSource({
            "1, 35.50, 35455, 2020-06-14 00:00:00",
            "1, 25.45, 35455, 2020-06-14 15:00:00",
            "1, 35.50, 35455, 2020-06-14 18:30:00",
            "1, 30.50, 35455, 2020-06-15 00:00:00",
            "1, 38.95, 35455, 2020-06-15 16:00:00",
            "2, 99.99, 22222, 2020-06-21 11:00:00"
    })
    public void testPriceCalculation(Long brandId, BigDecimal expectedPrice,
                                      Long productId, String startDateTime) {
        setUpTestDataOnH2();

        // Create a new requestDateTime and startDateTime based on the provided test data
        LocalDateTime requestDate = LocalDateTime.parse(startDateTime, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));

        // Assert that the expected price matches the calculated price
        if (brandId.equals(1L)){
            Assertions.assertEquals(expectedPrice, priceService.getPriceByProductAndDate(productId, requestDate, brandId).get().getPrice());
        } else {
            Assertions.assertThrows(NoSuchElementException.class, () -> priceService.getPriceByProductAndDate(productId, requestDate, brandId).get().getPrice());
        }
    }

    private void setUpTestDataOnH2() {
        try (CSVReader reader = new CSVReader(new FileReader("src/test/resources/prices.csv"))) {
            String[] nextLine;
            while ((nextLine = reader.readNext()) != null) {
                // Extract the values from the CSV record
                Long brandId = Long.parseLong(nextLine[0]);
                String currency = nextLine[1];
                String endDate = nextLine[2];
                BigDecimal price = new BigDecimal(nextLine[3]);
                Long priceList = Long.parseLong(nextLine[4]);
                int priority = Integer.parseInt(nextLine[5]);
                Long productId = Long.parseLong(nextLine[6]);
                String startDate = nextLine[7];
                // Create a new Price entity and set its properties based on the provided test data
                Price priceEntity = new Price();
                priceEntity.setBrandId(brandId);
                priceEntity.setCurr(currency);
                priceEntity.setEndDate(LocalDateTime.parse(endDate, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
                priceEntity.setPrice(price);
                priceEntity.setPriceList(priceList);
                priceEntity.setPriority(priority);
                priceEntity.setProductId(productId);
                priceEntity.setStartDate(LocalDateTime.parse(startDate, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));

                // Save the Price entity to the database using the PriceRepository
                priceService.save(priceEntity);

            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException | CsvValidationException e) {
            throw new RuntimeException(e);
        }
    }
}