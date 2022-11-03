package ru.ashepelev.drones.entity.medication;

import lombok.*;
import ru.ashepelev.drones.entity.droneMedication.DroneMedication;
import ru.ashepelev.drones.entity.image.Image;

import javax.persistence.*;
import java.util.Set;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@ToString
@Table(name = "medication")
@Entity
public class Medication {
    @Id
    private String code;

    private String name;
    private double weight;

    @OneToMany(mappedBy = "medication")
    private Set<DroneMedication> droneMedications;

    @ManyToOne
    @JoinColumn(name = "imageId")
    private Image image;
}
