package com.junxia.demo.redis.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.util.StringUtils;
import redis.clients.jedis.JedisPoolConfig;

@Configuration
public class RedisConfig {
    @Value("${spring.data.redis.host}")
    private String host;

    @Value("${spring.data.redis.port}")
    private int port;

    @Value("${spring.data.redis.pass}")
    private String pass;

//    @Value("${spring.data.redis.default.db}")
//    private int db;

    @Value("${spring.data.redis.timeout}")
    private int timeout;

    @Value("${spring.data.redis.maxActive}")
    private int maxActive;

    @Value("${spring.data.redis.maxIdle}")
    private int maxIdle;

    @Value("${spring.data.redis.maxWait}")
    private int maxWait;

    @Value("${spring.data.redis.testOnBorrow}")
    private boolean testOnBorrow;

    @Bean
    public RedisConnectionFactory jedisConnectionFactory() {
        JedisPoolConfig poolConfig = new JedisPoolConfig();
//        poolConfig.setMaxTotal();
        poolConfig.setMaxIdle(maxIdle);
//        poolConfig.setMaxWaitMillis(maxWait);
        poolConfig.setTestOnBorrow(true);
//        poolConfig.setTestOnCreate(true);
//        poolConfig.setTestWhileIdle(true);
        JedisConnectionFactory jedisConnectionFactory = new JedisConnectionFactory(poolConfig);
        jedisConnectionFactory.setHostName(host);
//        jedisConnectionFactory.setDatabase(db);
        if (!StringUtils.isEmpty(pass)) {
            jedisConnectionFactory.setPassword(pass);
        }
        jedisConnectionFactory.setPort(port);
//        jedisConnectionFactory.setTimeout(timeout);
        //其他配置，可再次扩展
        return jedisConnectionFactory;
    }

    @Bean(name = "myRedisTemplate")
    @ConditionalOnMissingBean(name = {"myRedisTemplate"})
    public RedisTemplate<String, Object> redisTemplate() {
        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<String, Object>();
        redisTemplate.setConnectionFactory(jedisConnectionFactory());
        RedisSerializer stringSerializer = new StringRedisSerializer();
        redisTemplate.setKeySerializer(stringSerializer);
        redisTemplate.setValueSerializer(stringSerializer);
        redisTemplate.setHashKeySerializer(stringSerializer);
        //redisTemplate.setHashValueSerializer(stringSerializer);
        return redisTemplate;
    }
}
