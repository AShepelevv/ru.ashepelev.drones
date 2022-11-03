package ru.ashepelev.drones.api.drone;

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

    @PutMapping("/register")
    public Response<Boolean, ?> register(
            @RequestBody @Valid DroneRegistrationDto data) {
        return success(droneRegistrator.registerDrone(data));
    }

    @GetMapping("/check/available")
    public Response<List<DroneDto>, ?> available() {
        return success(droneService.getAvailable());
    }

    @GetMapping("/check/{droneSerialNumber}/battery")
    public Response<Double, ?> checkBatteryCapacity(
            @PathVariable @DroneSerialNumber String droneSerialNumber) {
        return success(droneService.getBySerialNumber(droneSerialNumber).getBatteryCapacity());
    }

    @GetMapping("/check/{droneSerialNumber}/medications")
    public Response<List<LoadedMedicationDto>, ?> checkLoadedMedication(
            @PathVariable @DroneSerialNumber String droneSerialNumber) {
        return success(droneService.getLoadedMedication(droneSerialNumber));
    }

    @PutMapping("/load/{droneSerialNumber}")
    public Response<Boolean, ?> load(
            @PathVariable @DroneSerialNumber String droneSerialNumber,
            @RequestBody @Valid List<MedicationLoadDto> loadData) {
        return success(droneLoader.load(droneSerialNumber, loadData));
    }



    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<Response<?, String>> handleNotFound(NoSuchElementException ex) {
        return new ResponseEntity<>(error(ex.getMessage()), NOT_FOUND);
    }

    @ExceptionHandler({
            IllegalArgumentException.class,
            IllegalStateException.class,
            WebExchangeBindException.class,
            ValidationException.class
    })
    public ResponseEntity<Response<?, String>> handleBadRequest(RuntimeException ex) {
        return new ResponseEntity<>(error(ex.getMessage()), BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Response<?, String>> handleException(Exception ex) {
        return new ResponseEntity<>(error(ex.getMessage()), INTERNAL_SERVER_ERROR);
    }
}
