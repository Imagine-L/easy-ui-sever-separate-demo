-- ----------------------------
-- 创建用户表
-- ----------------------------
DROP TABLE IF EXISTS `ydl_user`;
CREATE TABLE `ydl_user`  (
  `user_id` BIGINT(20) NOT NULL AUTO_INCREMENT COMMENT '用户ID',
  `user_name` VARCHAR(30) NOT NULL COMMENT '用户账号',
  `nick_name` VARCHAR(30)  NOT NULL COMMENT '用户昵称',
  `email` VARCHAR(50)  NULL DEFAULT '' COMMENT '用户邮箱',
  `phonenumber` VARCHAR(11)  NULL DEFAULT '' COMMENT '手机号码',
  `sex` CHAR(1)  NULL DEFAULT '0' COMMENT '用户性别（0男 1女 2未知）',
  `avatar` VARCHAR(100)  NULL DEFAULT '' COMMENT '头像地址',
  `password` VARCHAR(100)  NULL DEFAULT '' COMMENT '密码',
  `status` CHAR(1)  NULL DEFAULT '0' COMMENT '帐号状态（0正常 1停用）',
  `del_flag` CHAR(1)  NULL DEFAULT '0' COMMENT '删除标志（0代表存在 2代表删除）',
  `login_ip` VARCHAR(128)  NULL DEFAULT '' COMMENT '最后登录IP',
  `login_date` DATETIME NULL DEFAULT NULL COMMENT '最后登录时间',
  `create_by` VARCHAR(64)  NULL DEFAULT '' COMMENT '创建者',
  `create_time` DATETIME NULL DEFAULT NULL COMMENT '创建时间',
  `update_by` VARCHAR(64)  NULL DEFAULT '' COMMENT '更新者',
  `update_time` DATETIME NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`user_id`) USING BTREE
) ENGINE = INNODB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '用户信息表';


-- ----------------------------
-- 增加几条用户信息
-- ----------------------------
INSERT INTO `ydl_user` VALUES (1, 'ydl_admin', '管理员', '253546544@qq.com', '18888888888', '0', '', 'xxxxxx', '0', '0', '10.25.245.45', '2022-01-23 15:50:27', 'admin', '2022-01-23 15:50:48', 'ydl', '2022-01-26 15:50:53');


DESC `ydl_user`;