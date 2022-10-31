create table drone
(
    serial_number    varchar(100) primary key,
    model            varchar(13)      not null,
    state            varchar(10)      not null default 'IDLE',
    weight_limit     double precision not null check ( weight_limit <= 500.0 and weight_limit >= 0.0),
    battery_capacity double precision not null default 0.0 check ( battery_capacity >= 0.0 and battery_capacity <= 100.0)
);

create table image
(
    id   uuid primary key,
    data bytea not null check ( length(data) / 1048576.0 <= 1.0 ) -- 1 MB limit
);

create table medication
(
    code     text primary key check ( code similar to '\m[A-Z0-9_]+\M' ),
    name     text             not null check ( name similar to '\m[a-zA-Z0-9_\-]+\M'),
    weight   double precision not null check ( weight <= 500.0 and weight > 0.0 ),
    image_id uuid,
    foreign key (image_id) references image (id)
);

create table drone_medication
(
    id                  uuid primary key,
    drone_serial_number varchar(100) not null,
    medication_code     text         not null,
    count               integer      not null default 1,
    unique (drone_serial_number, medication_code),
    foreign key (drone_serial_number) references drone (serial_number),
    foreign key (medication_code) references medication (code)
);
