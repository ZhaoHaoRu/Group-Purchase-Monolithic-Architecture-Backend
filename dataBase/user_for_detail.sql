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

 Date: 04/09/2022 19:11:23
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

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
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '用户表' ROW_FORMAT = Dynamic;

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

SET FOREIGN_KEY_CHECKS = 1;
