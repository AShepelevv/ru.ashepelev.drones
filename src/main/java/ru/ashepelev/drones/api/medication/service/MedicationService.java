package ru.ashepelev.drones.api.medication.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.ashepelev.drones.dto.medication.MedicationDto;
import ru.ashepelev.drones.entity.medication.MedicationRepository;

import java.util.List;

import static ru.ashepelev.drones.utils.CollectionUtils.map;

@Service
@RequiredArgsConstructor
public class MedicationService {
    private final MedicationRepository medicationRepository;

    public List<MedicationDto> getAll() {
        return map(medicationRepository.findAll(), MedicationDto::from);
    }
}
