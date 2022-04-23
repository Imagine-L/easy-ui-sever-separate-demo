package top.liubaiblog.admin.interceptor;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import top.liubaiblog.admin.annotation.StopRepeat;
import top.liubaiblog.admin.config.CustomObjectMapper;
import top.liubaiblog.admin.config.redis.RedisTemplate;
import top.liubaiblog.admin.constant.Constant;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;
import java.util.Objects;

/**
 * @author 刘佳俊
 */
public class FormRepeatInterceptor implements HandlerInterceptor {

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private CustomObjectMapper objectMapper;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (!(handler instanceof HandlerMethod)) return true;
        // 获取我们的controller方法
        HandlerMethod handlerMethod = (HandlerMethod) handler;
        Method method = handlerMethod.getMethod();
        StopRepeat annotation = method.getAnnotation(StopRepeat.class);

        // 判断控制器方法是否包含@StopRepeat注解并且判断是否为重复提交的表单
        if (annotation != null && isRepeatSubmit(request, annotation)) {
            // 符合以上两个条件则返回403
            ResponseEntity<String> responseEntity = ResponseEntity.status(403).body("请勿重复提交表单");
            response.setStatus(403);
            response.setContentType("application/json;charset=utf-8");
            response.getWriter().write(objectMapper.writeValueAsString(responseEntity));
            return false;
        }
        return true;
    }

    // 判断是否为重复提交的表单
    public boolean isRepeatSubmit(HttpServletRequest request, StopRepeat stopRepeat) throws JsonProcessingException {
        // 获取参数列表
        String params = objectMapper.writeValueAsString(request.getParameterMap());

        // 请求地址(作为cache的key值)
        String url = request.getRequestURI();

        // 获取请求头携带的token
        String token = request.getHeader(Constant.REQUEST_HEADER_AUTHORIZATION);

        // cache的唯一标识
        String cacheRepeatKey = Constant.REPEAT_SUBMIT_KEY + token + ":" + url;

        // 从redis中获取这个key，判断是否重复提交
        String preParams = redisTemplate.get(cacheRepeatKey);
        if (preParams != null && Objects.equals(preParams, params)) {
            return true;
        }

        // 如果不是重复提交的，就存入redis，并设置过期时间为注解的value值
        long expire = stopRepeat.value();
        redisTemplate.set(cacheRepeatKey, params, expire);
        return false;
    }
}
