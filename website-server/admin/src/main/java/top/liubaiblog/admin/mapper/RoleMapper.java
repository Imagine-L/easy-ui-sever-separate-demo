package top.liubaiblog.admin.mapper;

import java.util.List;
import top.liubaiblog.admin.pojo.Role;

public interface RoleMapper {
    int deleteByPrimaryKey(Long roleId);

    int insert(Role record);

    Role selectByPrimaryKey(Long roleId);

    List<Role> selectAll();

    int updateByPrimaryKey(Role record);
}