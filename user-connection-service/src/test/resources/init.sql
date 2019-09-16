CREATE TABLE IF NOT EXISTS `connection` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `originator_id` bigint NOT NULL,
  `recipient_id` bigint NOT NULL,
  `relationship` varchar(16) NOT NULL,
  PRIMARY KEY (`id`)
) DEFAULT CHARSET=utf8;

CREATE TABLE IF NOT EXISTS `connection_user` (
  `id` bigint NOT NULL,
  `username` varchar(16) NOT NULL,
  PRIMARY KEY (`id`)
) DEFAULT CHARSET=utf8;