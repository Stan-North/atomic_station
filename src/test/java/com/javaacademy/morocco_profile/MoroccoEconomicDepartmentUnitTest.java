package com.javaacademy.morocco_profile;

import com.javaacademy.departments.economicdepartment.MoroccoEconomicDepartment;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.math.BigDecimal;
import java.math.RoundingMode;

@ActiveProfiles("morocco")
@SpringBootTest
public class MoroccoEconomicDepartmentUnitTest {
    @Autowired
    MoroccoEconomicDepartment moroccoEconomicDepartment;

    @Test
    public void calculateSuccess1() {
        BigDecimal result = BigDecimal.valueOf(5_000_000_000L);
        Assertions.assertEquals(result, moroccoEconomicDepartment.computeYearIncomes(1_000_000_000L)
                .setScale(0, RoundingMode.DOWN));
    }

    @Test
    public void calculateSuccess2() {
        BigDecimal result = BigDecimal.valueOf(31_000_000_000L);
        Assertions.assertEquals(result, moroccoEconomicDepartment.computeYearIncomes(6_000_000_000L)
                .setScale(0, RoundingMode.DOWN));
    }
}
