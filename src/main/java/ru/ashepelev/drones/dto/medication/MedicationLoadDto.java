package ru.ashepelev.drones.dto.medication;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.ashepelev.drones.dto.medication.validator.code.MedicationCode;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Schema(description = "Data for loading medication")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MedicationLoadDto {
    @Schema(description = "Medication code")
    @MedicationCode
    private String medicationCode;

    @Schema(description = "Count of units")
    @NotNull
    @Min(0)
    private Integer count;
}
