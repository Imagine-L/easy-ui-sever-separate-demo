package top.liubaiblog.admin.aspect;

import com.fasterxml.jackson.core.type.TypeReference;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import top.liubaiblog.admin.annotation.HasPermission;
import top.liubaiblog.admin.annotation.HasRole;
import top.liubaiblog.admin.config.redis.RedisTemplate;
import top.liubaiblog.admin.constant.Constant;
import top.liubaiblog.admin.exception.user.UserNotPermissionException;
import top.liubaiblog.admin.pojo.Role;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author 刘佳俊
 */
@Aspect
@Component
public class PermissionAspect {

    @Autowired
    private RedisTemplate redisTemplate;

    @Before("@annotation(hasRole)")
    public void roleBefore(JoinPoint joinPoint, HasRole hasRole) {
        // 获取注解中当前方法需要的角色权限集合
        String[] roles = hasRole.value();
        // 获取拥有的角色
        HttpServletRequest request = ((ServletRequestAttributes) Objects.requireNonNull(RequestContextHolder.getRequestAttributes())).getRequest();
        // 获取当前登录的用户
        String token = request.getHeader(Constant.REQUEST_HEADER_AUTHORIZATION);
        List<String> hasRoles = redisTemplate.getObject(Constant.ROLE_TAG_PREFIX + token, new TypeReference<List<String>>() {
        }).get();

        long count = Arrays.stream(roles).filter(hasRoles::contains).count();
        if (count == 0) {
            throw new UserNotPermissionException("用户没有此接口的权限");
        }
    }

    @Before("@annotation(hasPermission)")
    public void permissionBefore(JoinPoint joinPoint, HasPermission hasPermission) {
        // 获取注解中当前方法需要的角色权限集合
        String[] permissions = hasPermission.value();
        // 获取拥有的角色
        HttpServletRequest request = ((ServletRequestAttributes) Objects.requireNonNull(RequestContextHolder.getRequestAttributes())).getRequest();
        // 获取当前登录的用户
        String token = request.getHeader(Constant.REQUEST_HEADER_AUTHORIZATION);
        List<String> hasPermissions = redisTemplate.getObject(Constant.PERM_PREFIX + token, new TypeReference<List<String>>() {
        }).get();

        long count = Arrays.stream(permissions).filter(hasPermissions::contains).count();
        if (count == 0) {
            throw new UserNotPermissionException("用户没有此接口的权限");
        }
    }

}
