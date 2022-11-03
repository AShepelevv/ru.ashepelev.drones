package ru.ashepelev.drones.api.drone.service.load;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ru.ashepelev.drones.api.drone.service.load.check.impl.DroneBatteryCapacityCheck;
import ru.ashepelev.drones.dto.medication.MedicationLoadDto;
import ru.ashepelev.drones.entity.drone.Drone;
import ru.ashepelev.drones.entity.drone.DroneRepository;
import ru.ashepelev.drones.entity.droneMedication.DroneMedication;
import ru.ashepelev.drones.entity.droneMedication.DroneMedicationRepository;
import ru.ashepelev.drones.entity.medication.Medication;
import ru.ashepelev.drones.entity.medication.MedicationRepository;
import ru.ashepelev.drones.exception.drone.NoSuchDroneException;

import java.util.List;
import java.util.Map;

import static java.util.List.of;
import static java.util.Optional.empty;
import static java.util.Optional.ofNullable;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentCaptor.forClass;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static ru.ashepelev.drones.entity.drone.constants.DroneState.LOADING;

@ExtendWith({SpringExtension.class})
@Import({DroneLoader.class})
class DroneLoaderTest {
    @Autowired
    private DroneLoader droneLoader;
    @MockBean
    private MedicationRepository medicationRepository;
    @MockBean
    private DroneRepository droneRepository;
    @MockBean
    private DroneMedicationRepository droneMedicationRepository;
    @MockBean
    private DroneBatteryCapacityCheck check;

    @Test
    void loadNoDrone() {
        doReturn(of()).when(medicationRepository).findAllByCodeIn(any());
        doReturn(empty()).when(droneRepository).findBySerialNumber(any());
        assertThrows(NoSuchDroneException.class, () -> droneLoader.load("SN", of()));
        verify(droneRepository).findBySerialNumber("SN");
    }

    @Test
    void loadFailedCheck() {
        doReturn(of()).when(medicationRepository).findAllByCodeIn(any());
        doReturn(ofNullable(Drone.builder().build())).when(droneRepository).findBySerialNumber(any());
        doThrow(new IllegalArgumentException("TEST")).when(check).check(any(), any(), any());
        var exception = assertThrows(IllegalArgumentException.class,
                () -> droneLoader.load("SN", of()));
        assertThat(exception.getMessage()).isEqualTo("TEST");
        verify(droneRepository).findBySerialNumber("SN");
        verify(check).check(Drone.builder().build(), List.of(), Map.of());
    }

    @Test
    void loadSuccess() {

        var medication1 = Medication.builder().code("A").build();
        var medication2 = Medication.builder().code("B").build();
        doReturn(of(medication1, medication2)).when(medicationRepository).findAllByCodeIn(any());

        var drone = Drone.builder().build();
        doReturn(ofNullable(drone)).when(droneRepository).findBySerialNumber(any());
        doNothing().when(check).check(any(), any(), any());

        var medicationLoad1 = MedicationLoadDto.builder().medicationCode("A").count(2).build();
        var medicationLoad2 = MedicationLoadDto.builder().medicationCode("B").count(3).build();

        var actual = droneLoader.load("SN", of(medicationLoad1, medicationLoad2));
        assertThat(actual).isTrue();

        verify(droneRepository).findBySerialNumber("SN");
        verify(check).check(drone, List.of(medicationLoad1, medicationLoad2),
                Map.of("A", medication1, "B", medication2));

        ArgumentCaptor<DroneMedication> droneMedicationCaptor = forClass(DroneMedication.class);
        verify(droneMedicationRepository, times(2)).save(droneMedicationCaptor.capture());
        assertThat(droneMedicationCaptor.getAllValues()).hasSize(2);
        assertThat(droneMedicationCaptor.getAllValues().get(0)).isEqualTo(DroneMedication.builder()
                .drone(drone)
                .medication(medication1)
                .count(2)
                .build());
        assertThat(droneMedicationCaptor.getAllValues().get(1)).isEqualTo(DroneMedication.builder()
                .drone(drone)
                .medication(medication2)
                .count(3)
                .build());

        assertThat(drone.getState()).isEqualTo(LOADING);
    }
}
