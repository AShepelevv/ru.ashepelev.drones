insert into drone (serial_number, model, weight_limit)
values ('A', 'Lightweight', 50.0),
       ('B', 'Lightweight', 75.0),
       ('C', 'Lightweight', 100.0),
       ('D', 'Middleweight', 150.0),
       ('E', 'Middleweight', 200.0),
       ('F', 'Cruiserweight', 250.0),
       ('G', 'Cruiserweight', 300.0),
       ('H', 'Cruiserweight', 350.0),
       ('I', 'Heavyweight', 400.0),
       ('J', 'Heavyweight', 500.0)
on conflict (serial_number) do update
    set model = excluded.model;
