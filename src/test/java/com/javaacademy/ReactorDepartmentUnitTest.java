package com.javaacademy;

import com.javaacademy.departments.ReactorDepartment;
import com.javaacademy.departments.SecurityDepartment;
import com.javaacademy.exceptions.ReactorWorkException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.math.BigDecimal;

import static java.math.BigDecimal.valueOf;
import static org.junit.jupiter.api.Assertions.*;

public class ReactorDepartmentUnitTest {

    @Test
    public void runSuccess() {
        SecurityDepartment securityDepartment = Mockito.mock(SecurityDepartment.class);
        ReactorDepartment reactorDepartment = new ReactorDepartment(securityDepartment);
        BigDecimal result = valueOf(10_000_000);
        assertEquals(result, reactorDepartment.run());
        assertTrue(reactorDepartment.isWorking());
    }

    @Test
    public void stopSuccess() {
        SecurityDepartment securityDepartment = Mockito.mock(SecurityDepartment.class);
        ReactorDepartment reactorDepartment = new ReactorDepartment(securityDepartment);
        reactorDepartment.run();
        reactorDepartment.stop();
        assertFalse(reactorDepartment.isWorking());
    }

    @Test
    public void stopFailure() {
        SecurityDepartment securityDepartment = Mockito.mock(SecurityDepartment.class);
        ReactorDepartment reactorDepartment = new ReactorDepartment(securityDepartment);
        assertThrows(ReactorWorkException.class, () -> reactorDepartment.stop());
    }
}
