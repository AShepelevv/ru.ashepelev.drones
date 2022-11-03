package ru.ashepelev.drones.dto.drone;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.ashepelev.drones.dto.drone.validator.serialNumber.DroneSerialNumber;
import ru.ashepelev.drones.dto.validator.doubleRange.DoubleRange;
import ru.ashepelev.drones.entity.drone.constants.DroneModel;

import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DroneRegistrationDto {
    @NotNull
    @DroneSerialNumber
    String droneSerialNumber;

    @NotNull
    DroneModel droneModel;

    @NotNull
    @DoubleRange(min = 0.0, message = "Drone weight limit must be positive")
    Double weightLimit;
}
