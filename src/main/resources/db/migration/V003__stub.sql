create table activity
(
    id                  uuid primary key,
    drone_serial_number varchar(100),
    start_timestamp     timestamp,
    duration_in_sec     int,
    type                varchar(7),
    foreign key (drone_serial_number) references drone (serial_number)
);
