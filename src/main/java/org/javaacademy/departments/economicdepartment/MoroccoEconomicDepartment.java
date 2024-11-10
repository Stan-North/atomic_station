package org.javaacademy.departments.economicdepartment;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import static java.math.BigDecimal.ZERO;
import static java.math.BigDecimal.valueOf;

import java.math.BigDecimal;

@Component
@Profile("morocco")
public class MoroccoEconomicDepartment extends EconomicDepartment {

    @Value("${app.price}")
    private double priceMultiplier;
    private static final BigDecimal INCREASED_INCOME_PER_KWT_MULTIPLIER = BigDecimal.valueOf(6);
    private static final BigDecimal LIMIT_TO_INCREASE = BigDecimal.valueOf(5_000_000_000L);


    @Override
    public BigDecimal computeYearIncomes(long countElectricity) {
        return calculateIncome(valueOf(countElectricity));
    }

    /**
     * Расчет доходов по формуле для Морокко
     */
    private BigDecimal calculateIncome(BigDecimal value) {
        BigDecimal result = ZERO;
        if (value.compareTo(LIMIT_TO_INCREASE) < 0) {
            result = value.multiply(valueOf(priceMultiplier));
        } else {
            while (true) {
                if (value.compareTo(LIMIT_TO_INCREASE) > 0) {
                    BigDecimal incomePerBillion = LIMIT_TO_INCREASE.multiply(valueOf(priceMultiplier));
                    result = result.add(incomePerBillion);
                    value = value.subtract(LIMIT_TO_INCREASE);
                } else {
                    BigDecimal incomePerBillion = value.multiply(INCREASED_INCOME_PER_KWT_MULTIPLIER);
                    result = result.add(incomePerBillion);
                    break;
                }
            }
        }
        return result;
    }
}
