-- --------------------------------------------------------
-- Host:                         127.0.0.1
-- Versión del servidor:         10.6.21-MariaDB - mariadb.org binary distribution
-- SO del servidor:              Win64
-- HeidiSQL Versión:             12.10.0.7000
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8 */;
/*!50503 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

-- Volcando datos para la tabla sisccel.buzon_contacto: ~6 rows (aproximadamente)
INSERT INTO `buzon_contacto` (`idMensajeBuzon`, `tipo`, `mensaje`, `fecha`, `email`, `nombre`, `idUsuario`, `estado`) VALUES
	(1, 'QUEJA', 'Prueba', '2025-05-11 16:58:14', 'sam@mail.com', 'Samantha', NULL, 'PENDIENTE'),
	(2, 'QUEJA', 'No llegó rápido mi pedido', '2025-05-18 10:15:25', 'moises@correo.com', 'Moises Acosta', NULL, 'PENDIENTE'),
	(3, 'SUGERENCIA', 'Deberían agregar productos de Hatsune Miku', '2025-05-18 10:16:47', 'carlos@gmail.com', 'Carlos Martinez', NULL, 'PENDIENTE'),
	(4, 'SUGERENCIA', 'Deberían permitir el pickup en tiendas.', '2025-05-18 10:20:44', 'rodrigo@correo.com', 'Rodrigo ', NULL, 'PENDIENTE'),
	(5, 'SUGERENCIA', 'Deberían permitir el pickup en tiendas.', '2025-05-18 10:20:44', 'rodrigo@correo.com', 'Rodrigo ', NULL, 'PENDIENTE'),
	(6, 'QUEJA', 'Queja de usuario', '2025-05-18 10:42:46', 'jose@mail.com', 'Jose Antonio ', NULL, 'PENDIENTE');

-- Volcando datos para la tabla sisccel.carrito: ~2 rows (aproximadamente)
INSERT INTO `carrito` (`idCarrito`, `idUsuario`, `idProducto`, `cantidadProductos`, `subtotal`) VALUES
	(40, 6, 2, 1, 226),
	(59, 1, 18, 1, 300);

-- Volcando datos para la tabla sisccel.categorias: ~6 rows (aproximadamente)
INSERT INTO `categorias` (`idCategoria`, `nombreCategoria`) VALUES
	(1, 'Bebidas'),
	(2, 'CD'),
	(6, 'Chamarras'),
	(3, 'Figuras'),
	(4, 'Mangas'),
	(5, 'Revistas');

-- Volcando datos para la tabla sisccel.detallepedido: ~45 rows (aproximadamente)
INSERT INTO `detallepedido` (`idDetallePedido`, `idPedido`, `idProducto`, `cantidadProductos`, `subtotal`) VALUES
	(1, 1, 9, 1, 500),
	(2, 1, 2, 1, 226),
	(3, 1, 10, 1, 75),
	(4, 2, 5, 1, 500),
	(5, 2, 2, 1, 226),
	(6, 2, 10, 1, 75),
	(7, 6, 9, 1, 500),
	(8, 6, 2, 1, 226),
	(9, 6, 10, 1, 75),
	(10, 7, 9, 1, 500),
	(11, 7, 2, 1, 226),
	(12, 8, 9, 1, 500),
	(13, 8, 2, 1, 226),
	(14, 8, 10, 1, 75),
	(15, 9, 14, 2, 1000),
	(16, 9, 3, 2, 452),
	(17, 16, 15, 2, 150),
	(27, 20, 9, 1, 500),
	(28, 20, 10, 1, 75),
	(29, 21, 9, 1, 500),
	(30, 21, 2, 1, 226),
	(31, 21, 10, 1, 75),
	(32, 22, 9, 1, 500),
	(33, 22, 2, 1, 226),
	(34, 22, 10, 1, 75),
	(35, 23, 9, 1, 500),
	(36, 23, 2, 1, 226),
	(37, 23, 10, 1, 75),
	(38, 24, 9, 1, 500),
	(39, 24, 2, 1, 226),
	(40, 24, 10, 1, 75),
	(41, 25, 9, 1, 500),
	(42, 25, 2, 1, 226),
	(43, 25, 10, 1, 75),
	(44, 26, 23, 2, 600),
	(45, 26, 9, 1, 500),
	(46, 26, 7, 1, 500),
	(47, 26, 22, 1, 300),
	(48, 26, 2, 1, 226),
	(49, 26, 7, 1, 500),
	(50, 26, 16, 1, 999),
	(51, 26, 15, 1, 999),
	(52, 28, 23, 1, 850),
	(53, 29, 24, 1, 1200),
	(54, 30, 2, 1, 226);

-- Volcando datos para la tabla sisccel.estatuspedido: ~3 rows (aproximadamente)
INSERT INTO `estatuspedido` (`idEstatusPedido`, `nombreEstatus`) VALUES
	(3, 'Entregado'),
	(2, 'Enviado'),
	(1, 'Pendiente');

-- Volcando datos para la tabla sisccel.inventarios: ~24 rows (aproximadamente)
INSERT INTO `inventarios` (`idInventario`, `idProducto`, `numeroExistencias`, `ultimoInventario`) VALUES
	(1, 1, 10, '2025-05-18 12:38:37'),
	(2, 2, 8, '2025-05-18 12:43:54'),
	(3, 3, 11, '2025-05-14 21:24:44'),
	(4, 4, 0, '2025-05-14 21:18:35'),
	(5, 5, 9, '2025-05-18 12:38:37'),
	(6, 9, 10, '2025-05-14 21:18:42'),
	(7, 6, 9, '2025-05-18 12:38:37'),
	(8, 7, 10, '2025-05-14 21:25:24'),
	(9, 8, 10, '2025-05-14 21:25:29'),
	(10, 10, 9, '2025-05-18 12:38:37'),
	(11, 11, 9, '2025-05-18 12:38:37'),
	(12, 12, 10, '2025-05-14 21:25:57'),
	(13, 13, 10, '2025-05-14 21:19:58'),
	(14, 19, 9, '2025-05-18 12:43:41'),
	(15, 14, 10, '2025-05-14 21:20:09'),
	(16, 15, 10, '2025-05-14 21:20:12'),
	(17, 16, 10, '2025-05-14 21:32:00'),
	(18, 17, 9, '2025-05-18 12:38:37'),
	(19, 18, 4, '2025-05-18 13:30:58'),
	(20, 23, 28, '2025-05-18 13:27:38'),
	(21, 24, 15, '2025-05-18 13:27:44'),
	(22, 25, 12, '2025-05-18 13:30:37'),
	(23, 26, 30, '2025-05-18 13:32:36'),
	(24, 27, 5, '2025-05-18 13:37:42');

-- Volcando datos para la tabla sisccel.pedidos: ~16 rows (aproximadamente)
INSERT INTO `pedidos` (`idPedido`, `idUsuario`, `idEstatusPedido`, `fechaPedido`, `totalPedido`) VALUES
	(1, 1, 1, '2025-05-12 21:03:16', 801.00),
	(2, 1, 3, '2025-05-12 21:09:58', 801.00),
	(6, 1, 3, '2025-05-12 21:22:02', 801.00),
	(7, 1, 2, '2025-05-12 21:28:57', 726.00),
	(8, 1, 3, '2025-05-12 21:34:09', 801.00),
	(9, 1, 3, '2025-05-12 21:34:50', 1602.00),
	(20, 1, 2, '2025-05-12 21:39:09', 575.00),
	(21, 1, 1, '2025-05-12 21:49:11', 801.00),
	(22, 1, 2, '2025-05-12 21:52:50', 801.00),
	(23, 1, 1, '2025-05-12 21:54:48', 801.00),
	(24, 1, 1, '2025-05-12 21:57:07', 801.00),
	(25, 1, 3, '2025-05-12 21:58:16', 801.00),
	(26, 1, 1, '2025-05-18 12:38:37', 4624.00),
	(28, 1, 1, '2025-05-18 12:40:05', 850.00),
	(29, 1, 1, '2025-05-18 12:43:41', 1200.00),
	(30, 1, 1, '2025-05-18 12:43:54', 226.00);

-- Volcando datos para la tabla sisccel.perfiles: ~2 rows (aproximadamente)
INSERT INTO `perfiles` (`idPerfil`, `nombrePerfil`) VALUES
	(1, 'Administrador'),
	(2, 'Cliente');

-- Volcando datos para la tabla sisccel.productos: ~26 rows (aproximadamente)
INSERT INTO `productos` (`idProducto`, `nombreProducto`, `descripcion`, `precio`, `imagen`, `sku`, `fechaCreacion`, `idCategoria`) VALUES

	(1, 'Adidas Chinese New Year Red Jacket ', 'The new Adidas Tang Chinese New Year Jacket is a stylish and modern take on a classic. Taking inspiration from traditional Chinese jackets. LIMITED!!!', 4500.00, 'Adidas-Chinese-Red-Jacket.png', 'JCKT-001', '2025-05-18 13:37:30', 6),
	(2, 'Vino Coreano Bokbunja De Frambuesa', 'Vino Coreano Bokbunja De Frambuesa, Kook Soon Dang 375 Ml', 226.00, 'Vino Coreano.png', 'Bebida-001', '2025-05-11 20:21:14', 1),
	(3, 'INROCK -Louis Tomlinson 2023', 'Louis Tomlinson, Niall Horan INROCK Magazine May 2023 Japan', 499.00, 'Inrock-Louis-2023.png', 'RV-002', '2025-05-18 13:27:16', 5),
	(4, 'INROCK - Niall Horan 2023', ' Niall Horan cover INROCK Magazine September 2023 Japan', 630.00, 'Inrock-Niall-2023.png', 'RV-004', '2025-05-18 13:32:25', 5),
	(5, 'Harry Styles Fine Line', 'Fine Line\r\nÁlbum de Harry Styles', 500.00, 'Fine Line.png', 'FineLine-001', '2025-05-14 20:53:04', 2),
	(6, 'Harry Styles Harry\'s House', 'Harry Styles Harry\'s House Album', 500.00, 'Harry\'s House.png', 'HarrysHouse-001', '2025-05-14 20:54:28', 2),
	(7, 'Harry Styles - Harry Styles ', 'Harry Styles - Harry Styles Album', 500.00, 'Harry Styles.png', 'HarryStyles-001', '2025-05-14 20:57:03', 2),
	(8, 'Dork - Louis Tomlinson', 'Dork, October 2022 (Louis Tomlinson cover)', 850.00, 'Dork-louis.jpg', 'RV-001', '2025-05-18 11:23:19', 2),
	(9, 'Blonde', 'Blonde - Frank Ocean', 500.00, 'Blonde.png', 'Blonde-001', '2025-05-11 16:51:25', 2),
	(10, 'Soju Coreano Jinro Toronja', 'Soju Coreano Jinro Toronja 360 Ml', 75.00, 'Soju Coreano.png', 'Bebida-002', '2025-05-11 20:27:57', 1),
	(11, 'Tame Impala Currents', 'Currents\r\nÁlbum de Tame Impala', 500.00, 'Currents.png', 'Currents-001', '2025-05-14 20:51:25', 2),
	(12, 'INROCK - Liam Payne 2024', ' INROCK Magazine December 2024 Japanese Liam Payne cover memory', 630.00, 'Inrock-Liam-2024.png', 'RV-003', '2025-05-18 13:30:04', 5),
	(13, 'EVA-01 Action Figure ', 'Kaiyodo Revoltech Evangelion Evolution EV-0015 EVA-01 Action Figure ', 999.00, 'Eva01.png', 'Eva01-001', '2025-05-14 21:00:32', 3),
	(14, 'Figura de acción Evolution EVA-08', 'NEON GENESIS EVANGELION EVA Figura de acción Evolution EVA-08', 999.00, 'Eva08.png', 'Eva08-001', '2025-05-14 21:02:17', 3),
	(15, 'Figura Eren Yeager', 'Figura Eren Yeager', 999.00, 'Eren.png', 'Eren-001', '2025-05-14 21:03:58', 3),
	(16, 'Figura Levi Ackerman', 'Figura Levi Ackerman Attack On Titan Shingeki No Kyojin', 999.00, 'Levi.png', 'Levi-001', '2025-05-14 21:05:12', 3),
	(17, ' Naruto Shippuden Manga Vol 40', ' Naruto Shippuden Manga Vol 40', 300.00, 'NarutoV20.png', 'Naruto-001', '2025-05-14 21:07:07', 4),
	(18, ' Naruto Shippuden Manga Vol 70', ' Naruto Shippuden Manga Vol 70', 300.00, 'NarutoV70.png', 'Naruto-002', '2025-05-14 21:08:17', 4),
	(19, ' Naruto Shippuden Manga Vol 52', ' Naruto Shippuden Manga Vol 52', 300.00, 'NarutoV52.png', 'Naruto-003', '2025-05-14 21:09:23', 4),
	(20, ' Naruto Shippuden Manga Vol 58', ' Naruto Shippuden Manga Vol 58', 300.00, 'NarutoV58.png', 'Naruto-004', '2025-05-14 21:10:36', 4),
	(21, 'Shingeki no Kyojin Vol 13', 'Shingeki no Kyojin Vol 13', 300.00, 'SNK13.png', 'SNK-001', '2025-05-14 21:12:55', 4),
	(22, 'Jason Voorhees', 'Freddy x Jason Ultimate Action Figure: Jason Voorhees', 1200.00, 'Jason01.png', 'Jason-001', '2025-05-14 21:14:25', 3),
	(23, 'Freddy Krueger', 'Freddy Krueger Action Figure', 1200.00, 'Freddy01.png', 'Freddy-001', '2025-05-14 21:15:28', 3),
	(24, 'Soda Matcha', 'Soda japonesa Ramune HATA Matcha gasosa pop frutas bebida dulces 200 ml', 800.00, 'Soda Ramune.png', 'Bebida-004', '2025-05-14 21:17:53', 1),
	(25, 'Coca-Cola ', 'Coca-Cola y Rosalía edición limitada', 500.00, 'CocaCola Rosalia.png', 'CocaCola-001', '2025-05-14 20:59:00', 1),
	(26, 'Refresco Coreano Sparkling Sabor Uva', 'Refresco Coreano Sparkling Sabor Uva, Okf, 350 Ml', 25.00, 'Sparkling Coreano.png', 'Bebida-003', '2025-05-11 20:30:41', 1);

-- Volcando datos para la tabla sisccel.usuarios: ~2 rows (aproximadamente)
INSERT INTO `usuarios` (`idUsuario`, `nombre`, `apellidoPaterno`, `apellidoMaterno`, `email`, `contrasena`, `direccion`, `telefono`, `idPerfil`) VALUES
	(1, 'admin', 'admin', 'admin', 'admin@tokyo.com', '$2a$10$1shsLhYA.UuQI6n67TOtPe10r544JYwRUnc1OxpPDSos4lWSG5ASW', '31-8 Udagawacho, Shibuya, Tokyo 150-0042, Japón', '5555555555', 1),
	(6, 'prueba', 'prueba', 'prueba', 'prueba@mail.com', '$2a$10$uzrP/DvRiHahECQIU90yr.MSuH1uTJaaEDEN9QAkfKFJwrwGGQTyq', 'Calle prueba cliente', '2222222222', 2);

/*!40103 SET TIME_ZONE=IFNULL(@OLD_TIME_ZONE, 'system') */;
/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IFNULL(@OLD_FOREIGN_KEY_CHECKS, 1) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40111 SET SQL_NOTES=IFNULL(@OLD_SQL_NOTES, 1) */;
