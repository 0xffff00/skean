/*
SQLyog  v12.2.6 (64 bit)
MySQL - 10.1.23-MariaDB : Database - skean0
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`skean0` /*!40100 DEFAULT CHARACTER SET utf8 */;

USE `skean0`;

/*Table structure for table `dct_rel_ge_dat1` */

DROP TABLE IF EXISTS `dct_rel_ge_dat1`;

CREATE TABLE `dct_rel_ge_dat1` (
  `key` varchar(200) NOT NULL,
  `attr` varchar(100) NOT NULL,
  `attrx` varchar(100) DEFAULT '',
  `pred` char(3) DEFAULT 'IS',
  `vno` smallint(6) NOT NULL DEFAULT '0',
  `val` varchar(100) DEFAULT NULL,
  `adv` varchar(100) DEFAULT NULL,
  `time1` bigint(20) DEFAULT NULL,
  `time2` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`key`,`attr`,`vno`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Table structure for table `dct_rel_ge_dat2` */

DROP TABLE IF EXISTS `dct_rel_ge_dat2`;

CREATE TABLE `dct_rel_ge_dat2` (
  `key` varchar(200) NOT NULL,
  `attr` varchar(100) NOT NULL,
  `attrx` varchar(100) DEFAULT '',
  `pred` char(3) DEFAULT NULL,
  `vno` smallint(6) NOT NULL DEFAULT '0',
  `valstr` varchar(200) DEFAULT NULL,
  `valnum` int(11) DEFAULT NULL,
  `valmu` varchar(25) DEFAULT NULL,
  `adv` varchar(100) DEFAULT NULL,
  `time1` bigint(20) DEFAULT NULL,
  `time2` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`key`,`attr`,`vno`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Table structure for table `dct_rel_ge_def` */

DROP TABLE IF EXISTS `dct_rel_ge_def`;

CREATE TABLE `dct_rel_ge_def` (
  `claz` varchar(200) NOT NULL,
  `attr` varchar(100) NOT NULL,
  `attrx` varchar(100) NOT NULL DEFAULT '',
  `type` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`claz`,`attr`,`attrx`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Table structure for table `dct_rel_sp_alias` */

DROP TABLE IF EXISTS `dct_rel_sp_alias`;

CREATE TABLE `dct_rel_sp_alias` (
  `key` varchar(100) NOT NULL,
  `attr` char(4) DEFAULT NULL,
  `lang` char(3) DEFAULT NULL,
  `vno` smallint(6) DEFAULT '0',
  `val` varchar(100) NOT NULL,
  `adv` varchar(100) DEFAULT NULL,
  `time1` bigint(20) DEFAULT NULL,
  `time2` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`key`,`val`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Table structure for table `dct_rel_sp_dual` */

DROP TABLE IF EXISTS `dct_rel_sp_dual`;

CREATE TABLE `dct_rel_sp_dual` (
  `key` varchar(100) NOT NULL,
  `attr` char(4) NOT NULL,
  `vno` smallint(6) NOT NULL DEFAULT '0',
  `val` varchar(100) DEFAULT NULL,
  `adv` varchar(100) DEFAULT NULL,
  `time1` bigint(20) DEFAULT NULL,
  `time2` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`key`,`attr`,`vno`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Table structure for table `dct_word` */

DROP TABLE IF EXISTS `dct_word`;

CREATE TABLE `dct_word` (
  `text` varchar(50) NOT NULL,
  `desc` varchar(255) DEFAULT NULL,
  `save_time` datetime DEFAULT NULL,
  PRIMARY KEY (`text`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Table structure for table `hd_media` */

DROP TABLE IF EXISTS `hd_media`;

CREATE TABLE `hd_media` (
  `hash` char(40) NOT NULL,
  `size` bigint(10) DEFAULT NULL,
  `type` char(8) DEFAULT NULL,
  `subtype` char(12) DEFAULT NULL,
  `desc` varchar(255) DEFAULT NULL,
  `sync_time` datetime DEFAULT NULL,
  PRIMARY KEY (`hash`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Table structure for table `hd_media_image` */

DROP TABLE IF EXISTS `hd_media_image`;

CREATE TABLE `hd_media_image` (
  `hash` char(40) NOT NULL,
  `type` varchar(60) DEFAULT NULL,
  `height` int(11) DEFAULT NULL,
  `width` int(11) DEFAULT NULL,
  `exif_bit_depth` smallint(6) DEFAULT NULL,
  `exif_make` varchar(200) DEFAULT NULL,
  `exif_model` varchar(200) DEFAULT NULL,
  `exif_date_time` datetime DEFAULT NULL,
  `exif_color_space` varchar(200) DEFAULT NULL,
  `exif_exposure_time` varchar(200) DEFAULT NULL,
  `exif_white_balance` varchar(200) DEFAULT NULL,
  `exif_aperture` varchar(200) DEFAULT NULL,
  PRIMARY KEY (`hash`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Table structure for table `hd_media_path` */

DROP TABLE IF EXISTS `hd_media_path`;

CREATE TABLE `hd_media_path` (
  `hash` char(40) NOT NULL,
  `path` varchar(255) NOT NULL,
  `type` char(8) DEFAULT NULL,
  `repo_name` varchar(60) DEFAULT NULL,
  `sync_time` datetime DEFAULT NULL,
  PRIMARY KEY (`hash`,`path`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Table structure for table `hd_repo` */

DROP TABLE IF EXISTS `hd_repo`;

CREATE TABLE `hd_repo` (
  `name` varchar(100) NOT NULL,
  `abs_path` varchar(2000) DEFAULT NULL,
  `url` varchar(2000) DEFAULT NULL,
  `type` char(8) DEFAULT NULL,
  `desc` varchar(2000) DEFAULT NULL,
  `save_time` datetime DEFAULT NULL,
  `state` char(1) DEFAULT NULL,
  PRIMARY KEY (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Table structure for table `hd_repo_log` */

DROP TABLE IF EXISTS `hd_repo_log`;

CREATE TABLE `hd_repo_log` (
  `action_time` datetime NOT NULL,
  `action_type` varchar(255) NOT NULL,
  `entity_key` varchar(255) NOT NULL,
  `entity_val` varchar(255) NOT NULL,
  `result` varchar(200) DEFAULT NULL,
  `desc` varchar(1000) DEFAULT NULL,
  PRIMARY KEY (`action_time`,`action_type`,`entity_key`,`entity_val`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Table structure for table `id_role` */

DROP TABLE IF EXISTS `id_role`;

CREATE TABLE `id_role` (
  `name` varchar(50) NOT NULL,
  `state` char(1) NOT NULL DEFAULT 'A',
  PRIMARY KEY (`name`,`state`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Table structure for table `id_user` */

DROP TABLE IF EXISTS `id_user`;

CREATE TABLE `id_user` (
  `name` varchar(60) NOT NULL,
  `name_disp` varchar(60) DEFAULT NULL,
  `name_full` varchar(60) DEFAULT NULL,
  `psd` varchar(60) DEFAULT NULL,
  `state` char(1) DEFAULT 'A',
  `lop_time` datetime DEFAULT NULL,
  PRIMARY KEY (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Table structure for table `id_user_role` */

DROP TABLE IF EXISTS `id_user_role`;

CREATE TABLE `id_user_role` (
  `user_name` varchar(50) NOT NULL,
  `role_name` varchar(50) NOT NULL,
  `role_seq` int(11) DEFAULT NULL,
  PRIMARY KEY (`user_name`,`role_name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Table structure for table `dct_v$noun` */

DROP TABLE IF EXISTS `dct_v$noun`;

/*!50001 DROP VIEW IF EXISTS `dct_v$noun` */;
/*!50001 DROP TABLE IF EXISTS `dct_v$noun` */;

/*!50001 CREATE TABLE  `dct_v$noun`(
 `key` varchar(200) 
)*/;

/*View structure for view dct_v$noun */

/*!50001 DROP TABLE IF EXISTS `dct_v$noun` */;
/*!50001 DROP VIEW IF EXISTS `dct_v$noun` */;

/*!50001 CREATE ALGORITHM=UNDEFINED DEFINER=`root`@`10.0.%` SQL SECURITY DEFINER VIEW `dct_v$noun` AS select `dct_rel_ge_dat1`.`key` AS `key` from `dct_rel_ge_dat1` union select `dct_rel_ge_dat1`.`val` AS `val` from `dct_rel_ge_dat1` union select `dct_rel_ge_dat2`.`key` AS `key` from `dct_rel_ge_dat2` union select `dct_rel_sp_dual`.`key` AS `key` from `dct_rel_sp_dual` union select `dct_rel_sp_dual`.`val` AS `val` from `dct_rel_sp_dual` union select `dct_rel_sp_alias`.`key` AS `key` from `dct_rel_sp_alias` union select `dct_rel_sp_alias`.`val` AS `val` from `dct_rel_sp_alias` */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
