/*
 Navicat Premium Data Transfer

 Source Server         : localhost_3306
 Source Server Type    : MySQL
 Source Server Version : 80028
 Source Host           : localhost:3306
 Source Schema         : gpurchase

 Target Server Type    : MySQL
 Target Server Version : 80028
 File Encoding         : 65001

 Date: 24/06/2022 15:08:39
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for addressbook
-- ----------------------------
DROP TABLE IF EXISTS `addressbook`;
CREATE TABLE `addressbook`  (
  `addressId` int UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '唯一标识收货地址的ID',
  `receiver` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '收货人姓名',
  `phone` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '收货人联系电话',
  `region` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '省 市 区',
  `location` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '详细地址',
  `ownerId` int UNSIGNED NOT NULL COMMENT '所属用户ID外键关联',
  PRIMARY KEY (`addressId`) USING BTREE,
  INDEX `ownerId`(`ownerId` ASC) USING BTREE,
  CONSTRAINT `addressbook_ibfk_1` FOREIGN KEY (`ownerId`) REFERENCES `users` (`userId`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of addressbook
-- ----------------------------

-- ----------------------------
-- Table structure for goodstable
-- ----------------------------
DROP TABLE IF EXISTS `goodstable`;
CREATE TABLE `goodstable`  (
  `goodsId` int UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '商品ID',
  `groupId` int UNSIGNED NOT NULL COMMENT '所属团的ID',
  `goodsName` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '字符集',
  `goodsInfo` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '商品描述',
  `price` decimal(10, 2) UNSIGNED NOT NULL COMMENT '商品价格',
  `inventory` int NOT NULL COMMENT '库存',
  `seckill` bit(1) NOT NULL COMMENT '是否为秒杀商品',
  PRIMARY KEY (`goodsId`) USING BTREE,
  INDEX `groupId`(`groupId` ASC) USING BTREE,
  CONSTRAINT `goodstable_ibfk_1` FOREIGN KEY (`groupId`) REFERENCES `group` (`groupId`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of goodstable
-- ----------------------------

-- ----------------------------
-- Table structure for group
-- ----------------------------
DROP TABLE IF EXISTS `group`;
CREATE TABLE `group`  (
  `groupId` int UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '团购ID',
  `headerId` int UNSIGNED NOT NULL COMMENT '团长ID',
  `groupTitle` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '团购标题',
  `groupInfo` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '团购简介',
  `delivery` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '配送方式：同城配送 | 顾客自提',
  `startTime` datetime NOT NULL COMMENT '团购开始时间',
  `endTime` datetime NOT NULL COMMENT '团购结束时间',
  `picture` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '图片？如何存储尚待讨论',
  PRIMARY KEY (`groupId`) USING BTREE,
  INDEX `headerId`(`headerId` ASC) USING BTREE,
  CONSTRAINT `group_ibfk_1` FOREIGN KEY (`headerId`) REFERENCES `users` (`userId`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of group
-- ----------------------------

-- ----------------------------
-- Table structure for orderitems
-- ----------------------------
DROP TABLE IF EXISTS `orderitems`;
CREATE TABLE `orderitems`  (
  `orderItemId` int UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '订单表项',
  `goodsId` int UNSIGNED NOT NULL COMMENT '商品ID',
  `goodsNumber` int UNSIGNED NOT NULL COMMENT '商品数量',
  `orderId` int UNSIGNED NOT NULL COMMENT '对应的订单号',
  PRIMARY KEY (`orderItemId`) USING BTREE,
  INDEX `orderId`(`orderId` ASC) USING BTREE,
  CONSTRAINT `orderitems_ibfk_1` FOREIGN KEY (`orderId`) REFERENCES `orders` (`orderId`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of orderitems
-- ----------------------------

-- ----------------------------
-- Table structure for orders
-- ----------------------------
DROP TABLE IF EXISTS `orders`;
CREATE TABLE `orders`  (
  `orderId` int UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '订单ID',
  `groupId` int UNSIGNED NOT NULL COMMENT '所属团的ID',
  `ownerId` int UNSIGNED NOT NULL COMMENT '所属用户的ID',
  `state` bit(1) NOT NULL COMMENT '0-未支付，表示购物车，1-支付，表示订单',
  `time` datetime NOT NULL COMMENT '下单时间',
  `addressId` int UNSIGNED NOT NULL COMMENT '收货地址ID',
  PRIMARY KEY (`orderId`) USING BTREE,
  INDEX `groupId`(`groupId` ASC) USING BTREE,
  INDEX `ownerId`(`ownerId` ASC) USING BTREE,
  INDEX `addressId`(`addressId` ASC) USING BTREE,
  CONSTRAINT `orders_ibfk_1` FOREIGN KEY (`groupId`) REFERENCES `group` (`groupId`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `orders_ibfk_2` FOREIGN KEY (`ownerId`) REFERENCES `users` (`userId`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `orders_ibfk_3` FOREIGN KEY (`addressId`) REFERENCES `addressbook` (`addressId`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of orders
-- ----------------------------

-- ----------------------------
-- Table structure for users
-- ----------------------------
DROP TABLE IF EXISTS `users`;
CREATE TABLE `users`  (
  `userId` int UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '用户ID',
  `userName` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '用户名',
  `password` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '密码',
  `phone` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '联系电话',
  PRIMARY KEY (`userId`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of users
-- ----------------------------

SET FOREIGN_KEY_CHECKS = 1;
