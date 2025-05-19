-- Configuración inicial
SET NAMES 'UTF8MB4';
DROP DATABASE IF EXISTS SISCCEL;
CREATE DATABASE SISCCEL DEFAULT CHARACTER SET UTF8MB4;
USE SISCCEL;

-- Eliminar tablas en orden correcto
DROP TABLE IF EXISTS buzon_contacto;
DROP TABLE IF EXISTS DetallePedido;
DROP TABLE IF EXISTS Pedidos;
DROP TABLE IF EXISTS Inventarios;
DROP TABLE IF EXISTS Carrito;
DROP TABLE IF EXISTS Productos;
DROP TABLE IF EXISTS Categorias;
DROP TABLE IF EXISTS EstatusPedido;
DROP TABLE IF EXISTS Usuarios;
DROP TABLE IF EXISTS perfiles;

-- Creación de tablas
CREATE TABLE perfiles (
    idPerfil INT AUTO_INCREMENT PRIMARY KEY,
    nombrePerfil VARCHAR(50) NOT NULL UNIQUE
);

CREATE TABLE Usuarios (
    idUsuario INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(50) NOT NULL,
    apellidoPaterno VARCHAR(50) NOT NULL,
    apellidoMaterno VARCHAR(50),
    email VARCHAR(100) NOT NULL UNIQUE,
    contrasena VARCHAR(255) NOT NULL,
    direccion VARCHAR(300) NOT NULL,
    telefono VARCHAR(10) NOT NULL,
    idPerfil INT NOT NULL,
    FOREIGN KEY (idPerfil) REFERENCES perfiles(idPerfil)
);

CREATE TABLE Categorias (
    idCategoria INT AUTO_INCREMENT PRIMARY KEY,
    nombreCategoria VARCHAR(50) NOT NULL UNIQUE
);

CREATE TABLE Productos (
    idProducto INT AUTO_INCREMENT PRIMARY KEY,
    nombreProducto VARCHAR(100) NOT NULL UNIQUE,
    descripcion VARCHAR(500) NOT NULL,
    precio DECIMAL(10,2) NOT NULL CHECK(precio >= 0),
    imagen VARCHAR(50) NOT NULL,
    sku VARCHAR(50) NOT NULL UNIQUE,
    fechaCreacion DATETIME DEFAULT CURRENT_TIMESTAMP,
    idCategoria INT NOT NULL,
    FOREIGN KEY (idCategoria) REFERENCES Categorias(idCategoria)
);

CREATE TABLE Carrito (
    idCarrito INT AUTO_INCREMENT PRIMARY KEY,
    idUsuario INT NOT NULL,
	 idProducto INT NOT NULL,
	 cantidadProductos INT NOT NULL CHECK(cantidadProductos > 0),
    subtotal DOUBLE NOT NULL CHECK(subtotal >= 0),
    FOREIGN KEY (idUsuario) REFERENCES Usuarios(idUsuario),
    FOREIGN KEY (idProducto) REFERENCES Productos(idProducto)
);


CREATE TABLE EstatusPedido (
    idEstatusPedido INT AUTO_INCREMENT PRIMARY KEY,
    nombreEstatus VARCHAR(50) NOT NULL UNIQUE
);

CREATE TABLE Pedidos (
    idPedido INT AUTO_INCREMENT PRIMARY KEY,
    idUsuario INT NOT NULL,
    idEstatusPedido INT NOT NULL,
    fechaPedido DATETIME DEFAULT CURRENT_TIMESTAMP,
    totalPedido DECIMAL(10,2) NOT NULL CHECK(totalPedido >= 0),
    FOREIGN KEY (idUsuario) REFERENCES Usuarios(idUsuario),
    FOREIGN KEY (idEstatusPedido) REFERENCES EstatusPedido(idEstatusPedido),
    UNIQUE (idUsuario, fechaPedido)
);

CREATE TABLE DetallePedido (
    idDetallePedido INT AUTO_INCREMENT PRIMARY KEY,
    idPedido INT NOT NULL,
    idProducto INT NOT NULL,
    cantidadProductos INT NOT NULL CHECK(cantidadProductos >= 0),
    subtotal DOUBLE NOT NULL,
    FOREIGN KEY (idPedido) REFERENCES Pedidos(idPedido),
    FOREIGN KEY (idProducto) REFERENCES Productos(idProducto)

);

CREATE TABLE Inventarios (
    idInventario INT AUTO_INCREMENT PRIMARY KEY,
    idProducto INT NOT NULL,
    numeroExistencias INT NOT NULL,
    ultimoInventario DATETIME DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (idProducto) REFERENCES Productos(idProducto),
    UNIQUE (idProducto)  -- Solo un registro por producto
);

CREATE TABLE buzon_contacto (
    idMensajeBuzon INT AUTO_INCREMENT PRIMARY KEY,
    tipo VARCHAR(20) NOT NULL,
    mensaje VARCHAR(500) NOT NULL,
    fecha DATETIME DEFAULT CURRENT_TIMESTAMP,
    email VARCHAR(100) NOT NULL,
    nombre VARCHAR(100) NOT NULL,
    idUsuario INT,
    estado VARCHAR(20) DEFAULT 'PENDIENTE',
    FOREIGN KEY (idUsuario) REFERENCES Usuarios(idUsuario)
);