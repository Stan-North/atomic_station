package com.javaacademy.departments;

import com.javaacademy.exceptions.NuclearFuelIsEmptyException;
import com.javaacademy.exceptions.ReactorWorkException;
import lombok.Getter;
import lombok.NonNull;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class ReactorDepartment {
    private static final String REACTOR_ALREADY_WORKING_EXCEPTION_MESSAGE = "Реактор уже работает";
    private static final String REACTOR_ALREADY_TURNED_OFF_EXCEPTION_MESSAGE = "Реактор уже работает";

    private static final BigDecimal KILOWATTS_PER_LAUNCH = BigDecimal.valueOf(10_000_000);
    private static final int LAUNCH_COUNTER_INIT_VALUE = 0;
    private static final int LAUNCH_COUNT_TO_EMPTY_FUEL = 100;

    private SecurityDepartment securityDepartment;
    @Getter
    private boolean isWorking = false;
    private int starterCounter = LAUNCH_COUNTER_INIT_VALUE;

    public ReactorDepartment(@NonNull @Lazy SecurityDepartment securityDepartment) {
        this.securityDepartment = securityDepartment;
    }

    /**
     * Запуск реактора с предварительными проверками
     */
    public BigDecimal run() {
        checkReactorStatusToLaunch();
        checkReactorCountLaunches();
        return KILOWATTS_PER_LAUNCH;
    }

    /**
     * Проверяет, работает ли реактор, если работает - выключает его, иначе - выкидывает исключение
     */
    public void stop() {
        if (isWorking) {
            isWorking = false;
        } else {
            throw new ReactorWorkException(REACTOR_ALREADY_TURNED_OFF_EXCEPTION_MESSAGE);
        }
    }

    /**
     * Проверка, работает ли реактор, если не работает - включает его, иначе выбрасывает исключение
     */
    private void checkReactorStatusToLaunch() {
        if (!isWorking) {
            isWorking = true;
        } else {
            throw new ReactorWorkException(REACTOR_ALREADY_WORKING_EXCEPTION_MESSAGE);
        }
    }

    /**
     * Проверяет, сколько запусков реактора было, если не равно количеству допустимых запусков - инкрементирует счетчик,
     * иначе - выбрасывает исключение
     */
    private void checkReactorCountLaunches() {
        if (starterCounter != LAUNCH_COUNT_TO_EMPTY_FUEL) {
            starterCounter++;
        } else {
            starterCounter = LAUNCH_COUNTER_INIT_VALUE;
            throw new NuclearFuelIsEmptyException();
        }
    }
}
