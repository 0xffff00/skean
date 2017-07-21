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

/*Table structure for table `dct_noun` */

DROP TABLE IF EXISTS `dct_noun`;

CREATE TABLE `dct_noun` (
  `word` varchar(50) NOT NULL,
  `qual` varchar(50) NOT NULL DEFAULT '' COMMENT 'classifier',
  `lang` char(6) DEFAULT NULL,
  `type` char(6) DEFAULT NULL,
  PRIMARY KEY (`word`,`qual`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `dct_noun` */

insert  into `dct_noun`(`word`,`qual`,`lang`,`type`) values 
('985高校','','zh',NULL),
('China','','en',NULL),
('Chine','','fr',NULL),
('CHN','','en','abbr'),
('CN','','en','abbr'),
('fdu','','en','abbr'),
('fudan','','en','abbr'),
('SH','','en','abbr'),
('Shanghai','','en','abbr'),
('上海','','zh','full'),
('上海985高校','','zh',NULL),
('上海市','','zh','full'),
('上海高校','','zh',NULL),
('东南大学','','zh',NULL),
('中国','','zh',NULL),
('中国高校','','zh',NULL),
('九校联盟','','zh',NULL),
('加利福利亚州','','zh',NULL),
('加州大学伯克利分校','','zh',NULL),
('加州高校','','zh',NULL),
('华东师范大学','','zh',NULL),
('南京大学','','zh',NULL),
('南京市','','zh',NULL),
('县级行政区','','zh',NULL),
('国家','','zh',NULL),
('圣克拉拉县','','zh',NULL),
('地级行政区','','zh',NULL),
('复旦','','zh','abbr'),
('复旦大学','','zh',NULL),
('大学','','zh',NULL),
('扬州市','','zh',NULL),
('斯坦福大学','','zh',NULL),
('杨浦区','','zh',NULL),
('江苏省','','zh','full'),
('沪','','zh','abbr'),
('泰州市','','zh',NULL),
('浦东新区','','zh',NULL),
('省级行政区','','zh',NULL),
('美国','','zh',NULL),
('美国高校','','zh',NULL),
('苏州市','','zh',NULL),
('镇江市','','zh',NULL),
('闵行区','','zh',NULL),
('阿拉梅达县','','zh',NULL),
('高校','','zh',NULL),
('魔都','','zh','surn'),
('黄浦区','','zh',NULL);

/*Table structure for table `dct_rel_alias` */

DROP TABLE IF EXISTS `dct_rel_alias`;

CREATE TABLE `dct_rel_alias` (
  `main` varchar(100) NOT NULL,
  `attr` char(4) NOT NULL,
  `lang` char(3) NOT NULL,
  `vno` smallint(6) NOT NULL DEFAULT '0',
  `val` varchar(100) NOT NULL,
  `adv` varchar(100) DEFAULT NULL,
  `time1` bigint(20) DEFAULT NULL,
  `time2` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`main`,`attr`,`lang`,`vno`,`val`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `dct_rel_alias` */

insert  into `dct_rel_alias`(`main`,`attr`,`lang`,`vno`,`val`,`adv`,`time1`,`time2`) values 
('复旦大学','ABBR','en',0,'FDU\r',NULL,NULL,NULL),
('复旦大学','ABBR','zh',0,'复旦\r',NULL,NULL,NULL),
('复旦大学','FULL','en',0,'Fudan University\r',NULL,NULL,NULL),
('复旦大学','FULL','zh',0,'复旦大学\r',NULL,NULL,NULL);

/*Table structure for table `dct_rel_attr` */

DROP TABLE IF EXISTS `dct_rel_attr`;

CREATE TABLE `dct_rel_attr` (
  `claz` varchar(200) NOT NULL,
  `attr` varchar(100) NOT NULL,
  `attr2` varchar(100) NOT NULL DEFAULT '',
  `type` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`claz`,`attr`,`attr2`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `dct_rel_attr` */

insert  into `dct_rel_attr`(`claz`,`attr`,`attr2`,`type`) values 
('中国','省级行政区','','str'),
('国家','GDP人均','','str'),
('国家','GDP总量','','str'),
('国家','人口','','str'),
('国家','所在洲','','str'),
('国家','面积','','str'),
('学校','办学性质','','str\r'),
('学校','学科特色','主要','str\r'),
('学校','学院','','str\r'),
('学校','所在地(地级）','','str\r'),
('学校','所在地(省级）','','str\r'),
('学校','所属国家','','str\r'),
('学校','所属部门','','str\r'),
('学校','校区','','str\r'),
('学校','校长','','str\r'),
('学校','面积','大致','str\r'),
('学校','高考招生批次','主要','str\r'),
('学校社团','所属学校','','str');

/*Table structure for table `dct_rel_attr_dat0` */

DROP TABLE IF EXISTS `dct_rel_attr_dat0`;

CREATE TABLE `dct_rel_attr_dat0` (
  `main` varchar(200) NOT NULL,
  `attr` varchar(100) NOT NULL,
  `attr2` varchar(100) NOT NULL DEFAULT '',
  `pred` char(3) NOT NULL DEFAULT 'IS',
  `vno` smallint(6) NOT NULL DEFAULT '0',
  `val` varchar(100) DEFAULT NULL,
  `adv` varchar(100) DEFAULT NULL,
  `time1` bigint(20) DEFAULT NULL,
  `time2` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`main`,`attr`,`attr2`,`pred`,`vno`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `dct_rel_attr_dat0` */

insert  into `dct_rel_attr_dat0`(`main`,`attr`,`attr2`,`pred`,`vno`,`val`,`adv`,`time1`,`time2`) values 
('北京大学','主要学科特色','','IS',0,'综合','',0,0),
('北京大学','主要高考招生批次','','IS',0,'一本','',0,0),
('北京大学','办学性质','','IS',0,'公办','',0,0),
('北京大学','学院','','HAS',0,'经济学院','',0,0),
('北京大学','所在地(地级）','','IS',0,'海淀区','',0,0),
('北京大学','所在地(省级）','','IS',0,'北京市','',0,0),
('北京大学','所属国家','','IS',0,'中国','',0,0),
('北京大学','所属部门','','IS',0,'教育部','',0,0),
('复旦大学','办学性质','','IS',0,'公办','',0,0),
('复旦大学','学科特色','主要','IS',0,'综合','',0,0),
('复旦大学','学院','','HAS',0,'经济学院','',0,0),
('复旦大学','学院','','HAS',1,'计算机科学计算学院','',0,0),
('复旦大学','学院','','HAS',2,'经济学院','',0,0),
('复旦大学','学院','','HAS',3,'计算机学院','',0,0),
('复旦大学','所在地(地级）','','IS',0,'杨浦区','',0,0),
('复旦大学','所在地(省级）','','IS',0,'上海市','',0,0),
('复旦大学','所属国家','','IS',0,'中国','',0,0),
('复旦大学','所属部门','','IS',0,'教育部','',0,0),
('复旦大学','校区','','ARE',0,'复旦大学邯郸校区','',0,0),
('复旦大学','校区','','ARE',1,'复旦大学江湾校区','',0,0),
('复旦大学','校区','','ARE',2,'复旦大学张江校区','',0,0),
('复旦大学','校区','','ARE',3,'复旦大学枫林校区','',0,0),
('复旦大学','校长','','IS',0,'许宁生','',2014,2077),
('复旦大学','校长','','IS',1,'杨玉良','',0,2014),
('复旦大学','高考招生批次','主要','IS',0,'一本','',0,0),
('复旦大学旅游协会','所属学校','','IS',0,'复旦大学','',0,0),
('复旦研究生园委会','所属学校','','IS',0,'复旦大学','',0,0),
('复旦研究生园委会新媒体部','部长','','IS',0,'HZK','',0,0);

/*Table structure for table `dct_rel_attr_dat1` */

DROP TABLE IF EXISTS `dct_rel_attr_dat1`;

CREATE TABLE `dct_rel_attr_dat1` (
  `main` varchar(200) NOT NULL,
  `attr` varchar(100) NOT NULL,
  `attr2` varchar(100) NOT NULL DEFAULT '',
  `pred` char(3) NOT NULL,
  `vno` smallint(6) NOT NULL DEFAULT '0',
  `valstr` varchar(200) DEFAULT NULL,
  `valnum` int(11) DEFAULT NULL,
  `valmu` varchar(25) DEFAULT NULL,
  `adv` varchar(100) DEFAULT NULL,
  `time1` bigint(20) DEFAULT NULL,
  `time2` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`main`,`attr`,`attr2`,`pred`,`vno`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `dct_rel_attr_dat1` */

insert  into `dct_rel_attr_dat1`(`main`,`attr`,`attr2`,`pred`,`vno`,`valstr`,`valnum`,`valmu`,`adv`,`time1`,`time2`) values 
('北京大学','QS世界大学排名','','IS',0,'',38,'','',2016,2017),
('北京大学','创办时间','','IS',0,'',1898,'','',0,0),
('北京大学','面积','','IS',0,'',4488,'亩','',0,0),
('复旦大学','QS世界大学排名','','IS',0,'',40,'','',2016,2017),
('复旦大学','创办时间','','IS',0,'',1905,'','',0,0),
('复旦大学','校训','','IS',0,'博学而笃志，切问而近思',0,'','',0,0),
('复旦大学','面积','大致','IS',0,'',3476,'亩','',0,0);

/*Table structure for table `dct_rel_dual` */

DROP TABLE IF EXISTS `dct_rel_dual`;

CREATE TABLE `dct_rel_dual` (
  `main` varchar(100) NOT NULL,
  `attr` char(4) NOT NULL,
  `vno` smallint(6) NOT NULL DEFAULT '0',
  `val` varchar(100) NOT NULL,
  `adv` varchar(100) DEFAULT NULL,
  `time1` bigint(20) DEFAULT NULL,
  `time2` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`main`,`attr`,`vno`,`val`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `dct_rel_dual` */

insert  into `dct_rel_dual`(`main`,`attr`,`vno`,`val`,`adv`,`time1`,`time2`) values 
('211高校','PART',0,'985高校\r',NULL,NULL,NULL),
('985高校','PART',0,'C9高校\r',NULL,NULL,NULL),
('985高校','PART',1,'华东五校\r',NULL,NULL,NULL),
('C9高校','INST',0,'复旦大学\r',NULL,NULL,NULL),
('C9高校','INST',1,'北京大学\r',NULL,NULL,NULL),
('中国','UND',0,'中国高校\r',NULL,NULL,NULL),
('中国','UND',1,'中国经济\r',NULL,NULL,NULL),
('中国高校','PART',0,'211高校\r',NULL,NULL,NULL),
('华东五校','INST',0,'复旦大学\r',NULL,NULL,NULL),
('复旦研究生园委会','PART',0,'复旦研究生园委会主席团\r',NULL,NULL,NULL),
('复旦研究生园委会','PART',0,'复旦研究生园委会新媒体部\r',NULL,NULL,NULL),
('学校社团','INST',0,'复旦大学旅游协会\r',NULL,NULL,NULL),
('学校社团','INST',1,'复旦研究生园委会\r',NULL,NULL,NULL),
('高校','PART',0,'中国高校\r',NULL,NULL,NULL),
('﻿学校','PART',0,'高校\r',NULL,NULL,NULL);

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
