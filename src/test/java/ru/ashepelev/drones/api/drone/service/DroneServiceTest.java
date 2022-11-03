package ru.ashepelev.drones.api.drone.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ru.ashepelev.drones.dto.drone.DroneDto;
import ru.ashepelev.drones.dto.medication.LoadedMedicationDto;
import ru.ashepelev.drones.dto.medication.MedicationDto;
import ru.ashepelev.drones.entity.drone.Drone;
import ru.ashepelev.drones.entity.drone.DroneRepository;
import ru.ashepelev.drones.entity.droneMedication.DroneMedication;
import ru.ashepelev.drones.entity.droneMedication.DroneMedicationRepository;
import ru.ashepelev.drones.entity.image.Image;
import ru.ashepelev.drones.entity.medication.Medication;
import ru.ashepelev.drones.exception.drone.NoSuchDroneException;

import static java.util.List.of;
import static java.util.Optional.empty;
import static java.util.Optional.ofNullable;
import static java.util.UUID.randomUUID;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;
import static ru.ashepelev.drones.entity.drone.constants.DroneModel.Lightweight;
import static ru.ashepelev.drones.entity.drone.constants.DroneState.IDLE;
import static ru.ashepelev.drones.utils.CollectionUtils.map;

@ExtendWith({SpringExtension.class})
@Import({DroneService.class})
@TestPropertySource(properties = { "drone.min-battery-for-load=17.0" })
class DroneServiceTest {
    @Autowired
    private DroneService droneService;
    @MockBean
    private DroneRepository droneRepository;
    @MockBean
    private DroneMedicationRepository droneMedicationRepository;

    @Test
    void getAvailable() {
        var drone1 = Drone.builder().batteryCapacity(15.0).weightLimit(0.0).build();
        var drone2 = Drone.builder().batteryCapacity(17.0).weightLimit(0.0).build();
        var drone3 = Drone.builder().batteryCapacity(20.0).weightLimit(0.0).build();
        doReturn(of(drone1, drone2, drone3)).when(droneRepository).findAllByState(any());

        var actual = droneService.getAvailable();

        assertThat(actual).asList().containsExactly(DroneDto.builder().batteryCapacity(20.0).weightLimit(0.0).build());
        verify(droneRepository).findAllByState(IDLE);
    }

    @Test
    void getBySerialNumber() {
        var drone = Drone.builder()
                .serialNumber("SN1")
                .model(Lightweight)
                .weightLimit(77.0)
                .batteryCapacity(35.0)
                .build();
        doReturn(ofNullable(drone)).when(droneRepository).findBySerialNumber("SN1");
        doReturn(empty()).when(droneRepository).findBySerialNumber("SN2");

        assertThat(droneService.getBySerialNumber("SN1")).isEqualTo(DroneDto.builder()
                        .serialNumber("SN1")
                        .model(Lightweight)
                        .weightLimit(77.0)
                        .batteryCapacity(35.0)
                .build());
        var exception = assertThrows(NoSuchDroneException.class,
                () -> droneService.getBySerialNumber("SN2"));
        assertThat(exception.getMessage()).isEqualTo("No such registered drone with serialNumber=SN2");
    }

    @Test
    void getLoadedMedication() {
        var drone = Drone.builder()
                .serialNumber("SN1")
                .model(Lightweight)
                .weightLimit(77.0)
                .batteryCapacity(35.0)
                .build();
        doReturn(ofNullable(drone)).when(droneRepository).findBySerialNumber("SN1");
        doReturn(empty()).when(droneRepository).findBySerialNumber("SN2");

        assertThrows(NoSuchDroneException.class, () -> droneService.getLoadedMedication("SN2"));

        var imageId1 = randomUUID();
        var imageId2 = randomUUID();

        var image1 = Image.builder().id(imageId1).build();
        var image2 = Image.builder().id(imageId2).build();

        var medication1 = Medication.builder().code("C1").name("N1").weight(1.0).image(image1).build();
        var medication2 = Medication.builder().code("C2").name("N2").weight(2.0).image(image2).build();

        var dm1 = DroneMedication.builder().medication(medication1).count(2).build();
        var dm2 = DroneMedication.builder().medication(medication2).count(3).build();

        doReturn(of(dm1, dm2)).when(droneMedicationRepository).getDroneMedicationsByDroneSerialNumber("SN1");

        assertThat(map(droneService.getLoadedMedication("SN1"), LoadedMedicationDto::getCount))
                .containsExactly(2, 3);

        assertThat(map(map(droneService.getLoadedMedication("SN1"),
                LoadedMedicationDto::getMedicationDto), MedicationDto::getImageId))
                .containsExactly(imageId1, imageId2);
    }
}
