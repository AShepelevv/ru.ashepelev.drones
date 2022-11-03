package ru.ashepelev.drones.entity.drone;

import lombok.*;
import ru.ashepelev.drones.entity.drone.constants.DroneModel;
import ru.ashepelev.drones.entity.drone.constants.DroneState;
import ru.ashepelev.drones.entity.droneMedication.DroneMedication;

import javax.persistence.*;
import java.util.Set;

import static javax.persistence.EnumType.STRING;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@ToString
@Table(name = "drone")
@Entity
public class Drone {
    @Id
    private String serialNumber;

    @Enumerated(STRING)
    private DroneModel model;

    @Enumerated(STRING)
    private DroneState state;

    private double weightLimit;
    private double batteryCapacity;

    @OneToMany(mappedBy = "drone")
    private Set<DroneMedication> droneMedications;
}
