DROP TABLE IF EXISTS `dct_rel_b`;

CREATE TABLE `dct_rel_b` (
  `src` varchar(200) NOT NULL,
  `attr` char(4) NOT NULL,
  `no` smallint(6) NOT NULL,
  `dst` varchar(200) DEFAULT NULL,
  `memo` varchar(5000) DEFAULT NULL,
  PRIMARY KEY (`src`,`attr`,`no`)
);

DROP TABLE IF EXISTS `dct_rel_x1`;

CREATE TABLE `dct_rel_x1` (
  `src` varchar(200) NOT NULL,
  `attr` varchar(200) NOT NULL,
  `no` smallint(6) NOT NULL,
  `dst` varchar(200) DEFAULT NULL,
  `attrx` varchar(200) DEFAULT NULL,
  `pred` char(4) DEFAULT NULL,
  `dst_txt` varchar(5000) DEFAULT NULL,
  `dst_num` decimal(20,4) DEFAULT NULL,
  `dst_mu` varchar(100) DEFAULT NULL,
  `adv` varchar(200) DEFAULT NULL,
  `time1` datetime DEFAULT NULL,
  `time2` datetime DEFAULT NULL,
  `memo` varchar(5000) DEFAULT NULL,
  PRIMARY KEY (`src`,`attr`,`no`)
) ;

DROP TABLE IF EXISTS `dct_word`;

CREATE TABLE `dct_word` (
  `text` varchar(50) NOT NULL,
  `desc` varchar(255) DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `state` char(1) DEFAULT NULL,
  PRIMARY KEY (`text`)
) ;