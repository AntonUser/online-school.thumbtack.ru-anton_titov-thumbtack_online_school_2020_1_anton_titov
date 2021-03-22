DROP DATABASE IF EXISTS ttschool;
CREATE DATABASE `ttschool`;
USE `ttschool`;

CREATE TABLE `school`
(
    id   INT primary key auto_increment,
    name VARCHAR(50) not null,
    year INT         not null
) ENGINE = INNODB
  DEFAULT CHARSET = utf8;

CREATE TABLE `group`
(
    id       INT primary key auto_increment,
    name     varchar(50) not null,
    room     varchar(50) not null,
    idSchool int,
    FOREIGN KEY (idSchool) REFERENCES `school` (id)
) ENGINE = INNODB
  DEFAULT CHARSET = utf8;

ALTER TABLE `ttschool`.`group`
    DROP FOREIGN KEY `group_ibfk_1`;
ALTER TABLE `ttschool`.`group`
    ADD CONSTRAINT `group_ibfk_1`
        FOREIGN KEY (`idSchool`)
            REFERENCES `ttschool`.`school` (`id`)
            ON DELETE CASCADE;

CREATE TABLE trainee
(
    id        INT primary key auto_increment,
    firstname VARCHAR(50) not null,
    lastname  VARCHAR(50) not null,
    rating    INT,
    idGroup   INT,
    FOREIGN KEY (idGroup) REFERENCES `group` (id)
) ENGINE = INNODB
  DEFAULT CHARSET = utf8;

CREATE TABLE subject
(
    id      INT primary key auto_increment,
    name    varchar(50) not null,
    idgroup int,
    FOREIGN KEY (idGroup) REFERENCES `group` (id)
) ENGINE = INNODB
  DEFAULT CHARSET = utf8;

CREATE TABLE `ttschool`.`timetable`
(
    `idtimetable` INT NOT NULL AUTO_INCREMENT,
    `id_group`    INT NOT NULL,
    `id_subject`  INT NOT NULL,
    PRIMARY KEY (`idtimetable`)
);
