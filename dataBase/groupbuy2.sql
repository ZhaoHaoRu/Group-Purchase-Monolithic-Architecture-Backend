/*
 Navicat Premium Data Transfer

 Source Server         : localhost_3306
 Source Server Type    : MySQL
 Source Server Version : 80028
 Source Host           : localhost:3306
 Source Schema         : groupbuy2

 Target Server Type    : MySQL
 Target Server Version : 80028
 File Encoding         : 65001

 Date: 09/09/2022 15:00:40
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
) ENGINE = InnoDB AUTO_INCREMENT = 34 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of address
-- ----------------------------
INSERT INTO `address` VALUES (1, '东川路800号', '13886768222', '罗世才', '上海闵行', 1);
INSERT INTO `address` VALUES (2, '东川路800号', '13886768222', '小明', '上海闵行', 1);
INSERT INTO `address` VALUES (3, '东川路800号', '12334455677', 'Jack', '上海闵行', 2);
INSERT INTO `address` VALUES (4, '东川路600号', '1791882442', 'Alice', '上海闵行', 2);
INSERT INTO `address` VALUES (5, 'dongchuan890', '123567', 'Alice', 'shanghai', 2);
INSERT INTO `address` VALUES (6, '东川路899号', '132902', 'alice', 'shanghai', 2);
INSERT INTO `address` VALUES (7, '东城区', '13333474', 'Jack', '中国北京', 2);
INSERT INTO `address` VALUES (8, '东川路800号', '123456543', 'Judy', '上海闵行区', 2);
INSERT INTO `address` VALUES (9, '东川路800号', '1234567', 'ALICE', '上海市闵行区', 2);
INSERT INTO `address` VALUES (10, '东川路800号', '1234567', 'ALICE', '上海市闵行区', 2);
INSERT INTO `address` VALUES (11, '东川路800号', '1234567', 'ALICE', '上海市闵行区', 2);
INSERT INTO `address` VALUES (12, '东川路800号', '123445', 'judy', '上海市闵行区', 2);
INSERT INTO `address` VALUES (13, '东川路800号', '12345', 'alice', '上海市闵行区', 2);
INSERT INTO `address` VALUES (14, '东川路800号', '12345', 'alice', '上海市闵行区', 2);
INSERT INTO `address` VALUES (15, '东川路800号', '123456', 'Jack', '上海市闵行区', 2);
INSERT INTO `address` VALUES (16, '东川路800号', '123556', 'Jack', '上海市闵行区', 2);
INSERT INTO `address` VALUES (21, '东川路800号', '1234567', 'ALICE', '上海市闵行区', 2);
INSERT INTO `address` VALUES (22, 'dongchuan890', '123567', 'Alice', 'shanghai', 2);
INSERT INTO `address` VALUES (23, 'Dd', 'Bb', 'Aa', 'Cc', 2);
INSERT INTO `address` VALUES (24, '东川路800号', '123556', 'Jack', '上海市闵行区', 2);
INSERT INTO `address` VALUES (25, '东川路800号', '123556', 'Jack', '上海市闵行区', 2);
INSERT INTO `address` VALUES (28, '东川路800号', '1322288955', 'Judy', '上海市闵行区', 15);
INSERT INTO `address` VALUES (29, 'dongchuan', '112233', 'test1', '上海', 2);
INSERT INTO `address` VALUES (30, '东川路800号', '13290211396', 'test2', 'shanghai ', 2);
INSERT INTO `address` VALUES (31, '东川路800号', '132456735', 'Alice', '上海市', 16);
INSERT INTO `address` VALUES (32, '东川路800号', '132456735', 'Alicica', '上海市', 17);
INSERT INTO `address` VALUES (33, '东川路800号', '13290211395', 'zhr', '上海市闵行区', 2);

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
) ENGINE = InnoDB AUTO_INCREMENT = 73 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of goods
-- ----------------------------
INSERT INTO `goods` VALUES (1, '新鲜的油条', '油条', -1, 'https://st-cn.meishij.net/r/181/98/13899681/s13899681_159730313168615.jpg', 2.00, 1);
INSERT INTO `goods` VALUES (2, '新鲜的白粥', '粥', 94, 'https://img.zcool.cn/community/01b1525abb0aa1a801218207c80341.jpg@1280w_1l_2o_100sh.jpg', 2.00, 1);
INSERT INTO `goods` VALUES (3, 'not so fresh bread', 'bread', -1, 'https://img.zcool.cn/community/01cf6b5b896eaca80120245c3d1203.jpg@1280w_1l_2o_100sh.jpg', 4.00, 3);
INSERT INTO `goods` VALUES (4, 'fresh milk', 'milk', -1, 'https://img.zcool.cn/community/01484f5dee2027a801213853b632b2.jpg@1280w_1l_2o_100sh.jpg', 4.00, 3);
INSERT INTO `goods` VALUES (5, '新鲜水蜜桃', '水蜜桃', 106, 'https://img.zcool.cn/community/0150d65d22cb7ba801207640d8782f.jpg@2o.jpg', 3.00, 4);
INSERT INTO `goods` VALUES (6, '绿色的农家土鸡蛋', '土鸡蛋', 96, 'https://img.zcool.cn/community/01f4e75c08b576a80120925200ca38.jpg@1280w_1l_2o_100sh.jpg', 1.00, 5);
INSERT INTO `goods` VALUES (7, '甜甜的巧乐兹甜筒', '巧乐兹甜筒', -1, 'https://img.zcool.cn/community/01fafc5ccba072a8012141685ee396.jpg@1280w_1l_2o_100sh.jpg', 4.00, 6);
INSERT INTO `goods` VALUES (11, '新鲜牛奶', 'milk', -1, 'https://img.zcool.cn/community/01484f5dee2027a801213853b632b2.jpg@1280w_1l_2o_100sh.jpg', 8.00, 3);
INSERT INTO `goods` VALUES (12, 'not so fresh bread', 'bread', -1, 'https://img.zcool.cn/community/01cf6b5b896eaca80120245c3d1203.jpg@1280w_1l_2o_100sh.jpg', 8.00, 3);
INSERT INTO `goods` VALUES (16, '甜甜的巧乐兹甜筒', '巧乐兹甜筒', 111, 'https://img.zcool.cn/community/01fafc5ccba072a8012141685ee396.jpg@1280w_1l_2o_100sh.jpg', 5.00, 6);
INSERT INTO `goods` VALUES (21, 'not so fresh bread', 'bread', -1, 'https://img.zcool.cn/community/01cf6b5b896eaca80120245c3d1203.jpg@1280w_1l_2o_100sh.jpg', 4.00, 3);
INSERT INTO `goods` VALUES (22, '新鲜面包', 'bread', 118, 'https://img.zcool.cn/community/01cf6b5b896eaca80120245c3d1203.jpg@1280w_1l_2o_100sh.jpg', 8.00, 3);
INSERT INTO `goods` VALUES (23, 'fresh milk', 'milk', -1, 'https://img.zcool.cn/community/01484f5dee2027a801213853b632b2.jpg@1280w_1l_2o_100sh.jpg', 4.00, 3);
INSERT INTO `goods` VALUES (24, '新鲜牛奶', 'milk', 118, 'https://img.zcool.cn/community/01484f5dee2027a801213853b632b2.jpg@1280w_1l_2o_100sh.jpg', 8.00, 3);
INSERT INTO `goods` VALUES (26, '好喝的奶茶', '布丁奶茶', 2000, 'https://img.zcool.cn/community/0141c65dcd6250a8012129e2db4fe9.jpg@1280w_1l_2o_100sh.jpghttps://img.zcool.cn/community', 7.00, 19);
INSERT INTO `goods` VALUES (28, '新鲜的包子', '包子', 195, 'https://st-cn.meishij.net/r/181/98/13899681/s13899681_159730313168615.jpg', 2.20, 1);
INSERT INTO `goods` VALUES (29, '新鲜的油条', '油条', 197, 'https://st-cn.meishij.net/r/181/98/13899681/s13899681_159730313168615.jpg', 2.20, 1);
INSERT INTO `goods` VALUES (60, '新出炉的面包', '面包', 3998, NULL, 4.00, 36);
INSERT INTO `goods` VALUES (61, '丝滑慕斯', '慕斯蛋糕', 999, NULL, 12.00, 37);
INSERT INTO `goods` VALUES (62, '新鲜生虾', '虾', 398, NULL, 12.00, 38);
INSERT INTO `goods` VALUES (63, '柠檬水加冰', '鲜榨柠檬水', -1, NULL, 6.00, 39);
INSERT INTO `goods` VALUES (64, '冰鲜柠檬水', '鲜榨柠檬水', 1200, NULL, 12.00, 39);
INSERT INTO `goods` VALUES (66, '刚打捞上来的小银鱼', '新鲜小银鱼', 1322, NULL, 12.00, 41);
INSERT INTO `goods` VALUES (67, 'delicious', '辣椒炒肉', 1000, NULL, 20.00, 42);
INSERT INTO `goods` VALUES (68, 'delicious', '辣椒炒肉', 1000, NULL, 20.00, 43);
INSERT INTO `goods` VALUES (69, 'delicious', '辣椒炒肉', 1000, NULL, 20.00, 44);
INSERT INTO `goods` VALUES (70, 'delicious', '辣椒炒肉', 1000, NULL, 20.00, 45);
INSERT INTO `goods` VALUES (71, 'delicious', '辣椒炒肉', 1000, NULL, 20.00, 46);
INSERT INTO `goods` VALUES (72, '新鲜菠萝包', '菠萝包', 3000, NULL, 5.00, 47);

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
) ENGINE = InnoDB AUTO_INCREMENT = 48 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of groupbuying
-- ----------------------------
INSERT INTO `groupbuying` VALUES (1, '肉禽蛋', '同城配送', 72, '新鲜的美食', '早餐特供', 'https://img.zcool.cn/community/0143da5c0a399fa8012092522e5328.jpg@3000w_1l_2o_100sh.jpg', '2022-09-01 11:06:21.000000', 1, 2, 1);
INSERT INTO `groupbuying` VALUES (3, '肉禽蛋', '同城配送', 48, '新鲜的美食', 'breakfast', 'https://img.zcool.cn/community/016eb45c0a3985a801209252f3d239.jpg@1280w_1l_2o_100sh.jpg', '2022-09-01 17:06:21.000000', 0, 2, 3);
INSERT INTO `groupbuying` VALUES (4, '水果鲜花', '同城配送', 72, '新鲜水果一小时达', '时鲜水果', 'https://img.zcool.cn/community/01a83f5c31a389a8012029acb9541e.jpg@1280w_1l_2o_100sh.jpg', '2022-09-01 11:06:21.000000', 1, 3, 2);
INSERT INTO `groupbuying` VALUES (5, '肉禽蛋', '快递', 30, '新鲜绿色农家蛋', '农家鸡蛋', 'https://img.zcool.cn/community/01076c5c08b579a801209252d89133.jpg@1280w_1l_2o_100sh.jpg', '2022-09-01 20:00:00.000000', 1, 1, 4);
INSERT INTO `groupbuying` VALUES (6, '乳品烘培', '快递', 48, '各品牌雪糕', '雪糕批发', 'https://img.zcool.cn/community/01fafc5ccba072a8012141685ee396.jpg@1280w_1l_2o_100sh.jpg', '2022-09-01 12:00:00.000000', 0, 1, 1);
INSERT INTO `groupbuying` VALUES (19, '酒水饮料', '快递', 48, '好喝的奶茶', '奶茶团购', 'https://img.zcool.cn/community/0141c65dcd6250a8012129e2db4fe9.jpg@1280w_1l_2o_100sh.jpghttps://img.zcool.cn/community', '2022-09-01 20:00:00.000000', 1, 2, 5);
INSERT INTO `groupbuying` VALUES (36, '乳品烘培', '快递', 48, '新鲜的面包团购', '面包团购', NULL, '2022-09-01 15:23:05.000000', 1, 2, 11);
INSERT INTO `groupbuying` VALUES (37, '乳品烘培', '快递', 48, '新鲜蛋糕', '蛋糕团购', NULL, '2022-09-01 05:30:56.000000', 1, 4, 10);
INSERT INTO `groupbuying` VALUES (38, '水产海鲜', '快递', 0, '新鲜海鲜', '海鲜团购', NULL, '2022-09-01 08:59:30.000000', 3, 2, 9);
INSERT INTO `groupbuying` VALUES (39, '水果鲜花', '快递', 128, '纤纤鲜先柠檬水', '鲜柠', NULL, '2022-09-01 02:00:00.000000', 1, 15, 2);
INSERT INTO `groupbuying` VALUES (41, '水产海鲜', '快递', 48, '新鲜小银鱼', '小银鱼团购', NULL, '2022-09-01 22:49:47.000000', 3, 2, 15);
INSERT INTO `groupbuying` VALUES (42, '肉禽蛋', '同城配送', 128, '农家菜小炒', '农家菜', NULL, '2022-09-01 02:00:00.000000', 0, 15, 0);
INSERT INTO `groupbuying` VALUES (43, '肉禽蛋', '同城配送', 128, '农家菜小炒', '农家菜', NULL, '2022-09-07 02:00:00.000000', 3, 15, 0);
INSERT INTO `groupbuying` VALUES (44, '肉禽蛋', '同城配送', 128, '农家菜小炒', '新鲜农家菜', NULL, '2022-09-07 02:00:00.000000', 3, 15, 0);
INSERT INTO `groupbuying` VALUES (45, '肉禽蛋', '同城配送', 128, '农家菜小炒', '新鲜农家菜', NULL, '2022-09-07 02:00:00.000000', 3, 15, 0);
INSERT INTO `groupbuying` VALUES (46, '肉禽蛋', '同城配送', 128, '农家菜小炒', '新鲜农家小炒', NULL, '2022-09-01 02:00:00.000000', 1, 16, 0);
INSERT INTO `groupbuying` VALUES (47, '乳品烘培', '快递', 72, '刚出炉的菠萝包', '菠萝包团购', NULL, '2022-09-07 21:39:07.000000', 3, 2, 0);

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
) ENGINE = InnoDB AUTO_INCREMENT = 93 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '订单详情信息表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of order_item
-- ----------------------------
INSERT INTO `order_item` VALUES (1, 2, 1, 1);
INSERT INTO `order_item` VALUES (2, 1, 2, 1);
INSERT INTO `order_item` VALUES (19, 10, 1, 14);
INSERT INTO `order_item` VALUES (20, 5, 2, 14);
INSERT INTO `order_item` VALUES (21, 4, 1, 3);
INSERT INTO `order_item` VALUES (22, 2, 3, 15);
INSERT INTO `order_item` VALUES (23, 2, 4, 15);
INSERT INTO `order_item` VALUES (24, 1, 1, 16);
INSERT INTO `order_item` VALUES (25, 2, 2, 16);
INSERT INTO `order_item` VALUES (26, 4, 2, 17);
INSERT INTO `order_item` VALUES (27, 7, 1, 17);
INSERT INTO `order_item` VALUES (30, 2, 5, 19);
INSERT INTO `order_item` VALUES (31, 2, 5, 20);
INSERT INTO `order_item` VALUES (32, 1, 5, 21);
INSERT INTO `order_item` VALUES (33, 1, 2, 22);
INSERT INTO `order_item` VALUES (34, 1, 1, 22);
INSERT INTO `order_item` VALUES (35, 1, 5, 23);
INSERT INTO `order_item` VALUES (36, 1, 1, 24);
INSERT INTO `order_item` VALUES (37, 4, 2, 24);
INSERT INTO `order_item` VALUES (41, 4, 6, 28);
INSERT INTO `order_item` VALUES (42, 1, 1, 29);
INSERT INTO `order_item` VALUES (43, 1, 2, 29);
INSERT INTO `order_item` VALUES (44, 1, 2, 30);
INSERT INTO `order_item` VALUES (45, 1, 1, 31);
INSERT INTO `order_item` VALUES (46, 2, 5, 32);
INSERT INTO `order_item` VALUES (47, 1, 1, 33);
INSERT INTO `order_item` VALUES (48, 1, 2, 33);
INSERT INTO `order_item` VALUES (49, 2, 1, 34);
INSERT INTO `order_item` VALUES (50, 1, 1, 35);
INSERT INTO `order_item` VALUES (51, 1, 6, 36);
INSERT INTO `order_item` VALUES (52, 1, 1, 37);
INSERT INTO `order_item` VALUES (53, 1, 6, 38);
INSERT INTO `order_item` VALUES (54, 2, 22, 39);
INSERT INTO `order_item` VALUES (55, 1, 1, 40);
INSERT INTO `order_item` VALUES (56, 1, 6, 41);
INSERT INTO `order_item` VALUES (57, 1, 2, 42);
INSERT INTO `order_item` VALUES (58, 1, 6, 43);
INSERT INTO `order_item` VALUES (59, 1, 6, 44);
INSERT INTO `order_item` VALUES (64, 1, 28, 49);
INSERT INTO `order_item` VALUES (76, 1, 2, 60);
INSERT INTO `order_item` VALUES (77, 2, 28, 60);
INSERT INTO `order_item` VALUES (78, 1, 2, 61);
INSERT INTO `order_item` VALUES (80, 1, 5, 63);
INSERT INTO `order_item` VALUES (81, 1, 60, 64);
INSERT INTO `order_item` VALUES (84, 3, 61, 67);
INSERT INTO `order_item` VALUES (85, 1, 60, 68);
INSERT INTO `order_item` VALUES (86, 1, 64, 69);
INSERT INTO `order_item` VALUES (87, 1, 62, 70);
INSERT INTO `order_item` VALUES (88, 2, 61, 71);
INSERT INTO `order_item` VALUES (89, 2, 66, 72);
INSERT INTO `order_item` VALUES (90, 2, 66, 73);
INSERT INTO `order_item` VALUES (91, 1, 60, 74);
INSERT INTO `order_item` VALUES (92, 2, 66, 75);

-- ----------------------------
-- Table structure for orders
-- ----------------------------
DROP TABLE IF EXISTS `orders`;
CREATE TABLE `orders`  (
  `order_id` int NOT NULL AUTO_INCREMENT,
  `state` int NULL DEFAULT NULL,
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
) ENGINE = InnoDB AUTO_INCREMENT = 76 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of orders
-- ----------------------------
INSERT INTO `orders` VALUES (1, 2, '2022-06-29 09:07:49.000000', 1, 1, 1);
INSERT INTO `orders` VALUES (3, 2, '2022-07-04 09:38:42.000000', 1, 1, 1);
INSERT INTO `orders` VALUES (14, 2, '2022-06-29 01:07:49.000000', 2, 1, 3);
INSERT INTO `orders` VALUES (15, 2, '2022-07-04 21:52:50.000000', 1, 3, 1);
INSERT INTO `orders` VALUES (16, 2, '2022-07-07 11:00:01.000000', 5, 1, 2);
INSERT INTO `orders` VALUES (17, 2, '2022-07-10 17:39:53.000000', 6, 1, 2);
INSERT INTO `orders` VALUES (19, 0, NULL, NULL, 4, 1);
INSERT INTO `orders` VALUES (20, 2, '2022-07-10 17:37:55.000000', 4, 4, 2);
INSERT INTO `orders` VALUES (21, 1, '2022-07-10 17:42:01.000000', 7, 4, 2);
INSERT INTO `orders` VALUES (22, 2, '2022-07-11 10:00:25.000000', 8, 1, 2);
INSERT INTO `orders` VALUES (23, 1, '2022-07-11 10:04:01.000000', 7, 4, 2);
INSERT INTO `orders` VALUES (24, 2, '2022-07-11 11:22:04.000000', 2, 1, 1);
INSERT INTO `orders` VALUES (28, 0, NULL, NULL, 5, 1);
INSERT INTO `orders` VALUES (29, 2, '2022-07-11 11:24:29.000000', 1, 1, 1);
INSERT INTO `orders` VALUES (30, 2, '2022-07-11 11:30:30.000000', 1, 1, 1);
INSERT INTO `orders` VALUES (31, 2, '2022-07-11 19:35:35.000000', 4, 1, 2);
INSERT INTO `orders` VALUES (32, 1, '2022-07-26 17:16:53.645000', 15, 4, 2);
INSERT INTO `orders` VALUES (33, 2, '2022-07-11 21:34:38.000000', 8, 1, 2);
INSERT INTO `orders` VALUES (34, 2, '2022-07-11 21:38:56.000000', 11, 1, 2);
INSERT INTO `orders` VALUES (35, 2, '2022-07-11 23:35:56.000000', 8, 1, 2);
INSERT INTO `orders` VALUES (36, 1, '2022-07-11 23:37:02.000000', 12, 5, 2);
INSERT INTO `orders` VALUES (37, 2, '2022-07-12 00:23:02.000000', 6, 1, 2);
INSERT INTO `orders` VALUES (38, 1, '2022-07-12 00:24:01.000000', 14, 5, 2);
INSERT INTO `orders` VALUES (39, 0, NULL, NULL, 3, 2);
INSERT INTO `orders` VALUES (40, 2, '2022-07-12 09:40:04.000000', 8, 1, 2);
INSERT INTO `orders` VALUES (41, 1, '2022-07-12 09:41:14.000000', 15, 5, 2);
INSERT INTO `orders` VALUES (42, 2, '2022-07-12 10:04:34.000000', 4, 1, 2);
INSERT INTO `orders` VALUES (43, 1, '2022-07-12 10:05:19.000000', 16, 5, 2);
INSERT INTO `orders` VALUES (44, 0, NULL, NULL, 5, 2);
INSERT INTO `orders` VALUES (49, 2, '2022-07-13 12:16:41.000000', 6, 1, 2);
INSERT INTO `orders` VALUES (60, 1, '2022-07-26 17:08:24.173000', 5, 1, 2);
INSERT INTO `orders` VALUES (61, 1, '2022-07-26 17:15:42.574000', 5, 1, 2);
INSERT INTO `orders` VALUES (63, 1, '2022-07-26 17:20:26.983000', 16, 4, 2);
INSERT INTO `orders` VALUES (64, 2, '2022-08-20 10:12:53.000000', 7, 36, 2);
INSERT INTO `orders` VALUES (67, 1, '2022-08-28 16:00:12.000000', 21, 37, 2);
INSERT INTO `orders` VALUES (68, 2, '2022-08-23 10:57:02.000000', 9, 36, 2);
INSERT INTO `orders` VALUES (69, 2, '2022-08-28 14:24:13.200000', 25, 39, 2);
INSERT INTO `orders` VALUES (70, 2, '2022-08-28 16:38:16.000000', 28, 38, 15);
INSERT INTO `orders` VALUES (71, 2, '2022-08-29 17:22:20.000000', 30, 37, 2);
INSERT INTO `orders` VALUES (72, 2, '2022-09-01 11:54:03.796000', 28, 41, 16);
INSERT INTO `orders` VALUES (73, 2, '2022-08-29 17:22:20.000000', 31, 41, 16);
INSERT INTO `orders` VALUES (74, 1, '2022-09-01 21:18:51.000000', 33, 36, 2);
INSERT INTO `orders` VALUES (75, 0, NULL, NULL, 41, 16);

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
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of subscriptions
-- ----------------------------
INSERT INTO `subscriptions` VALUES (2, 1);
INSERT INTO `subscriptions` VALUES (2, 3);
INSERT INTO `subscriptions` VALUES (2, 36);
INSERT INTO `subscriptions` VALUES (2, 37);
INSERT INTO `subscriptions` VALUES (2, 38);

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
) ENGINE = InnoDB AUTO_INCREMENT = 19 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '用户表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES (1, '1665249633@qq.com', '123456', 'Luoshicai', 725.00);
INSERT INTO `user` VALUES (2, 'xxx@xx.com', '123456', 'John', 680.40);
INSERT INTO `user` VALUES (3, 'yyy@yy.com', '123456', 'Mike', 451.00);
INSERT INTO `user` VALUES (4, 'test1@163.com', '1234', 'test1', 1012.00);
INSERT INTO `user` VALUES (5, 'Aa@gmail.com', 'Aa', 'Aa', 1000.00);
INSERT INTO `user` VALUES (6, 'Bb@gmail.com', 'Bb', 'Bb', 1000.00);
INSERT INTO `user` VALUES (15, 'test2@sina.com', '1234', 'Alice', 964.00);
INSERT INTO `user` VALUES (16, 'micro@gmail.com', '12345', 'micro', 1000.00);
INSERT INTO `user` VALUES (17, 'micro@gmail.com', '12345', 'gateway', 1000.00);
INSERT INTO `user` VALUES (18, 'security@gmail.com', '1234', 'security', 1000.00);

-- ----------------------------
-- Table structure for user_for_detail
-- ----------------------------
DROP TABLE IF EXISTS `user_for_detail`;
CREATE TABLE `user_for_detail`  (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '主键',
  `password` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `username` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `user_id` int NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `FKnw6pexrnsfxkuhth6y9dmsf75`(`user_id`) USING BTREE,
  CONSTRAINT `FKnw6pexrnsfxkuhth6y9dmsf75` FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 10 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '用户表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of user_for_detail
-- ----------------------------
INSERT INTO `user_for_detail` VALUES (1, '$2a$10$yzr3QlHxXmIix6asaLLF..qj0m1jPMEFXXETZ270ZzBE1v9EHyXE6', 'security', 18);
INSERT INTO `user_for_detail` VALUES (2, '$2a$10$m/DfOG8Yk4IJWNcRr1SOkeCdPtPhZtwU2BbftvfS3BqyG1WpjwJj2', 'Luoshicai', 1);
INSERT INTO `user_for_detail` VALUES (3, '$2a$10$m/DfOG8Yk4IJWNcRr1SOkeCdPtPhZtwU2BbftvfS3BqyG1WpjwJj2', 'John', 2);
INSERT INTO `user_for_detail` VALUES (4, '$2a$10$m/DfOG8Yk4IJWNcRr1SOkeCdPtPhZtwU2BbftvfS3BqyG1WpjwJj2', 'Mike', 3);
INSERT INTO `user_for_detail` VALUES (5, '$2a$10$B4Hy6kpTMe3JPSS7tEgFx.02eH/BIV/upanW.Y2Ul.1W9fmWj1lK6', 'test1', 4);
INSERT INTO `user_for_detail` VALUES (6, '$2a$10$sXY6dkb5nrlf2sTir/QmjeygCChF4z5PEBoIzPA5ZimDOhnmYq7a2', 'Aa', 5);
INSERT INTO `user_for_detail` VALUES (7, '$2a$10$QSeft1oVm6uAPyMdGA27pe6uRWClmgf5KoZV2fE4jbmBaxzVAhV5i', 'Bb', 6);
INSERT INTO `user_for_detail` VALUES (8, '$2a$10$B4Hy6kpTMe3JPSS7tEgFx.02eH/BIV/upanW.Y2Ul.1W9fmWj1lK6', 'Alice', 15);
INSERT INTO `user_for_detail` VALUES (9, '$2a$10$L3LPCxRdLS8zOF0j/AaHw.vgGlkYSMaf1aoLVL86nfWGzazANfHiS', 'micro', 16);
INSERT INTO `user_for_detail` VALUES (10, '$2a$10$L3LPCxRdLS8zOF0j/AaHw.vgGlkYSMaf1aoLVL86nfWGzazANfHiS', 'gateway', 17);

-- ----------------------------
-- Table structure for user_history
-- ----------------------------
DROP TABLE IF EXISTS `user_history`;
CREATE TABLE `user_history`  (
  `history_id` int NOT NULL AUTO_INCREMENT,
  `category` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `liking` int NULL DEFAULT NULL,
  `user_id` int NULL DEFAULT NULL,
  PRIMARY KEY (`history_id`) USING BTREE,
  INDEX `FKaa6ilb6iqih95bntoeyysb2pc`(`user_id`) USING BTREE,
  CONSTRAINT `FKaa6ilb6iqih95bntoeyysb2pc` FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 56 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of user_history
-- ----------------------------
INSERT INTO `user_history` VALUES (1, '水果鲜花', 5, 1);
INSERT INTO `user_history` VALUES (2, '肉禽蛋', 0, 1);
INSERT INTO `user_history` VALUES (3, '水产海鲜', 1, 1);
INSERT INTO `user_history` VALUES (4, '乳品烘培', 2, 1);
INSERT INTO `user_history` VALUES (5, '酒水饮料', 3, 1);
INSERT INTO `user_history` VALUES (6, '水果鲜花', 3, 2);
INSERT INTO `user_history` VALUES (7, '肉禽蛋', 5, 2);
INSERT INTO `user_history` VALUES (8, '水产海鲜', 7, 2);
INSERT INTO `user_history` VALUES (9, '乳品烘培', 29, 2);
INSERT INTO `user_history` VALUES (10, '酒水饮料', 3, 2);
INSERT INTO `user_history` VALUES (12, '水果鲜花', 8, 3);
INSERT INTO `user_history` VALUES (13, '肉禽蛋', 1, 3);
INSERT INTO `user_history` VALUES (14, '水产海鲜', 2, 3);
INSERT INTO `user_history` VALUES (15, '乳品烘培', 3, 3);
INSERT INTO `user_history` VALUES (16, '酒水饮料', 5, 3);
INSERT INTO `user_history` VALUES (17, '水果鲜花', 2, 4);
INSERT INTO `user_history` VALUES (18, '肉禽蛋', 3, 4);
INSERT INTO `user_history` VALUES (19, '水产海鲜', 1, 4);
INSERT INTO `user_history` VALUES (20, '乳品烘培', 8, 4);
INSERT INTO `user_history` VALUES (21, '酒水饮料', 0, 4);
INSERT INTO `user_history` VALUES (22, '水果鲜花', 3, 5);
INSERT INTO `user_history` VALUES (23, '肉禽蛋', 4, 5);
INSERT INTO `user_history` VALUES (24, '水产海鲜', 2, 5);
INSERT INTO `user_history` VALUES (25, '乳品烘培', 4, 5);
INSERT INTO `user_history` VALUES (26, '酒水饮料', 1, 5);
INSERT INTO `user_history` VALUES (27, '水果鲜花', 9, 6);
INSERT INTO `user_history` VALUES (28, '肉禽蛋', 7, 6);
INSERT INTO `user_history` VALUES (29, '水产海鲜', 11, 6);
INSERT INTO `user_history` VALUES (30, '乳品烘培', 1, 6);
INSERT INTO `user_history` VALUES (31, '酒水饮料', 2, 6);
INSERT INTO `user_history` VALUES (37, '水果鲜花', 0, 15);
INSERT INTO `user_history` VALUES (38, '肉禽蛋', 0, 15);
INSERT INTO `user_history` VALUES (39, '水产海鲜', 8, 15);
INSERT INTO `user_history` VALUES (40, '乳品烘培', 0, 15);
INSERT INTO `user_history` VALUES (41, '酒水饮料', 0, 15);
INSERT INTO `user_history` VALUES (42, '水果鲜花', 0, 16);
INSERT INTO `user_history` VALUES (43, '肉禽蛋', 0, 16);
INSERT INTO `user_history` VALUES (44, '水产海鲜', 15, 16);
INSERT INTO `user_history` VALUES (45, '乳品烘培', 0, 16);
INSERT INTO `user_history` VALUES (46, '酒水饮料', 0, 16);
INSERT INTO `user_history` VALUES (47, '水果鲜花', 0, 17);
INSERT INTO `user_history` VALUES (48, '肉禽蛋', 0, 17);
INSERT INTO `user_history` VALUES (49, '水产海鲜', 0, 17);
INSERT INTO `user_history` VALUES (50, '乳品烘培', 0, 17);
INSERT INTO `user_history` VALUES (51, '酒水饮料', 0, 17);
INSERT INTO `user_history` VALUES (52, '水果鲜花', 0, 18);
INSERT INTO `user_history` VALUES (53, '肉禽蛋', 0, 18);
INSERT INTO `user_history` VALUES (54, '水产海鲜', 0, 18);
INSERT INTO `user_history` VALUES (55, '乳品烘培', 0, 18);
INSERT INTO `user_history` VALUES (56, '酒水饮料', 0, 18);

SET FOREIGN_KEY_CHECKS = 1;
