-- ----------------------------
-- Table structure for sys_role_menu
-- ----------------------------
DROP TABLE IF EXISTS `ydl_role_menu`;
CREATE TABLE `ydl_role_menu`  (
  `role_id` BIGINT(20) NOT NULL COMMENT '角色ID',
  `menu_id` BIGINT(20) NOT NULL COMMENT '菜单ID',
  PRIMARY KEY (`role_id`, `menu_id`) USING BTREE
) ENGINE = INNODB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '角色和菜单关联表';

-- ----------------------------
-- Records of sys_role_menu
-- ----------------------------
INSERT INTO `ydl_role_menu` VALUES (1, 1);
INSERT INTO `ydl_role_menu` VALUES (1, 2);
INSERT INTO `ydl_role_menu` VALUES (1, 3);
INSERT INTO `ydl_role_menu` VALUES (1, 4);