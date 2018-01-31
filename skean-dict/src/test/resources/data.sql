insert  into `dct_rel_b`(`src`,`attr`,`no`,`dst`,`memo`) values

  ('211高校','INST',0,'上海财经大学',NULL),

  ('211高校','SUBS',0,'985高校',NULL),

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

  ('复旦大学','ALIA',12,'复旦',NULL),

  ('学校','SUBS',0,'高校',NULL),

  ('高校','SUBS',0,'中国高校',NULL);
