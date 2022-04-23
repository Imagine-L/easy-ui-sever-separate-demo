package top.liubaiblog.admin.util;

import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import top.liubaiblog.admin.config.redis.RedisTemplate;
import top.liubaiblog.admin.constant.Constant;
import top.liubaiblog.admin.exception.user.AuthorizationException;
import top.liubaiblog.admin.pojo.LoginUser;

import javax.servlet.http.HttpServletRequest;
import java.util.Objects;
import java.util.Set;

/**
 * @author 刘佳俊
 */
public final class AuthUtil {

    private AuthUtil() {
    }

    public static LoginUser getLoginUserByRedis(RedisTemplate redisTemplate) {
        HttpServletRequest request = ((ServletRequestAttributes) Objects.requireNonNull(RequestContextHolder.getRequestAttributes())).getRequest();

        // 从请求头中获取认证信息
        String authorization = request.getHeader(Constant.REQUEST_HEADER_AUTHORIZATION);
        if (!StringUtils.hasLength(authorization)) {
            throw new AuthorizationException("请求方未进行认证");
        }
        String tokenPart = Constant.TOKEN_PREFIX + "*" + authorization;

        // 从redis中获取认证信息是否存在
        Set<String> keys = redisTemplate.keys(tokenPart);
        if (keys == null || keys.size() != 1) {
            throw new AuthorizationException("无法从缓存中获取请求方认证信息");
        }

        String s = keys.toArray()[0].toString();
        return redisTemplate.getObject(s, LoginUser.class).get();
    }
}
