/*
 Navicat Premium Data Transfer

 Source Server         : local_mysql5.7.26
 Source Server Type    : MySQL
 Source Server Version : 50726
 Source Host           : localhost:3306
 Source Schema         : api_monitor

 Target Server Type    : MySQL
 Target Server Version : 50726
 File Encoding         : 65001

 Date: 22/08/2019 16:17:57
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for api_monitor_record
-- ----------------------------
DROP TABLE IF EXISTS `api_monitor_record`;
CREATE TABLE `api_monitor_record`  (
  `monitor_id` bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '主键',
  `service_id` bigint(20) NOT NULL COMMENT '服务ID',
  `request_id` varchar(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '请求id UUID',
  `referer` varchar(250) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT 'referer',
  `ip` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '请求ip地址',
  `user_id` int(11) NOT NULL COMMENT '用户id',
  `client_id` int(11) NULL DEFAULT NULL COMMENT '客户端登录时不为空',
  `request_url` varchar(511) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '请求url',
  `request_method` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '请求方法GET/POST等',
  `request_header` text CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '请求头',
  `user_agent` varchar(511) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT 'User-Agent',
  `class_method` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '请求的具体方法名  格式：类.方法',
  `request_param` text CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL COMMENT '请求参数',
  `request_time` datetime(0) NOT NULL COMMENT '请求时间',
  `response_time` datetime(0) NOT NULL COMMENT '返回时间',
  `invoke_cost` bigint(20) NOT NULL COMMENT '调用耗时',
  `invoke_cost_format` varchar(63) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '调用耗时格式化',
  `response_code` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '返回状态码',
  `response_status` varchar(15) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL DEFAULT '' COMMENT '返回状态 SUCCESS FAIL',
  `response_entity` mediumtext CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL COMMENT '返回结果',
  `tenant_id` bigint(20) NULL DEFAULT NULL COMMENT '租户id',
  `role_id` bigint(20) NULL DEFAULT NULL COMMENT '角色id',
  `object_version_number` bigint(20) NOT NULL DEFAULT 1,
  `creation_date` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0),
  `created_by` bigint(20) NOT NULL DEFAULT -1,
  `last_updated_by` bigint(20) NOT NULL DEFAULT -1,
  `last_update_date` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0),
  PRIMARY KEY (`monitor_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin COMMENT = 'API监控记录表' ROW_FORMAT = Dynamic;

SET FOREIGN_KEY_CHECKS = 1;
