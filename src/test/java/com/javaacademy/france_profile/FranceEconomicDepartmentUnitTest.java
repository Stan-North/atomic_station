package com.javaacademy.france_profile;

import com.javaacademy.departments.economicdepartment.FranceEconomicDepartment;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.math.BigDecimal;
import java.math.RoundingMode;

@ActiveProfiles("france")
@SpringBootTest
public class FranceEconomicDepartmentUnitTest {
    @Autowired
    FranceEconomicDepartment franceEconomicDepartment;

    @Test
    public void calculateSuccess1() {
        BigDecimal first = BigDecimal.valueOf(1_000_000_000).multiply(BigDecimal.valueOf(0.5));
        BigDecimal secondYearMultiplier = BigDecimal.valueOf(0.5).multiply(BigDecimal.valueOf(0.99));
        BigDecimal second = BigDecimal.valueOf(1_000_000_000).multiply(secondYearMultiplier);
        BigDecimal result = first.add(second).setScale(0, RoundingMode.DOWN);

        Assertions.assertEquals(result, franceEconomicDepartment.computeYearIncomes(2_000_000_000L)
                .setScale(0, RoundingMode.DOWN));
    }

    @Test
    public void calculateSuccess2() {
        BigDecimal result = BigDecimal.valueOf(1_000_000_000L).multiply(BigDecimal.valueOf(0.5))
                .setScale(0, RoundingMode.DOWN);
        Assertions.assertEquals(result, franceEconomicDepartment.computeYearIncomes(1_000_000_000L)
                .setScale(0, RoundingMode.DOWN));
    }
}
