CREATE TABLE IF NOT EXISTS `user_entity` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `username` varchar(16) NOT NULL,
  `password` varchar(64) NOT NULL,
  PRIMARY KEY (`id`)
) DEFAULT CHARSET=utf8;
