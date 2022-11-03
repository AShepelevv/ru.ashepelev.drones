package ru.ashepelev.drones.dto.medication;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.ashepelev.drones.dto.medication.validator.code.MedicationCode;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MedicationLoadDto {
    @MedicationCode
    private String medicationCode;
    @NotNull
    @Min(0)
    private Integer count;
}
