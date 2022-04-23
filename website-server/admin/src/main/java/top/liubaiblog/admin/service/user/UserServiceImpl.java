package top.liubaiblog.admin.service.user;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import eu.bitwalker.useragentutils.UserAgent;
import org.apache.tomcat.websocket.AuthenticationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import top.liubaiblog.admin.config.redis.RedisTemplate;
import top.liubaiblog.admin.exception.user.PasswordMistakeException;
import top.liubaiblog.admin.exception.user.UserNotFoundException;
import top.liubaiblog.admin.mapper.UserMapper;
import top.liubaiblog.admin.pojo.LoginUser;
import top.liubaiblog.admin.pojo.Menu;
import top.liubaiblog.admin.pojo.Role;
import top.liubaiblog.admin.pojo.User;
import top.liubaiblog.admin.constant.Constant;

import javax.servlet.http.HttpServletRequest;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author 刘佳俊
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private RedisTemplate redisTemplate;

    @Override
    public LoginUser login(String username, String password) throws JsonProcessingException {
        // 判断用户是否合法
        User user = userMapper.getUserByUserName(username);
        if (user == null) throw new UserNotFoundException("登录用户【" + username + "】不存在");
        if (!password.equals(user.getPassword())) throw new PasswordMistakeException("登录用户【" + username + "】输入的密码不正确");

        // 获取userAgent
        HttpServletRequest request = ((ServletRequestAttributes) Objects.requireNonNull(RequestContextHolder.getRequestAttributes())).getRequest();
        UserAgent userAgent = new UserAgent(request.getHeader("User-Agent"));

        // 获取地址json字符串
        ResponseEntity<String> responseEntity = restTemplate.getForEntity("https://whois.pconline.com.cn/ipJson.jsp?ip=" + request.getRemoteHost() + "&json=true", String.class);
        String body = responseEntity.getBody();
        Map<String, String> map = objectMapper.readValue(body, new TypeReference<Map<String, String>>() {
        });
        String location = map.get("addr") + map.get("pro") + map.get("city") + map.get("region");

        // 查询到了，则生产token，并创建LoginUser对象
        String token = UUID.randomUUID().toString();
        LoginUser loginUser = LoginUser.builder()
                .userId(user.getUserId())
                .token(token)
                .loginTime(new Date())
                .ipaddr(request.getRemoteAddr())
                .loginLocation(location)
                .browser(userAgent.getBrowser().getName())
                .os(userAgent.getOperatingSystem().getName())
                .user(user)
                .build();

        // 凭借token的前缀
        String keyPrefix = Constant.TOKEN_PREFIX + loginUser.getUser().getUserName() + ":";

        // 删除其他相同前缀的token，实现唯一登录
        Set<String> keys = redisTemplate.keys(keyPrefix + "*");
        keys.forEach(redisTemplate::remove);

        // 把新的数据添加到redis中
        redisTemplate.setObject(keyPrefix + token, loginUser, Constant.TOKEN_RUNTIME);

        return loginUser;
    }

    @Override
    public boolean logout() {
        // 从请求头中获取token
        HttpServletRequest req = ((ServletRequestAttributes) Objects.requireNonNull(RequestContextHolder.getRequestAttributes())).getRequest();
        String token = req.getHeader(Constant.REQUEST_HEADER_AUTHORIZATION);
        // 从数据库中移除token
        return redisTemplate.remove(Constant.TOKEN_PREFIX + token) != -1L;
    }

    @Override
    public PageInfo<User> selectUserForPage(int pageNo, int pageSize, String userId, String nickName) {
        PageHelper.startPage(pageNo, pageSize);
        List<User> users = userMapper.selectUserByConditional(userId, nickName);
        return new PageInfo<>(users);
    }

    @Override
    public Map<String, List<String>> getUserInfo() throws AuthenticationException {
        // 从redis中获取当前登录的用户
        LoginUser loginUser = getLoginUser();
        // 获取该用户的角色和权限信息
//        System.out.println("用户ID：" + loginUser.getUserId());
        User userInfo = userMapper.getUserInfoById(loginUser.getUserId());

        // 获取该user的所有角色并存储到redis
        List<String> roleTags = userInfo.getRoles().stream().map(Role::getRoleTag).collect(Collectors.toList());
        redisTemplate.setObject(Constant.ROLE_TAG_PREFIX + loginUser.getToken(), roleTags, Constant.TOKEN_RUNTIME);

        // 获取该user的所有menu并存储到redis
        List<String> perms = userInfo.getRoles().stream()
                .flatMap(role -> role.getMenus().stream())
                .map(Menu::getPerms)
                .collect(Collectors.toList());
        redisTemplate.setObject(Constant.PERM_PREFIX + loginUser.getToken(), perms, Constant.TOKEN_RUNTIME);

        // 将角色和权限重新封装成一个map返回
        Map<String, List<String>> map = new HashMap<>(2);
        map.put("roles", roleTags);
        map.put("perms", perms);
        return map;
    }

    @Override
    public User selectUserById(Integer id) {
        return userMapper.selectByPrimaryKey(id.longValue());
    }

    @Override
    public boolean saveUser(User user) {
        user.setCreateTime(new Date());
        return userMapper.insert(user) > 0;
    }

    @Override
    public boolean updateUser(User user) {
        return userMapper.updateByPrimaryKey(user) > 0;
    }

    @Override
    public boolean deleteUser(Integer userId) {
        return userMapper.deleteByPrimaryKey(userId.longValue()) > 0;
    }

    private LoginUser getLoginUser() throws AuthenticationException {
        HttpServletRequest request = ((ServletRequestAttributes) Objects.requireNonNull(RequestContextHolder.getRequestAttributes())).getRequest();
        // 获取当前登录的用户
        String authorization = request.getHeader(Constant.REQUEST_HEADER_AUTHORIZATION);
        if (!StringUtils.hasLength(authorization)) {
            throw new AuthenticationException("请求方未进行认证");
        }
        String tokenPart = Constant.TOKEN_PREFIX + "*" + authorization;
        // 从redis中获取认证信息是否存在
        Set<String> keys = redisTemplate.keys(tokenPart);
        if (keys == null || keys.size() != 1) {
            throw new AuthenticationException("无法从缓存中获取请求方认证信息");
        }

        String token = keys.toArray()[0].toString();
        return redisTemplate.getObject(token, LoginUser.class).get();
    }
}
