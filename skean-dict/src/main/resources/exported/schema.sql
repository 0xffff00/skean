DROP TABLE IF EXISTS `dct_rel_b`;

CREATE TABLE `dct_rel_b` (
  `src` varchar(200) NOT NULL,
  `attr` char(4) NOT NULL,
  `no` smallint(6) NOT NULL,
  `dst` varchar(200) DEFAULT NULL,
  `memo` varchar(5000) DEFAULT NULL,
  PRIMARY KEY (`src`,`attr`,`no`),
  KEY `dct_idx_dst2` (`dst`)
)  DEFAULT CHARSET=utf8;

/*Data for the table `dct_rel_b` */

insert  into `dct_rel_b`(`src`,`attr`,`no`,`dst`,`memo`) values

  ('211高校','INST',0,'上海财经大学',NULL),

  ('211高校','SUBS',0,'985高校',NULL),

  ('985高校','INST',0,'清华大学',NULL),

  ('985高校','INST',1,'华东师范大学',NULL),

  ('985高校','SUBS',0,'C9高校',NULL),

  ('985高校','SUBS',1,'华东五校',NULL),

  ('C9高校','INST',0,'清华大学',NULL),

  ('C9高校','INST',1,'北京大学',NULL),

  ('C9高校','INST',3,'复旦大学',NULL),

  ('中国','SUBT',0,'中国高校',NULL),

  ('中国','SUBT',1,'中国经济',NULL),

  ('中国高校','SUBS',0,'211高校',NULL),

  ('华东五校','INST',0,'复旦大学',NULL),

  ('复旦大学','ALIA',8,'FDU',NULL),

  ('复旦大学','ALIA',9,'Fudan University',NULL),

  ('复旦大学','ALIA',12,'复旦',NULL),

  ('学校','SUBS',0,'高校',NULL),

  ('高校','SUBS',0,'中国高校',NULL);

/*Table structure for table `dct_rel_x1` */

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
  PRIMARY KEY (`src`,`attr`,`no`),
  KEY `dct_idx_dst2` (`dst`)
)  DEFAULT CHARSET=utf8;

/*Data for the table `dct_rel_x1` */

insert  into `dct_rel_x1`(`src`,`attr`,`no`,`dst`,`attrx`,`pred`,`dst_txt`,`dst_num`,`dst_mu`,`adv`,`time1`,`time2`,`memo`) values

  ('北京大学','QS世界大学排名',0,NULL,'','IS','',38.0000,'名次',NULL,NULL,NULL,NULL),

  ('北京大学','主要学科特色',0,'综合','','IS',NULL,NULL,NULL,NULL,NULL,NULL,NULL),

  ('北京大学','主要高考招生批次',0,'一本','','IS',NULL,NULL,NULL,NULL,NULL,NULL,NULL),

  ('北京大学','创办时间',0,NULL,'','IS','',1898.0000,'年',NULL,NULL,NULL,NULL),

  ('北京大学','办学性质',0,'公办','','IS',NULL,NULL,NULL,NULL,NULL,NULL,NULL),

  ('北京大学','学院',0,'经济学院','','HAS',NULL,NULL,NULL,NULL,NULL,NULL,NULL),

  ('北京大学','所在地(地级）',0,'海淀区','','IS',NULL,NULL,NULL,NULL,NULL,NULL,NULL),

  ('北京大学','所在地(省级）',0,'北京市','','IS',NULL,NULL,NULL,NULL,NULL,NULL,NULL),

  ('北京大学','所属国家',0,'中国','','IS',NULL,NULL,NULL,NULL,NULL,NULL,NULL),

  ('北京大学','所属部门',0,'教育部','','IS',NULL,NULL,NULL,NULL,NULL,NULL,NULL),

  ('北京大学','面积',0,NULL,'','IS','',4488.0000,'亩',NULL,NULL,NULL,NULL),

  ('复旦大学','QS世界大学排名',0,NULL,'','IS','',40.0000,'名次',NULL,NULL,NULL,NULL),

  ('复旦大学','创办时间',0,NULL,'','IS','',1905.0000,'年',NULL,NULL,NULL,NULL),

  ('复旦大学','办学性质',0,'公办','','IS',NULL,NULL,NULL,NULL,NULL,NULL,NULL),

  ('复旦大学','学科特色',0,'综合','主要','IS',NULL,NULL,NULL,NULL,NULL,NULL,NULL),

  ('复旦大学','学院',0,'经济学院','','HAS',NULL,NULL,NULL,NULL,NULL,NULL,NULL),

  ('复旦大学','学院',1,'计算机科学计算学院','','HAS',NULL,NULL,NULL,NULL,NULL,NULL,NULL),

  ('复旦大学','所在地(地级）',0,'杨浦区','','IS',NULL,NULL,NULL,NULL,NULL,NULL,NULL),

  ('复旦大学','所在地(省级）',0,'上海市','','IS',NULL,NULL,NULL,NULL,NULL,NULL,NULL),

  ('复旦大学','所属国家',0,'中国','','IS',NULL,NULL,NULL,NULL,NULL,NULL,NULL),

  ('复旦大学','所属部门',0,'教育部','','IS',NULL,NULL,NULL,NULL,NULL,NULL,NULL),

  ('复旦大学','校区',0,'复旦大学邯郸校区','','ARE',NULL,NULL,NULL,NULL,NULL,NULL,NULL),

  ('复旦大学','校区',1,'复旦大学江湾校区','','ARE',NULL,NULL,NULL,NULL,NULL,NULL,NULL),

  ('复旦大学','校区',2,'复旦大学张江校区','','ARE',NULL,NULL,NULL,NULL,NULL,NULL,NULL),

  ('复旦大学','校区',3,'复旦大学枫林校区','','ARE',NULL,NULL,NULL,NULL,NULL,NULL,NULL),

  ('复旦大学','校训',0,NULL,'','IS','博学而笃志，切问而近思',0.0000,'',NULL,NULL,NULL,NULL),

  ('复旦大学','校长',0,'许宁生','','IS',NULL,NULL,NULL,NULL,NULL,NULL,NULL),

  ('复旦大学','校长',1,'杨玉良','','IS',NULL,NULL,NULL,NULL,NULL,NULL,NULL),

  ('复旦大学','面积',0,NULL,'大致','IS','',3476.0000,'亩',NULL,NULL,NULL,NULL),

  ('复旦大学','高考招生批次',0,'一本','主要','IS',NULL,NULL,NULL,NULL,NULL,NULL,NULL),

/*Table structure for table `dct_word` */

DROP TABLE IF EXISTS `dct_word`;

CREATE TABLE `dct_word` (
  `text` varchar(50) NOT NULL,
  `desc` varchar(255) DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `state` char(1) DEFAULT NULL,
  PRIMARY KEY (`text`)
)  DEFAULT CHARSET=utf8;

/*Data for the table `dct_word` */

insert  into `dct_word`(`text`,`desc`,`update_time`,`state`) values

  ('211高校','auto imported',NULL,NULL),

  ('985高校','auto imported',NULL,NULL),

  ('C9高校','auto imported',NULL,NULL),

  ('FDU','auto imported',NULL,NULL),

  ('Fudan University','auto imported',NULL,NULL),

  ('一本','auto imported',NULL,NULL),

  ('上海市','auto imported',NULL,NULL),

  ('中国','auto imported',NULL,NULL),

  ('中国经济','auto imported',NULL,NULL),

  ('中国高校','auto imported',NULL,NULL),

  ('公办','auto imported',NULL,NULL),

  ('北京大学','auto imported',NULL,NULL),

  ('北京市','auto imported',NULL,NULL),

  ('华东五校','auto imported',NULL,NULL),

  ('复旦','auto imported',NULL,NULL),

  ('复旦大学','auto imported',NULL,NULL),

  ('复旦大学张江校区','auto imported',NULL,NULL),

  ('复旦大学旅游协会','auto imported',NULL,NULL),

  ('复旦大学枫林校区','auto imported',NULL,NULL),

  ('复旦大学江湾校区','auto imported',NULL,NULL),

  ('复旦大学邯郸校区','auto imported',NULL,NULL),

  ('复旦研究生园委会','auto imported',NULL,NULL),

  ('学校','auto imported',NULL,NULL),

  ('学校社团','auto imported',NULL,NULL),

  ('教育部','auto imported',NULL,NULL),

  ('杨浦区','auto imported',NULL,NULL),

  ('杨玉良','auto imported',NULL,NULL),

  ('海淀区','auto imported',NULL,NULL),

  ('经济学院','auto imported',NULL,NULL),

  ('综合','auto imported',NULL,NULL),

  ('计算机学院','auto imported',NULL,NULL),

  ('计算机科学计算学院','auto imported',NULL,NULL),

  ('许宁生','auto imported',NULL,NULL),

  ('高校','auto imported',NULL,NULL);