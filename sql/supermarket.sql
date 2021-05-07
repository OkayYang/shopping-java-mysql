/*
 Navicat MySQL Data Transfer

 Source Server         : 192.168.100.1-mysql
 Source Server Type    : MySQL
 Source Server Version : 80023
 Source Host           : localhost:3306
 Source Schema         : supermarket

 Target Server Type    : MySQL
 Target Server Version : 80023
 File Encoding         : 65001

 Date: 07/05/2021 19:48:09
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for goods
-- ----------------------------
DROP TABLE IF EXISTS `goods`;
CREATE TABLE `goods`  (
  `id` int NOT NULL AUTO_INCREMENT,
  `gname` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `bid` double(5, 2) NULL DEFAULT NULL,
  `price` double(5, 2) NULL DEFAULT NULL,
  `stock` int NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of goods
-- ----------------------------
INSERT INTO `goods` VALUES (1, '可乐', 1.50, 3.50, 172);
INSERT INTO `goods` VALUES (2, '雪碧', 1.50, 3.50, 339);
INSERT INTO `goods` VALUES (3, '芬达', 2.00, 4.00, 169);
INSERT INTO `goods` VALUES (4, '冰红茶', 2.00, 4.00, 0);

-- ----------------------------
-- Table structure for record
-- ----------------------------
DROP TABLE IF EXISTS `record`;
CREATE TABLE `record`  (
  `datetime` datetime NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  `rid` int NOT NULL AUTO_INCREMENT,
  `uid` int NOT NULL,
  `gid` int NOT NULL,
  `num` int NULL DEFAULT NULL,
  PRIMARY KEY (`rid`) USING BTREE,
  INDEX `uid`(`uid`) USING BTREE,
  INDEX `gid`(`gid`) USING BTREE,
  CONSTRAINT `record_ibfk_1` FOREIGN KEY (`uid`) REFERENCES `user` (`uid`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `record_ibfk_2` FOREIGN KEY (`gid`) REFERENCES `goods` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of record
-- ----------------------------
INSERT INTO `record` VALUES ('2021-05-07 15:30:58', 16, 18, 2, 5);
INSERT INTO `record` VALUES ('2021-05-07 15:31:01', 17, 18, 2, 5);
INSERT INTO `record` VALUES ('2021-05-07 15:33:09', 18, 18, 2, 5);
INSERT INTO `record` VALUES ('2021-05-07 15:33:27', 19, 18, 2, 5);
INSERT INTO `record` VALUES ('2021-05-07 15:33:39', 20, 18, 2, 5);
INSERT INTO `record` VALUES ('2021-05-07 15:34:02', 21, 18, 2, 5);
INSERT INTO `record` VALUES ('2021-05-07 15:37:05', 22, 18, 2, 5);
INSERT INTO `record` VALUES ('2021-05-07 15:37:25', 23, 18, 2, 5);
INSERT INTO `record` VALUES ('2021-05-07 15:38:02', 24, 18, 2, 5);
INSERT INTO `record` VALUES ('2021-05-07 15:38:33', 25, 18, 2, 5);
INSERT INTO `record` VALUES ('2021-05-07 16:32:33', 26, 1, 1, 50);
INSERT INTO `record` VALUES ('2021-05-07 16:33:58', 27, 1, 2, 50);
INSERT INTO `record` VALUES ('2021-05-07 16:39:02', 28, 1, 4, 1);
INSERT INTO `record` VALUES ('2021-05-07 16:53:11', 29, 1, 1, 5);
INSERT INTO `record` VALUES ('2021-05-07 16:53:44', 30, 1, 3, 5);
INSERT INTO `record` VALUES ('2021-05-07 17:00:33', 32, 1, 2, 60);

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user`  (
  `uid` int NOT NULL AUTO_INCREMENT,
  `uname` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `upd` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `usex` char(2) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `uage` int NULL DEFAULT NULL,
  `uphone` bigint NULL DEFAULT NULL,
  `ubalance` double(10, 2) NULL DEFAULT NULL,
  PRIMARY KEY (`uid`) USING BTREE,
  UNIQUE INDEX `uname`(`uname`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 20 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES (1, 'admin', '123456', '男', 21, 16666666666, 25616.45);
INSERT INTO `user` VALUES (15, 'yang@qq.com', 'yang123', '男', 20, 17777777777, 1.00);
INSERT INTO `user` VALUES (17, '168@qq.com', 'yang123', '男', 25, 18888888888, 25600.00);
INSERT INTO `user` VALUES (18, '1347456958@qq.com', 'yang123', '男', 21, 15555555555, 998952.50);
INSERT INTO `user` VALUES (19, 'hehe@qq.com', 'yang521', '男', 56, 17633505913, 5.00);

SET FOREIGN_KEY_CHECKS = 1;
