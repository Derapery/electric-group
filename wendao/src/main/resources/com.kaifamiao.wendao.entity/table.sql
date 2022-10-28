DROP DATABASE IF EXISTS `wendao`;
CREATE DATABASE `wendao`;
CREATE table t_customer(
    id BIGINT,
    username VARCHAR (20) NOT NULL ,
    password VARCHAR (40) NOT NULL ,
    salt VARCHAR (5) NOT NULL,
    reg_date DATE ,
    nickname VARCHAR (20) NOT NULL ,
    PRIMARY key(id),
    UNIQUE (username)
);
CREATE TABLE t_topics(
    id BIGINT,
    `title` VARCHAR(200) NOT NULL,
    `content` TEXT NOT NULL,
    `customer_id` BIGINT,
    `publish_time` TIMESTAMP ,
    `publish_address` VARCHAR(40),
    `priority` INT,
    PRIMARY KEY(`id`),
    FOREIGN KEY (`customer_id`) REFERENCES `t_customer` (`id`)

);
CREATE TABLE t_explains(
                           `id` BIGINT ,
                           `customer_id` BIGINT,
                           `publish_time` TIMESTAMP ,
                           `publish_address` VARCHAR(40),
                           `topic_id` BIGINT,
                           `content` TEXT NOT NULL,
                           `praise` INT,
                           `despise` INT,
                           PRIMARY KEY(`id`),
                           FOREIGN KEY (`customer_id`) REFERENCES `t_customers` (`id`),
                           FOREIGN KEY (`topic_id`) REFERENCES `t_topics` (`id`)
);