package ru.ashepelev.drones.dto.medication;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.ashepelev.drones.entity.droneMedication.DroneMedication;

@Schema(description = "Data of specific loaded on drone medication")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LoadedMedicationDto {
    @Schema(description = "Count of units")
    private int count;
    @Schema(description = "Medication data")
    private MedicationDto medicationDto;

    public static LoadedMedicationDto from(DroneMedication droneMedication) {
        return LoadedMedicationDto.builder()
                .count(droneMedication.getCount())
                .medicationDto(MedicationDto.from(droneMedication.getMedication()))
                .build();
    }
}
