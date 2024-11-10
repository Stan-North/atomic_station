package org.javaacademy.departments.economicdepartment;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import static java.math.BigDecimal.*;

import java.math.BigDecimal;

@Component
@Profile(value = "france")
public class FranceEconomicDepartment extends EconomicDepartment {
    @Value("${app.price}")
    private double priceMultiplier;
    private static final BigDecimal MULTIPLIER_TO_DECREASE = BigDecimal.valueOf(0.99);
    private static final BigDecimal LIMIT_TO_DECREASE = BigDecimal.valueOf(1_000_000_000L);

    @Override
    public BigDecimal computeYearIncomes(long countElectricity) {
        return calculateIncomes(BigDecimal.valueOf(countElectricity));
    }

    /**
     * Расчет доходов по формуле для Франции
     */
    private BigDecimal calculateIncomes(BigDecimal value) {
        BigDecimal result = ZERO;
        int counter = 1;
        BigDecimal baseMultiplier = valueOf(priceMultiplier);
        while (true) {
            if (value.compareTo(LIMIT_TO_DECREASE) < 0) {
                BigDecimal tempResult = value.multiply(valueOf(priceMultiplier));
                result = result.add(tempResult);
                break;
            }
            if (counter > 1) {
                for (int i = 1; i < counter; i++) {
                    baseMultiplier = baseMultiplier.multiply(MULTIPLIER_TO_DECREASE);
                }
            }
            BigDecimal loopResult = LIMIT_TO_DECREASE.multiply(baseMultiplier);
            result = result.add(loopResult);
            value = value.subtract(LIMIT_TO_DECREASE);
            counter++;
            baseMultiplier = valueOf(priceMultiplier);
        }
        return result;
    }
}
