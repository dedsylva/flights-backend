create table if not exists "user" (
    id int auto_increment primary key,
    name varchar(50) not null,
    email varchar(50) not null,
    balance float not null
);

insert into "user" (name, email, balance) values
('Tadeu', 'dedsylva@gmail.com', 2500.5),
('John', 'john@gmail.com', 3500.0);

create table if not exists flight (
    id int auto_increment primary key,
    source varchar(50) not null,
    destination varchar(50) not null,
    flight_time date not null,
    passengers int not null,
    total_capacity int not null,
    price float not null,
    profit float not null
);

insert into flight (source, destination, flight_time, passengers, total_capacity, price, profit) values
 ('New York', 'Dubai', '2024-12-17', 0, 200, 1500.0, 0.0),
 ('Madrid', 'New York', '2024-10-22', 22, 200, 1100.0, 22*1100.0),
 ('Miami', 'Ibiza', '2024-11-11', 0, 200, 2500.0, 0.0),
 ('London', 'Munich', '2024-12-01', 15, 200, 2700.0, 15*2700.0),
 ('Rome', 'Dubai', '2024-12-17', 0, 200, 2300.0, 0.0);