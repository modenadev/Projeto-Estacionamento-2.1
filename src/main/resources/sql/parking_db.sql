create database parking_db;
use parking_db;

create table vehicle(
                        id int not null auto_increment,
                        plate varchar(8),
                        category varchar(30),
                        type_vehicle varchar(30),
                        active boolean,
                        primary key (id)
);

create table ticket(
                       id int not null auto_increment,
                       vehicle int,
                       entry_date datetime,
                       gate_entry int,
                       exit_date datetime,
                       gate_exit int,
                       price double,
                       parked boolean default false,
                       primary key (id),
                       foreign key (vehicle) references vehicle(id)
);

create table parking_spot(
                             id int not null auto_increment,
                             reserved boolean default false,
                             vacancy_occupied boolean default false,
                             occupied_by int,
                             active_vacancy boolean default true,
                             ticket_id int,
                             parked boolean default false,
                             primary key (id),
                             foreign key (occupied_by) references vehicle(id),
                             foreign key (ticket_id) references ticket(id)
);

SET @rownum = 0;
INSERT INTO parking_spot (id, reserved, vacancy_occupied, active_vacancy)
SELECT @rownum := @rownum + 1 AS number,
CASE
    WHEN @rownum <= 200 THEN 1
    ELSE 0
END AS reserved,
FALSE AS vacancy_occupied,
TRUE AS active_vacancy
FROM (
    SELECT 1 UNION SELECT 2 UNION SELECT 3 UNION SELECT 4 UNION SELECT 5
    UNION SELECT 6 UNION SELECT 7 UNION SELECT 8 UNION SELECT 9 UNION SELECT 10
) AS t1,
(
    SELECT 1 UNION SELECT 2 UNION SELECT 3 UNION SELECT 4 UNION SELECT 5
    UNION SELECT 6 UNION SELECT 7 UNION SELECT 8 UNION SELECT 9 UNION SELECT 10
) AS t2,
(
    SELECT 1 UNION SELECT 2 UNION SELECT 3 UNION SELECT 4 UNION SELECT 5
    UNION SELECT 6 UNION SELECT 7 UNION SELECT 8 UNION SELECT 9 UNION SELECT 10
) AS t3,
(
    SELECT 1 UNION SELECT 2 UNION SELECT 3 UNION SELECT 4 UNION SELECT 5
    UNION SELECT 6 UNION SELECT 7 UNION SELECT 8 UNION SELECT 9 UNION SELECT 10
) AS t4
WHERE @rownum < 500;