package com.javaacademy;

import com.javaacademy.departments.SecurityDepartment;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class SecurityDepartmentUnitTest {

    @Test
    public void addCounterSuccess() {
        SecurityDepartment securityDepartment = new SecurityDepartment();
        securityDepartment.addAccident();
        assertEquals(1, securityDepartment.getCountAccidents());
    }

    @Test
    public void resetSuccess() {
        SecurityDepartment securityDepartment = new SecurityDepartment();
        securityDepartment.addAccident();
        securityDepartment.reset();
        assertEquals(0, securityDepartment.getCountAccidents());
    }
}
