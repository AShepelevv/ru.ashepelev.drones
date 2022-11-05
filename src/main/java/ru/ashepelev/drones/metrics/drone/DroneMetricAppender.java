package ru.ashepelev.drones.metrics.drone;

import io.micrometer.core.instrument.Gauge;
import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.stereotype.Component;
import ru.ashepelev.drones.api.drone.service.DroneService;
import ru.ashepelev.drones.entity.drone.Drone;
import ru.ashepelev.drones.entity.drone.DroneRepository;

@Component
public class DroneMetricAppender {
    private final MeterRegistry registry;
    private final DroneService droneService;

    DroneMetricAppender(MeterRegistry registry, DroneService droneService, DroneRepository droneRepository) {
        this.registry = registry;
        this.droneService = droneService;
        droneRepository.findAll().stream()
                .map(Drone::getSerialNumber)
                .forEach(this::append);
    }

    public void append(String droneSerialNumber) {
        Gauge.builder("drone_battery",
                        () -> droneService.getBySerialNumber(droneSerialNumber).getBatteryCapacity())
                .tag("drone_serial_number", droneSerialNumber)
                .description("drone_battery")
                .register(registry);
    }
}
