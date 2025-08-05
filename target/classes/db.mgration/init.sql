
-- NSB_BK_14012024.`role` definition

CREATE TABLE `role` (
                        `id` int NOT NULL AUTO_INCREMENT ,
                        `created_at` timestamp(6) DEFAULT NULL,
                        `created_by` int DEFAULT NULL,
                        `modified_at` timestamp(6) DEFAULT NULL,
                        `updated_by` int DEFAULT NULL,
                        `name` varchar(255) NOT NULL,
                        PRIMARY KEY (`id`),
                        UNIQUE KEY `UK_8sewwnpamngi6b1dwaa88askk` (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


-- NSB_BK_14012024.`user` definition

CREATE TABLE `user` (
                        `id` int NOT NULL AUTO_INCREMENT,
                        `created_at` timestamp(6) DEFAULT NULL,
                        `created_by` int DEFAULT NULL,
                        `modified_at` timestamp(6) DEFAULT NULL,
                        `updated_by` int DEFAULT NULL,
                        `address` varchar(255) DEFAULT NULL,
                        `avatar` varchar(1000) DEFAULT NULL,
                        `date_of_birth` date DEFAULT NULL,
                        `email` text,
                        `role_id` int DEFAULT NULL,
                        `expired_time` timestamp(6) DEFAULT NULL,
                        `first_name` varchar(255) DEFAULT NULL,
                        `last_name` varchar(255) DEFAULT NULL,
                        `otp_email` varchar(255) DEFAULT NULL,
                        `otp_email_created_at` timestamp(6) DEFAULT NULL,
                        `otp_phone` varchar(255) DEFAULT NULL,
                        `otp_phone_created_at` timestamp(6) DEFAULT NULL,
                        `password` text,
                        `phone` varchar(255) DEFAULT NULL,
                        `reset_token` text,
                        `status` varchar(255) DEFAULT NULL,
                        `type` int DEFAULT NULL,
                        `user_name` varchar(255) DEFAULT NULL,
                        `reset_token` text,
                        PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;





