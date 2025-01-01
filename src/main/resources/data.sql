create table if not exists "user" (
    id int auto_increment primary key,
    name varchar(50) not null,
    email varchar(50) not null
);

insert into "user" (name, email) values
('Tadeu', 'dedsylva@gmail.com'),
('John', 'john@gmail.com');

create table if not exists flight (
    id int auto_increment primary key,
    source varchar(50) not null,
    destination varchar(50) not null,
    flight_time date not null,
    passengers int not null
);

insert into flight (source, destination, flight_time, passengers) values
 ('New York', 'Dubai', '2024-12-17', 0),
 ('Madrid', 'New York', '2024-10-22', 22),
 ('Miami', 'Ibiza', '2024-11-11', 0),
 ('London', 'Munich', '2024-12-01', 15),
 ('Rome', 'Dubai', '2024-12-17', 0);