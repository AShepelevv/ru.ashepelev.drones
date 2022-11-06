package ru.ashepelev.drones.api.medication;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.ashepelev.drones.api.common.Response;
import ru.ashepelev.drones.api.medication.service.MedicationService;
import ru.ashepelev.drones.dto.medication.MedicationDto;

import java.util.List;

import static ru.ashepelev.drones.api.common.Response.success;

@RequestMapping("/api/medication")
@RestController
@RequiredArgsConstructor
public class MedicationController {
    private final MedicationService medicationService;

    @GetMapping("/all")
    public Response<List<MedicationDto>, Object> all() {
        return success(medicationService.getAll());
    }
}
