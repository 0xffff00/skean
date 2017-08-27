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

insert into `t1ship` (`code`,`name`,`weig`,`birth`) values('DD32','Alagh',1324,1985);
insert into `t1ship` (`code`,`name`,`weig`,`birth`) values('CV05','Hun',25200,1988);
insert into `t1ship` (`code`,`name`,`weig`,`birth`) values('CV06','Big E',33500,1989);
insert into `t1ship` (`code`,`name`,`weig`,`birth`) values('BB14','Mas',39254,1983);
insert into `t1ship` (`code`,`name`,`weig`,`birth`) values('BB15','Lute',46852,1987);
insert into `t1ship` (`code`,`name`,`weig`,`birth`) values('BB16','Skin',45852,1987);
insert into `t1ship` (`code`,`name`,`weig`,`birth`) values('SS01',NULL,NULL,1999);