/*TABLA REGIONES*/
INSERT INTO regiones(id, nombre) VALUES(1,'Lima');
INSERT INTO regiones(id, nombre) VALUES(2,'Chaclacayo');
INSERT INTO regiones(id, nombre) VALUES(3,'Lurigancho');
INSERT INTO regiones(id, nombre) VALUES(4,'Santa Anita');
INSERT INTO regiones(id, nombre) VALUES(5,'Los Olivos');
INSERT INTO regiones(id, nombre) VALUES(6,'La Molina');
INSERT INTO regiones(id, nombre) VALUES(7,'Puente Piedra');
INSERT INTO regiones(id, nombre) VALUES(8,'San Juan de Miraflores');


/*TABLA CLIENTES*/
INSERT INTO clientes(region_id, nombre, apellido, email, create_at) VALUES (1, 'Vanderlei','Pérez','correo1@gmail.com','2022-05-22');
INSERT INTO clientes(region_id, nombre, apellido, email, create_at) VALUES (2, 'Rogger','Camarena','correo2@gmail.com','2022-05-22');
INSERT INTO clientes(region_id, nombre, apellido, email, create_at) VALUES (1, 'Hiro','Hamada','correo3@gmail.com','2022-05-22');
INSERT INTO clientes(region_id, nombre, apellido, email, create_at) VALUES (3, 'Ruggero','Pascuarelli','correo4@gmail.com','2022-05-22');
INSERT INTO clientes(region_id, nombre, apellido, email, create_at) VALUES (2, 'Simbar','Dalas','correo5@gmail.com','2022-05-22');
INSERT INTO clientes(region_id, nombre, apellido, email, create_at) VALUES (7, 'Ruggero','Pascuarelli','correo6@gmail.com','2022-05-22');
INSERT INTO clientes(region_id, nombre, apellido, email, create_at) VALUES (8, 'Simbar','Dalas','correo7@gmail.com','2022-05-22');

/* USUARIOS Y ROLES */
INSERT INTO `usuarios` (username, password, enabled, nombre, apellido, email) VALUES ('vander','$2a$10$C3Uln5uqnzx/GswADURJGOIdBqYrly9731fnwKDaUdBkt/M3qvtLq',1,'Vander','Pérez','vander@gmail.com');
INSERT INTO `usuarios` (username, password, enabled, nombre, apellido, email) VALUES ('admin','$2a$10$RmdEsvEfhI7Rcm9f/uZXPebZVCcPC7ZXZwV51efAvMAp1rIaRAfPK',1,'John','Doe','admin@gmail.com');

INSERT INTO `roles` (nombre) VALUES ('ROLE_USER');
INSERT INTO `roles` (nombre) VALUES ('ROLE_ADMIN');

INSERT INTO `usuarios_roles` (usuario_id, role_id) VALUES (1, 1);
INSERT INTO `usuarios_roles` (usuario_id, role_id) VALUES (2, 2);
INSERT INTO `usuarios_roles` (usuario_id, role_id) VALUES (2, 1);

/* PRODUCTOS*/
INSERT INTO productos (nombre, precio, create_at) VALUES('Panasonic Pantalla LCD', 1000,NOW());
INSERT INTO productos (nombre, precio, create_at) VALUES('Sony Camara digital DSC-W320B', 500,NOW());
INSERT INTO productos (nombre, precio, create_at) VALUES('Apple iPod shuffle', 800, NOW());
INSERT INTO productos (nombre, precio, create_at) VALUES('Sony Notebook Z110', 3000,NOW());
INSERT INTO productos (nombre, precio, create_at) VALUES('Hewlett Packard Multifuncional F2280', 500,NOW());
INSERT INTO productos (nombre, precio, create_at) VALUES('Bianchi Bicicleta Aro 26', 100,NOW());
INSERT INTO productos (nombre, precio, create_at) VALUES('Mica Comoda 5 Cajones', 350,NOW());

/* FACTURAS */
INSERT INTO facturas (descripcion, observacion, cliente_id, create_at) VALUES('Factura equipos de oficina', null, 1, NOW());

INSERT INTO facturas_items (cantidad, factura_id, producto_id) VALUES(1, 1, 1);
INSERT INTO facturas_items (cantidad, factura_id, producto_id) VALUES(2, 1, 4);
INSERT INTO facturas_items (cantidad, factura_id, producto_id) VALUES(1, 1, 5);
INSERT INTO facturas_items (cantidad, factura_id, producto_id) VALUES(1, 1, 7);

INSERT INTO facturas (descripcion, observacion, cliente_id, create_at) VALUES('Factura Bicicleta', 'Alguna nota importante!', 1, NOW());
INSERT INTO facturas_items (cantidad, factura_id, producto_id) VALUES(3, 2, 6);