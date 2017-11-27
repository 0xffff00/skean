create table IF NOT EXISTS `navy_ship` (
  `code` varchar (150),
  `name` varchar (150),
  `weight` int,
  `birth_year` int,
  `create_time` datetime,
  `update_time` datetime
);

create table IF NOT EXISTS `navy_fleet` (
  `country_code` varchar (150),
  `code` varchar (150),
  `name` varchar (150),
  `leader_name` varchar (150),
  `update_time` datetime
);

create table IF NOT EXISTS `navy_fleet_ship` (
  `country_code` varchar (150),
  `fleet_code` varchar (150),
  `ship_code` varchar (150),
  `is_flagship` tinyint(1)
);