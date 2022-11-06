package ru.ashepelev.drones.dto.drone;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.ashepelev.drones.dto.drone.validator.serialNumber.DroneSerialNumber;
import ru.ashepelev.drones.dto.validator.doubleRange.DoubleRange;
import ru.ashepelev.drones.entity.drone.constants.DroneModel;

import javax.validation.constraints.NotNull;

@Schema(description = "Drone registration data")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DroneRegistrationDto {
    @Schema(description = "Drone serial number")
    @NotNull
    @DroneSerialNumber
    String droneSerialNumber;

    @Schema(description = "Drone model")
    @NotNull
    DroneModel droneModel;

    @Schema(description = "Drone weight limit")
    @NotNull
    @DoubleRange(min = 0.0, message = "Drone weight limit must be positive")
    Double weightLimit;
}
