package top.liubaiblog.admin.config.thread;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.concurrent.TimeUnit;

/**
 * @author 刘佳俊
 */
@Data
@ConfigurationProperties(prefix = "liubai.thread")
public class ThreadPoolProperties {

    private int corePoolSize = 20;
    private int maximumPoolSize = 50;
    private long keepAliveTime = 120;
    private TimeUnit unit = TimeUnit.SECONDS;
    private int blockingQueueSize = 100;

}
