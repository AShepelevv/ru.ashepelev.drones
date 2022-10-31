package ru.ashepelev.drones.dto.medication;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.ashepelev.drones.entity.droneMedication.DroneMedication;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LoadedMedicationDto {
    private int count;
    private MedicationDto medicationDto;

    public static LoadedMedicationDto from(DroneMedication droneMedication) {
        return LoadedMedicationDto.builder()
                .count(droneMedication.getCount())
                .medicationDto(MedicationDto.from(droneMedication.getMedication()))
                .build();
    }
}
