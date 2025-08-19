-- ROLE
INSERT INTO shop_online.`role`
(id, created_at, created_by, updated_at, updated_by, name)
VALUES(1, UTC_TIMESTAMP(), NULL, UTC_TIMESTAMP(), NULL, 'ADMIN');
INSERT INTO shop_online.`role`
(id, created_at, created_by, updated_at, updated_by, name)
VALUES(2, UTC_TIMESTAMP(), NULL, UTC_TIMESTAMP(), NULL, 'SUB_ADMIN');
INSERT INTO shop_online.`role`
(id, created_at, created_by, updated_at, updated_by, name)
VALUES(3, UTC_TIMESTAMP(), NULL, UTC_TIMESTAMP(), NULL, 'USER');
-- USER

INSERT INTO shop_online.`user`
(id, created_at, created_by, updated_at, updated_by, address, avatar, date_of_birth, email, expired_time, first_name, last_name, otp_email, otp_email_created_at, otp_phone, otp_phone_created_at, password, phone, reset_token, status, `type`, user_name, role_id)
VALUES(1, UTC_TIMESTAMP(), NULL, UTC_TIMESTAMP(), NULL, NULL, NULL, NULL, 'vuhoan485@gmail.com', '2025-08-04 14:49:21.705265', NULL, NULL, NULL, NULL, NULL, NULL, '$2a$12$BooOEcD09dtb.ozRohF9xubkgAELq5aJNQVTNSAigrKLofO0ZKDPK', NULL, NULL, 'ACTIVE', 0, NULL, 1);
INSERT INTO shop_online.`user`
(id, created_at, created_by, updated_at, updated_by, address, avatar, date_of_birth, email, expired_time, first_name, last_name, otp_email, otp_email_created_at, otp_phone, otp_phone_created_at, password, phone, reset_token, status, `type`, user_name, role_id)
VALUES(2, UTC_TIMESTAMP(), NULL, UTC_TIMESTAMP(), NULL, NULL, NULL, NULL, 'hoan123@gmail.com', '2025-08-14 15:21:06.831033', NULL, NULL, NULL, NULL, NULL, NULL, '$2a$12$b.SR.8cMTQEmH.CL7TqorOUwIYTl87ysLt49FzsdfVwI5fsdkUjPG', NULL, NULL, 'ACTIVE', 2, NULL, 3);


-- image_s3

INSERT INTO shop_online.image_s3
(id, created_at, created_by, updated_at, updated_by, url)
VALUES(1, UTC_TIMESTAMP(), 1, UTC_TIMESTAMP(), 1, 'image/product/cat.png');
INSERT INTO shop_online.image_s3
(id, created_at, created_by, updated_at, updated_by, url)
VALUES(2, UTC_TIMESTAMP(), 1, UTC_TIMESTAMP(), 1, 'image/product/Lion-dance.gif');
INSERT INTO shop_online.image_s3
(id, created_at, created_by, updated_at, updated_by, url)
VALUES(3, UTC_TIMESTAMP(), 1, UTC_TIMESTAMP(), 1, 'image/product/background1.jpg');
INSERT INTO shop_online.image_s3
(id, created_at, created_by, updated_at, updated_by, url)
VALUES(4, UTC_TIMESTAMP(), 1, UTC_TIMESTAMP(), 1, 'image/product/ran.jpg');
INSERT INTO shop_online.image_s3
(id, created_at, created_by, updated_at, updated_by, url)
VALUES(5, UTC_TIMESTAMP(), 1, UTC_TIMESTAMP(), 1, 'image/product/texture-clouds.png');
INSERT INTO shop_online.image_s3
(id, created_at, created_by, updated_at, updated_by, url)
VALUES(6, UTC_TIMESTAMP(), 1, UTC_TIMESTAMP(), 1, 'image/product/cloud-red2.png');
INSERT INTO shop_online.image_s3
(id, created_at, created_by, updated_at, updated_by, url)
VALUES(7, UTC_TIMESTAMP(), 1, UTC_TIMESTAMP(), 1, 'image/product/lanterns.png');
INSERT INTO shop_online.image_s3
(id, created_at, created_by, updated_at, updated_by, url)
VALUES(8, UTC_TIMESTAMP(), 1, UTC_TIMESTAMP(), 1, 'image/product/big-clouds3.png');
INSERT INTO shop_online.image_s3
(id, created_at, created_by, updated_at, updated_by, url)
VALUES(9, UTC_TIMESTAMP(), 1, UTC_TIMESTAMP(), 1, 'image/product/ran.webp');

-- category

INSERT INTO shop_online.category
(id, created_at, created_by, updated_at, updated_by, delete_flg, description, header_url, name)
VALUES(1, NULL, 1, NULL, 1, 0, 'none', '/image/product/background.jpg', 'quần áo');
INSERT INTO shop_online.category
(id, created_at, created_by, updated_at, updated_by, delete_flg, description, header_url, name)
VALUES(4, UTC_TIMESTAMP(), 1, UTC_TIMESTAMP(), 1, 0, 'thể thao là tận hưởng', 'image/product/background.jpg', 'quần áo thể thao');
INSERT INTO shop_online.category
(id, created_at, created_by, updated_at, updated_by, delete_flg, description, header_url, name)
VALUES(5, UTC_TIMESTAMP(), 1, UTC_TIMESTAMP(), 1, 0, 'thể thao là tận hưởng', 'image/product/background.jpg', 'mũ thể thao');
INSERT INTO shop_online.category
(id, created_at, created_by, updated_at, updated_by, delete_flg, description, header_url, name)
VALUES(6, UTC_TIMESTAMP(), 1, UTC_TIMESTAMP(), 1, 1, 'thể thao là không bao giờ bỏ cuộc', 'image/product/background.jpg', 'dụng cụ thể thao');