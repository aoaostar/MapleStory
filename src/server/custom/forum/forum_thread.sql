/*
Navicat MySQL Data Transfer

Source Server         : MXD
Source Server Version : 50513
Source Host           : localhost:3306
Source Database       : mxd

Target Server Type    : MYSQL
Target Server Version : 50513
File Encoding         : 65001

Date: 2017-06-29 17:58:43
*/

SET FOREIGN_KEY_CHECKS=0;
-- ----------------------------
-- Table structure for `forum_thread`
-- ----------------------------
DROP TABLE IF EXISTS `forum_thread`;
CREATE TABLE `forum_thread` (
  `tid` int(4) NOT NULL AUTO_INCREMENT,
  `sid` int(4) DEFAULT NULL,
  `tname` varchar(255) DEFAULT NULL,
  `cid` int(11) DEFAULT NULL,
  `cname` varchar(255) DEFAULT NULL,
  `time` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `up` int(4) NOT NULL DEFAULT '0',
  `down` int(4) NOT NULL DEFAULT '0',
  PRIMARY KEY (`tid`)
) ENGINE=MyISAM AUTO_INCREMENT=27 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of forum_thread
-- ----------------------------
