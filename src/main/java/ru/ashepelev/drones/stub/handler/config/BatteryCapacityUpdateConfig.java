package ru.ashepelev.drones.stub.handler.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.ashepelev.drones.entity.drone.constants.DroneState;

import java.util.Map;

import static ru.ashepelev.drones.entity.drone.constants.DroneState.*;

@Configuration
public class BatteryCapacityUpdateConfig {
    @Bean
    public Map<DroneState, Double> batteryChangeRateByDroneState() {
        return Map.of(
                IDLE, +2.0,
                LOADING, -0.1,
                LOADED, -0.1,
                DELIVERING, -3.0,
                DELIVERED, -1.0,
                RETURNING, -1.0
        );
    }
}
