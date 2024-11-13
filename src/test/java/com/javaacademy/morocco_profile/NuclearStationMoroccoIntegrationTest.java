package com.javaacademy.morocco_profile;

import com.javaacademy.NuclearStation;
import com.javaacademy.departments.ReactorDepartment;
import com.javaacademy.departments.SecurityDepartment;
import com.javaacademy.departments.economicdepartment.EconomicDepartment;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.test.context.ActiveProfiles;

import java.math.BigDecimal;

@ActiveProfiles("morocco")
@SpringBootTest
public class NuclearStationMoroccoIntegrationTest {
    @Autowired
    NuclearStation nuclearStation;

    @SpyBean
    ReactorDepartment reactorDepartment;

    @SpyBean
    SecurityDepartment securityDepartment;

    @SpyBean
    EconomicDepartment economicDepartment;

    @Test
    public void startSuccess() {
        BigDecimal kwtPerLaunch = BigDecimal.valueOf(10_000_000);
        int countDaysOff = 3;
        int workingDays = 365 - countDaysOff;
        BigDecimal expected = kwtPerLaunch.multiply(BigDecimal.valueOf(workingDays));
        nuclearStation.start(1);
        int expectedDaysOff = 3;
        Assertions.assertEquals(expected, nuclearStation.getAmountOfGeneratedEnergy());
        Assertions.assertEquals(expectedDaysOff, nuclearStation.getAccidentCountAllTime());
    }

    @Test
    public void fieldValuesSuccess() {
        String country = "Морокко";
        String currency = "Дирхам";

        Assertions.assertEquals(country, nuclearStation.getCountry());
        Assertions.assertEquals(currency, nuclearStation.getCurrency());
    }
}
