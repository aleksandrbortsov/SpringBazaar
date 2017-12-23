CREATE TABLE `spring_bazaar`.`sb_users` (
  `id` INT UNSIGNED NOT NULL AUTO_INCREMENT,
  `username` VARCHAR(45) NOT NULL,
  `password` VARCHAR(255) NOT NULL,
  `state` TINYINT(1) UNSIGNED NULL,
  `group_id` INT(1) UNSIGNED NULL,
  `person_id` INT UNSIGNED NULL,
  `account_non_expired` TINYINT(1) UNSIGNED NULL DEFAULT 1,
  `account_non_locked` TINYINT(1) UNSIGNED NULL DEFAULT 1,
  `credentials_non_expired` TINYINT(1) UNSIGNED NULL DEFAULT 1,
  PRIMARY KEY (`id`));
  

  CREATE TABLE `spring_bazaar`.`sb_roles` (
  `id` INT UNSIGNED NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`id`));


  CREATE TABLE `spring_bazaar`.`sb_groups` (
  `id` INT UNSIGNED NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`id`));
  

  CREATE TABLE `spring_bazaar`.`sb_persons` (
  `id` INT UNSIGNED NOT NULL AUTO_INCREMENT,
  `user_id` INT UNSIGNED NULL,
  `email` VARCHAR(45) NULL,
  `first_name` VARCHAR(45) NULL,
  `middle_name` VARCHAR(45) NULL,
  `last_name` VARCHAR(45) NULL,
  PRIMARY KEY (`id`));
  

  CREATE TABLE `spring_bazaar`.`sb_products` (
  `id` INT UNSIGNED NOT NULL AUTO_INCREMENT,
  `caption` VARCHAR(50) NOT NULL,
  `description` VARCHAR(500) DEFAULT NULL,
  `price` DECIMAL(8,2) DEFAULT NULL,
  `image_url` VARCHAR(200) DEFAULT NULL,
  `person_id` INT NOT NULL,
  `created_when` DATETIME NOT NULL,
  `created_by` INT UNSIGNED NOT NULL,
  PRIMARY KEY (`id`));
  

  CREATE TABLE `spring_bazaar`.`sb_users_roles` (
  `user_id` INT UNSIGNED NOT NULL,
  `role_id` INT UNSIGNED NOT NULL,
  PRIMARY KEY (`user_id`,`role_id`),
  KEY `fk_usersroles_role_idx` (`role_id`),
  CONSTRAINT `fk_usersroles_user` FOREIGN KEY (`user_id`) REFERENCES `sb_users` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `fk_usersroles_role` FOREIGN KEY (`role_id`) REFERENCES `sb_roles` (`id`) ON DELETE CASCADE ON UPDATE CASCADE);