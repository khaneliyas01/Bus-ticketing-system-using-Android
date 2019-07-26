-- phpMyAdmin SQL Dump
-- version 3.5.2.2
-- http://www.phpmyadmin.net
--
-- Host: 127.0.0.1
-- Generation Time: Feb 22, 2018 at 11:51 AM
-- Server version: 5.5.27
-- PHP Version: 5.4.7

SET SQL_MODE="NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--
-- Database: `bus`
--

-- --------------------------------------------------------

--
-- Table structure for table `admin`
--

CREATE TABLE IF NOT EXISTS `admin` (
  `id` int(10) NOT NULL,
  `name` varchar(200) NOT NULL DEFAULT '0',
  `password` varchar(200) NOT NULL DEFAULT '0',
  `role` varchar(200) NOT NULL DEFAULT '0',
  `org_id` int(10) NOT NULL DEFAULT '0'
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `admin`
--

INSERT INTO `admin` (`id`, `name`, `password`, `role`, `org_id`) VALUES
(1, 'admin', 'admin', '1', 1),
(2, 'rolemanager', 'rolemanager', '2', 1);

-- --------------------------------------------------------

--
-- Table structure for table `booking`
--

CREATE TABLE IF NOT EXISTS `booking` (
  `id` int(15) NOT NULL AUTO_INCREMENT,
  `userid` varchar(15) NOT NULL,
  `busid` varchar(15) NOT NULL,
  `busno` varchar(15) NOT NULL,
  `ticketno` varchar(20) NOT NULL,
  `totaltickets` varchar(15) NOT NULL,
  `adulttickets` varchar(15) NOT NULL,
  `childtickets` varchar(15) NOT NULL,
  `startloc` varchar(30) NOT NULL,
  `endloc` varchar(30) NOT NULL,
  `cost` varchar(15) NOT NULL,
  `qrcode` varchar(100) NOT NULL,
  `daten` varchar(20) NOT NULL,
  `status` varchar(50) NOT NULL DEFAULT 'not used',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=10 ;

--
-- Dumping data for table `booking`
--

INSERT INTO `booking` (`id`, `userid`, `busid`, `busno`, `ticketno`, `totaltickets`, `adulttickets`, `childtickets`, `startloc`, `endloc`, `cost`, `qrcode`, `daten`, `status`) VALUES
(8, '1', '101', 'MH14AH1990', '2018429702_03', '1', '1', '0', 'Akurdi', 'Dange Chowk', '5.0', '689df7a7-3e7d-4158-92e8-0f5f637e6b09', '03/02/2018', 'not used'),
(9, '1', '101', 'MH14AH1990', '2018839502_05', '2', '1', '1', 'Akurdi', 'Pune University', '22.5', 'a69abd4d-bc71-4f83-be1c-05b1baade743', '05/02/2018', 'used');

-- --------------------------------------------------------

--
-- Table structure for table `buses`
--

CREATE TABLE IF NOT EXISTS `buses` (
  `id` int(12) NOT NULL AUTO_INCREMENT,
  `busno` varchar(15) NOT NULL,
  `busid` varchar(12) NOT NULL,
  `source` varchar(30) NOT NULL,
  `destination` varchar(30) NOT NULL,
  `daten` varchar(15) NOT NULL,
  `timen` varchar(15) NOT NULL,
  `stops` varchar(12) NOT NULL,
  `rate` varchar(12) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=7 ;

--
-- Dumping data for table `buses`
--

INSERT INTO `buses` (`id`, `busno`, `busid`, `source`, `destination`, `daten`, `timen`, `stops`, `rate`) VALUES
(1, 'MH14AH1990', '101', 'Akurdi', 'Pune Station', '9/12/2017', '17:00', '5', '10'),
(4, 'MH1420202', '102', 'nigdi', 'talegaon', '23/09/2018', '14:52', '2', '0'),
(5, 'MH1420204', '104', 'pune', 'mumbai', '23/03/2018', '15:40', '1', '0'),
(6, 'MH1420205', '105', 'pune', 'talegaon', '23/09/2018', '16:08', '1', '0');

-- --------------------------------------------------------

--
-- Table structure for table `report`
--

CREATE TABLE IF NOT EXISTS `report` (
  `id` int(20) NOT NULL AUTO_INCREMENT,
  `busid` varchar(20) NOT NULL,
  `busno` varchar(20) NOT NULL,
  `daten` varchar(15) NOT NULL,
  `totaltickets` varchar(20) NOT NULL,
  `balance` varchar(20) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=3 ;

--
-- Dumping data for table `report`
--

INSERT INTO `report` (`id`, `busid`, `busno`, `daten`, `totaltickets`, `balance`) VALUES
(2, '101', 'MH14AH1990', '05/02/2018', '2', '22.5');

-- --------------------------------------------------------

--
-- Table structure for table `route`
--

CREATE TABLE IF NOT EXISTS `route` (
  `id` int(10) NOT NULL AUTO_INCREMENT,
  `RouteId` varchar(200) NOT NULL DEFAULT '0',
  `start` varchar(200) NOT NULL DEFAULT '0',
  `AddedDate` datetime NOT NULL DEFAULT '0000-00-00 00:00:00',
  `end` varchar(100) NOT NULL,
  `bid` varchar(200) NOT NULL,
  `distance` varchar(200) NOT NULL,
  `ballocate` varchar(100) NOT NULL,
  `betwen` varchar(100) NOT NULL,
  `bseats` varchar(100) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=23 ;

--
-- Dumping data for table `route`
--

INSERT INTO `route` (`id`, `RouteId`, `start`, `AddedDate`, `end`, `bid`, `distance`, `ballocate`, `betwen`, `bseats`) VALUES
(1, '12', 'Nigdi', '2016-01-09 15:43:46', 'Akurdi Railway Station', 'MH-12 1234', '10', '4', 'Sambhaji chowk', '45'),
(2, '14', 'nigdi', '2016-01-09 19:02:15', 'arkurdi station', 'MH-12 8877', '5', '4', 'sambhaji chowk', '40'),
(3, '16', 'Nigdi', '2016-01-09 19:37:19', 'Pune Station', 'MH- 12 0212', '30', '12', 'manapa', '50'),
(4, '20', 'Nigdi', '2009-09-17 01:05:27', 'katraj', 'MH-12 5678', '40', '10', 'dange chowk', '50'),
(5, '22', 'Nigdi', '2016-01-10 10:41:01', 'Bhosari', 'MH-12 7834', '20', '2', 'thermax', '45'),
(6, '33', 'Nigdi', '2016-01-11 10:59:27', 'Hadapsar', 'MH-12 1111', '40', '4', 'manapa', '50'),
(7, '66', 'Nigdi', '2016-01-11 11:20:08', 'ravet', 'MH-12 2222', '20', '4', 'akurdi', '40'),
(18, '66', 'Nigdi', '0000-00-00 00:00:00', 'ravet', 'MH-12-DR-9913', '20', '1', 'akurdi', '45'),
(19, '66', 'Nigdi', '0000-00-00 00:00:00', 'ravet', 'MH-12-DR-1010', '20', '2', 'akurdi', '40'),
(20, '66', 'Nigdi', '0000-00-00 00:00:00', 'ravet', 'MH-12-DR-2020', '20', '1', 'akurdi', '45'),
(21, '66', 'Nigdi', '0000-00-00 00:00:00', 'ravet', 'MH-12-DR-3030', '20', '1', 'akurdi', '40'),
(22, '20', 'Nigdi', '0000-00-00 00:00:00', 'katraj', 'MH-12-DR-9999', '40', '1', 'dange chowk', '40');

-- --------------------------------------------------------

--
-- Table structure for table `stationcount`
--

CREATE TABLE IF NOT EXISTS `stationcount` (
  `id` int(12) NOT NULL AUTO_INCREMENT,
  `stationno` varchar(200) NOT NULL,
  `busid` varchar(15) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=11 ;

-- --------------------------------------------------------

--
-- Table structure for table `stationname`
--

CREATE TABLE IF NOT EXISTS `stationname` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `busid` varchar(200) NOT NULL,
  `busno` varchar(200) NOT NULL,
  `stationname` varchar(200) NOT NULL,
  `stationno` int(100) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=13 ;

--
-- Dumping data for table `stationname`
--

INSERT INTO `stationname` (`id`, `busid`, `busno`, `stationname`, `stationno`) VALUES
(1, '101', 'MH14AH1990', 'Akurdi', 1),
(2, '101', 'MH14AH1990', 'Dange Chowk', 2),
(3, '101', 'MH14AH1990', 'Aundh', 3),
(4, '101', 'MH14AH1990', 'Pune University', 4),
(5, '101', 'MH14AH1990', 'Pune Station', 5),
(9, '102', 'MH1420202', 'dehu', 1),
(10, '102', 'MH1420202', 'alandi', 1),
(11, '104', 'MH1420204', 'lonwala', 1),
(12, '105', 'MH1420205', 'nigdi', 1);

-- --------------------------------------------------------

--
-- Table structure for table `tclogin`
--

CREATE TABLE IF NOT EXISTS `tclogin` (
  `id` int(12) NOT NULL AUTO_INCREMENT,
  `name` varchar(20) NOT NULL,
  `mobile` varchar(12) NOT NULL,
  `password` varchar(20) NOT NULL,
  `busnumber` varchar(20) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=2 ;

--
-- Dumping data for table `tclogin`
--

INSERT INTO `tclogin` (`id`, `name`, `mobile`, `password`, `busnumber`) VALUES
(1, 'tc', '9999999999', '123', 'MH14AH1990');

-- --------------------------------------------------------

--
-- Table structure for table `ticketcharges`
--

CREATE TABLE IF NOT EXISTS `ticketcharges` (
  `id` int(15) NOT NULL AUTO_INCREMENT,
  `startpoint` varchar(50) NOT NULL,
  `endpoint` varchar(50) NOT NULL,
  `charges` varchar(15) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=11 ;

--
-- Dumping data for table `ticketcharges`
--

INSERT INTO `ticketcharges` (`id`, `startpoint`, `endpoint`, `charges`) VALUES
(1, 'Akurdi', 'Dange Chowk', '5'),
(2, 'Akurdi', 'Aundh', '10'),
(3, 'Akurdi', 'Pune University', '15'),
(4, 'Akurdi', 'Pune Station', '20'),
(5, 'Dange Chowk', 'Aundh', '5'),
(6, 'Dange Chowk', 'Pune University', '10'),
(7, 'Dange Chowk', 'Pune Station', '15'),
(8, 'Aundh', 'Pune University', '5'),
(9, 'Aundh', 'Pune Station', '10'),
(10, 'Pune University', 'Pune Station', '5');

-- --------------------------------------------------------

--
-- Table structure for table `user_info`
--

CREATE TABLE IF NOT EXISTS `user_info` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `fname` varchar(100) NOT NULL,
  `lname` varchar(100) NOT NULL,
  `gender` varchar(100) NOT NULL,
  `age` varchar(100) NOT NULL,
  `email` varchar(100) NOT NULL,
  `mobno` varchar(100) NOT NULL,
  `username` varchar(100) NOT NULL,
  `password` varchar(100) NOT NULL,
  `aadhar` varchar(20) NOT NULL,
  `ppicture` varchar(100) NOT NULL,
  `cardno` varchar(100) NOT NULL DEFAULT '0',
  `cvv` varchar(100) NOT NULL DEFAULT '0',
  `amount` varchar(100) NOT NULL DEFAULT '2000',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=28 ;

--
-- Dumping data for table `user_info`
--

INSERT INTO `user_info` (`id`, `fname`, `lname`, `gender`, `age`, `email`, `mobno`, `username`, `password`, `aadhar`, `ppicture`, `cardno`, `cvv`, `amount`) VALUES
(1, 'RAHUL', 'BIRADAR', 'MALE', '06/11/1995', 'rahul.srccode@gmail.com', '9665234874', 'rahul', '123', '', 'logg.jpg', '816894588556', '222', '20955'),
(4, 'TEJAS', 'SAKOLE', 'MALE', '06/11/1995', 'rahul.srccode@gmail.com', '9665234874', 'tejas', 'admin', '', 'student.png', '0999999999999', '123', '20000'),
(5, 'JAYDEEP', 'SAKOLE', 'MALE', '06/11/1995', 'jaydeep.srccode@gmail.com', '9665234874', 'jaydeep', '1234', '', 'logg.jpg', '978648484846', '233', '61000'),
(26, 'rahul', 'biradar', 'Male', '22/11/1997', 'r@gmail.com', '7020907782', '', '123', '484648488484', '6.png', '0', '0', '0'),
(27, 'PRIYA', 'KESHARI', 'MALE', '23/11/1994', 'priya.srccode@gmail.com', '9665234874', 'priya', '123', '111111111111', 'attandance_sys.png', '1233333333333', '123', '20000');

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
