DROP TABLE IF EXISTS `ydl_oper_log`;
CREATE TABLE `ydl_oper_log`  (
  `oper_id` INT(11) NOT NULL AUTO_INCREMENT COMMENT '日志主键',
  `title` VARCHAR(255)  COMMENT '操作模块',
  `business_type` VARCHAR(255)  COMMENT '业务类型',
  `method` VARCHAR(255)  COMMENT 'api方法',
  `request_method` VARCHAR(255)  COMMENT '请求方式',
  `oper_name` VARCHAR(255)  COMMENT '操作人员',
  `oper_url` VARCHAR(255)  COMMENT '请求url',
  `oper_ip` VARCHAR(255)  COMMENT '操作地址',
  `status` INT(11) NULL DEFAULT NULL COMMENT '操作状态',
  `errorMsg` VARCHAR(255)  COMMENT '错误消息',
  `operTime` DATETIME(0) NULL DEFAULT NULL COMMENT '操作时间',
  PRIMARY KEY (`oper_id`) USING BTREE
) ENGINE = INNODB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '操作日志';