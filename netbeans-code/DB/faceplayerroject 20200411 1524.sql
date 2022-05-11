-- MySQL Administrator dump 1.4
--
-- ------------------------------------------------------
-- Server version	5.5.5-10.1.9-MariaDB


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;


--
-- Create schema faceplayer
--

CREATE DATABASE IF NOT EXISTS faceplayer;
USE faceplayer;

--
-- Definition of table `expression`
--

DROP TABLE IF EXISTS `expression`;
CREATE TABLE `expression` (
  `eid` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `exp` varchar(45) NOT NULL,
  `udate` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`eid`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `expression`
--

/*!40000 ALTER TABLE `expression` DISABLE KEYS */;
/*!40000 ALTER TABLE `expression` ENABLE KEYS */;


--
-- Definition of table `genredetails`
--

DROP TABLE IF EXISTS `genredetails`;
CREATE TABLE `genredetails` (
  `gid` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `genre` varchar(45) NOT NULL,
  PRIMARY KEY (`gid`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=latin1;

--
-- Dumping data for table `genredetails`
--

/*!40000 ALTER TABLE `genredetails` DISABLE KEYS */;
INSERT INTO `genredetails` (`gid`,`genre`) VALUES 
 (1,'pop'),
 (2,'jazz'),
 (3,'rock'),
 (4,'classic'),
 (5,'party');
/*!40000 ALTER TABLE `genredetails` ENABLE KEYS */;


--
-- Definition of table `mooddetails`
--

DROP TABLE IF EXISTS `mooddetails`;
CREATE TABLE `mooddetails` (
  `mid` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `mood` varchar(45) NOT NULL,
  PRIMARY KEY (`mid`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=latin1;

--
-- Dumping data for table `mooddetails`
--

/*!40000 ALTER TABLE `mooddetails` DISABLE KEYS */;
INSERT INTO `mooddetails` (`mid`,`mood`) VALUES 
 (1,'happy'),
 (2,'sad'),
 (3,'angry');
/*!40000 ALTER TABLE `mooddetails` ENABLE KEYS */;


--
-- Definition of table `songsddetails`
--

DROP TABLE IF EXISTS `songsddetails`;
CREATE TABLE `songsddetails` (
  `sids` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `songname` longtext NOT NULL,
  `songpath` longtext NOT NULL,
  PRIMARY KEY (`sids`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `songsddetails`
--

/*!40000 ALTER TABLE `songsddetails` DISABLE KEYS */;
/*!40000 ALTER TABLE `songsddetails` ENABLE KEYS */;


--
-- Definition of table `useraccount`
--

DROP TABLE IF EXISTS `useraccount`;
CREATE TABLE `useraccount` (
  `uid` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `fname` varchar(45) NOT NULL,
  `phone` varchar(45) NOT NULL,
  `uname` varchar(45) NOT NULL,
  `password` varchar(45) NOT NULL,
  `address` longtext NOT NULL,
  PRIMARY KEY (`uid`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `useraccount`
--

/*!40000 ALTER TABLE `useraccount` DISABLE KEYS */;
/*!40000 ALTER TABLE `useraccount` ENABLE KEYS */;




/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
