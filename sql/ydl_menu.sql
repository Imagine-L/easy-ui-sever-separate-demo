-- ----------------------------
-- Table structure for ydl_menu
-- ----------------------------
DROP TABLE IF EXISTS `ydl_menu`;
CREATE TABLE `ydl_menu`  (
  `menu_id` BIGINT(20) NOT NULL AUTO_INCREMENT COMMENT '菜单ID',
  `menu_name` VARCHAR(50)  NOT NULL COMMENT '菜单名称',
  `perms` VARCHAR(50)  NULL DEFAULT NULL COMMENT '权限标识',
  `parent_id` BIGINT(20) NULL DEFAULT 0 COMMENT '父菜单ID',
  `order_num` INT(4) NULL DEFAULT 0 COMMENT '显示顺序',
  `path` VARCHAR(200)  NULL DEFAULT '' COMMENT '路由地址',
  `component` VARCHAR(255)  NULL DEFAULT NULL COMMENT '组件路径',
  `menu_type` CHAR(1)  NULL DEFAULT '' COMMENT '菜单类型（M目录 C菜单 F按钮）',
  `visible` CHAR(1)  NULL DEFAULT '0' COMMENT '菜单状态（0显示 1隐藏）',
  `icon` VARCHAR(100)  NULL DEFAULT '#' COMMENT '菜单图标',
  `create_by` VARCHAR(64)  NULL DEFAULT '' COMMENT '创建者',
  `create_time` DATETIME NULL DEFAULT NULL COMMENT '创建时间',
  `update_by` VARCHAR(64)  NULL DEFAULT '' COMMENT '更新者',
  `update_time` DATETIME NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`menu_id`) USING BTREE
) ENGINE = INNODB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '菜单权限表' ;

-- ----------------------------
-- Records of ydl_menu
-- ----------------------------
INSERT INTO `ydl_menu` VALUES (1, '系统管理', 'system', 0, 0, 'system', 'null', '1', '0', '#', '', NULL, '', NULL);
INSERT INTO `ydl_menu` VALUES (2, '用户管理', 'system:user', 1, 1, 'system/user/index', NULL, '', '0', '#', '', NULL, '', NULL);
INSERT INTO `ydl_menu` VALUES (3, '角色管理', 'system:role', 1, 2, 'system/role/index', NULL, '', '0', '#', '', NULL, '', NULL);
INSERT INTO `ydl_menu` VALUES (4, '菜单管理', 'system:menu', 1, 3, 'system/menu/index', NULL, '', '0', '#', '', NULL, '', NULL);