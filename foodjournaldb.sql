-- phpMyAdmin SQL Dump
-- version 5.1.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Dec 05, 2021 at 06:43 PM
-- Server version: 10.4.21-MariaDB
-- PHP Version: 8.0.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `foodjournaldb`
--

-- --------------------------------------------------------

--
-- Table structure for table `users`
--

CREATE TABLE `users` (
  `id` int(11) UNSIGNED NOT NULL,
  `email` varchar(255) NOT NULL,
  `password` varchar(128) NOT NULL,
  `username` varchar(30) NOT NULL,
  `age` tinyint(3) UNSIGNED NOT NULL,
  `gender` char(1) NOT NULL,
  `weight` decimal(5,2) UNSIGNED NOT NULL,
  `height` decimal(3,2) UNSIGNED NOT NULL,
  `objective` char(1) NOT NULL,
  `privileges` char(1) NOT NULL DEFAULT 'U',
  `AMR` char(1) DEFAULT NULL,
  `history` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL DEFAULT '{}',
  `dailyKcal` int(5) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `users`
--

INSERT INTO `users` (`id`, `email`, `password`, `username`, `age`, `gender`, `weight`, `height`, `objective`, `privileges`, `AMR`, `history`, `dailyKcal`) VALUES
(1, 'ovidiubachmatchi@gmail.com', 'ce98ffa01031d666e568501d269fbbfa227efbe07b4a883b75007d5a79886cc5123362c1fa48195e42658035eed9ea85ff4b05dcfd6145b5631c35abb8c1323f', 'ovidiu', 20, 'M', '71.50', '1.85', 'g', 'U', 'L', '{\"2021-12-01\": [{\"height\": 1.85, \"weight\": 71.5, \"kcal_consumed\": 1062.0}], \"2021-11-30\": [{\"height\": 1.85, \"weight\": 69.85, \"kcal_consumed\": 70.8}], \"2021-12-02\": [{\"height\": 1.85, \"weight\": 71.5, \"kcal_consumed\": 70.8}], \"2021-12-05\": [{\"height\": 1.85, \"weight\": 71.5, \"kcal_consumed\": 354.0}]}', 3000),
(14, 'history@test.ro', 'ce98ffa01031d666e568501d269fbbfa227efbe07b4a883b75007d5a79886cc5123362c1fa48195e42658035eed9ea85ff4b05dcfd6145b5631c35abb8c1323f', 'username@test.test', 20, 'M', '80.50', '1.98', 'm', 'U', 'L', '{}', 0),
(15, 'email@email.ro', 'ce98ffa01031d666e568501d269fbbfa227efbe07b4a883b75007d5a79886cc5123362c1fa48195e42658035eed9ea85ff4b05dcfd6145b5631c35abb8c1323f', 'username', 19, 'F', '95.00', '1.80', 'g', 'U', 'L', '{}', 0),
(16, 'kcal@kcal.kcal', '8511796cfb8022c43d258ab2c860d568cded4e995e75133ef485f471c39ff08cb4b033069a9ee8a5316e32e9f11f79b5e8dbfb8b5778cfec621cf2397f2bbc72', 'kcal@kcal.kcal', 20, 'M', '68.00', '1.75', 'g', 'U', 'L', '{}', 2395),
(17, 'kcal@kcal.kcals', '8a192c20e2f068066cb9fc5de96e0aa370648b529e8b284aa028a3763d8fc140b0229d32620ce0d6bce5d7a20639b7d049ce851eaf0189d933d8cf73a9689989', 'kcal@kcal.kcal1', 20, 'F', '68.00', '1.82', 'g', 'U', 'L', '{}', 2129),
(18, 'ovidiubach@gmail.com', 'ce98ffa01031d666e568501d269fbbfa227efbe07b4a883b75007d5a79886cc5123362c1fa48195e42658035eed9ea85ff4b05dcfd6145b5631c35abb8c1323f', 'ovidiu', 20, 'M', '68.00', '1.75', 'g', 'U', 'L', '{}', 2895);

--
-- Indexes for dumped tables
--

--
-- Indexes for table `users`
--
ALTER TABLE `users`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `email` (`email`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `users`
--
ALTER TABLE `users`
  MODIFY `id` int(11) UNSIGNED NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=19;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
