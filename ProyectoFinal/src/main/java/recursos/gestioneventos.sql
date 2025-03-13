-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Servidor: 127.0.0.1
-- Tiempo de generación: 03-03-2025 a las 23:10:29
-- Versión del servidor: 10.4.32-MariaDB
-- Versión de PHP: 8.0.30

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de datos: `gestioneventos`
--

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `artistas`
--

CREATE TABLE `artistas` (
  `id` int(11) NOT NULL,
  `nombre` varchar(255) NOT NULL,
  `categoria` enum('House','Techno','Progressive House','Deep House','Tech House','Trance','Minimal','Electro House','Hard Techno','Disco','Hip-Hop','R&B','Otros') NOT NULL,
  `evento` varchar(255) NOT NULL,
  `hora` time NOT NULL,
  `id_evento` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `artistas`
--

INSERT INTO `artistas` (`id`, `nombre`, `categoria`, `evento`, `hora`, `id_evento`) VALUES
(79, 'Solomun', 'Techno', 'Grand Opening Party con Solomun', '23:00:00', 37),
(80, 'Mau P', 'Techno', 'Grand Opening Party con Solomun', '02:00:00', 37),
(81, 'Purple Disco Machine', 'Disco', 'Purple Disco Machine', '23:00:00', 38),
(82, 'Mousse T', 'Disco', 'Purple Disco Machine', '01:00:00', 38),
(83, 'David Bay', 'Disco', 'Purple Disco Machine', '03:00:00', 38),
(84, 'Ilario Alicante', 'Tech House', 'Ilario Alicante', '23:00:00', 40),
(85, 'Ben Sterling', 'Tech House', 'Ilario Alicante', '02:00:00', 40),
(86, 'Ale de Tuglie', 'Tech House', 'Ilario Alicante', '04:00:00', 40),
(87, 'Loco Dice', 'Techno', 'Loco Dice', '23:00:00', 42),
(88, 'Fabrice', 'Techno', 'Loco Dice', '03:00:00', 42),
(89, 'Josh Baker B2B Rossi', 'Tech House', 'Pyramid', '23:00:00', 44),
(90, 'Mar-T', 'Tech House', 'Pyramid', '00:00:00', 44),
(91, 'Ricardo Villalobos B2B Raresh', 'Minimal', 'Pyramid', '01:00:00', 44),
(92, 'Deborah De Luca', 'Hard Techno', 'Pyramid', '02:30:00', 44),
(93, 'Luca Donzelli', 'Tech House', 'Pyramid', '03:30:00', 44),
(94, 'Marco Faraone', 'Techno', 'Pyramid', '04:30:00', 44),
(95, 'Nina Kraviz', 'Techno', 'Pyramid', '05:30:00', 44),
(96, 'ANOTR', 'Tech House', 'Circoloco', '23:00:00', 48),
(97, 'Bedouin', 'Deep House', 'Circoloco', '00:00:00', 48),
(98, 'The Blessed Madonna', 'House', 'Circoloco', '01:00:00', 48),
(99, 'Chris Stussy', 'Deep House', 'Circoloco', '02:00:00', 48),
(100, 'Damian Lazarus', 'Minimal', 'Circoloco', '03:00:00', 48),
(101, 'Dixon', 'Progressive House', 'Circoloco', '04:00:00', 48),
(102, 'DJ Tennis', 'Techno', 'Circoloco', '05:00:00', 48),
(103, 'Carlita', 'Deep House', 'Circoloco', '23:00:00', 49),
(104, 'PAWSA', 'Tech House', 'Circoloco', '00:00:00', 49),
(105, 'Dennis Cruz', 'Tech House', 'Circoloco', '01:00:00', 49),
(106, 'Ivan Smagghe', 'Electro House', 'Circoloco', '02:00:00', 49),
(107, 'Joy Orbison', 'Techno', 'Circoloco', '03:00:00', 49),
(108, 'Jimi Jules', 'Deep House', 'Circoloco', '04:00:00', 49),
(109, 'Luciano', 'Minimal', 'Circoloco', '05:00:00', 49),
(110, 'O.BEE', 'Minimal', 'Circoloco', '23:00:00', 49),
(111, 'Tomas Station', 'House', 'Circoloco', '00:00:00', 49),
(112, 'Prospa', 'Electro House', 'Circoloco', '01:00:00', 49),
(113, 'Seth Troxler', 'Tech House', 'Circoloco', '02:00:00', 49),
(114, 'Sossa', 'Deep House', 'Circoloco', '03:00:00', 49),
(115, 'Sparrow & Barbossa', 'House', 'Circoloco', '04:00:00', 49),
(116, 'Tania Vulcano', 'Tech House', 'Circoloco', '05:00:00', 49),
(117, 'Cream Ibiza', 'Electro House', 'Cream Ibiza', '23:00:00', 51);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `eventos`
--

CREATE TABLE `eventos` (
  `id` int(11) NOT NULL,
  `nombre` varchar(255) NOT NULL,
  `fecha` datetime NOT NULL,
  `ubicacion` varchar(255) NOT NULL,
  `categoria` enum('House','Techno','Progressive House','Deep House','Tech House','Trance','Minimal','Electro House','Hard Techno','Disco','Hip-Hop','R&B','Otros') DEFAULT NULL,
  `precio` decimal(10,2) DEFAULT 0.00,
  `id_organizador` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `eventos`
--

INSERT INTO `eventos` (`id`, `nombre`, `fecha`, `ubicacion`, `categoria`, `precio`, `id_organizador`) VALUES
(37, 'Grand Opening Party con Solomun', '2025-04-25 23:00:00', 'Pacha Ibiza', 'Techno', 150.00, 1),
(38, 'Purple Disco Machine', '2025-04-26 23:00:00', 'Pacha Ibiza', 'Disco', 160.00, 1),
(39, 'ANOTR Opening Weekend', '2025-04-27 23:00:00', 'Pacha Ibiza', 'Progressive House', 150.00, 1),
(40, 'Ilario Alicante', '2025-05-03 23:00:00', 'Pacha Ibiza', 'Tech House', 170.00, 1),
(41, 'Defected-Opening-Party', '2025-05-08 23:00:00', 'Pacha Ibiza', 'House', 180.00, 1),
(42, 'Loco Dice', '2025-05-09 23:00:00', 'Pacha Ibiza', 'Techno', 200.00, 1),
(43, 'Opening Party', '2025-05-10 23:00:00', 'Amnesia Ibiza', 'House', 160.00, 2),
(44, 'Pyramid', '2025-05-15 23:00:00', 'Amnesia Ibiza', 'Techno', 180.00, 2),
(45, 'Do Not Sleep', '2025-06-19 23:00:00', 'Amnesia Ibiza', 'Minimal', 170.00, 2),
(46, 'Amnesia Presents', '2025-06-25 23:00:00', 'Amnesia Ibiza', 'Progressive House', 150.00, 2),
(47, 'RESISTANCE', '2025-07-01 23:00:00', 'Amnesia Ibiza', 'Techno', 200.00, 2),
(48, 'Circoloco', '2025-06-02 23:00:00', 'DC10 Ibiza', 'Techno', 220.00, 3),
(49, 'Circoloco', '2025-06-09 23:00:00', 'DC10 Ibiza', 'Minimal', 220.00, 3),
(50, 'Circoloco', '2025-06-16 23:00:00', 'DC10 Ibiza', 'Techno', 220.00, 3),
(51, 'Cream Ibiza', '2025-06-05 23:00:00', 'Eden Ibiza', 'Electro House', 170.00, 4),
(52, 'Defected', '2025-06-12 23:00:00', 'Eden Ibiza', 'House', 180.00, 4),
(53, 'We Love Space', '2025-06-15 23:00:00', 'Space Ibiza', 'Techno', 150.00, 5),
(54, 'We Love Space', '2025-06-22 23:00:00', 'Space Ibiza', 'Progressive House', 150.00, 5);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `reservas`
--

CREATE TABLE `reservas` (
  `id` int(11) NOT NULL,
  `nombre` varchar(100) DEFAULT NULL,
  `id_usuario` int(11) DEFAULT NULL,
  `id_evento` int(11) DEFAULT NULL,
  `cantidad` decimal(11,0) NOT NULL DEFAULT 1,
  `personas` int(11) NOT NULL,
  `fecha_reserva` timestamp NOT NULL DEFAULT current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `reservas`
--

INSERT INTO `reservas` (`id`, `nombre`, `id_usuario`, `id_evento`, `cantidad`, `personas`, `fecha_reserva`) VALUES
(1, 'Reserva Martin MD', 1, 37, 150, 7, '2025-04-24 22:00:00'),
(2, 'Reserva María López', 1, 38, 160, 5, '2025-04-25 22:00:00'),
(3, 'Reserva Carlos Gómez', 1, 39, 150, 11, '2025-04-26 22:00:00'),
(4, 'Reserva Ana Torres', 1, 40, 170, 3, '2025-05-02 22:00:00'),
(5, 'Reserva Luis Ramírez', 1, 41, 180, 4, '2025-05-07 22:00:00'),
(6, 'Reserva Sofía Méndez', 1, 42, 200, 2, '2025-05-08 22:00:00'),
(7, 'Reserva Pedro Castillo', 2, 43, 160, 8, '2025-05-09 22:00:00'),
(8, 'Reserva Gabriela Ruiz', 2, 44, 180, 2, '2025-05-14 22:00:00'),
(9, 'Reserva Fernando Díaz', 2, 45, 170, 3, '2025-06-18 22:00:00'),
(10, 'Reserva Elena Vargas', 2, 46, 150, 3, '2025-06-24 22:00:00'),
(11, 'Reserva Ricardo Fernández', 2, 47, 200, 9, '2025-06-30 22:00:00'),
(12, 'Reserva Laura Sánchez', 3, 48, 220, 4, '2025-06-01 22:00:00'),
(13, 'Reserva Jorge Herrera', 3, 49, 220, 5, '2025-06-08 22:00:00'),
(14, 'Reserva Beatriz Molina', 3, 50, 220, 5, '2025-06-15 22:00:00'),
(15, 'Reserva Raúl Ortega', 4, 51, 170, 5, '2025-06-04 22:00:00'),
(16, 'Reserva Carmen Espinoza', 4, 52, 180, 3, '2025-06-11 22:00:00'),
(17, 'Reserva David Chávez', 5, 53, 150, 7, '2025-06-14 22:00:00'),
(18, 'Reserva Andrea Rojas', 5, 54, 150, 6, '2025-06-21 22:00:00');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `usuarios`
--

CREATE TABLE `usuarios` (
  `id` int(11) NOT NULL,
  `nombre` varchar(100) NOT NULL,
  `email` varchar(100) NOT NULL,
  `password` varchar(255) NOT NULL,
  `telefono` varchar(15) DEFAULT NULL,
  `tipo` enum('usuario','organizador','admin') DEFAULT 'usuario',
  `fecha_registro` timestamp NOT NULL DEFAULT current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `usuarios`
--

INSERT INTO `usuarios` (`id`, `nombre`, `email`, `password`, `telefono`, `tipo`, `fecha_registro`) VALUES
(1, 'PachaIbiza', 'pacha@gmail.com', 'pacha2025', '123456789', '', '2024-12-31 23:00:00'),
(2, 'AmnesiaIbiza', 'amnesia@gmail.com', 'password123', '987654321', '', '2025-01-01 23:00:00'),
(3, 'DC10Ibiza', 'dc10@gmail.com', 'password123', '555123456', '', '2025-01-02 23:00:00'),
(4, 'EdenIbiza', 'eden@gmail.com', 'password123', '555654321', '', '2025-01-03 23:00:00'),
(5, 'SpaceIbiza', 'space@gmail.com', 'password123', '555987654', '', '2025-01-04 23:00:00');

--
-- Índices para tablas volcadas
--

--
-- Indices de la tabla `artistas`
--
ALTER TABLE `artistas`
  ADD PRIMARY KEY (`id`),
  ADD KEY `id_evento` (`id_evento`);

--
-- Indices de la tabla `eventos`
--
ALTER TABLE `eventos`
  ADD PRIMARY KEY (`id`),
  ADD KEY `id_organizador` (`id_organizador`);

--
-- Indices de la tabla `reservas`
--
ALTER TABLE `reservas`
  ADD PRIMARY KEY (`id`),
  ADD KEY `id_usuario` (`id_usuario`),
  ADD KEY `id_evento` (`id_evento`);

--
-- Indices de la tabla `usuarios`
--
ALTER TABLE `usuarios`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `email` (`email`);

--
-- AUTO_INCREMENT de las tablas volcadas
--

--
-- AUTO_INCREMENT de la tabla `artistas`
--
ALTER TABLE `artistas`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=118;

--
-- AUTO_INCREMENT de la tabla `eventos`
--
ALTER TABLE `eventos`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=55;

--
-- AUTO_INCREMENT de la tabla `reservas`
--
ALTER TABLE `reservas`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=19;

--
-- AUTO_INCREMENT de la tabla `usuarios`
--
ALTER TABLE `usuarios`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;

--
-- Restricciones para tablas volcadas
--

--
-- Filtros para la tabla `artistas`
--
ALTER TABLE `artistas`
  ADD CONSTRAINT `artistas_ibfk_1` FOREIGN KEY (`id_evento`) REFERENCES `eventos` (`id`);

--
-- Filtros para la tabla `eventos`
--
ALTER TABLE `eventos`
  ADD CONSTRAINT `eventos_ibfk_1` FOREIGN KEY (`id_organizador`) REFERENCES `usuarios` (`id`) ON DELETE SET NULL;

--
-- Filtros para la tabla `reservas`
--
ALTER TABLE `reservas`
  ADD CONSTRAINT `reservas_ibfk_1` FOREIGN KEY (`id_usuario`) REFERENCES `usuarios` (`id`) ON DELETE CASCADE,
  ADD CONSTRAINT `reservas_ibfk_2` FOREIGN KEY (`id_evento`) REFERENCES `eventos` (`id`) ON DELETE CASCADE;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
