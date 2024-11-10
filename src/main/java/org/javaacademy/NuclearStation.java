package org.javaacademy;

import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.javaacademy.departments.ReactorDepartment;
import org.javaacademy.departments.SecurityDepartment;
import org.javaacademy.departments.economicdepartment.EconomicDepartment;
import org.javaacademy.exceptions.NuclearFuelIsEmptyException;
import org.javaacademy.exceptions.ReactorWorkException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import static java.math.BigDecimal.*;

import java.math.BigDecimal;
import java.text.DecimalFormat;

@Component
@Slf4j
public class NuclearStation {
    private static final String NUCLEAR_STATION_STARTED_WORK = "Атомная станция начала работу";
    private static final int YEAR_LOOP_COUNTER = 365;
    private static final String GENERATE_ENERGY_ERROR_MESSAGE = "Внимание! Происходят работы на атомной станции! " +
            "Электричества нет!";
    private static final String YEAR_WORK_RESULT = "Атомная станция закончила работу. " +
            "За год Выработано %s киловатт/часов";
    private static final String ACCIDENT_BY_ALL_TIME = "Количество инцидентов за всю работу станции: %d";
    private static final String ACCIDENT_BY_YEAR = "Количество инцидентов за год: %d";
    private static final DecimalFormat DECIMAL_FORMAT = new DecimalFormat("#,##0.00");
    private static final String COUNTRY_MESSAGE  = "Действие происходит в стране: %s";
    private static final String YEAR_INCOME_MESSAGE = "Доход за год составил %s %s";

    @NonNull
    private final ReactorDepartment reactorDepartment;
    @NonNull
    private final SecurityDepartment securityDepartment;
    @NonNull
    private final EconomicDepartment economicDepartment;

    private BigDecimal amountOfGeneratedEnergy = ZERO;
    private int accidentCountAllTime;
    @Value("${app.country}")
    private String country;
    @Value("${app.currency}")
    private String currency;

    public NuclearStation(@NonNull ReactorDepartment reactorDepartment,
                          @NonNull @Lazy SecurityDepartment securityDepartment,
                          @NonNull EconomicDepartment economicDepartment) {
        this.reactorDepartment = reactorDepartment;
        this.securityDepartment = securityDepartment;
        this.economicDepartment = economicDepartment;
    }

    private void incrementAccident(int count) {
        accidentCountAllTime += count;
    }

    /**
     * Запуск станции на год
     */
    private void startYear() {
        BigDecimal amountOfEnergyPerYear = ZERO;
        log.info(NUCLEAR_STATION_STARTED_WORK);
        for (int i = 0; i < YEAR_LOOP_COUNTER; i++) {
            try {
                amountOfEnergyPerYear = amountOfEnergyPerYear.add(reactorDepartment.run());
            } catch (NuclearFuelIsEmptyException | ReactorWorkException exception) {
                securityDepartment.addAccident();
                log.info(GENERATE_ENERGY_ERROR_MESSAGE);
            } finally {
                reactorDepartment.stop();
            }
        }
        incrementAccident(securityDepartment.getCountAccidents());
        amountOfGeneratedEnergy = amountOfGeneratedEnergy.add(amountOfEnergyPerYear);
        loggingInfo(amountOfEnergyPerYear);
        securityDepartment.reset();
    }

    /**
     * Запуск станции на нужное количество лет
     */
    public void start(int year) {
        log.info(String.format(COUNTRY_MESSAGE, country));
        for (int i = 0; i < year; i++) {
            startYear();
        }
        log.info(String.format(ACCIDENT_BY_ALL_TIME, accidentCountAllTime));
    }

    /**
     * Логирование информации по итогу года
     */
    private void loggingInfo(BigDecimal amountOfEnergyPerYear) {
        log.info(String.format(YEAR_WORK_RESULT, DECIMAL_FORMAT.format(amountOfEnergyPerYear)));
        log.info(String.format(ACCIDENT_BY_YEAR, securityDepartment.getCountAccidents()));
        log.info(String.format(YEAR_INCOME_MESSAGE,
                DECIMAL_FORMAT.format(economicDepartment.computeYearIncomes(amountOfEnergyPerYear.longValue())),
                currency));
    }
}
