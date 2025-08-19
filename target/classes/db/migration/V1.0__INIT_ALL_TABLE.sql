
-- shop_online_BK_180082025.`role` definition

CREATE TABLE `role` (
                        `id` int NOT NULL AUTO_INCREMENT,
                        `created_at` datetime(6) DEFAULT NULL,
                        `created_by` int DEFAULT NULL,
                        `updated_at` datetime(6) DEFAULT NULL,
                        `updated_by` int DEFAULT NULL,
                        `name` varchar(255) DEFAULT NULL,
                        PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


-- shop_online_BK_180082025.`user` definition

CREATE TABLE `user` (
                        `id` int NOT NULL AUTO_INCREMENT,
                        `created_at` datetime(6) DEFAULT NULL,
                        `created_by` int DEFAULT NULL,
                        `updated_at` datetime(6) DEFAULT NULL,
                        `updated_by` int DEFAULT NULL,
                        `address` varchar(255) DEFAULT NULL,
                        `avatar` varchar(1000) DEFAULT NULL,
                        `date_of_birth` varchar(255) DEFAULT NULL,
                        `email` varchar(255) DEFAULT NULL,
                        `expired_time` datetime(6) DEFAULT NULL,
                        `first_name` varchar(255) DEFAULT NULL,
                        `last_name` varchar(255) DEFAULT NULL,
                        `otp_email` varchar(255) DEFAULT NULL,
                        `otp_email_created_at` datetime(6) DEFAULT NULL,
                        `otp_phone` varchar(255) DEFAULT NULL,
                        `otp_phone_created_at` datetime(6) DEFAULT NULL,
                        `password` varchar(255) DEFAULT NULL,
                        `phone` varchar(255) DEFAULT NULL,
                        `reset_token` text,
                        `status` enum('ACTIVE','BLOCKED','DELETED','PENDING','WAIT_FOR_CONFIRMATION') DEFAULT NULL,
                        `type` int DEFAULT NULL,
                        `user_name` varchar(255) DEFAULT NULL,
                        `role_id` int DEFAULT NULL,
                        PRIMARY KEY (`id`),
                        KEY `FKn82ha3ccdebhokx3a8fgdqeyy` (`role_id`),
                        CONSTRAINT `FKn82ha3ccdebhokx3a8fgdqeyy` FOREIGN KEY (`role_id`) REFERENCES `role` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- shop_online_BK_180082025.`category` definition
CREATE TABLE `category` (
                            `id` int NOT NULL AUTO_INCREMENT,
                            `created_at` datetime(6) DEFAULT NULL,
                            `created_by` int DEFAULT NULL,
                            `updated_at` datetime(6) DEFAULT NULL,
                            `updated_by` int DEFAULT NULL,
                            `delete_flg` bit(1) DEFAULT NULL,
                            `description` text,
                            `header_url` varchar(255) DEFAULT NULL,
                            `name` varchar(255) DEFAULT NULL,
                            PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- shop_online_BK_180082025.`image_s3` definition
CREATE TABLE `image_s3` (
                            `id` int NOT NULL AUTO_INCREMENT,
                            `created_at` datetime(6) DEFAULT NULL,
                            `created_by` int DEFAULT NULL,
                            `updated_at` datetime(6) DEFAULT NULL,
                            `updated_by` int DEFAULT NULL,
                            `url` varchar(255) DEFAULT NULL,
                            PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- shop_online_BK_180082025.`product` definition

CREATE TABLE `product` (
                           `id` int NOT NULL AUTO_INCREMENT,
                           `created_at` datetime(6) DEFAULT NULL,
                           `created_by` int DEFAULT NULL,
                           `updated_at` datetime(6) DEFAULT NULL,
                           `updated_by` int DEFAULT NULL,
                           `description` text,
                           `name` varchar(255) DEFAULT NULL,
                           `price` float DEFAULT NULL,
                           `status` enum('ACTIVE','DELETED') DEFAULT NULL,
                           `category_id` int DEFAULT NULL,
                           PRIMARY KEY (`id`),
                           KEY `FK1mtsbur82frn64de7balymq9s` (`category_id`),
                           CONSTRAINT `FK1mtsbur82frn64de7balymq9s` FOREIGN KEY (`category_id`) REFERENCES `category` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- shop_online_BK_180082025.`product_size` definition

CREATE TABLE `product_size` (
                                `id` int NOT NULL AUTO_INCREMENT,
                                `created_at` datetime(6) DEFAULT NULL,
                                `created_by` int DEFAULT NULL,
                                `updated_at` datetime(6) DEFAULT NULL,
                                `updated_by` int DEFAULT NULL,
                                `inventory` bigint DEFAULT NULL,
                                `quantity` bigint DEFAULT NULL,
                                `size` varchar(255) DEFAULT NULL,
                                `shop_product_id` int DEFAULT NULL,
                                PRIMARY KEY (`id`),
                                KEY `FK1mnp7vwpkttsadtfg70f1wd7u` (`shop_product_id`),
                                CONSTRAINT `FK1mnp7vwpkttsadtfg70f1wd7u` FOREIGN KEY (`shop_product_id`) REFERENCES `product` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


-- shop_online_BK_180082025.`product_image` definition

CREATE TABLE `product_image` (
                                 `id` int NOT NULL AUTO_INCREMENT,
                                 `created_at` datetime(6) DEFAULT NULL,
                                 `created_by` int DEFAULT NULL,
                                 `updated_at` datetime(6) DEFAULT NULL,
                                 `updated_by` int DEFAULT NULL,
                                 `url` varchar(255) DEFAULT NULL,
                                 `shop_product_id` int DEFAULT NULL,
                                 PRIMARY KEY (`id`),
                                 KEY `FKafbk8q2b5sd39ovbv9bpkmogh` (`shop_product_id`),
                                 CONSTRAINT `FKafbk8q2b5sd39ovbv9bpkmogh` FOREIGN KEY (`shop_product_id`) REFERENCES `product` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=17 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


-- shop_online_BK_180082025.`shopping_order` definition

CREATE TABLE `shopping_order` (
                                  `id` int NOT NULL AUTO_INCREMENT,
                                  `created_at` datetime(6) DEFAULT NULL,
                                  `created_by` int DEFAULT NULL,
                                  `updated_at` datetime(6) DEFAULT NULL,
                                  `updated_by` int DEFAULT NULL,
                                  `delivery_address` text,
                                  `description` text,
                                  `email` varchar(255) DEFAULT NULL,
                                  `order_id` varchar(255) NOT NULL,
                                  `status` enum('CANCELLED_TRANSFERRED','CANCELLED_TRANSFERRING','DELIVERY','FAIL_PAYMENT','NEW','PROGRESS','SUCCESS') DEFAULT NULL,
                                  `total_amount` decimal(38,2) DEFAULT NULL,
                                  `user_id` int DEFAULT NULL,
                                  `user_name` varchar(255) DEFAULT NULL,
                                  PRIMARY KEY (`id`),
                                  UNIQUE KEY `UK_dka1j0u1uhp7q988xubne58jo` (`order_id`)
) ENGINE=InnoDB AUTO_INCREMENT=23686 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- shop_online_BK_180082025.`shopping_order_detail` definition

CREATE TABLE `shopping_order_detail` (
                                         `id` int NOT NULL AUTO_INCREMENT,
                                         `created_at` datetime(6) DEFAULT NULL,
                                         `created_by` int DEFAULT NULL,
                                         `updated_at` datetime(6) DEFAULT NULL,
                                         `updated_by` int DEFAULT NULL,
                                         `product_name_ship` varchar(255) DEFAULT NULL,
                                         `product_price` decimal(38,2) DEFAULT NULL,
                                         `product_size_ship` varchar(255) DEFAULT NULL,
                                         `quantity` int DEFAULT NULL,
                                         `shop_order_id` int DEFAULT NULL,
                                         `shop_product_id` int DEFAULT NULL,
                                         PRIMARY KEY (`id`),
                                         KEY `FKr79b7wmhwu3s97smlh544h03j` (`shop_order_id`),
                                         KEY `FKi4eursjqbnv5w69mrdmt1l1ck` (`shop_product_id`),
                                         CONSTRAINT `FKi4eursjqbnv5w69mrdmt1l1ck` FOREIGN KEY (`shop_product_id`) REFERENCES `product` (`id`),
                                         CONSTRAINT `FKr79b7wmhwu3s97smlh544h03j` FOREIGN KEY (`shop_order_id`) REFERENCES `shopping_order` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=15791 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- shop_online_BK_180082025.`shop_order_seq` definition

CREATE TABLE `shop_order_seq` (
                                  `id` int NOT NULL AUTO_INCREMENT,
                                  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7896 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;



