/*LLENADO TABLA REGIONES*/
INSERT INTO regiones(id, nombre) VALUES(1,'Sudamérica');
INSERT INTO regiones(id, nombre) VALUES(2,'Centroamérica');
INSERT INTO regiones(id, nombre) VALUES(3,'Norteamérica');
INSERT INTO regiones(id, nombre) VALUES(4,'Europa');
INSERT INTO regiones(id, nombre) VALUES(5,'Asia');
INSERT INTO regiones(id, nombre) VALUES(6,'Africa');
INSERT INTO regiones(id, nombre) VALUES(7,'Oceania');
INSERT INTO regiones(id, nombre) VALUES(8,'Antártida');


/*LLENADO TABLA CLIENTES*/
INSERT INTO clientes(region_id, nombre, apellido, email, create_at) VALUES (1, 'Vanderlei','Pérez','correo1@gmail.com','2022-05-22');
INSERT INTO clientes(region_id, nombre, apellido, email, create_at) VALUES (2, 'Rogger','Camarena','correo2@gmail.com','2022-05-22');
INSERT INTO clientes(region_id, nombre, apellido, email, create_at) VALUES (1, 'Hiro','Hamada','correo3@gmail.com','2022-05-22');
INSERT INTO clientes(region_id, nombre, apellido, email, create_at) VALUES (3, 'Ruggero','Pascuarelli','correo4@gmail.com','2022-05-22');
INSERT INTO clientes(region_id, nombre, apellido, email, create_at) VALUES (2, 'Simbar','Dalas','correo5@gmail.com','2022-05-22');
INSERT INTO clientes(region_id, nombre, apellido, email, create_at) VALUES (7, 'Ruggero','Pascuarelli','correo6@gmail.com','2022-05-22');
INSERT INTO clientes(region_id, nombre, apellido, email, create_at) VALUES (8, 'Simbar','Dalas','correo7@gmail.com','2022-05-22');


