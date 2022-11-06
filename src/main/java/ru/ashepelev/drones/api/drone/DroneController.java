package ru.ashepelev.drones.api.drone;

import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.WebExchangeBindException;
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
import javax.validation.ValidationException;
import java.util.List;
import java.util.NoSuchElementException;

import static org.springframework.http.HttpStatus.*;
import static ru.ashepelev.drones.api.common.Response.error;
import static ru.ashepelev.drones.api.common.Response.success;

@ControllerAdvice
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

    @ResponseStatus(NOT_FOUND)
    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<Response<Object, String>> handleNotFound(NoSuchElementException ex) {
        return new ResponseEntity<>(error(ex.getMessage()), NOT_FOUND);
    }

    @ResponseStatus(BAD_REQUEST)
    @ExceptionHandler({
            IllegalArgumentException.class,
            IllegalStateException.class,
            WebExchangeBindException.class,
            ValidationException.class
    })
    public ResponseEntity<Response<Object, String>> handleBadRequest(RuntimeException ex) {
        return new ResponseEntity<>(error(ex.getMessage()), BAD_REQUEST);
    }

    @ResponseStatus(INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Response<Object, String>> handleException(Exception ex) {
        return new ResponseEntity<>(error(ex.getMessage()), INTERNAL_SERVER_ERROR);
    }
}
