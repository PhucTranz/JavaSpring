-- phpMyAdmin SQL Dump
-- version 5.2.0
-- https://www.phpmyadmin.net/
--
-- Máy chủ: 127.0.0.1
-- Thời gian đã tạo: Th12 14, 2022 lúc 04:15 PM
-- Phiên bản máy phục vụ: 10.4.24-MariaDB
-- Phiên bản PHP: 8.1.6

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Cơ sở dữ liệu: `qltb`
--

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `device`
--

CREATE TABLE `device` (
  `maTB` int(11) NOT NULL,
  `ten` varchar(30) DEFAULT NULL,
  `soluong` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Đang đổ dữ liệu cho bảng `device`
--

INSERT INTO `device` (`maTB`, `ten`, `soluong`) VALUES
(1, 'Macbook Air', 6),
(4, 'Ipad', 5),
(5, 'TV SamSung 75inch', 1),
(6, 'Dây HDMI 10m', 1),
(7, 'Sạc Macbook', 30),
(8, 'Sạc Ipad', 20);

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `phieu`
--

CREATE TABLE `phieu` (
  `ma_phieu` int(11) NOT NULL,
  `id` int(11) DEFAULT NULL,
  `giomuon` datetime DEFAULT NULL,
  `giotra` datetime DEFAULT NULL,
  `trangthai` varchar(15) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Đang đổ dữ liệu cho bảng `phieu`
--

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `phieu_thietbi`
--

CREATE TABLE `phieu_thietbi` (
  `ma_phieu` int(11) NOT NULL,
  `maTB` int(11) NOT NULL,
  `soluong` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Đang đổ dữ liệu cho bảng `phieu_thietbi`
--

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `users`
--

CREATE TABLE `users` (
  `id` int(11) NOT NULL,
  `username` varchar(20) DEFAULT NULL,
  `password` varchar(100) NOT NULL,
  `name` varchar(50) DEFAULT NULL,
  `chucvu` varchar(15) DEFAULT NULL,
  `role` varchar(15) DEFAULT NULL,
  `ENABLE` tinyint(4) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Đang đổ dữ liệu cho bảng `users`
--

-- username: admin
-- password: 123
INSERT INTO `users` (`id`, `username`, `password`, `name`, `chucvu`, `role`, `ENABLE`) VALUES
(1, 'admin', '$2a$10$rBetqQ796xXhXm.JQQRB/.0c.63I/c7LWb4y9Qe5O5Wve72fyP.pe', 'Phuc', 'Admin', 'ROLE_ADMIN', 1);

--
-- Chỉ mục cho các bảng đã đổ
--

--
-- Chỉ mục cho bảng `device`
--
ALTER TABLE `device`
  ADD PRIMARY KEY (`maTB`);

--
-- Chỉ mục cho bảng `phieu`
--
ALTER TABLE `phieu`
  ADD PRIMARY KEY (`ma_phieu`),
  ADD KEY `id` (`id`);

--
-- Chỉ mục cho bảng `phieu_thietbi`
--
ALTER TABLE `phieu_thietbi`
  ADD PRIMARY KEY (`ma_phieu`,`maTB`),
  ADD KEY `maTB` (`maTB`);

--
-- Chỉ mục cho bảng `users`
--
ALTER TABLE `users`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `username` (`username`);

--
-- AUTO_INCREMENT cho các bảng đã đổ
--

--
-- AUTO_INCREMENT cho bảng `device`
--
ALTER TABLE `device`
  MODIFY `maTB` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=9;

--
-- AUTO_INCREMENT cho bảng `phieu`
--
ALTER TABLE `phieu`
  MODIFY `ma_phieu` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=20;

--
-- AUTO_INCREMENT cho bảng `users`
--
ALTER TABLE `users`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=14;

--
-- Các ràng buộc cho các bảng đã đổ
--

--
-- Các ràng buộc cho bảng `phieu`
--
ALTER TABLE `phieu`
  ADD CONSTRAINT `phieu_ibfk_1` FOREIGN KEY (`id`) REFERENCES `users` (`id`);

--
-- Các ràng buộc cho bảng `phieu_thietbi`
--
ALTER TABLE `phieu_thietbi`
  ADD CONSTRAINT `phieu_thietbi_ibfk_1` FOREIGN KEY (`ma_phieu`) REFERENCES `phieu` (`ma_phieu`),
  ADD CONSTRAINT `phieu_thietbi_ibfk_2` FOREIGN KEY (`maTB`) REFERENCES `device` (`maTB`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
