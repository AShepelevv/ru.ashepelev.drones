package ru.ashepelev.drones.api.drone;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
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
import java.util.NoSuchElementException;

import static org.springframework.http.HttpStatus.NOT_FOUND;

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
    public boolean register(@RequestBody @Valid DroneRegistrationDto data) {
        return droneRegistrator.registerDrone(data);
    }

    @GetMapping("/check/available")
    public List<DroneDto> available() {
        return droneService.getAvailable();
    }

    @GetMapping("/check/{droneSerialNumber}/battery")
    public double checkBatteryCapacity(@PathVariable String droneSerialNumber) {
        return droneService.getBySerialNumber(droneSerialNumber).getBatteryCapacity();
    }

    @GetMapping("/check/{droneSerialNumber}/medications")
    public List<LoadedMedicationDto> checkLoadedMedication(@PathVariable String droneSerialNumber) {
        return droneService.getLoadedMedication(droneSerialNumber);
    }

    @PutMapping("/load/{droneSerialNumber}")
    public boolean load(@PathVariable @DroneSerialNumber String droneSerialNumber,
                        @RequestBody @Valid List<MedicationLoadDto> loadData) {
        return droneLoader.load(droneSerialNumber, loadData);
    }

    //todo: build default error response to JSON with reason explanation
    @ExceptionHandler(Exception.class)
    public ResponseEntity<RuntimeException> handleConflict(RuntimeException ex) {
        return ResponseEntity.badRequest().body(ex);
    }

    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<String> handleNotFound(NoSuchElementException ex) {
        return new ResponseEntity<>(ex.getMessage(), NOT_FOUND);
    }
}
