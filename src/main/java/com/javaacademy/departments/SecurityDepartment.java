package com.javaacademy.departments;

import com.javaacademy.NuclearStation;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SecurityDepartment {
    private static final int ACCIDENT_INIT_VALUE = 0;

    private NuclearStation nuclearStation;
    private int accidentCountPeriod;

    public void addAccident() {
        accidentCountPeriod++;
    }

    public int getCountAccidents() {
        return accidentCountPeriod;
    }

    public void reset() {
        accidentCountPeriod = ACCIDENT_INIT_VALUE;
    }
}
