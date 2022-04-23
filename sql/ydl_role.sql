-- ----------------------------
-- Table structure for ydl_role
-- ----------------------------
DROP TABLE IF EXISTS `ydl_role`;
CREATE TABLE `ydl_role`  (
  `role_id` BIGINT(20) NOT NULL AUTO_INCREMENT COMMENT '角色ID',
  `role_name` VARCHAR(30)  NOT NULL COMMENT '角色名称',
  `role_tag` VARCHAR(30)  NULL DEFAULT NULL COMMENT '角色标签',
  `role_sort` INT(4) NOT NULL COMMENT '显示顺序',
  `status` CHAR(1)  NOT NULL COMMENT '角色状态（0正常 1停用）',
  `del_flag` CHAR(1)  NULL DEFAULT '0' COMMENT '删除标志（0代表存在 1代表删除）',
  `create_by` VARCHAR(64)  NULL DEFAULT '' COMMENT '创建者',
  `create_time` DATETIME NULL DEFAULT NULL COMMENT '创建时间',
  `update_by` VARCHAR(64)  NULL DEFAULT '' COMMENT '更新者',
  `update_time` DATETIME NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`role_id`) USING BTREE
) ENGINE = INNODB AUTO_INCREMENT = 2 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '角色信息表' ;

-- ----------------------------
-- Records of ydl_role
-- ----------------------------
INSERT INTO `ydl_role` VALUES (1, '管理员', NULL, 1, '0', '0', 'admin', '2022-01-28 15:57:57', 'admin', '2022-01-29 15:58:04');