package top.liubaiblog.admin.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import top.liubaiblog.admin.pojo.RoleMenu;

public interface RoleMenuMapper {
    int deleteByPrimaryKey(@Param("roleId") Long roleId, @Param("menuId") Long menuId);

    int insert(RoleMenu record);

    List<RoleMenu> selectAll();
}