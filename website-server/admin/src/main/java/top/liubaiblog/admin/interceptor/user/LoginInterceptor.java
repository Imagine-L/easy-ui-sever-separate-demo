package top.liubaiblog.admin.interceptor.user;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.websocket.AuthenticationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import top.liubaiblog.admin.config.redis.RedisTemplate;
import top.liubaiblog.admin.constant.Constant;
import top.liubaiblog.admin.exception.user.AuthorizationException;
import top.liubaiblog.admin.pojo.LoginUser;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

/**
 * @author 刘佳俊
 */
@Slf4j
public class LoginInterceptor implements HandlerInterceptor {

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private ObjectMapper objectMapper;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws IOException {
        ResponseEntity<String> responseEntity = null;
        try {
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

            String token = keys.toArray()[0].toString();
            // 从redis中获取认证信息是否存在
            // Optional<LoginUser> optionalLoginUser = redisTemplate.getObject(token, new TypeReference<LoginUser>() {
            // });
            // // 根据请求结果设置响应体
            // if (!optionalLoginUser.isPresent()) {
            //     throw new AuthenticationException("无法从缓存中获取请求方认证信息");
            // }

            // 如果请求没有被拦截，则增加token的持续时间
            redisTemplate.expire(token, Constant.TOKEN_RUNTIME);
            // 放行
            return true;
        } catch (Exception e) {
            log.warn(e.getMessage());
            response.setStatus(401);
            responseEntity = ResponseEntity.status(401).body("Not authorization!");
            String s = objectMapper.writeValueAsString(responseEntity);
            response.getWriter().write(s);
            return false;
        }
    }
}
