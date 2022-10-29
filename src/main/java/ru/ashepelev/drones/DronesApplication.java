package ru.ashepelev.drones;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
public class DronesApplication {
    public static void main(String[] args) {
        SpringApplication.run(DronesApplication.class, args);
    }
}
