-- phpMyAdmin SQL Dump
-- version 4.3.11
-- http://www.phpmyadmin.net
--
-- Host: 127.0.0.1
-- Generation Time: Mar 16, 2016 at 09:00 AM
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
) ENGINE=InnoDB AUTO_INCREMENT=280 DEFAULT CHARSET=utf8;

--
-- Dumping data for table `key_words`
--

INSERT INTO `key_words` (`wid`, `content`) VALUES
(123, 'Cô'),
(124, 'ấy'),
(125, 'cho'),
(126, 'tôi'),
(127, 'một'),
(128, 'quả'),
(129, 'cam'),
(130, '.'),
(131, 'Bất kì'),
(132, '1'),
(133, 'USD'),
(134, 'tăng trưởng'),
(135, 'nào'),
(136, 'của'),
(137, 'ASEAN'),
(138, 'có'),
(139, '6'),
(140, 'cent'),
(141, 'xuất khẩu'),
(142, 'của'),
(143, 'Nhật Bản'),
(144, 'tới'),
(145, 'Điều'),
(146, 'này'),
(147, 'cho'),
(148, 'thấy'),
(149, 'tỉ trọng'),
(150, 'của'),
(151, 'kinh tế'),
(152, 'trong'),
(153, 'GDP'),
(154, 'Nhật Bản'),
(155, 'lớn'),
(156, 'như thế nào'),
(157, 'Tình hình'),
(158, 'với'),
(159, 'Trung Quốc'),
(160, 'như vậy'),
(161, 'Như'),
(162, 'bạn'),
(163, 'biết'),
(164, ','),
(165, 'Biển Đông'),
(166, 'là'),
(167, 'tuyến'),
(168, 'đường biển'),
(169, 'chiến lược'),
(170, 'Bất chấp'),
(171, 'căng thẳng'),
(172, 'và'),
(173, 'phản ứng'),
(174, 'của'),
(175, 'bên'),
(176, 'giữa'),
(177, 'quốc gia'),
(178, 'liên quan'),
(179, 'chia sẻ'),
(180, 'lợi ích'),
(181, 'chung'),
(182, ':'),
(183, 'duy trì'),
(184, 'Biển Đông'),
(185, 'an ninh'),
(186, 'và'),
(187, 'ổn định'),
(188, '80%'),
(189, 'nguồn'),
(190, 'năng lượng'),
(191, 'cung cấp'),
(192, 'cho'),
(193, 'ba'),
(194, 'quốc gia'),
(195, 'lớn'),
(196, 'nhất'),
(197, 'ở'),
(198, 'Đông'),
(199, 'Á'),
(200, 'là'),
(201, 'Nhật Bản'),
(202, 'Hàn Quốc'),
(203, 'và'),
(204, 'Trung Quốc'),
(205, 'từ'),
(206, 'Biển Đông'),
(207, 'hoặc'),
(208, 'đi'),
(209, 'qua'),
(210, 'Biển Đông'),
(211, 'Thương mại'),
(212, 'và'),
(213, 'sản phẩm'),
(214, 'vậy'),
(215, 'Nhờ'),
(216, 'đó'),
(217, 'chúng ta'),
(218, 'kiềm chế'),
(219, 'căng thẳng'),
(220, 'và'),
(221, 'xung đột'),
(222, 'tiếm'),
(223, 'dẫn'),
(224, 'tới'),
(225, 'xung đột'),
(226, 'quân sự'),
(227, 'Mặc dù'),
(228, 'cuộc chiến'),
(229, 'Iraq'),
(230, 'và'),
(231, 'Afghanistan'),
(232, 'cũng như'),
(233, 'việc'),
(234, 'sao nhãng'),
(235, 'khu vực'),
(236, 'chiến lược'),
(237, 'quan trọng'),
(238, 'Đông'),
(239, 'lâu nay'),
(240, 'khiến'),
(241, 'Hoa Kỳ'),
(242, 'yếu'),
(243, 'đi'),
(244, 'Nhưng'),
(245, 'hai'),
(246, 'động thái'),
(247, 'gần'),
(248, 'đây'),
(249, 'Washington'),
(250, '-'),
(251, 'Cùng'),
(252, 'hải quân'),
(253, 'tập trận'),
(254, 'trên'),
(255, 'biển'),
(256, 'thực hiện'),
(257, 'nỗ lực'),
(258, 'ngoại giao'),
(259, 'mạnh mẽ'),
(260, 'bảo vệ'),
(261, 'quyền'),
(262, 'tự do'),
(263, 'thể hiện'),
(264, 'sự'),
(265, 'tái'),
(266, 'quan tâm'),
(267, 'quan ngại'),
(268, 'về'),
(269, 'Mỹ'),
(270, 'Đông Bắc'),
(271, 'Đông Nam'),
(272, 'Cả'),
(273, 'hai'),
(274, 'hành động'),
(275, 'Mỹ'),
(276, 'nói chung'),
(277, 'được'),
(278, 'hoan nghênh'),
(279, 'ngoại trừ');

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
-- AUTO_INCREMENT for table `key_words`
--
ALTER TABLE `key_words`
  MODIFY `wid` int(6) NOT NULL AUTO_INCREMENT,AUTO_INCREMENT=280;
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
