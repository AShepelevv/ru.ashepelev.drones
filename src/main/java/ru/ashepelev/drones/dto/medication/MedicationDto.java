package ru.ashepelev.drones.dto.medication;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.ashepelev.drones.entity.medication.Medication;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MedicationDto {
    private String code;
    private String name;
    private double weight;
    private UUID imageId;

    public static MedicationDto from(Medication medication) {
        return MedicationDto.builder()
                .code(medication.getCode())
                .name(medication.getName())
                .weight(medication.getWeight())
                .imageId(medication.getImage().getId())
                .build();
    }
}
