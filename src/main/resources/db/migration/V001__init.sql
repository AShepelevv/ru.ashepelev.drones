create table drone
(
    serial_number    varchar(100) primary key,
    model            varchar(13),
    state            varchar(10),
    weight_limit     double precision check ( weight_limit <= 500.0 and weight_limit >= 0.0),
    battery_capacity double precision check ( battery_capacity >= 0.0 and battery_capacity <= 100.0 )
);

create table image
(
    id   uuid primary key,
    data bytea check ( length(data) / 1048576.0 <= 1.0 ) -- 1 MB limit
);

create table medication
(
    code     text primary key check ( code similar to '\m[A-Z0-9_]+\M' ),
    name     text check ( name similar to '\m[a-zA-Z0-9_\-]+\M'),
    weight   double precision check ( weight <= 500.0 and weight > 0.0 ),
    image_id uuid,
    foreign key (image_id) references image (id)
);

create table drone_medication
(
    id                  uuid primary key,
    drone_serial_number varchar(100),
    medication_code     text,
    count               integer,
    unique (drone_serial_number, medication_code),
    foreign key (drone_serial_number) references drone (serial_number),
    foreign key (medication_code) references medication (code)
);
