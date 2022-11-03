package ru.ashepelev.drones.dto.medication;

import org.junit.jupiter.api.Test;
import ru.ashepelev.drones.entity.image.Image;
import ru.ashepelev.drones.entity.medication.Medication;

import java.util.UUID;

import static java.util.UUID.randomUUID;
import static org.assertj.core.api.Assertions.assertThat;

class MedicationDtoTest {
    private static final UUID IMAGE_ID = randomUUID();

    @Test
    void from() {
        var image = Image.builder().id(IMAGE_ID).build();
        var medication = Medication.builder()
                .code("CODE")
                .name("NAME")
                .weight(77.0)
                .image(image)
                .build();
        var actual = MedicationDto.from(medication);
        assertThat(actual).isEqualTo(MedicationDto.builder()
                .code("CODE")
                .name("NAME")
                .weight(77.00)
                .imageId(IMAGE_ID)
                .build());
    }
}
