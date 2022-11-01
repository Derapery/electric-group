CREATE DATABASE shuzu;
USE shuzu;
CREATE TABLE `t_category` (
                              `id` BIGINT,
                              `name` VARCHAR(300) NOT NULL,
                              PRIMARY KEY(`id`)
);
CREATE TABLE `t_customers` (
                               `id` BIGINT ,
                               `username` VARCHAR(20) NOT NULL,
                               `password` VARCHAR(40) NOT NULL,
                               `salt` VARCHAR(5) NOT NULL,
                               `reg_date` DATE,
                               `nickname` VARCHAR(100),
                               `introduce` VARCHAR(100),
                               `management` INT,
                               `gender` VARCHAR(20),
                               PRIMARY KEY(`id`),
                               UNIQUE(`username`)
);
CREATE TABLE `t_fans` (
                          `id` BIGINT,
                          `follwer_id` BIGINT NOT NULL,
                          `up_id` BIGINT NOT NULL,
                          PRIMARY KEY(`id`),
                          FOREIGN KEY(`follwer_id`) REFERENCES `t_customers` (`id`),
                          FOREIGN KEY (`up_id`) REFERENCES `t_customers` (`id`)
);
CREATE TABLE `t_topics` (
                            `id` BIGINT ,
                            `title` VARCHAR(200) NOT NULL,
                            `content` TEXT NOT NULL,
                            `customer_id` BIGINT,
                            `publish_time` TIMESTAMP ,
                            `publish_address` VARCHAR(40),
                            `priority` INT,
                            `category_id`  BIGINT,
                            PRIMARY KEY(`id`),
                            FOREIGN KEY(`category_id`) REFERENCES `t_category` (`id`),
                            FOREIGN KEY (`customer_id`) REFERENCES `t_customers` (`id`)
);

CREATE TABLE `t_explains` (
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
CREATE TABLE `t_file_infos` (
                                `id` BIGINT,
                                `path` VARCHAR(300) NOT NULL,
                                `name` VARCHAR(300) NOT NULL,
                                `checksum` VARCHAR(40) NOT NULL,
                                `file_size` BIGINT,
                                `upload_time` TIMESTAMP,
                                `customer_id` BIGINT,
                                PRIMARY KEY(`id`),
                                FOREIGN KEY(`customer_id`) REFERENCES `t_customers` (`id`)
);
CREATE TABLE `t_like` (
                                `id` BIGINT,
                                `customer_id` BIGINT,
                                `topic_id` BIGINT,
                                `praise` BIGINT,
                                PRIMARY KEY(`id`),
                                FOREIGN KEY(`customer_id`) REFERENCES `t_customers` (`id`),
                                FOREIGN KEY (`topic_id`) REFERENCES `t_topics` (`id`)

);
CREATE TABLE `t_like_explain` (
                          `id` BIGINT,
                          `customer_id` BIGINT,
                          `explain_id` BIGINT,
                          `praise` BIGINT,
                          PRIMARY KEY(`id`),
                          FOREIGN KEY(`customer_id`) REFERENCES `t_customers` (`id`),
                          FOREIGN KEY (`explain_id`) REFERENCES `t_explains` (`id`)

);
