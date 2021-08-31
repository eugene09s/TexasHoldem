
CREATE SCHEMA IF NOT EXISTS `pokerschema` DEFAULT CHARACTER SET utf8 ;
USE `pokerschema` ;


CREATE TABLE IF NOT EXISTS `pokerschema`.`roles` (
                                                     `role_id` BIGINT NOT NULL AUTO_INCREMENT,
                                                     `role` ENUM('ADMIN', 'USER', 'MANAGER', 'GUEST') NOT NULL,
                                                     PRIMARY KEY (`role_id`),
                                                     UNIQUE INDEX `role_UNIQUE` (`role` ASC) VISIBLE);


CREATE TABLE IF NOT EXISTS `pokerschema`.`statuses` (
                                                        `status_id` BIGINT NOT NULL AUTO_INCREMENT,
                                                        `status` ENUM('ACTIVE', 'BANNED', 'CONFIRMATION_AWAITING') NOT NULL,
                                                        PRIMARY KEY (`status_id`));


CREATE TABLE IF NOT EXISTS `pokerschema`.`users` (
                                                     `user_id` BIGINT NOT NULL AUTO_INCREMENT,
                                                     `login` VARCHAR(32) NOT NULL,
                                                     `password` VARCHAR(32) NOT NULL,
                                                     `first_name` VARCHAR(32) NOT NULL,
                                                     `last_name` VARCHAR(32) NOT NULL,
                                                     `email` VARCHAR(64) NOT NULL,
                                                     `balance` DECIMAL(10,2) NOT NULL DEFAULT 100,
                                                     `photo` BLOB NULL,
                                                     `create_time` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
                                                     `phone_number` VARCHAR(16) NULL DEFAULT '0000000000000000',
                                                     `about_yourself` VARCHAR(512) NULL DEFAULT 'write about yourself',
                                                     `user_role_id` BIGINT NOT NULL,
                                                     `user_status_id` BIGINT NOT NULL,
                                                     PRIMARY KEY (`user_id`),
                                                     UNIQUE INDEX `login_UNIQUE` (`login` ASC) VISIBLE,
                                                     INDEX `status_id_idx` (`user_status_id` ASC) VISIBLE,
                                                     INDEX `role_id_idx` (`user_role_id` ASC) VISIBLE,
                                                     UNIQUE INDEX `email_UNIQUE` (`email` ASC) VISIBLE,
                                                     UNIQUE INDEX `user_id_UNIQUE` (`user_id` ASC) VISIBLE,
                                                     CONSTRAINT `role_id`
                                                         FOREIGN KEY (`user_role_id`)
                                                             REFERENCES `pokerschema`.`roles` (`role_id`)
                                                             ON DELETE CASCADE
                                                             ON UPDATE CASCADE,
                                                     CONSTRAINT `status_id`
                                                         FOREIGN KEY (`user_status_id`)
                                                             REFERENCES `pokerschema`.`statuses` (`status_id`)
                                                             ON DELETE CASCADE
                                                             ON UPDATE CASCADE);


CREATE TABLE IF NOT EXISTS `pokerschema`.`games` (
                                                     `game_id` BIGINT NOT NULL AUTO_INCREMENT,
                                                     `title` VARCHAR(128) NOT NULL,
                                                     `date` TIMESTAMP NOT NULL,
                                                     `bank` DECIMAL(10,2) NOT NULL,
                                                     `five_cards` VARCHAR(128) NOT NULL,
                                                     PRIMARY KEY (`game_id`),
                                                     UNIQUE INDEX `game_id_UNIQUE` (`game_id` ASC) VISIBLE);


CREATE TABLE IF NOT EXISTS `pokerschema`.`game_winners` (
                                                            game_winner_id BIGINT NOT NULL AUTO_INCREMENT,
                                                            `game_id` BIGINT NOT NULL,
                                                            `game_winner_user_id` BIGINT NOT NULL,
                                                            INDEX `player_user_id_idx` (`game_winner_user_id` ASC) VISIBLE,
                                                            PRIMARY KEY (game_winner_id),
                                                            UNIQUE INDEX `game_winners_id_UNIQUE` (game_winner_id ASC) VISIBLE,
                                                            CONSTRAINT `game_user_id`
                                                                FOREIGN KEY (`game_id`)
                                                                    REFERENCES `pokerschema`.`games` (`game_id`)
                                                                    ON DELETE CASCADE
                                                                    ON UPDATE CASCADE,
                                                            CONSTRAINT `player_user_id`
                                                                FOREIGN KEY (`game_winner_user_id`)
                                                                    REFERENCES `pokerschema`.`users` (`user_id`)
                                                                    ON DELETE CASCADE
                                                                    ON UPDATE CASCADE);


CREATE TABLE IF NOT EXISTS `pokerschema`.`rating_players` (
                                                              `player_id` BIGINT NOT NULL AUTO_INCREMENT,
                                                              `ranking` DECIMAL(4,2) NOT NULL DEFAULT 0,
                                                              `best_prize` DECIMAL(10,2) NOT NULL DEFAULT 0,
                                                              `award` VARCHAR(128) NULL DEFAULT NULL,
                                                              UNIQUE INDEX `player_id_UNIQUE` (`player_id` ASC) VISIBLE,
                                                              PRIMARY KEY (`player_id`),
                                                              CONSTRAINT `player_id`
                                                                  FOREIGN KEY (`player_id`)
                                                                      REFERENCES `pokerschema`.`users` (`user_id`)
                                                                      ON DELETE CASCADE
                                                                      ON UPDATE CASCADE);


CREATE TABLE IF NOT EXISTS `pokerschema`.`game_players` (
                                                            `game_player_id` BIGINT NOT NULL AUTO_INCREMENT,
                                                            `last_action` VARCHAR(64) NOT NULL,
                                                            `two_cards` VARCHAR(64) NOT NULL,
                                                            `combinations_cards` VARCHAR(128) NULL DEFAULT NULL,
                                                            `game_player_user_id` BIGINT NOT NULL,
                                                            `game_id` BIGINT NOT NULL,
                                                            PRIMARY KEY (`game_player_id`),
                                                            INDEX `user_id_idx` (`game_player_user_id` ASC) VISIBLE,
                                                            CONSTRAINT `user_id`
                                                                FOREIGN KEY (`game_player_user_id`)
                                                                    REFERENCES `pokerschema`.`users` (`user_id`)
                                                                    ON DELETE CASCADE
                                                                    ON UPDATE CASCADE,
                                                            CONSTRAINT `game_player_id`
                                                                FOREIGN KEY (`game_id`)
                                                                    REFERENCES `pokerschema`.`games` (`game_id`)
                                                                    ON DELETE CASCADE
                                                                    ON UPDATE CASCADE);
