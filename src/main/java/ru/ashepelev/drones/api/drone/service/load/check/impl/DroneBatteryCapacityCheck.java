package ru.ashepelev.drones.api.drone.service.load.check.impl;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import ru.ashepelev.drones.api.drone.service.load.check.DroneLoadingCheck;
import ru.ashepelev.drones.dto.medication.MedicationLoadDto;
import ru.ashepelev.drones.entity.drone.Drone;
import ru.ashepelev.drones.entity.medication.Medication;

import java.util.List;
import java.util.Map;

import static java.lang.String.format;
import static ru.ashepelev.drones.utils.Precision.of;

@Order(1)
@Component
public class DroneBatteryCapacityCheck implements DroneLoadingCheck {
    @Value("${drone.min-battery-for-load:25.0}")
    private double minimumLoadingBatteryLevel;

    @Override
    public void check(Drone drone,
                      List<MedicationLoadDto> medicationLoads,
                      Map<String, Medication> medicationsByCodes) {
        if (of(drone.getBatteryCapacity()).isLessThan(minimumLoadingBatteryLevel)) {
            throw new IllegalStateException(format("Drone must have at least %f battery capacity to be loaded",
                    minimumLoadingBatteryLevel));
        }
    }
}
