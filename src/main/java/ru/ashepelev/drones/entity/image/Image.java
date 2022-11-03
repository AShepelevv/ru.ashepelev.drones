package ru.ashepelev.drones.entity.image;

import lombok.*;
import org.hibernate.annotations.GenericGenerator;
import ru.ashepelev.drones.entity.medication.Medication;

import javax.persistence.*;
import java.util.Set;
import java.util.UUID;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@ToString
@Table(name = "image")
@Entity
public class Image {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator"
    )
    private UUID id;

    private byte[] data;

    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @OneToMany(mappedBy = "image")
    private Set<Medication> medications;
}
