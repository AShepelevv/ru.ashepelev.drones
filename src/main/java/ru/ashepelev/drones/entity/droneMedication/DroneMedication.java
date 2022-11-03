package ru.ashepelev.drones.entity.droneMedication;

import lombok.*;
import org.hibernate.annotations.GenericGenerator;
import ru.ashepelev.drones.entity.drone.Drone;
import ru.ashepelev.drones.entity.medication.Medication;

import javax.persistence.*;
import java.util.UUID;

import static javax.persistence.FetchType.LAZY;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@ToString
@Table(name = "drone_medication")
@Entity
public class DroneMedication {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator"
    )
    private UUID id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "droneSerialNumber")
    private Drone drone;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "medicationCode")
    private Medication medication;

    private Integer count;
}
