package com.junxia.demo.redis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Repository
public class RedisDao {
    @Autowired
    @Qualifier("myRedisTemplate")
    RedisTemplate<String, Object> redisTemplate;

    public void saveSingleHash(String key, String hkey, String value) {
        HashOperations<String, String, String> hash = redisTemplate.opsForHash();
        hash.put(key, hkey, value);
    }

    public void saveRedisMap(String key, Map<String, String> map) {
        HashOperations<String, String, String> hash = redisTemplate.opsForHash();
        hash.putAll(key, map);
    }

    public String getSingleHash(String key, String hkey) {
        HashOperations<String, String, String> hash = redisTemplate.opsForHash();
        return hash.get(key, hkey);
    }

    public void deleteSingleHash(String key, String hkey) {
        redisTemplate.opsForHash().delete(key, hkey);
    }

    public void deleteAllHash(String hkey) {
        redisTemplate.delete(hkey);
    }

    public Long increment(String key, Integer timeExpire) {
        Long value = redisTemplate.opsForValue().increment(key, 1);
        if (value != null) {
            redisTemplate.expire(key, timeExpire, TimeUnit.SECONDS);
        }
        return value;
    }

    /**
     * 没有有效时间设置的计数
     * @param key
     * @return
     */
    public Long incrementStep(String key,Integer step) {
        return redisTemplate.opsForValue().increment(key, step);
    }

    public Long hashLen(String key) {
        Long size = redisTemplate.opsForHash().size(key);
        return size;
    }

    public  boolean exists(String key) {
        return redisTemplate.hasKey(key);
    }

    public void setValue(String key,String value){
        redisTemplate.opsForValue().set(key,value);
    }

    /*public void setJson(String key, JSONObject jsonObject){
        redisTemplate.opsForValue().set(key,jsonObject);
    }*/
    /**
     * 设置缓存数据的超时时间
     * @param key
     * @param value
     * @param timeExpire
     */
    public void setValue(String key,String value,Integer timeExpire) {
        redisTemplate.opsForValue().set(key,value);
        redisTemplate.expire(key, timeExpire, TimeUnit.SECONDS);
    }

    public void saveRedisMaps(String key, Map<String, String> map,Integer timeExpire) {
        HashOperations<String, String, String> hash = redisTemplate.opsForHash();
        hash.putAll(key, map);
        redisTemplate.expire(key, timeExpire, TimeUnit.SECONDS);
    }

    public Object getValue(String key){
        return   redisTemplate.opsForValue().get(key);
    }
    public List<Object> getValues(List<String> keys){
        return redisTemplate.opsForValue().multiGet(keys);
    }
}
