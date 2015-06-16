/*
Navicat MySQL Data Transfer

Source Server         : root-connect
Source Server Version : 50132
Source Host           : localhost:3306
Source Database       : youme

Target Server Type    : MYSQL
Target Server Version : 50132
File Encoding         : 65001

Date: 2015-06-16 09:58:02
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `activity`
-- ----------------------------
DROP TABLE IF EXISTS `activity`;
CREATE TABLE `activity` (
  `activityId` int(11) NOT NULL AUTO_INCREMENT,
  `typeId` int(11) DEFAULT NULL,
  `userId` int(11) DEFAULT NULL,
  `title` varchar(25) COLLATE utf8_unicode_ci DEFAULT NULL,
  `city` varchar(10) COLLATE utf8_unicode_ci DEFAULT NULL,
  `place` varchar(40) COLLATE utf8_unicode_ci DEFAULT NULL,
  `poster` varchar(256) COLLATE utf8_unicode_ci DEFAULT NULL,
  `description` varchar(300) COLLATE utf8_unicode_ci DEFAULT NULL,
  `latitude` double DEFAULT NULL,
  `longitude` double DEFAULT NULL,
  `createTime` datetime DEFAULT NULL,
  `startTime` datetime DEFAULT NULL,
  `endTime` datetime DEFAULT NULL,
  `attendNum` int(11) DEFAULT NULL,
  `browseNum` int(11) DEFAULT NULL,
  PRIMARY KEY (`activityId`),
  KEY `FK_Relationship_5` (`userId`),
  KEY `FK_Relationship_6` (`typeId`),
  CONSTRAINT `FK_Relationship_5` FOREIGN KEY (`userId`) REFERENCES `user` (`userId`) ON DELETE CASCADE,
  CONSTRAINT `FK_Relationship_6` FOREIGN KEY (`typeId`) REFERENCES `type` (`typeId`) ON DELETE SET NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- ----------------------------
-- Records of activity
-- ----------------------------

-- ----------------------------
-- Table structure for `attend`
-- ----------------------------
DROP TABLE IF EXISTS `attend`;
CREATE TABLE `attend` (
  `attendId` int(11) NOT NULL AUTO_INCREMENT,
  `activityId` int(11) DEFAULT NULL,
  `userId` int(11) DEFAULT NULL,
  PRIMARY KEY (`attendId`),
  KEY `FK_Relationship_1` (`userId`),
  KEY `FK_Relationship_4` (`activityId`),
  CONSTRAINT `FK_Relationship_1` FOREIGN KEY (`userId`) REFERENCES `user` (`userId`) ON DELETE CASCADE,
  CONSTRAINT `FK_Relationship_4` FOREIGN KEY (`activityId`) REFERENCES `activity` (`activityId`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- ----------------------------
-- Records of attend
-- ----------------------------

-- ----------------------------
-- Table structure for `browse`
-- ----------------------------
DROP TABLE IF EXISTS `browse`;
CREATE TABLE `browse` (
  `browseId` int(11) NOT NULL AUTO_INCREMENT,
  `userId` int(11) DEFAULT NULL,
  `activityId` int(11) DEFAULT NULL,
  PRIMARY KEY (`browseId`),
  KEY `FK_Relationship_13` (`userId`),
  KEY `FK_Relationship_14` (`activityId`),
  CONSTRAINT `FK_Relationship_13` FOREIGN KEY (`userId`) REFERENCES `user` (`userId`) ON DELETE CASCADE,
  CONSTRAINT `FK_Relationship_14` FOREIGN KEY (`activityId`) REFERENCES `activity` (`activityId`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- ----------------------------
-- Records of browse
-- ----------------------------

-- ----------------------------
-- Table structure for `collect`
-- ----------------------------
DROP TABLE IF EXISTS `collect`;
CREATE TABLE `collect` (
  `collectId` int(11) NOT NULL AUTO_INCREMENT,
  `activityId` int(11) DEFAULT NULL,
  `userId` int(11) DEFAULT NULL,
  `createTime` datetime DEFAULT NULL,
  PRIMARY KEY (`collectId`),
  KEY `FK_Relationship_11` (`userId`),
  KEY `FK_Relationship_12` (`activityId`),
  CONSTRAINT `FK_Relationship_11` FOREIGN KEY (`userId`) REFERENCES `user` (`userId`) ON DELETE CASCADE,
  CONSTRAINT `FK_Relationship_12` FOREIGN KEY (`activityId`) REFERENCES `activity` (`activityId`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- ----------------------------
-- Records of collect
-- ----------------------------

-- ----------------------------
-- Table structure for `comment`
-- ----------------------------
DROP TABLE IF EXISTS `comment`;
CREATE TABLE `comment` (
  `commentId` int(11) NOT NULL AUTO_INCREMENT,
  `userId` int(11) DEFAULT NULL,
  `activityId` int(11) DEFAULT NULL,
  `content` varchar(50) COLLATE utf8_unicode_ci DEFAULT NULL,
  `createTime` datetime DEFAULT NULL,
  PRIMARY KEY (`commentId`),
  KEY `FK_Relationship_10` (`activityId`),
  KEY `FK_Relationship_8` (`userId`),
  CONSTRAINT `FK_Relationship_10` FOREIGN KEY (`activityId`) REFERENCES `activity` (`activityId`) ON DELETE CASCADE,
  CONSTRAINT `FK_Relationship_8` FOREIGN KEY (`userId`) REFERENCES `user` (`userId`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- ----------------------------
-- Records of comment
-- ----------------------------

-- ----------------------------
-- Table structure for `footprint`
-- ----------------------------
DROP TABLE IF EXISTS `footprint`;
CREATE TABLE `footprint` (
  `footprintId` int(11) NOT NULL AUTO_INCREMENT,
  `userId` int(11) DEFAULT NULL,
  `activityId` int(11) DEFAULT NULL,
  `mood` varchar(300) COLLATE utf8_unicode_ci DEFAULT NULL,
  `createTime` datetime DEFAULT NULL,
  PRIMARY KEY (`footprintId`),
  KEY `FK_Relationship_2` (`userId`),
  KEY `FK_Relationship_3` (`activityId`),
  CONSTRAINT `FK_Relationship_2` FOREIGN KEY (`userId`) REFERENCES `user` (`userId`) ON DELETE CASCADE,
  CONSTRAINT `FK_Relationship_3` FOREIGN KEY (`activityId`) REFERENCES `activity` (`activityId`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- ----------------------------
-- Records of footprint
-- ----------------------------

-- ----------------------------
-- Table structure for `photo`
-- ----------------------------
DROP TABLE IF EXISTS `photo`;
CREATE TABLE `photo` (
  `photoId` int(11) NOT NULL AUTO_INCREMENT,
  `footprintId` int(11) DEFAULT NULL,
  `photoUrl` varchar(256) COLLATE utf8_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`photoId`),
  KEY `FK_Relationship_9` (`footprintId`),
  CONSTRAINT `FK_Relationship_9` FOREIGN KEY (`footprintId`) REFERENCES `footprint` (`footprintId`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- ----------------------------
-- Records of photo
-- ----------------------------

-- ----------------------------
-- Table structure for `type`
-- ----------------------------
DROP TABLE IF EXISTS `type`;
CREATE TABLE `type` (
  `typeId` int(11) NOT NULL AUTO_INCREMENT,
  `typeName` varchar(10) COLLATE utf8_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`typeId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- ----------------------------
-- Records of type
-- ----------------------------

-- ----------------------------
-- Table structure for `user`
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `userId` int(11) NOT NULL AUTO_INCREMENT,
  `account` varchar(15) COLLATE utf8_unicode_ci DEFAULT NULL,
  `pwd` varchar(32) COLLATE utf8_unicode_ci DEFAULT NULL,
  `nickname` varchar(15) COLLATE utf8_unicode_ci DEFAULT NULL,
  `head` varchar(100) COLLATE utf8_unicode_ci DEFAULT NULL,
  `age` int(11) DEFAULT NULL,
  `sex` varchar(2) COLLATE utf8_unicode_ci DEFAULT NULL,
  `name` varchar(20) COLLATE utf8_unicode_ci DEFAULT NULL,
  `sign` varchar(30) COLLATE utf8_unicode_ci DEFAULT NULL,
  `city` varchar(50) COLLATE utf8_unicode_ci DEFAULT NULL,
  `createTime` datetime DEFAULT NULL,
  PRIMARY KEY (`userId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- ----------------------------
-- Records of user
-- ----------------------------

-- ----------------------------
-- Procedure structure for `login`
-- ----------------------------
DROP PROCEDURE IF EXISTS `login`;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `login`(`acc` varchar(50), `pwd` varchar(50))
BEGIN
	SELECT * FROM `user` WHERE account=acc;

END
;;
DELIMITER ;
DROP TRIGGER IF EXISTS `attendActivity`;
DELIMITER ;;
CREATE TRIGGER `attendActivity` AFTER INSERT ON `attend` FOR EACH ROW begin
update activity set attendNum = attendNum+1 where activityId = new.activityId;
end
;;
DELIMITER ;
DROP TRIGGER IF EXISTS `cancelActivity`;
DELIMITER ;;
CREATE TRIGGER `cancelActivity` AFTER DELETE ON `attend` FOR EACH ROW begin
update activity set attendNum = attendNum-1 where activityId = old.activityId;
end
;;
DELIMITER ;
DROP TRIGGER IF EXISTS `insertBrowse`;
DELIMITER ;;
CREATE TRIGGER `insertBrowse` AFTER INSERT ON `browse` FOR EACH ROW begin
update activity set browseNum = browseNum + 1 where activityId = new.activityId;
end
;;
DELIMITER ;
