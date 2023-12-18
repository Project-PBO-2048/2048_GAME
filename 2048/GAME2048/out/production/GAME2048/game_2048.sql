-- phpMyAdmin SQL Dump
-- version 5.2.0
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Dec 16, 2023 at 06:33 PM
-- Server version: 10.4.25-MariaDB
-- PHP Version: 8.1.10

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `game_2048`
--

-- --------------------------------------------------------

--
-- Table structure for table `score`
--

CREATE TABLE `score` (
  `id` int(4) NOT NULL,
  `player_id` int(4) NOT NULL,
  `score` int(4) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `score`
--

INSERT INTO `score` (`id`, `player_id`, `score`) VALUES
(161, 162, 0),
(162, 163, 0),
(163, 164, 0),
(164, 165, 0),
(165, 166, 0),
(166, 167, 0),
(167, 168, 0),
(168, 169, 0),
(169, 170, 0),
(170, 171, 0),
(171, 172, 0),
(172, 173, 0),
(173, 174, 0),
(174, 175, 0),
(175, 176, 0),
(176, 177, 0),
(177, 178, 0),
(178, 179, 0),
(179, 180, 28),
(180, 181, 0),
(181, 182, 0),
(182, 183, 0),
(183, 184, 0),
(184, 185, 0),
(185, 186, 0),
(186, 187, 420),
(187, 188, 0);

-- --------------------------------------------------------

--
-- Table structure for table `users`
--

CREATE TABLE `users` (
  `id` int(4) NOT NULL,
  `created_at` date NOT NULL DEFAULT current_timestamp(),
  `username` varchar(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `users`
--

INSERT INTO `users` (`id`, `created_at`, `username`) VALUES
(162, '2023-12-16', 'Dhira'),
(163, '2023-12-16', 'Rama'),
(164, '2023-12-16', 'hgc'),
(165, '2023-12-16', 'asdas'),
(166, '2023-12-16', 'asdas'),
(167, '2023-12-16', 'asdas'),
(168, '2023-12-16', 'asdas'),
(169, '2023-12-16', 'asdas'),
(170, '2023-12-16', 'asdas'),
(171, '2023-12-16', 'asdas'),
(172, '2023-12-16', '45454'),
(173, '2023-12-16', 'Rama'),
(174, '2023-12-16', 'sda'),
(175, '2023-12-17', 'asd'),
(176, '2023-12-17', 'asdas'),
(177, '2023-12-17', 'sa'),
(178, '2023-12-17', 'asd'),
(179, '2023-12-17', 'Rama'),
(180, '2023-12-17', 'RAma'),
(181, '2023-12-17', 'RAma'),
(182, '2023-12-17', 'RAma'),
(183, '2023-12-17', 'Hmm'),
(184, '2023-12-17', 'Dhira'),
(185, '2023-12-17', 'a'),
(186, '2023-12-17', 'Dhira'),
(187, '2023-12-17', 'wertyui'),
(188, '2023-12-17', '123');

--
-- Triggers `users`
--
DELIMITER $$
CREATE TRIGGER `add_default_scores_trigger` AFTER INSERT ON `users` FOR EACH ROW BEGIN
    INSERT INTO score (player_id, score)
    VALUES (NEW.id, 0);
END
$$
DELIMITER ;

--
-- Indexes for dumped tables
--

--
-- Indexes for table `score`
--
ALTER TABLE `score`
  ADD PRIMARY KEY (`id`),
  ADD KEY `player_id` (`player_id`);

--
-- Indexes for table `users`
--
ALTER TABLE `users`
  ADD PRIMARY KEY (`id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `score`
--
ALTER TABLE `score`
  MODIFY `id` int(4) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=188;

--
-- AUTO_INCREMENT for table `users`
--
ALTER TABLE `users`
  MODIFY `id` int(4) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=189;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `score`
--
ALTER TABLE `score`
  ADD CONSTRAINT `score_ibfk_1` FOREIGN KEY (`player_id`) REFERENCES `users` (`id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
