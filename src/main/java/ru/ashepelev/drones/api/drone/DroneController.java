package ru.ashepelev.drones.api.drone;

import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.ashepelev.drones.api.common.Response;
import ru.ashepelev.drones.api.drone.service.DroneRegistrator;
import ru.ashepelev.drones.api.drone.service.DroneService;
import ru.ashepelev.drones.api.drone.service.load.DroneLoader;
import ru.ashepelev.drones.dto.drone.DroneDto;
import ru.ashepelev.drones.dto.drone.DroneRegistrationDto;
import ru.ashepelev.drones.dto.drone.validator.serialNumber.DroneSerialNumber;
import ru.ashepelev.drones.dto.medication.LoadedMedicationDto;
import ru.ashepelev.drones.dto.medication.MedicationLoadDto;

import javax.validation.Valid;
import java.util.List;

import static ru.ashepelev.drones.api.common.Response.success;

@RestController
@RequestMapping("/api/drone")
@RequiredArgsConstructor
@Validated
public class DroneController {
    private final DroneRegistrator droneRegistrator;
    private final DroneLoader droneLoader;
    private final DroneService droneService;

    @Schema(description = "Register new drone to fleet")
    @PutMapping("/register")
    public Response<Boolean, Object> register(
            @Parameter(description = "Drone registration data")
            @RequestBody @Valid DroneRegistrationDto data) {
        return success(droneRegistrator.registerDrone(data));
    }

    @Schema(description = "Get available drones for loading and shipping")
    @GetMapping("/check/available")
    public Response<List<DroneDto>, Object> available() {
        return success(droneService.getAvailable());
    }

    @Schema(description = "Get battery capacity of specific drone")
    @GetMapping("/check/{droneSerialNumber}/battery")
    public Response<Double, Object> checkBatteryCapacity(
            @Parameter(description = "Serial number of drone")
            @PathVariable @DroneSerialNumber String droneSerialNumber) {
        return success(droneService.getBySerialNumber(droneSerialNumber).getBatteryCapacity());
    }

    @Schema(description = "Get medication loaded on specific drone")
    @GetMapping("/check/{droneSerialNumber}/medications")
    public Response<List<LoadedMedicationDto>, Object> checkLoadedMedication(
            @Parameter(description = "Serial number of drone")
            @PathVariable @DroneSerialNumber String droneSerialNumber) {
        return success(droneService.getLoadedMedication(droneSerialNumber));
    }

    @Schema(description = "Load specific drone with specific medications")
    @PutMapping("/load/{droneSerialNumber}")
    public Response<Boolean, Object> load(
            @Parameter(description = "Serial number of drone")
            @PathVariable @DroneSerialNumber String droneSerialNumber,
            @Parameter(description = "Data about medications to load")
            @RequestBody @Valid List<MedicationLoadDto> loadData) {
        return success(droneLoader.load(droneSerialNumber, loadData));
    }
}
