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
  
CREATE TABLE `spring_bazaar`.`sb_login_attempts` (
  `id` INT UNSIGNED NOT NULL AUTO_INCREMENT,
  `username` VARCHAR(45) NOT NULL,
  `attempts` varchar(45) NOT NULL,
  `last_modified` datetime NOT NULL,
  PRIMARY KEY (`id`));


  CREATE TABLE `spring_bazaar`.`sb_roles` (
  `id` INT UNSIGNED NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(45) NOT NULL,
  `is_default_role` TINYINT(1) UNSIGNED NULL,
  PRIMARY KEY (`id`));

INSERT INTO `spring_bazaar`.`sb_roles` (`id`, `name`, `is_default_role`) VALUES ('1', 'ROLE_ADMIN', '0');
INSERT INTO `spring_bazaar`.`sb_roles` (`id`, `name`, `is_default_role`) VALUES ('2', 'ROLE_USER', '1');
INSERT INTO `spring_bazaar`.`sb_roles` (`id`, `name`, `is_default_role`) VALUES ('3', 'ROLE_SELLER', '0');
INSERT INTO `spring_bazaar`.`sb_roles` (`id`, `name`, `is_default_role`) VALUES ('4', 'ROLE_BUYER', '0');


  CREATE TABLE `spring_bazaar`.`sb_permissions` (
    `id` INT UNSIGNED NOT NULL AUTO_INCREMENT,
    `name` VARCHAR(45) NOT NULL,
    `description` VARCHAR(255) NULL,
    `value` TINYINT(1) NULL,
  PRIMARY KEY (`id`));

INSERT INTO `spring_bazaar`.`sb_permissions` (`id`, `name`, `description`) VALUES ('1', 'PERM_PRODUCT_CREATE', 'Create products');
INSERT INTO `spring_bazaar`.`sb_permissions` (`id`, `name`, `description`) VALUES ('2', 'PERM_PRODUCT_VIEW', 'View products');
INSERT INTO `spring_bazaar`.`sb_permissions` (`id`, `name`, `description`) VALUES ('3', 'PERM_PRODUCT_EDIT', 'Edit product');
INSERT INTO `spring_bazaar`.`sb_permissions` (`id`, `name`, `description`) VALUES ('4', 'PERM_PRODUCT_STATUS_CLOSE', 'Close products');
INSERT INTO `spring_bazaar`.`sb_permissions` (`id`, `name`, `description`) VALUES ('5', 'PERM_PRODUCT_DELETE', 'Delete products');


  CREATE TABLE `spring_bazaar`.`sb_roles_permissions` (
    `role_id` INT UNSIGNED NOT NULL,
    `permission_id` INT UNSIGNED NOT NULL,
  PRIMARY KEY (`role_id`, `permission_id`));

INSERT INTO `spring_bazaar`.`sb_roles_permissions` (`permission_id`, `role_id`) VALUES ('1', '3');
INSERT INTO `spring_bazaar`.`sb_roles_permissions` (`permission_id`, `role_id`) VALUES ('2', '3');
INSERT INTO `spring_bazaar`.`sb_roles_permissions` (`permission_id`, `role_id`) VALUES ('3', '3');
INSERT INTO `spring_bazaar`.`sb_roles_permissions` (`permission_id`, `role_id`) VALUES ('4', '3');
INSERT INTO `spring_bazaar`.`sb_roles_permissions` (`permission_id`, `role_id`) VALUES ('5', '3');


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

  create table spring_bazaar.persistent_logins (
    username varchar(64) not null,
    series varchar(64) not null,
    token varchar(64) not null,
    last_used timestamp not null,
    primary key(series));