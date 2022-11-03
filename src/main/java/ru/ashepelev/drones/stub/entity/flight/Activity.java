package ru.ashepelev.drones.stub.entity.flight;

import lombok.*;
import org.hibernate.annotations.GenericGenerator;
import ru.ashepelev.drones.stub.entity.flight.constant.ActivityType;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

import static javax.persistence.EnumType.STRING;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@ToString
@Table(name = "activity")
@Entity
public class Activity {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator"
    )
    private UUID id;
    private String droneSerialNumber;
    @Enumerated(STRING)
    private ActivityType type;
    private LocalDateTime startTimestamp;
    private int durationInSec;
}
