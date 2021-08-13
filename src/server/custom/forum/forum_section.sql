/*
Navicat MySQL Data Transfer

Source Server         : MXD
Source Server Version : 50513
Source Host           : localhost:3306
Source Database       : mxd

Target Server Type    : MYSQL
Target Server Version : 50513
File Encoding         : 65001

Date: 2017-06-29 17:58:40
*/

SET FOREIGN_KEY_CHECKS=0;
-- ----------------------------
-- Table structure for `forum_section`
-- ----------------------------
DROP TABLE IF EXISTS `forum_section`;
CREATE TABLE `forum_section` (
  `id` int(4) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL DEFAULT 'null',
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=32 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of forum_section
-- ----------------------------
