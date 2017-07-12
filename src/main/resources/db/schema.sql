/*
SQLyog  v12.2.6 (64 bit)
MySQL - 10.1.23-MariaDB : Database - webs4g
*********************************************************************
*/


/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
--CREATE DATABASE /*!32312 IF NOT EXISTS*/`webs4g` /*!40100 DEFAULT CHARACTER SET utf8 */;


create table `t1ship` (
	`code` varchar (150),
	`name` varchar (150),
	`weig` int,
	`birth` int
);
/*Table structure for table `dct_noun` */

DROP TABLE IF EXISTS `dct_noun`;

CREATE TABLE `dct_noun` (
  `word` varchar(50) NOT NULL,
  `qual` varchar(50) NOT NULL DEFAULT '' COMMENT 'classifier',
  `lang` char(6) DEFAULT NULL,
  `type` char(6) DEFAULT NULL,
  PRIMARY KEY (`word`,`qual`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;



/*Table structure for table `dct_rel` */

DROP TABLE IF EXISTS `dct_rel`;

CREATE TABLE `dct_rel` (
  `sbj` varchar(80) NOT NULL,
  `oba` varchar(80) NOT NULL,
  `obj` varchar(80) NOT NULL,
  `seq` smallint(6) NOT NULL DEFAULT '0',
  `adv` varchar(80) DEFAULT NULL,
  `ti1` varchar(50) NOT NULL DEFAULT '',
  `ti2` varchar(50) NOT NULL DEFAULT '',
  PRIMARY KEY (`sbj`,`oba`,`obj`,`ti1`,`ti2`,`seq`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
