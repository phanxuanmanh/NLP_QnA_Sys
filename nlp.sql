-- phpMyAdmin SQL Dump
-- version 4.3.11
-- http://www.phpmyadmin.net
--
-- Host: 127.0.0.1
-- Generation Time: Mar 15, 2016 at 09:36 AM
-- Server version: 5.6.24
-- PHP Version: 5.6.8

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--
-- Database: `nlp`
--

-- --------------------------------------------------------

--
-- Table structure for table `answers`
--

CREATE TABLE IF NOT EXISTS `answers` (
  `a_id` int(8) NOT NULL,
  `a_content` varchar(4000) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Table structure for table `key_words`
--

CREATE TABLE IF NOT EXISTS `key_words` (
  `wid` int(6) NOT NULL,
  `content` varchar(100) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `key_words`
--

INSERT INTO `key_words` (`wid`, `content`) VALUES
(1, 'đẹp trai');

-- --------------------------------------------------------

--
-- Table structure for table `qna_pair`
--

CREATE TABLE IF NOT EXISTS `qna_pair` (
  `q_id` int(8) NOT NULL,
  `a_id` int(8) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Table structure for table `questions`
--

CREATE TABLE IF NOT EXISTS `questions` (
  `q_id` int(8) NOT NULL,
  `q_content` varchar(4000) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Table structure for table `question_vectors`
--

CREATE TABLE IF NOT EXISTS `question_vectors` (
  `q_id` int(8) NOT NULL,
  `wid` int(6) NOT NULL,
  `weight` double(5,4) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Indexes for dumped tables
--

--
-- Indexes for table `answers`
--
ALTER TABLE `answers`
  ADD PRIMARY KEY (`a_id`);

--
-- Indexes for table `key_words`
--
ALTER TABLE `key_words`
  ADD PRIMARY KEY (`wid`);

--
-- Indexes for table `qna_pair`
--
ALTER TABLE `qna_pair`
  ADD KEY `fk_qna_ques` (`q_id`), ADD KEY `fk_qna_ans` (`a_id`);

--
-- Indexes for table `questions`
--
ALTER TABLE `questions`
  ADD PRIMARY KEY (`q_id`);

--
-- Indexes for table `question_vectors`
--
ALTER TABLE `question_vectors`
  ADD KEY `fk_vector_ques` (`q_id`), ADD KEY `fk_vector_word` (`wid`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `answers`
--
ALTER TABLE `answers`
  MODIFY `a_id` int(8) NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT for table `questions`
--
ALTER TABLE `questions`
  MODIFY `q_id` int(8) NOT NULL AUTO_INCREMENT;
--
-- Constraints for dumped tables
--

--
-- Constraints for table `qna_pair`
--
ALTER TABLE `qna_pair`
ADD CONSTRAINT `fk_qna_ans` FOREIGN KEY (`a_id`) REFERENCES `answers` (`a_id`),
ADD CONSTRAINT `fk_qna_ques` FOREIGN KEY (`q_id`) REFERENCES `questions` (`q_id`);

--
-- Constraints for table `question_vectors`
--
ALTER TABLE `question_vectors`
ADD CONSTRAINT `fk_vector_ques` FOREIGN KEY (`q_id`) REFERENCES `questions` (`q_id`),
ADD CONSTRAINT `fk_vector_word` FOREIGN KEY (`wid`) REFERENCES `key_words` (`wid`);

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
