package gin.service.util;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

///redis辅助类
@Service
public class RedisService {
    private final RedisTemplate<String, Object> redisTemplate;

    public RedisService(RedisTemplate<String, Object> redisTemplate, StringRedisTemplate stringRedisTemplate) {
        this.redisTemplate = redisTemplate;
        this.stringRedisTemplate = stringRedisTemplate;
    }
    private final StringRedisTemplate stringRedisTemplate;
    /**
     * 存值
     * @param key
     * @param value
     * @param seconds
     */
    public void setString(String key, String value, long seconds) {
        redisTemplate.opsForValue().set(key, value, seconds,TimeUnit.SECONDS);
    }

    public void setRawString(String key, String value, long seconds) {
        stringRedisTemplate.opsForValue().set(key, value, seconds,TimeUnit.SECONDS);
    }


    /**
     * 取值
     * @param key
     * @return
     */
    public Object getString(String key){
        return redisTemplate.opsForValue().get(key);
    }
    /**
     * 删除
     * @param key
     */
    public void deleteString(String key){
        redisTemplate.delete(key);
    }

    public boolean existsString(String key){
        return redisTemplate.hasKey(key);
    }
    public Long getExpire(String key) {
        return redisTemplate.getExpire(key, TimeUnit.SECONDS);
    }
    /**
     * 存值,无限市场缓存
     * @param userId
     */
    public void addOnlineUser(String userId) {
        redisTemplate.opsForSet().add("online::users",userId);
    }

    /**
     * 存值,无限市场缓存
     * @param userId
     */
    public void removeOnlineUser(String userId) {
        redisTemplate.opsForSet().remove("online::users",userId);
    }

    /**
     * 存值,无限市场缓存
     * @param userId
     */
    public Boolean isExistUsers(String userId) {
        return redisTemplate.opsForSet().isMember("online::users",userId);
    }

    /**
     * 获取当前的值
     */
    public Long countOnlineUser() {
        return redisTemplate.opsForSet().size("online::users");
    }
}
