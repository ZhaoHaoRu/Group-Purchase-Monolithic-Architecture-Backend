/*
 Navicat Premium Data Transfer

 Source Server         : localhost_3306
 Source Server Type    : MySQL
 Source Server Version : 80028
 Source Host           : localhost:3306
 Source Schema         : groupbuy

 Target Server Type    : MySQL
 Target Server Version : 80028
 File Encoding         : 65001

 Date: 03/09/2022 09:26:26
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for address
-- ----------------------------
DROP TABLE IF EXISTS `address`;
CREATE TABLE `address`  (
  `address_id` int NOT NULL AUTO_INCREMENT,
  `location` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `phone` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `receiver` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `region` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `user_id` int NULL DEFAULT NULL,
  PRIMARY KEY (`address_id`) USING BTREE,
  INDEX `FKda8tuywtf0gb6sedwk7la1pgi`(`user_id`) USING BTREE,
  CONSTRAINT `FKda8tuywtf0gb6sedwk7la1pgi` FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of address
-- ----------------------------
INSERT INTO `address` VALUES (1, '东川路800号', '12329022211', 'Alice', 'shanghai', 1);
INSERT INTO `address` VALUES (2, '东川路800号', '12329022211', 'Alice', 'shanghai', 2);

-- ----------------------------
-- Table structure for goods
-- ----------------------------
DROP TABLE IF EXISTS `goods`;
CREATE TABLE `goods`  (
  `goods_id` int NOT NULL AUTO_INCREMENT,
  `goods_info` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `goods_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `inventory` int NULL DEFAULT NULL,
  `picture` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `price` decimal(19, 2) NULL DEFAULT NULL,
  `group_id` int NULL DEFAULT NULL,
  PRIMARY KEY (`goods_id`) USING BTREE,
  INDEX `FKe6mh7n6wde6pobge5dky5gi2b`(`group_id`) USING BTREE,
  CONSTRAINT `FKe6mh7n6wde6pobge5dky5gi2b` FOREIGN KEY (`group_id`) REFERENCES `groupbuying` (`group_id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 28 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of goods
-- ----------------------------
INSERT INTO `goods` VALUES (9, 'delicious', '甜甜圈', 1000, 'https://imgwork.tooopen.com/20210616/tooopen_v1718581858d94c1dc3-ae72-43d2-a61f-88d13f2db576.jpg', 12.00, 18);
INSERT INTO `goods` VALUES (10, 'delicious', '辣椒炒肉', 1000, 'https://img.zcool.cn/community/01272858d8b7c8a801219c77827c87.jpg@1280w_1l_2o_100sh.jpg', 20.00, 18);
INSERT INTO `goods` VALUES (11, 'delicious', '甜甜圈', 999, 'https://imgwork.tooopen.com/20210616/tooopen_v1718581858d94c1dc3-ae72-43d2-a61f-88d13f2db576.jpg', 12.00, 19);
INSERT INTO `goods` VALUES (12, 'delicious', '辣椒炒肉', 1000, 'https://img.zcool.cn/community/01272858d8b7c8a801219c77827c87.jpg@1280w_1l_2o_100sh.jpg', 20.00, 19);
INSERT INTO `goods` VALUES (13, 'delicious', '甜甜圈', 1000, 'https://imgwork.tooopen.com/20210616/tooopen_v1718581858d94c1dc3-ae72-43d2-a61f-88d13f2db576.jpg', 12.00, 20);
INSERT INTO `goods` VALUES (14, 'delicious', '辣椒炒肉', 1000, 'https://img.zcool.cn/community/01272858d8b7c8a801219c77827c87.jpg@1280w_1l_2o_100sh.jpg', 20.00, 20);
INSERT INTO `goods` VALUES (15, 'delicious', '甜甜圈', 992, 'https://imgwork.tooopen.com/20210616/tooopen_v1718581858d94c1dc3-ae72-43d2-a61f-88d13f2db576.jpg', 12.00, 21);
INSERT INTO `goods` VALUES (16, 'delicious', '辣椒炒肉', 992, 'https://img.zcool.cn/community/01272858d8b7c8a801219c77827c87.jpg@1280w_1l_2o_100sh.jpg', 20.00, 21);
INSERT INTO `goods` VALUES (17, 'delicious', '甜甜圈', 1000, 'https://imgwork.tooopen.com/20210616/tooopen_v1718581858d94c1dc3-ae72-43d2-a61f-88d13f2db576.jpg', 12.00, 22);
INSERT INTO `goods` VALUES (18, 'delicious', '辣椒炒肉', 1000, 'https://img.zcool.cn/community/01272858d8b7c8a801219c77827c87.jpg@1280w_1l_2o_100sh.jpg', 20.00, 22);
INSERT INTO `goods` VALUES (27, 'delicious', '碗蒸菜', 1000, NULL, 12.00, 29);
INSERT INTO `goods` VALUES (28, 'delicious', '冒血旺', 1000, NULL, 20.00, 29);

-- ----------------------------
-- Table structure for groupbuying
-- ----------------------------
DROP TABLE IF EXISTS `groupbuying`;
CREATE TABLE `groupbuying`  (
  `group_id` int NOT NULL AUTO_INCREMENT,
  `category` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `delivery` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `duration` int NULL DEFAULT NULL,
  `group_info` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `group_title` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `picture` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `start_time` datetime(6) NULL DEFAULT NULL,
  `state` int NULL DEFAULT NULL,
  `user_id` int NULL DEFAULT NULL,
  `popularity` int NULL DEFAULT NULL COMMENT '团购的热卖指数',
  PRIMARY KEY (`group_id`) USING BTREE,
  INDEX `FK3cxh8xkv07nf46hjckvl63srt`(`user_id`) USING BTREE,
  CONSTRAINT `FK3cxh8xkv07nf46hjckvl63srt` FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 29 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of groupbuying
-- ----------------------------
INSERT INTO `groupbuying` VALUES (17, '水果鲜花', NULL, 128, 'testGroupInfo', 'testGroup1', 'https://img.zcool.cn/community/013bbc598873f9a801215603034dd9.jpg@3000w_1l_0o_100sh.jpg', '2022-08-22 18:00:00.000000', 1, 1, 1);
INSERT INTO `groupbuying` VALUES (18, '水产海鲜', NULL, 128, 'testGroupInfo', 'testGroup2', 'https://img.zcool.cn/community/013bbc598873f9a801215603034dd9.jpg@3000w_1l_0o_100sh.jpg', '2022-08-22 18:00:00.000000', 1, 2, 2);
INSERT INTO `groupbuying` VALUES (19, '水果鲜花', '同城配送', 128, 'testGroupInfo', 'testGroup3', 'https://img.zcool.cn/community/013bbc598873f9a801215603034dd9.jpg@3000w_1l_0o_100sh.jpg', '2022-08-22 18:00:00.000000', 1, 3, 3);
INSERT INTO `groupbuying` VALUES (20, '乳品烘培', '同城配送', 128, 'testGroupInfo', 'testGroup4', 'https://img.zcool.cn/community/013bbc598873f9a801215603034dd9.jpg@3000w_1l_0o_100sh.jpg', '2022-08-22 18:00:00.000000', 1, 4, 45);
INSERT INTO `groupbuying` VALUES (21, '水产海鲜', '同城配送', 48, 'testGroupInfo', 'testGroup5', 'https://img.zcool.cn/community/013bbc598873f9a801215603034dd9.jpg@3000w_1l_0o_100sh.jpg', '2022-08-22 12:00:00.000000', 3, 1, 5);
INSERT INTO `groupbuying` VALUES (22, '乳品烘培', '同城配送', 48, 'testGroupInfo', 'testGroup6', 'https://img.zcool.cn/community/013bbc598873f9a801215603034dd9.jpg@3000w_1l_0o_100sh.jpg', '2022-08-22 13:00:00.000000', 1, 2, 3);
INSERT INTO `groupbuying` VALUES (29, '肉禽蛋', '同城配送', 128, 'testGroupInfo', 'testGroup7', NULL, '2022-08-22 02:00:00.000000', 1, 3, 2);

-- ----------------------------
-- Table structure for order_item
-- ----------------------------
DROP TABLE IF EXISTS `order_item`;
CREATE TABLE `order_item`  (
  `order_item_id` int NOT NULL AUTO_INCREMENT,
  `goods_number` int NULL DEFAULT NULL,
  `goods_id` int NULL DEFAULT NULL COMMENT '商品主键',
  `order_id` int NULL DEFAULT NULL COMMENT '订单主键',
  PRIMARY KEY (`order_item_id`) USING BTREE,
  INDEX `FKati1k6nilkh8m762nn3736kea`(`goods_id`) USING BTREE,
  INDEX `FKt4dc2r9nbvbujrljv3e23iibt`(`order_id`) USING BTREE,
  CONSTRAINT `FKati1k6nilkh8m762nn3736kea` FOREIGN KEY (`goods_id`) REFERENCES `goods` (`goods_id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `FKt4dc2r9nbvbujrljv3e23iibt` FOREIGN KEY (`order_id`) REFERENCES `orders` (`order_id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 10 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '订单详情信息表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of order_item
-- ----------------------------
INSERT INTO `order_item` VALUES (2, 4, 17, 2);
INSERT INTO `order_item` VALUES (3, 3, 17, 3);
INSERT INTO `order_item` VALUES (4, 2, 18, 3);
INSERT INTO `order_item` VALUES (5, 2, 15, 4);
INSERT INTO `order_item` VALUES (6, 2, 16, 4);
INSERT INTO `order_item` VALUES (7, 2, 15, 5);
INSERT INTO `order_item` VALUES (8, 2, 16, 5);
INSERT INTO `order_item` VALUES (9, 3, 28, 6);
INSERT INTO `order_item` VALUES (10, 1, 11, 7);

-- ----------------------------
-- Table structure for orders
-- ----------------------------
DROP TABLE IF EXISTS `orders`;
CREATE TABLE `orders`  (
  `order_id` int NOT NULL AUTO_INCREMENT,
  `state` bit(1) NULL DEFAULT NULL,
  `time` datetime(6) NULL DEFAULT NULL,
  `address_id` int NULL DEFAULT NULL,
  `group_id` int NULL DEFAULT NULL,
  `user_id` int NULL DEFAULT NULL,
  PRIMARY KEY (`order_id`) USING BTREE,
  INDEX `FKf5464gxwc32ongdvka2rtvw96`(`address_id`) USING BTREE,
  INDEX `FKl561toe3wp1q2h5rq5wnchel7`(`group_id`) USING BTREE,
  INDEX `FKel9kyl84ego2otj2accfd8mr7`(`user_id`) USING BTREE,
  CONSTRAINT `FKel9kyl84ego2otj2accfd8mr7` FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `FKf5464gxwc32ongdvka2rtvw96` FOREIGN KEY (`address_id`) REFERENCES `address` (`address_id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `FKl561toe3wp1q2h5rq5wnchel7` FOREIGN KEY (`group_id`) REFERENCES `groupbuying` (`group_id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 7 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of orders
-- ----------------------------
INSERT INTO `orders` VALUES (2, b'0', '2022-07-04 09:38:42.000000', 1, 22, 2);
INSERT INTO `orders` VALUES (3, b'1', '2022-07-04 09:38:42.000000', 1, 22, 3);
INSERT INTO `orders` VALUES (4, b'1', '2022-07-20 10:16:16.374000', 1, 21, 1);
INSERT INTO `orders` VALUES (5, b'1', '2022-07-20 10:37:18.236000', 2, 21, 2);
INSERT INTO `orders` VALUES (6, b'0', NULL, NULL, 29, 2);
INSERT INTO `orders` VALUES (7, b'1', '2022-08-21 10:37:18.236000', 2, 19, 4);

-- ----------------------------
-- Table structure for subscriptions
-- ----------------------------
DROP TABLE IF EXISTS `subscriptions`;
CREATE TABLE `subscriptions`  (
  `user_id` int NOT NULL,
  `group_id` int NOT NULL,
  PRIMARY KEY (`user_id`, `group_id`) USING BTREE,
  INDEX `FKg1tx5623817rfod3on1rw56oe`(`group_id`) USING BTREE,
  CONSTRAINT `FKg1tx5623817rfod3on1rw56oe` FOREIGN KEY (`group_id`) REFERENCES `groupbuying` (`group_id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `FKjv5hoiko2qkwlbqccoyktauiv` FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of subscriptions
-- ----------------------------
INSERT INTO `subscriptions` VALUES (2, 19);
INSERT INTO `subscriptions` VALUES (2, 20);
INSERT INTO `subscriptions` VALUES (2, 29);
INSERT INTO `subscriptions` VALUES (4, 29);

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user`  (
  `user_id` int NOT NULL AUTO_INCREMENT COMMENT '用户主键',
  `email` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '用户邮箱',
  `password` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '用户密码',
  `user_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '用户姓名',
  `wallet` decimal(19, 2) NULL DEFAULT NULL COMMENT '钱包余额',
  PRIMARY KEY (`user_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 4 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '用户表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES (1, 'test@sjtu.edu.cn', '1234', 'test2', 9472.00);
INSERT INTO `user` VALUES (2, 'test@sjtu.edu.cn', '1234', 'test3', 1008.00);
INSERT INTO `user` VALUES (3, 'test@sjtu.edu.cn', '1234', 'test4', 1164.00);
INSERT INTO `user` VALUES (4, 'register@email.com', '1234', 'test5', 988.00);

-- ----------------------------
-- Table structure for user_history
-- ----------------------------
DROP TABLE IF EXISTS `user_history`;
CREATE TABLE `user_history`  (
  `history_id` int NOT NULL AUTO_INCREMENT,
  `category` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `liking` int NULL DEFAULT NULL,
  `user_id` int NULL DEFAULT NULL,
  PRIMARY KEY (`history_id`) USING BTREE,
  INDEX `FKaa6ilb6iqih95bntoeyysb2pc`(`user_id`) USING BTREE,
  CONSTRAINT `FKaa6ilb6iqih95bntoeyysb2pc` FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 20 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of user_history
-- ----------------------------
INSERT INTO `user_history` VALUES (1, '水果鲜花', 5, 4);
INSERT INTO `user_history` VALUES (2, '肉禽蛋', 1, 4);
INSERT INTO `user_history` VALUES (3, '水产海鲜', 1, 4);
INSERT INTO `user_history` VALUES (4, '乳品烘培', 2, 4);
INSERT INTO `user_history` VALUES (5, '酒水饮料', 3, 4);
INSERT INTO `user_history` VALUES (6, '水果鲜花', 4, 1);
INSERT INTO `user_history` VALUES (7, '肉禽蛋', 5, 1);
INSERT INTO `user_history` VALUES (8, '水产海鲜', 6, 1);
INSERT INTO `user_history` VALUES (9, '乳品烘培', 7, 1);
INSERT INTO `user_history` VALUES (10, '酒水饮料', 0, 1);
INSERT INTO `user_history` VALUES (11, '水果鲜花', 8, 2);
INSERT INTO `user_history` VALUES (12, '肉禽蛋', 1, 2);
INSERT INTO `user_history` VALUES (13, '水产海鲜', 2, 2);
INSERT INTO `user_history` VALUES (14, '乳品烘培', 3, 2);
INSERT INTO `user_history` VALUES (15, '酒水饮料', 5, 2);
INSERT INTO `user_history` VALUES (16, '水果鲜花', 2, 3);
INSERT INTO `user_history` VALUES (17, '肉禽蛋', 3, 3);
INSERT INTO `user_history` VALUES (18, '水产海鲜', 1, 3);
INSERT INTO `user_history` VALUES (19, '乳品烘培', 8, 3);
INSERT INTO `user_history` VALUES (20, '酒水饮料', 0, 3);

SET FOREIGN_KEY_CHECKS = 1;
