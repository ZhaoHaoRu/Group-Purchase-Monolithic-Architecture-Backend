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

 Date: 04/09/2022 19:11:15
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

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
) ENGINE = InnoDB AUTO_INCREMENT = 18 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '用户表' ROW_FORMAT = DYNAMIC;

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

SET FOREIGN_KEY_CHECKS = 1;
