package top.liubaiblog.admin.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import top.liubaiblog.admin.pojo.UserRole;

public interface UserRoleMapper {
    int deleteByPrimaryKey(@Param("userId") Long userId, @Param("roleId") Long roleId);

    int insert(UserRole record);

    List<UserRole> selectAll();
}