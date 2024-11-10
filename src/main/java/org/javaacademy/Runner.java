package org.javaacademy;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class Runner {
    private static final int YEARS = 3;
    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(Runner.class);
        NuclearStation nuclearStation = context.getBean(NuclearStation.class);
        nuclearStation.start(YEARS);
    }
}
