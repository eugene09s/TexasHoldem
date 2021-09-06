
CREATE SCHEMA IF NOT EXISTS `pokerschema` DEFAULT CHARACTER SET utf8 ;
USE `pokerschema` ;

CREATE TABLE IF NOT EXISTS `pokerschema`.`users` (
                                                     `user_id` BIGINT NOT NULL AUTO_INCREMENT,
                                                     `login` VARCHAR(32) NOT NULL,
                                                     `password` VARCHAR(32) NOT NULL,
                                                     `first_name` VARCHAR(32) NOT NULL,
                                                     `last_name` VARCHAR(32) NOT NULL,
                                                     `email` VARCHAR(64) NOT NULL,
                                                     `balance` DECIMAL(10,2) NOT NULL DEFAULT 100,
                                                     `role` ENUM('ADMIN', 'USER', 'MANAGER', 'GUEST') NOT NULL,
                                                     `status` ENUM('ACTIVE', 'BANNED', 'CONFIRMATION_AWAITING') NOT NULL,
                                                     `phone_number` BIGINT NULL DEFAULT 0,
                                                     `create_time` TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP,
                                                     PRIMARY KEY (`user_id`),
                                                     UNIQUE INDEX `login_UNIQUE` (`login` ASC) VISIBLE,
                                                     UNIQUE INDEX `email_UNIQUE` (`email` ASC) VISIBLE,
                                                     UNIQUE INDEX `user_id_UNIQUE` (`user_id` ASC) VISIBLE);

CREATE TABLE IF NOT EXISTS `pokerschema`.`games` (
                                                     `game_id` BIGINT NOT NULL AUTO_INCREMENT,
                                                     `title` VARCHAR(128) NOT NULL,
                                                     `date` TIMESTAMP NOT NULL,
                                                     `bank` DECIMAL(10,2) NOT NULL,
                                                     `five_cards` VARCHAR(128) NOT NULL,
                                                     PRIMARY KEY (`game_id`),
                                                     UNIQUE INDEX `game_id_UNIQUE` (`game_id` ASC) VISIBLE);

CREATE TABLE IF NOT EXISTS `pokerschema`.`game_winners` (
                                                            `game_winner_id` BIGINT NOT NULL AUTO_INCREMENT,
                                                            `game_id` BIGINT NOT NULL,
                                                            `game_winner_user_id` BIGINT NOT NULL,
                                                            INDEX `player_user_id_idx` (`game_winner_user_id` ASC) VISIBLE,
                                                            PRIMARY KEY (`game_winner_id`),
                                                            UNIQUE INDEX `game_winners_id_UNIQUE` (`game_winner_id` ASC) VISIBLE,
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

CREATE TABLE IF NOT EXISTS `pokerschema`.`profile_players` (
                                                               `player_id` BIGINT NOT NULL,
                                                               `best_prize` DECIMAL(10,2)  DEFAULT 0,
                                                               `award` VARCHAR(128)  DEFAULT NULL,
                                                               `photo` VARCHAR(32) DEFAULT NULL,
                                                               `about_yourself` VARCHAR(512) DEFAULT NULL,
                                                               `lost_money` DECIMAL(10,2),
                                                               `win_money` DECIMAL(10,2),
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
