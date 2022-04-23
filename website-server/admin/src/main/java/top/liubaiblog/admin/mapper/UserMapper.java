package top.liubaiblog.admin.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import top.liubaiblog.admin.pojo.User;

@Mapper
public interface UserMapper {
    int deleteByPrimaryKey(Long userId);

    int insert(User record);

    User selectByPrimaryKey(Long userId);

    List<User> selectAll();

    int updateByPrimaryKey(User record);

    User getUserByUserName(@Param("userName") String userName);

    List<User> selectUserByConditional(@Param("userId") String userId, @Param("nickName") String nickName);

    User getUserInfoById(@Param("userId") Long userId);
}