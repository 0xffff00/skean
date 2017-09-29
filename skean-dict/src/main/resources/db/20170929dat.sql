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

/*Data for the table `dct_rel_ge_dat1` */

insert  into `dct_rel_ge_dat1`(`key`,`attr`,`attrx`,`pred`,`vno`,`val`,`adv`,`time1`,`time2`) values 
('aaa11','attr1',NULL,NULL,1,'bbb',NULL,NULL,NULL),
('北京大学','主要学科特色','','IS',0,'综合','',0,0),
('北京大学','主要高考招生批次','','IS',0,'一本','',0,0),
('北京大学','办学性质','','IS',0,'公办','',0,0),
('北京大学','学院','','HAS',0,'经济学院','',0,0),
('北京大学','所在地(地级）','','IS',0,'海淀区','',0,0),
('北京大学','所在地(省级）','','IS',0,'北京市','',0,0),
('北京大学','所属国家','','IS',0,'中国','',0,0),
('北京大学','所属部门','','IS',0,'教育部','',0,0),
('华东师范大学','d',NULL,'HAS',1,'gf',NULL,NULL,NULL),
('华东师范大学','d',NULL,'HAS',2,'ccv',NULL,NULL,NULL),
('华东师范大学','xv',NULL,'HAS',1,'s',NULL,NULL,NULL),
('华东师范大学','xv',NULL,'HAS',3,'sd',NULL,NULL,NULL),
('华东师范大学','xv',NULL,'HAS',4,'c',NULL,NULL,NULL),
('华东师范大学','xv',NULL,'HAS',5,'sdfsf',NULL,NULL,NULL),
('复旦大学','办学性质','','IS',0,'公办','',0,0),
('复旦大学','学科特色','主要','IS',0,'综合','',0,0),
('复旦大学','学院','','HAS',0,'经济学院','',0,0),
('复旦大学','学院','','HAS',1,'计算机科学计算学院','',0,0),
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

/*Data for the table `dct_rel_ge_dat2` */

insert  into `dct_rel_ge_dat2`(`key`,`attr`,`attrx`,`pred`,`vno`,`valstr`,`valnum`,`valmu`,`adv`,`time1`,`time2`) values 
('北京大学','QS世界大学排名','','IS',0,'',38,'名次','',2016,2017),
('北京大学','创办时间','','IS',0,'',1898,'年','',0,0),
('北京大学','面积','','IS',0,'',4488,'亩','',0,0),
('华东师范大学','asd',NULL,'HAS',0,'334',NULL,'232',NULL,NULL,NULL),
('华东师范大学','asd',NULL,'HAS',1,'saweq',NULL,NULL,NULL,NULL,NULL),
('华东师范大学','asd',NULL,'HAS',2,'wwewe',NULL,NULL,NULL,NULL,NULL),
('华东师范大学','asd',NULL,'HAS',3,'5656',NULL,NULL,NULL,NULL,NULL),
('华东师范大学','asd',NULL,'HAS',4,NULL,546546,NULL,NULL,NULL,NULL),
('华东师范大学','asd',NULL,'HAS',5,'sfgf',NULL,NULL,NULL,NULL,NULL),
('华东师范大学','fgfg',NULL,'HAS',0,NULL,75665,'xx',NULL,NULL,NULL),
('复旦大学','QS世界大学排名','','IS',0,'',40,'名次','',2016,2017),
('复旦大学','创办时间','','IS',0,'',1905,'年','',0,0),
('复旦大学','校训','','IS',0,'博学而笃志，切问而近思',0,'','',0,0),
('复旦大学','面积','大致','IS',0,'',3476,'亩','',0,0);

/*Data for the table `dct_rel_ge_def` */

insert  into `dct_rel_ge_def`(`claz`,`attr`,`attrx`,`type`) values 
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

/*Data for the table `dct_rel_sp_alias` */

insert  into `dct_rel_sp_alias`(`key`,`attr`,`lang`,`vno`,`val`,`adv`,`time1`,`time2`) values 
('复旦大学','ABBR','en',0,'FDU',NULL,NULL,NULL),
('复旦大学','FULL','en',0,'Fudan University',NULL,NULL,NULL),
('复旦大学','ABBR','zh',0,'复旦',NULL,NULL,NULL),
('复旦大学','FULL','zh',0,'复旦大学',NULL,NULL,NULL);

/*Data for the table `dct_rel_sp_dual` */

insert  into `dct_rel_sp_dual`(`key`,`attr`,`vno`,`val`,`adv`,`time1`,`time2`) values 
('211高校','INST',0,'上海财经大学',NULL,NULL,NULL),
('211高校','SUBS',0,'985高校',NULL,NULL,NULL),
('985高校','INST',0,'清华大学',NULL,NULL,NULL),
('985高校','INST',1,'华东师范大学',NULL,NULL,NULL),
('985高校','SUBS',0,'C9高校',NULL,NULL,NULL),
('985高校','SUBS',1,'华东五校',NULL,NULL,NULL),
('aaa','INST',0,'bbb',NULL,NULL,NULL),
('aaa','INST',1,'bbb',NULL,NULL,NULL),
('AX','INST',0,'211高校',NULL,NULL,NULL),
('C9高校','INST',0,'清华大学',NULL,NULL,NULL),
('C9高校','INST',1,'北京大学',NULL,NULL,NULL),
('C9高校','INST',3,'复旦大学',NULL,NULL,NULL),
('cvsdsfs','GECH',0,'复旦大学',NULL,NULL,NULL),
('中国','GECH',0,'中国高校',NULL,NULL,NULL),
('中国','GECH',1,'中国经济',NULL,NULL,NULL),
('中国高校','SUBS',0,'211高校',NULL,NULL,NULL),
('华东五校','INST',0,'复旦大学',NULL,NULL,NULL),
('复旦大学','GECH',0,'dsfsdf',NULL,NULL,NULL),
('复旦研究生园委会','SUBS',0,'复旦研究生园委会主席团',NULL,NULL,NULL),
('复旦研究生园委会','SUBS',1,'复旦研究生园委会新媒体部',NULL,NULL,NULL),
('学校','SUBS',0,'高校',NULL,NULL,NULL),
('学校社团','INST',0,'复旦大学旅游协会',NULL,NULL,NULL),
('学校社团','INST',1,'复旦研究生园委会',NULL,NULL,NULL),
('高校','SUBS',0,'中国高校',NULL,NULL,NULL);

/*Data for the table `dct_word` */

insert  into `dct_word`(`text`,`desc`,`save_time`) values 
('211高校','auto imported',NULL),
('985高校','auto imported',NULL),
('C9高校','auto imported',NULL),
('FDU','auto imported',NULL),
('Fudan University','auto imported',NULL),
('hhh123',NULL,NULL),
('HZK','auto imported',NULL),
('一本','auto imported',NULL),
('上海市','auto imported',NULL),
('中国','auto imported',NULL),
('中国经济','auto imported',NULL),
('中国高校','auto imported',NULL),
('公办','auto imported',NULL),
('北京大学','auto imported',NULL),
('北京市','auto imported',NULL),
('华东五校','auto imported',NULL),
('复旦','auto imported',NULL),
('复旦大学','auto imported',NULL),
('复旦大学张江校区','auto imported',NULL),
('复旦大学旅游协会','auto imported',NULL),
('复旦大学枫林校区','auto imported',NULL),
('复旦大学江湾校区','auto imported',NULL),
('复旦大学邯郸校区','auto imported',NULL),
('复旦研究生园委会','auto imported',NULL),
('复旦研究生园委会主席团','auto imported',NULL),
('复旦研究生园委会新媒体部','auto imported',NULL),
('学校','auto imported',NULL),
('学校社团','auto imported',NULL),
('教育部','auto imported',NULL),
('杨浦区','auto imported',NULL),
('杨玉良','auto imported',NULL),
('海淀区','auto imported',NULL),
('经济学院','auto imported',NULL),
('综合','auto imported',NULL),
('计算机学院','auto imported',NULL),
('计算机科学计算学院','auto imported',NULL),
('许宁生','auto imported',NULL),
('高校','auto imported',NULL);

/*Data for the table `id_role` */

insert  into `id_role`(`name`,`state`) values 
('r1','A'),
('r2','A'),
('r3','A'),
('r31','A'),
('r321','A'),
('r3213','A'),
('r4','A'),
('r41','A'),
('role1','A'),
('role2','A'),
('role3','A');

/*Data for the table `id_user` */

insert  into `id_user`(`name`,`name_disp`,`name_full`,`psd`,`state`,`lop_time`) values 
('hzk','hzk','hzkkk','111','A','2017-07-30 23:09:44'),
('jack',NULL,NULL,NULL,'A',NULL),
('tom',NULL,NULL,NULL,'A',NULL);

/*Data for the table `id_user_role` */

insert  into `id_user_role`(`user_name`,`role_name`,`role_seq`) values 
('hzk','role1',NULL),
('hzk','role2',NULL),
('hzk','role3',NULL),
('jack','role1',NULL),
('jack','role3',NULL),
('tom','role2',NULL),
('tom','role3',NULL);

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
