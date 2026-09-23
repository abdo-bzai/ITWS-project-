-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Sep 23, 2026 at 11:32 PM
-- Server version: 10.4.32-MariaDB
-- PHP Version: 8.2.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `gym_db`
--

-- --------------------------------------------------------

--
-- Table structure for table `members`
--

CREATE TABLE `members` (
  `member_id` int(11) NOT NULL,
  `created_at` datetime(6) NOT NULL,
  `email` varchar(255) NOT NULL,
  `end_date` date NOT NULL,
  `name` varchar(255) NOT NULL,
  `start_date` date NOT NULL,
  `total_price` int(11) NOT NULL,
  `updated_at` datetime(6) DEFAULT NULL,
  `plan_id` int(11) NOT NULL,
  `trainer_id` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `members`
--

INSERT INTO `members` (`member_id`, `created_at`, `email`, `end_date`, `name`, `start_date`, `total_price`, `updated_at`, `plan_id`, `trainer_id`) VALUES
(1, '2026-09-23 14:03:18.000000', 'luka.ilic@mail.com', '2026-09-14', 'Luka Ilic', '2026-08-14', 8000, NULL, 1, 1),
(2, '2026-09-23 14:03:18.000000', 'milica.s@mail.com', '2027-05-23', 'Milica Stojanovic', '2026-05-23', 72000, NULL, 4, 2),
(3, '2026-09-23 14:03:18.000000', 'nikola.dj@mail.com', '2026-09-27', 'Nikola Djordjevic', '2026-06-27', 22500, NULL, 2, 1),
(4, '2026-09-23 14:03:18.000000', 'jelena.p@mail.com', '2026-10-20', 'Jelena Popovic', '2026-09-20', 7000, NULL, 1, 3),
(5, '2026-09-23 14:03:18.000000', 'petar.m@mail.com', '2027-02-23', 'Petar Markovic', '2026-08-23', 24000, NULL, 3, NULL),
(6, '2026-09-23 14:03:18.000000', 'sara.k@mail.com', '2026-10-13', 'Sara Kovac', '2026-09-13', 5000, NULL, 1, NULL);

-- --------------------------------------------------------

--
-- Table structure for table `membership_plans`
--

CREATE TABLE `membership_plans` (
  `plan_id` int(11) NOT NULL,
  `created_at` datetime(6) NOT NULL,
  `duration_months` int(11) NOT NULL,
  `name` varchar(255) NOT NULL,
  `price` int(11) NOT NULL,
  `updated_at` datetime(6) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `membership_plans`
--

INSERT INTO `membership_plans` (`plan_id`, `created_at`, `duration_months`, `name`, `price`, `updated_at`) VALUES
(1, '2026-09-23 14:03:18.000000', 1, 'Monthly', 5000, NULL),
(2, '2026-09-23 14:03:18.000000', 3, 'Quarterly', 13500, NULL),
(3, '2026-09-23 14:03:18.000000', 6, '6 months', 24000, NULL),
(4, '2026-09-23 14:03:18.000000', 12, 'Yearly', 42000, NULL);

-- --------------------------------------------------------

--
-- Table structure for table `trainers`
--

CREATE TABLE `trainers` (
  `trainer_id` int(11) NOT NULL,
  `created_at` datetime(6) NOT NULL,
  `monthly_fee` int(11) NOT NULL,
  `name` varchar(255) NOT NULL,
  `phone` varchar(255) DEFAULT NULL,
  `specialty` varchar(255) NOT NULL,
  `updated_at` datetime(6) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `trainers`
--

INSERT INTO `trainers` (`trainer_id`, `created_at`, `monthly_fee`, `name`, `phone`, `specialty`, `updated_at`) VALUES
(1, '2026-09-23 14:03:18.000000', 3000, 'Marko Petrovic', '+381 64 111 2233', 'Strength', NULL),
(2, '2026-09-23 14:03:18.000000', 2500, 'Ana Jovanovic', '+381 63 444 5566', 'CrossFit', '2026-09-23 15:38:53.000000'),
(3, '2026-09-23 14:03:18.000000', 2000, 'Stefan Nikolic', '+381 65 777 8899', 'Cardio', NULL);

--
-- Indexes for dumped tables
--

--
-- Indexes for table `members`
--
ALTER TABLE `members`
  ADD PRIMARY KEY (`member_id`),
  ADD UNIQUE KEY `UK9d30a9u1qpg8eou0otgkwrp5d` (`email`),
  ADD KEY `FKjuwy40aicmdogitnkjj38s78k` (`plan_id`),
  ADD KEY `FKpuumgrbmt270og5275pwltnq` (`trainer_id`);

--
-- Indexes for table `membership_plans`
--
ALTER TABLE `membership_plans`
  ADD PRIMARY KEY (`plan_id`);

--
-- Indexes for table `trainers`
--
ALTER TABLE `trainers`
  ADD PRIMARY KEY (`trainer_id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `members`
--
ALTER TABLE `members`
  MODIFY `member_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=8;

--
-- AUTO_INCREMENT for table `membership_plans`
--
ALTER TABLE `membership_plans`
  MODIFY `plan_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;

--
-- AUTO_INCREMENT for table `trainers`
--
ALTER TABLE `trainers`
  MODIFY `trainer_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `members`
--
ALTER TABLE `members`
  ADD CONSTRAINT `FKjuwy40aicmdogitnkjj38s78k` FOREIGN KEY (`plan_id`) REFERENCES `membership_plans` (`plan_id`),
  ADD CONSTRAINT `FKpuumgrbmt270og5275pwltnq` FOREIGN KEY (`trainer_id`) REFERENCES `trainers` (`trainer_id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
