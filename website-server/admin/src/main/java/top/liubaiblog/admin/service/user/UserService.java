package top.liubaiblog.admin.service.user;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.github.pagehelper.PageInfo;
import org.apache.ibatis.annotations.Param;
import org.apache.tomcat.websocket.AuthenticationException;
import org.springframework.stereotype.Service;
import top.liubaiblog.admin.pojo.LoginUser;
import top.liubaiblog.admin.pojo.User;

import java.util.List;
import java.util.Map;

/**
 * @author 刘佳俊
 */
public interface UserService {

    LoginUser login(String username, String password) throws JsonProcessingException;

    boolean logout();

    PageInfo<User> selectUserForPage(int pageNo, int pageSize, String userId, String nickName);

    Map<String, List<String>> getUserInfo() throws AuthenticationException;

    User selectUserById(Integer id);

    boolean saveUser(User user);

    boolean updateUser(User user);

    boolean deleteUser(Integer userId);
}
