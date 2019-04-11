-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema social_network
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema social_network
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `social_network` DEFAULT CHARACTER SET utf8 ;
SHOW WARNINGS;
USE `social_network` ;

-- -----------------------------------------------------
-- Table `social_network`.`user`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `social_network`.`user` (
  `user_id` INT NOT NULL AUTO_INCREMENT,
  `username` VARCHAR(50) NOT NULL UNIQUE,
  `password` VARCHAR(50) NOT NULL,
  `name` VARCHAR(50) NULL,
  `birthday` DATE NULL,
  `gender` ENUM("MALE", "FEMALE") NULL,
  `address` TEXT(200) NULL,
  PRIMARY KEY (`user_id`))
ENGINE = InnoDB AUTO_INCREMENT = 1000;

SHOW WARNINGS;

-- -----------------------------------------------------
-- Table `social_network`.`education`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `social_network`.`education` (
  `experience_id` INT NOT NULL AUTO_INCREMENT,
  `user_id` INT NOT NULL,
  `level` VARCHAR(50) NOT NULL,
  `start` DATE NOT NULL,
  `end` DATE NOT NULL,
  `school` VARCHAR(50) NOT NULL,
  `degree` VARCHAR(30) NOT NULL,
  PRIMARY KEY (`experience_id`),
  INDEX `user_id_idx` (`user_id` ASC),
  CONSTRAINT `education_user_id`
    FOREIGN KEY (`user_id`)
    REFERENCES `social_network`.`user` (`user_id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB AUTO_INCREMENT = 1000;

SHOW WARNINGS;

-- -----------------------------------------------------
-- Table `social_network`.`work_experience`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `social_network`.`work_experience` (
  `experience_id` INT NOT NULL AUTO_INCREMENT,
  `user_id` INT NOT NULL,
  `employer` VARCHAR(50) NOT NULL,
  `start` DATE NOT NULL,
  `end` DATE NOT NULL,
  `position` VARCHAR(50) NOT NULL,
  PRIMARY KEY (`experience_id`),
  INDEX `user_id_idx` (`user_id` ASC),
  CONSTRAINT `work_experience_user_id`
    FOREIGN KEY (`user_id`)
    REFERENCES `social_network`.`user` (`user_id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB AUTO_INCREMENT = 1000;

SHOW WARNINGS;

-- -----------------------------------------------------
-- Table `social_network`.`email`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `social_network`.`email` (
  `user_id` INT NOT NULL,
  `address` VARCHAR(50) NOT NULL,
  PRIMARY KEY (`address`),
  INDEX `user_id_idx` (`user_id` ASC),
  CONSTRAINT `email_user_id`
    FOREIGN KEY (`user_id`)
    REFERENCES `social_network`.`user` (`user_id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB;

SHOW WARNINGS;

-- -----------------------------------------------------
-- Table `social_network`.`friend_group`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `social_network`.`friend_group` (
  `group_id` INT NOT NULL AUTO_INCREMENT,
  `user_id` INT NOT NULL,
  `group_name` VARCHAR(100) NOT NULL,
  PRIMARY KEY (`group_id`),
  INDEX `user_id_idx` (`user_id` ASC),
  UNIQUE INDEX `group_name_UNIQUE` (`group_name` ASC),
  UNIQUE INDEX `group_id_UNIQUE` (`group_id` ASC),
  CONSTRAINT `friend_group_user_id`
    FOREIGN KEY (`user_id`)
    REFERENCES `social_network`.`user` (`user_id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB AUTO_INCREMENT = 1000;

SHOW WARNINGS;

-- -----------------------------------------------------
-- Table `social_network`.`friendship`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `social_network`.`friendship` (
  `user_id` INT NOT NULL,
  `friend_id` INT NOT NULL,
  `group_id` INT NULL,
  PRIMARY KEY (`user_id`, `friend_id`),
  INDEX `group_id_idx` (`group_id` ASC),
  CONSTRAINT `friendship_user_id`
    FOREIGN KEY (`user_id`)
    REFERENCES `social_network`.`user` (`user_id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `friendship_friend_id`
    FOREIGN KEY (`friend_id`)
    REFERENCES `social_network`.`user` (`user_id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `friendship_group_id`
    FOREIGN KEY (`group_id`)
    REFERENCES `social_network`.`friend_group` (`group_id`)
    ON DELETE SET NULL
    ON UPDATE SET NULL)
ENGINE = InnoDB;

SHOW WARNINGS;

-- -----------------------------------------------------
-- Table `social_network`.`post`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `social_network`.`post` (
  `post_id` INT NOT NULL AUTO_INCREMENT,
  `user_id` INT NOT NULL,
  `private` BOOLEAN NOT NULL DEFAULT FALSE,
  `content` LONGTEXT NOT NULL,
  `update_time` DATETIME NOT NULL,
  PRIMARY KEY (`post_id`),
  INDEX `user_id_idx` (`user_id` ASC),
  CONSTRAINT `post_user_id`
    FOREIGN KEY (`user_id`)
    REFERENCES `social_network`.`user` (`user_id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB AUTO_INCREMENT = 1000;

SHOW WARNINGS;

-- -----------------------------------------------------
-- Table `social_network`.`reply`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `social_network`.`reply` (
  `reply_id` INT NOT NULL AUTO_INCREMENT,
  `user_id` INT NOT NULL,
  `post_id` INT NOT NULL,
  `replied_id` INT NULL,
  `content` MEDIUMTEXT NOT NULL,
  `reply_time` DATETIME NOT NULL,
  PRIMARY KEY (`reply_id`),
  INDEX `post_id_idx` (`post_id` ASC),
  INDEX `replied_id_idx` (`replied_id` ASC),
  CONSTRAINT `reply_user_id`
    FOREIGN KEY (`user_id`)
    REFERENCES `social_network`.`user` (`user_id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `reply_post_id`
    FOREIGN KEY (`post_id`)
    REFERENCES `social_network`.`post` (`post_id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `reply_replied_id`
    FOREIGN KEY (`replied_id`)
    REFERENCES `social_network`.`reply` (`reply_id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB AUTO_INCREMENT = 1000;

SHOW WARNINGS;

-- -----------------------------------------------------
-- Table `social_network`.`share`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `social_network`.`share` (
  `share_id` INT NOT NULL AUTO_INCREMENT,
  `user_id` INT NOT NULL,
  `post_id` INT NULL,
  `content` LONGTEXT NOT NULL,
  `share_time` DATETIME NOT NULL,
  PRIMARY KEY (`share_id`),
  INDEX `user_id_idx` (`user_id` ASC),
  INDEX `post_id_idx` (`post_id` ASC),
  CONSTRAINT `share_user_id`
    FOREIGN KEY (`user_id`)
    REFERENCES `social_network`.`user` (`user_id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `share_post_id`
    FOREIGN KEY (`post_id`)
    REFERENCES `social_network`.`post` (`post_id`)
    ON DELETE SET NULL
    ON UPDATE SET NULL)
ENGINE = InnoDB AUTO_INCREMENT = 1000;

SHOW WARNINGS;
USE `social_network`;

DELIMITER $$
SHOW WARNINGS$$
USE `social_network`$$
CREATE DEFINER = CURRENT_USER TRIGGER `social_network`.`post_BEFORE_INSERT` BEFORE INSERT ON `post` FOR EACH ROW
BEGIN
set new.update_time = now();
END$$

SHOW WARNINGS$$
SHOW WARNINGS$$
USE `social_network`$$
CREATE DEFINER = CURRENT_USER TRIGGER `social_network`.`post_BEFORE_UPDATE` BEFORE UPDATE ON `post` FOR EACH ROW
BEGIN
set new.update_time = now();
END$$

SHOW WARNINGS$$
SHOW WARNINGS$$
USE `social_network`$$
CREATE DEFINER = CURRENT_USER TRIGGER `social_network`.`reply_BEFORE_INSERT` BEFORE INSERT ON `reply` FOR EACH ROW
BEGIN
set new.reply_time = now();
END$$

SHOW WARNINGS$$
SHOW WARNINGS$$
USE `social_network`$$
CREATE DEFINER = CURRENT_USER TRIGGER `social_network`.`reply_BEFORE_UPDATE` BEFORE UPDATE ON `reply` FOR EACH ROW
BEGIN
set new.reply_time = now();
END$$

SHOW WARNINGS$$
SHOW WARNINGS$$
USE `social_network`$$
CREATE DEFINER = CURRENT_USER TRIGGER `social_network`.`share_BEFORE_INSERT` BEFORE INSERT ON `share` FOR EACH ROW
BEGIN
set new.share_time = now();
END$$

SHOW WARNINGS$$
SHOW WARNINGS$$
USE `social_network`$$
CREATE DEFINER = CURRENT_USER TRIGGER `social_network`.`share_BEFORE_UPDATE` BEFORE UPDATE ON `share` FOR EACH ROW
BEGIN
set new.share_time = now();
END$$

SHOW WARNINGS$$

DELIMITER ;

SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
