package top.liubaiblog.admin.config.redis;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.exceptions.JedisException;
import top.liubaiblog.admin.config.CustomObjectMapper;

import java.util.Optional;
import java.util.Set;

/**
 * @author 刘佳俊
 */
@Slf4j
public class RedisTemplate {

    @Autowired
    private JedisPool jedisPool;

    @Autowired
    private CustomObjectMapper objectMapper;

    /**
     * 存储普通字符串
     *
     * @param key
     * @param value
     * @param expire
     */
    public String set(String key, String value, Long expire) {
        Jedis jedis = jedisPool.getResource();
        String returnValue = null;
        try {

            if (expire <= 0) {
                // 如果操作成功会返回“ok”字符串，
                returnValue = jedis.set(key, value);
            } else {
                returnValue = jedis.setex(key, expire, value);
            }
        } catch (JedisException e) {
            log.error("Redis execution error !", e);
        } finally {
            jedis.close();
        }
        return returnValue;
    }

    /**
     * 根据key获得一个字符串数据
     *
     * @param key
     * @return
     */
    public String get(String key) {
        Jedis jedis = jedisPool.getResource();
        String returnValue = null;
        try {
            returnValue = jedis.get(key);
        } catch (JedisException e) {
            log.error("Redis execution error !", e);
        } finally {
            jedis.close();
        }
        return returnValue;
    }

    /**
     * 存储对象类型的数据
     *
     * @param key
     * @param object
     * @param expire
     * @return
     */
    public String setObject(String key, Object object, Long expire) {
        Jedis jedis = jedisPool.getResource();
        String returnValue = null;
        try {
            // 讲object进行序列化
            String objValue = objectMapper.writeValueAsString(object);
            if (expire <= 0) {
                returnValue = jedis.set(key, objValue);
            } else {
                // 如果操作成功会返回“ok”字符串，
                returnValue = jedis.setex(key, expire, objValue);
            }
        } catch (JedisException | JsonProcessingException e) {
            log.error("Redis execution error !", e);
        } finally {
            jedis.close();
        }
        return returnValue;
    }

    /**
     * 获取对象类型的数据
     *
     * @param key
     * @param valueType
     * @param <T>
     * @return
     */
    public <T> Optional<T> getObject(String key, Class<T> valueType) {


        Jedis jedis = jedisPool.getResource();
        String returnValue = null;
        try {
            // 如果操作成功会返回“ok”字符串，
            String objectValue = jedis.get(key);

            return objectValue == null ? Optional.empty() : Optional.of(objectMapper.readValue(objectValue, valueType));
        } catch (JedisException | JsonProcessingException e) {
            log.error("Redis execution error !", e);
        } finally {
            jedis.close();
        }
        return Optional.empty();
    }

    public <T> Optional<T> getObject(String key, TypeReference<T> typeReference) {

        Jedis jedis = jedisPool.getResource();
        String returnValue = null;
        try {
            // 如果操作成功会返回“ok”字符串，
            String objectValue = jedis.get(key);
            return objectValue == null ? Optional.empty() : Optional.of(objectMapper.readValue(objectValue, typeReference));
        } catch (JedisException | JsonProcessingException e) {
            log.error("Redis execution error !", e);
        } finally {
            jedis.close();
        }
        return Optional.empty();
    }


    /**
     * 删除多个key
     *
     * @param key
     * @return
     */
    public Long remove(String... key) {
        Jedis jedis = jedisPool.getResource();
        String returnValue = null;
        try {
            // 如果操作成功会返回“ok”字符串，
            return jedis.del(key);
        } catch (JedisException e) {
            log.error("Redis execution error !", e);
        } finally {
            jedis.close();
        }
        return -1L;
    }

    public Long lpush(String key, String... value) {
        Jedis jedis = jedisPool.getResource();
        long pos = -1;
        try {
            // 如果操作成功会返回“ok”字符串，
            pos = jedis.lpush(key, value);
        } catch (JedisException e) {
            log.error("Redis execution error !", e);
        } finally {
            jedis.close();
        }
        return pos;
    }

    public Optional<String> rpop(String key) {
        Jedis jedis = jedisPool.getResource();
        Optional<String> returnValue = Optional.empty();
        try {
            // 如果操作成功会返回“ok”字符串，
            String value = jedis.rpop(key);
            System.out.println("---------" + value);
            returnValue = value == null ? Optional.empty() : Optional.of(value);
        } catch (JedisException e) {
            log.error("Redis execution error !", e);
        } finally {
            jedis.close();
        }
        return returnValue;
    }

    public long llen(String key) {
        Jedis jedis = jedisPool.getResource();
        long llen = -1;
        try {
            // 如果操作成功会返回“ok”字符串，
            llen = jedis.llen(key);
        } catch (JedisException e) {
            log.error("Redis execution error !", e);
        } finally {
            jedis.close();
        }
        return llen;
    }


    // 增加key的持续时间
    public long expire(String key, long expire) {
        Jedis jedis = jedisPool.getResource();
        long exp = -1L;
        try {
            // 从redis中获取
            exp = jedis.expire(key, expire);
        } catch (JedisException e) {
            e.printStackTrace();
        } finally {
            jedis.close();
        }
        return exp;
    }

    // 根据pattern查询key
    public Set<String> keys(String pattern) {
        Jedis jedis = jedisPool.getResource();
        Set<String> keys = null;
        try {
            // 从redis中获取
            keys = jedis.keys(pattern);
        } catch (JedisException e) {
            e.printStackTrace();
        } finally {
            jedis.close();
        }
        return keys;
    }

}