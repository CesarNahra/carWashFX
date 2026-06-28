CREATE DATABASE IF NOT EXISTS db_carwash;
USE db_carwash;


CREATE TABLE client(
client_id INT PRIMARY KEY NOT NULL AUTO_INCREMENT,
client_name VARCHAR(50) NOT NULL ,
client_email VARCHAR(50) NOT NULL ,
client_phone VARCHAR(20) NOT NULL ,
client_registration_date DATETIME,
client_score INT DEFAULT 0
) engine = InnoDB;

CREATE TABLE normal_person(
client_cpf VARCHAR(14),
client_birthdate DATE,
client_id INT NOT NULL,
CONSTRAINT fk_client_id_normal_person FOREIGN KEY (client_id) REFERENCES client(client_id)
  ON UPDATE CASCADE
  ON DELETE CASCADE
) engine = InnoDB;

CREATE TABLE juridical_person(
client_cnpj VARCHAR(14),
client_state_registration_number VARCHAR(20),
client_id INT NOT NULL,
CONSTRAINT fk_client_id_juridical_person FOREIGN KEY (client_id) REFERENCES client(client_id)
 ON UPDATE CASCADE ON DELETE CASCADE
) engine = InnoDB;

CREATE TABLE vehicle_brand(
vehicle_brand_id INT PRIMARY KEY NOT NULL AUTO_INCREMENT,
vehicle_brand_name VARCHAR(50)
) engine = InnoDB;

CREATE TABLE vehicle_model(
vehicle_model_id INT PRIMARY KEY NOT NULL AUTO_INCREMENT,
vehicle_model_description VARCHAR(50),
vehicle_brand_id INT NOT NULL,
vehicle_engine_power INT NOT NULL,
vehicle_engine_gas_type ENUM ('GAS', 'ETHANOL', 'FLEX', 'DIESEL', 'GNV' , 'OTHER') NOT NULL,
vehicle_category ENUM ('SMALL', 'MEDIUM' , 'LARGE', 'MOTORBIKE', 'DEFAULT') NOT NULL,
CONSTRAINT fk_vehicle_brand_id_vehicle_model FOREIGN KEY (vehicle_brand_id) REFERENCES vehicle_brand(vehicle_brand_id)
) engine = InnoDB;

CREATE TABLE vehicle_color(
vehicle_color_id INT PRIMARY KEY NOT NULL AUTO_INCREMENT,
vehicle_color_name VARCHAR(50)
) engine = InnoDB;

CREATE TABLE vehicle(
vehicle_id INT PRIMARY KEY NOT NULL AUTO_INCREMENT,
vehicle_plate VARCHAR(50),
vehicle_observations VARCHAR(250),
client_id INT NOT NULL,
vehicle_model_id INT NOT NULL,
vehicle_color_id INT NOT NULL,
CONSTRAINT fk_client_id_vehicle FOREIGN KEY (client_id) REFERENCES client(client_id),
CONSTRAINT fk_vehicle_model_id_vehicle FOREIGN KEY (vehicle_model_id) REFERENCES vehicle_model(vehicle_model_id),
CONSTRAINT fk_vehicle_color_id_vehicle FOREIGN KEY (vehicle_color_id) REFERENCES vehicle_color(vehicle_color_id)
ON UPDATE CASCADE ON DELETE CASCADE
) engine = InnoDB;

CREATE TABLE service(
service_id INT PRIMARY KEY NOT NULL AUTO_INCREMENT,
service_description VARCHAR(100),
service_price DECIMAL(10, 2),
service_given_points INT NOT NULL,
vehicle_category_service ENUM ('SMALL', 'MEDIUM' , 'LARGE', 'MOTORBIKE', 'DEFAULT') NOT NULL
) engine = InnoDB;

CREATE TABLE service_order(
service_order_number INT PRIMARY KEY NOT NULL AUTO_INCREMENT,
service_order_total_value DECIMAL(10, 2),
service_order_date DATETIME,
service_order_discount DECIMAL(10, 2),
service_order_status ENUM ('OPENED', 'CLOSED', 'CANCELED') NOT NULL DEFAULT 'OPENED',
vehicle_id INT NOT NULL,
CONSTRAINT fk_vehicle_id_service_order FOREIGN KEY (vehicle_id) REFERENCES vehicle(vehicle_id)
ON UPDATE CASCADE ON DELETE CASCADE
) engine = InnoDB;

select * from service_order;

CREATE TABLE item_os(
item_value DECIMAL(10, 2),
item_observations VARCHAR(100),
service_order_number INT NOT NULL,
service_id INT NOT NULL,
CONSTRAINT fk_service_order_number_item_os FOREIGN KEY (service_order_number) REFERENCES service_order(service_order_number),
CONSTRAINT fk_service_id_item_os FOREIGN KEY (service_id) REFERENCES service(service_id)
    ON DELETE CASCADE
) engine = InnoDB;

insert into vehicle_color (vehicle_color_name) values ('Prata');

INSERT INTO client (client_name, client_email, client_phone, client_registration_date) VALUES ('João', 'joao@email.com', '888888', '2026-01-01');
INSERT INTO normal_person (client_cpf, client_birthdate, client_id) VALUES ('151515', '1956-06-03', 1);

INSERT INTO client (client_name, client_email, client_phone, client_registration_date) VALUES ('Maria', 'maria@email.com', '777777', '2026-01-02');
INSERT INTO client (client_name, client_email, client_phone, client_registration_date) VALUES ('Maria', 'maria@email.com', '777777', '2026-01-02');
INSERT INTO client (client_name, client_email, client_phone, client_registration_date) VALUES ('Maria', 'maria@email.com', '777777', '2026-01-02');
INSERT INTO client (client_name, client_email, client_phone, client_registration_date) VALUES ('Maria', 'maria@email.com', '777777', '2026-01-02');
INSERT INTO client (client_name, client_email, client_phone, client_registration_date) VALUES ('Maria', 'maria@email.com', '777777', '2026-01-02');
INSERT INTO client (client_name, client_email, client_phone, client_registration_date) VALUES ('Maria', 'maria@email.com', '777777', '2026-01-02');
INSERT INTO juridical_person (client_cnpj, client_state_registration_number, client_id) VALUES ('1511616', '1956-06-03', 4);

insert into vehicle_brand (vehicle_brand_name) values ('GM');

insert into vehicle_model (vehicle_model_description, vehicle_brand_id, vehicle_engine_power, vehicle_engine_gas_type, vehicle_category) values ('rápido', 1, 250, 'GAS', 'SMALL');


insert into vehicle_color (vehicle_color_name) values ('blue');

INSERT INTO vehicle (vehicle_plate, vehicle_observations, client_id, vehicle_model_id, vehicle_color_id) VALUES ('7', 'sujo', 1, 1, 1);
INSERT INTO vehicle (vehicle_plate, vehicle_observations, client_id, vehicle_model_id, vehicle_color_id) VALUES ('7', 'sujo', 1, 1, 1);
INSERT INTO vehicle (vehicle_plate, vehicle_observations, client_id, vehicle_model_id, vehicle_color_id) VALUES ('7', 'sujo', 1, 1, 1);
INSERT INTO vehicle (vehicle_plate, vehicle_observations, client_id, vehicle_model_id, vehicle_color_id) VALUES ('7', 'sujo', 1, 1, 1);
INSERT INTO vehicle (vehicle_plate, vehicle_observations, client_id, vehicle_model_id, vehicle_color_id) VALUES ('7', 'sujo', 1, 1, 1);
INSERT INTO vehicle (vehicle_plate, vehicle_observations, client_id, vehicle_model_id, vehicle_color_id) VALUES ('7', 'sujo', 1, 1, 1);
INSERT INTO vehicle (vehicle_plate, vehicle_observations, client_id, vehicle_model_id, vehicle_color_id) VALUES ('hausfjçiojlçihfsaj', 'sujo por baixo do tapete e tá fedido', 1, 1, 1);

SELECT v.vehicle_plate, v.vehicle_observations, vm.vehicle_model_description
FROM vehicle v
JOIN vehicle_model vm ON v.vehicle_model_id = vm.vehicle_model_id;

select v.vehicle_plate, v.vehicle_observations, vm.vehicle_model_description, vm.vehicle_engine_power from vehicle v join vehicle_model vm on v.vehicle_model_id = vm.vehicle_model_id;

select vm.vehicle_model_description, vm.vehicle_engine_power, vb.vehicle_brand_name from vehicle_model vm join vehicle_brand vb on vm.vehicle_brand_id = vb.vehicle_brand_id;

select v.vehicle_plate, v.vehicle_observations, vm.vehicle_model_description, vm.vehicle_engine_power, vb.vehicle_brand_name, vc.vehicle_color_name from vehicle_model vm join vehicle_brand vb on vm.vehicle_brand_id = vb.vehicle_brand_id join vehicle v on v.vehicle_model_id = vm.vehicle_model_id join vehicle_color vc on v.vehicle_color_id = vc.vehicle_color_id;

select cl.client_name, cl.client_email, cl.client_phone, cl.client_registration_date, cl.client_score, np.client_cpf, np.client_birthdate, jp.client_cnpj, jp.client_state_registration_number from client cl join normal_person np on cl.client_id = np.client_id join juridical_person jp on cl.client_id = jp.client_id;




select v.vehicle_plate, v.vehicle_observations, vm.vehicle_model_description, vb.vehicle_brand_name from vehicle v join vehicle_model vm on v.vehicle_model_id = vm.vehicle_model_id join vehicle_brand vb on vm.vehicle_brand_id = vb.vehicle_brand_id;

select * from client;



SELECT
    v.vehicle_id,
    v.vehicle_plate,
    v.vehicle_observations,
    vm.vehicle_model_description,
    vm.vehicle_engine_power,
    vm.vehicle_category,
    vb.vehicle_brand_name,
    vc.vehicle_color_name
FROM vehicle v
         JOIN vehicle_model vm ON v.vehicle_model_id = vm.vehicle_model_id
         JOIN vehicle_brand vb ON vm.vehicle_brand_id = vb.vehicle_brand_id
         JOIN vehicle_color vc ON v.vehicle_color_id = vc.vehicle_color_id



SELECT cl.client_name, cl.client_email, cl.client_phone, cl.client_registration_date, np.client_cpf, np.client_birthdate, jp.client_cnpj, jp.client_state_registration_number FROM client cl LEFT JOIN normal_person np on np.client_id = cl.client_id LEFT JOIN juridical_person jp on cl.client_id = jp.client_id;



insert into service (service_description, service_price, service_given_points, vehicle_category_service) values ('Polimento de Rodas', 20.99, 50, 'SMALL')

select s.service_id, s.service_description, s.service_price, s.service_given_points, s.vehicle_category_service from service s;

select * from vehicle_model;

drop table service, service_order, item_os;


select * from client;

INSERT INTO vehicle (vehicle_plate, vehicle_observations, client_id, vehicle_model_id, vehicle_color_id) VALUES ('hausfjçiojlçihfsaj', 'sujo por baixo do tapete e tá fedido', 11, 1, 1);

select * from vehicle_model;

insert into service_order(service_order_number, service_order_total_value, service_order_date, service_order_discount, service_order_status, vehicle_model_id, service_id) values (1, 20.00, '2026-01-01', 0.00, 'OPENED', 1, 1);


insert into item_os(item_value, item_observations, service_order_number, service_id) values (50.00, 'nós tá bom', 1, 1);

SELECT item.item_value, item.item_observations, s.service_id, s.service_description, s.service_price, s.service_given_points, s.vehicle_category_service FROM item_os item join service s on s.service_id = item.service_id;

insert into juridical_person(client_cnpj, client_state_registration_number, client_id) values('89052', 'AJLDSJLKJÇLKGNÇJK', 10);
insert into normal_person(client_cpf, client_birthdate, client_id) values('225', '200-08-04', 10);
insert into normal_person(client_cpf, client_birthdate, client_id) values('225', '200-08-04', 11);

select * from client;

select * from service;

select * from normal_person;

select * from vehicle_model;

select v.vehicle_owner from vehicle v;

select * from vehicle_model;

select * from item_os;


insert into item_os(item_value, item_observations, service_order_number, service_id) values (8000.00, 'oito mil real', 1, 2);

INSERT INTO normal_person (client_cpf, client_birthdate, client_id) VALUES ('151515', '1956-06-03', 21);

INSERT INTO client (client_name, client_email, client_phone, client_registration_date, client_score) VALUES ('Sarah', 'sarah@email.com', '777777', '2026-01-02', 800);


SELECT cl.*, np.*, jp.* from client cl join normal_person np on np.client_id = cl.client_id join juridical_person jp on jp.client_id = cl.client_id;

select * from vehicle where vehicle_id = 5;

SELECT v.vehicle_id, v.vehicle_plate, v.vehicle_observations, cl.client_id, cl.client_name, np.client_cpf, jp.client_cnpj, vm.vehicle_model_id, vm.vehicle_model_description, vm.vehicle_category, vm.vehicle_engine_power, vm.vehicle_engine_gas_type, vb.vehicle_brand_id, vb.vehicle_brand_name, vc.vehicle_color_id, vc.vehicle_color_name from vehicle_model vm join vehicle_brand vb on vm.vehicle_brand_id = vb.vehicle_brand_id join vehicle v on v.vehicle_model_id = vm.vehicle_model_id join vehicle_color vc on v.vehicle_color_id = vc.vehicle_color_id join client cl on v.client_id = cl.client_id left join normal_person np on np.client_id = cl.client_id left join juridical_person jp on jp.client_id = cl.client_id

select * from service_order;