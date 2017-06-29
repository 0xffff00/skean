/*
SQLyog  v12.2.6 (64 bit)
MySQL - 10.1.23-MariaDB 
*********************************************************************
*/
/*!40101 SET NAMES utf8 */;

create table `t1ship` (
	`code` varchar (150),
	`name` varchar (150),
	`weig` int,
	`birth` int
);
create table dct_noun44 (
	`word` varchar (150),
	`claz` varchar (150),
	`lang` char (18),
	`type` char (18)
); 
create table `dct_noun` (
	`word` varchar (150),
	`claz` varchar (150),
	`lang` char (18),
	`type` char (18)
); 
create table `dct_rel` (
	`subject` varchar (150),
	`verb` varchar (150),
	`object` char (150),
	`adverb` char (150)
); 