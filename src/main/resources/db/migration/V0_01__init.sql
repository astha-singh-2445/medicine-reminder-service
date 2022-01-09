CREATE TABLE `medicine` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL,
  `current_dosage` int NOT NULL,
  `medicine_type` varchar(255) DEFAULT NULL,
  `user_id` bigint NOT NULL,
  `deleted` tinyint(1) NOT NULL DEFAULT '0',
  `time_created` datetime NOT NULL,
  `time_last_modified` datetime NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `medicine_UN` (`user_id`,`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;


CREATE TABLE `category` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL,
  `user_id` bigint NOT NULL,
  `deleted` tinyint(1) NOT NULL DEFAULT '0',
  `time_created` datetime NOT NULL,
  `time_last_modified` datetime NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `category_UN` (`user_id`,`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;


CREATE TABLE `medicine_category` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `category_id` bigint NOT NULL,
  `medicine_id` bigint NOT NULL,
  `deleted` tinyint(1) NOT NULL DEFAULT '0',
  `time_created` datetime NOT NULL,
  `time_last_modified` datetime NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `medicine_category_UN` (`category_id`,`medicine_id`),
  CONSTRAINT `medicine_category_FK_category` FOREIGN KEY (`category_id`) REFERENCES `category` (`id`),
  CONSTRAINT `medicine_category_FK_medicine` FOREIGN KEY (`medicine_id`) REFERENCES `medicine` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;


CREATE TABLE `dosage_history` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `dosage` int NOT NULL,
  `type` varchar(255) NOT NULL,
  `medicine_id` bigint NOT NULL,
  `user_id` bigint NOT NULL,
  `deleted` tinyint(1) NOT NULL DEFAULT '0',
  `time_created` datetime NOT NULL,
  `time_last_modified` datetime NOT NULL,
  PRIMARY KEY (`id`),
  CONSTRAINT `dosage_history_FK_medicine` FOREIGN KEY (`medicine_id`) REFERENCES `medicine` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;

