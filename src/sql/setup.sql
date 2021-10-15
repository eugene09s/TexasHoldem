USE `p0exjzr6gemg7f9r`;

CREATE TABLE IF NOT EXISTS `p0exjzr6gemg7f9r`.`users` (
    `user_id` BIGINT NOT NULL AUTO_INCREMENT,
    `login` VARCHAR(32) NOT NULL,
    `password` VARCHAR(64) NOT NULL,
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

CREATE TABLE IF NOT EXISTS `p0exjzr6gemg7f9r`.`games` (
    `game_id` BIGINT NOT NULL AUTO_INCREMENT,
    `title` VARCHAR(128) NOT NULL,
    `date` TIMESTAMP NOT NULL,
    `bank` DECIMAL(10,2) NOT NULL,
    `five_cards` VARCHAR(128),
    PRIMARY KEY (`game_id`),
    UNIQUE INDEX `game_id_UNIQUE` (`game_id` ASC) VISIBLE);

CREATE TABLE IF NOT EXISTS `p0exjzr6gemg7f9r`.`game_winners` (
    `game_winner_id` BIGINT NOT NULL AUTO_INCREMENT,
    `game_id` BIGINT NOT NULL,
    `game_winner_user_id` BIGINT NOT NULL,
    INDEX `player_user_id_idx` (`game_winner_user_id` ASC) VISIBLE,
    PRIMARY KEY (`game_winner_id`),
    UNIQUE INDEX `game_winners_id_UNIQUE` (`game_winner_id` ASC) VISIBLE,
    CONSTRAINT `game_user_id`
    FOREIGN KEY (`game_id`)
    REFERENCES `p0exjzr6gemg7f9r`.`games` (`game_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
    CONSTRAINT `player_user_id`
    FOREIGN KEY (`game_winner_user_id`)
    REFERENCES `p0exjzr6gemg7f9r`.`users` (`user_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION);

CREATE TABLE IF NOT EXISTS `p0exjzr6gemg7f9r`.`profile_players` (
    `player_id` BIGINT NOT NULL,
    `best_prize` DECIMAL(10,2) NULL DEFAULT 0,
    `award` VARCHAR(128) NULL,
    `photo` VARCHAR(64) NULL,
    `about_yourself` VARCHAR(512) NULL,
    `lost_money` DECIMAL(10,2) NULL,
    `win_money` DECIMAL(10,2) NULL,
    UNIQUE INDEX `player_id_UNIQUE` (`player_id` ASC) VISIBLE,
    PRIMARY KEY (`player_id`),
    CONSTRAINT `player_id`
    FOREIGN KEY (`player_id`)
    REFERENCES `p0exjzr6gemg7f9r`.`users` (`user_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION);

CREATE TABLE IF NOT EXISTS `p0exjzr6gemg7f9r`.`game_players` (
    `game_player_id` BIGINT NOT NULL AUTO_INCREMENT,
    `last_action` VARCHAR(64) NOT NULL,
    `two_cards` VARCHAR(64),
    `combinations_cards` VARCHAR(128),
    `game_player_user_id` BIGINT NOT NULL,
    `game_id` BIGINT NOT NULL,
    PRIMARY KEY (`game_player_id`),
    INDEX `user_id_idx` (`game_player_user_id` ASC) VISIBLE,
    UNIQUE INDEX `game_player_id_UNIQUE` (`game_player_id` ASC) VISIBLE,
    CONSTRAINT `user_id`
    FOREIGN KEY (`game_player_user_id`)
    REFERENCES `p0exjzr6gemg7f9r`.`users` (`user_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
    CONSTRAINT `game_player_id`
    FOREIGN KEY (`game_id`)
    REFERENCES `p0exjzr6gemg7f9r`.`games` (`game_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION);
