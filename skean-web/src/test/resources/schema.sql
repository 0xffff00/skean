create table `navy_ship` (
  `code` varchar (150),
  `name` varchar (150),
  `weight` int,
  `birth_year` int,
  `create_time` datetime,
  `update_time` datetime
);

create table `navy_fleet` (
  `country_code` varchar (150),
  `code` varchar (150),
  `name` varchar (150),
  `leader_name` varchar (150),
  `update_time` datetime
);
