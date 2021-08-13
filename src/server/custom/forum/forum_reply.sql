/*
Navicat MySQL Data Transfer

Source Server         : MXD
Source Server Version : 50513
Source Host           : localhost:3306
Source Database       : mxd

Target Server Type    : MYSQL
Target Server Version : 50513
File Encoding         : 65001

Date: 2017-06-29 17:57:52
*/

SET FOREIGN_KEY_CHECKS=0;
-- ----------------------------
-- Table structure for `forum_reply`
-- ----------------------------
DROP TABLE IF EXISTS `forum_reply`;
CREATE TABLE `forum_reply` (
  `rid` int(4) NOT NULL AUTO_INCREMENT,
  `tid` int(4) DEFAULT NULL,
  `cid` int(4) DEFAULT NULL,
  `cname` varchar(255) DEFAULT NULL,
  `time` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `news` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`rid`)
) ENGINE=MyISAM AUTO_INCREMENT=33 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of forum_reply
-- ----------------------------
