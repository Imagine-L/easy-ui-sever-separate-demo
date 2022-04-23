package top.liubaiblog.admin.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import top.liubaiblog.admin.annotation.Log;
import top.liubaiblog.admin.config.redis.RedisTemplate;
import top.liubaiblog.admin.pojo.LoginUser;
import top.liubaiblog.admin.pojo.OperLog;
import top.liubaiblog.admin.service.log.LogService;
import top.liubaiblog.admin.util.AuthUtil;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.Objects;

/**
 * @author 刘佳俊
 */
@Aspect
@Component
@Slf4j
public class LogAspect {

    @Autowired
    private LogService logService;

    @Autowired
    private RedisTemplate redisTemplate;

    @AfterReturning("@annotation(log)")
    public void afterReturning(JoinPoint joinPoint, Log log) {
        OperLog operlog = getOperlog(joinPoint, log, null);

        joinPoint.getSignature();
        handlerLog(operlog);
    }

    @AfterThrowing(value = "@annotation(log)", throwing = "e")
    public void afterThrowing(JoinPoint joinPoint, Log log, Exception e) {
        OperLog operlog = getOperlog(joinPoint, log, e);
        handlerLog(operlog);
    }

    public void handlerLog(OperLog operLog) {
        // 添加入数据库
        logService.addLog(operLog);
    }

    private OperLog getOperlog(JoinPoint joinPoint, Log log, Exception e) {
        // 确定参数
        String title = log.title();
        String businessType = log.businessType();

        String className = joinPoint.getSignature().getDeclaringType().getName();
        String methodName = joinPoint.getSignature().getName();

        HttpServletRequest request = ((ServletRequestAttributes) Objects.requireNonNull(RequestContextHolder.getRequestAttributes())).getRequest();
        String requestMethod = request.getMethod();
        String requestURI = request.getRequestURI();

        LoginUser loginUser = AuthUtil.getLoginUserByRedis(redisTemplate);
        String operName = "";
        if (loginUser.getUser() != null) {
            operName = loginUser.getUser().getUserName();
        }

        int status = e == null ? 200 : 500;

        String errorMsg = e == null ? "" : e.getMessage();

        // 为参数赋值
        return OperLog.builder()
                .title(title)
                .businessType(businessType)
                .method(className + "." + methodName + "()")
                .requestMethod(requestMethod)
                .operName(operName)
                .operUrl(requestURI)
                .operIp(request.getRemoteAddr())
                .status(status)
                .errormsg(errorMsg)
                .opertime(new Date())
                .build();
    }

}
