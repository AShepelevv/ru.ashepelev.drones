package ru.ashepelev.drones.api.drone.service.load.check.impl;

import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import ru.ashepelev.drones.api.drone.service.load.check.DroneLoadingCheck;
import ru.ashepelev.drones.dto.medication.MedicationLoadDto;
import ru.ashepelev.drones.entity.drone.Drone;
import ru.ashepelev.drones.entity.medication.Medication;

import java.util.List;
import java.util.Map;

import static ru.ashepelev.drones.utils.Precision.of;

@Order(3)
@Component
public class DroneWeightLimitCheck implements DroneLoadingCheck {
    @Override
    public void check(Drone drone,
                      List<MedicationLoadDto> medicationLoads,
                      Map<String, Medication> medicationsByCodes) {
        var totalWeight = medicationLoads.stream()
                .map(load -> load.getCount() * medicationsByCodes.get(load.getMedicationCode()).getWeight())
                .reduce(0.0, Double::sum);

        if (of(totalWeight).isGreaterThan(drone.getWeightLimit())) {
            throw new IllegalArgumentException("Total weight of medication must be less than drone's weight limit");
        }
    }
}
