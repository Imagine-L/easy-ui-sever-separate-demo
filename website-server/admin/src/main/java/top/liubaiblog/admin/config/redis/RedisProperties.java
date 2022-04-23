package top.liubaiblog.admin.config.redis;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author 刘佳俊
 */
@Data
@ConfigurationProperties(prefix = "liubai.redis")
public class RedisProperties {
    private int minIdle;
    private int maxIdle;
    private int maxTotal;
    private String host;
    private int port;
}
