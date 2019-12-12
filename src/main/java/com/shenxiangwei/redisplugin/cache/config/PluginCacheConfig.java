package com.shenxiangwei.redisplugin.cache.config;

import com.shenxiangwei.redisplugin.cache.RedisClient;
import com.shenxiangwei.redisplugin.cache.impl.RedisManagerImpl;
import com.shenxiangwei.redisplugin.cache.impl.RedisClientImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

@Configuration
public class PluginCacheConfig {

    private static final Logger logger = LoggerFactory.getLogger(PluginCacheConfig.class);

    @Value("${redis.host}")
    private String host;

    @Value("${redis.port}")
    private int port;

    @Value("${redis.timeout}")
    private int timeout;

    @Value("${redis.pwd}")
    private String pwd;

    @Value("${redis.database:0}")
    private int database;

    @Value("${redis.pool.maxTotal}")
    private int poolMaxTotal;

    @Value("${redis.pool.maxIdle}")
    private int poolMaxIdle;

    @Value("${redis.pool.maxWaitMillis}")
    private int poolMaxWaitMillis;

    @Value("${redis.pool.testOnBorrow:false}")
    private boolean poolTestOnBorrow;

    @Value("${redis.pool.testOnReturn:false}")
    private boolean poolTestOnReturn;

    @Bean
    @Primary
    public JedisPoolConfig jedisPoolConfig() {
        printRedisConfig();

        JedisPoolConfig config = new JedisPoolConfig();
        config.setMaxTotal(poolMaxTotal);
        config.setMaxIdle(poolMaxIdle);
        config.setMaxWaitMillis(poolMaxWaitMillis);
        config.setTestOnBorrow(poolTestOnBorrow);
        config.setTestOnReturn(poolTestOnReturn);
        return config;
    }

    private void printRedisConfig() {
        logger.info("plugin-cache-config:redis:host:{}, port:{}, timeout:{}, pwd:{}, database:{}", host, port, timeout, pwd, database);
        logger.info("plugin-cache-config:reids:poolMaxTotal:{}, poolMaxIdle:{}, poolMaxWaitMillis:{}, poolTestOnBorrow:{}, poolTestOnReturn:{}", poolMaxTotal, poolMaxIdle, poolMaxWaitMillis, poolTestOnBorrow, poolTestOnReturn);
    }

    @Bean
    @Primary
    public JedisPool jedisPool() {
        return new JedisPool(jedisPoolConfig(), host, port, timeout, pwd, database);
    }

    @Bean
    @Primary
    public RedisManagerImpl redisManager() {
        RedisManagerImpl manager = new RedisManagerImpl();
        manager.setJedisPool(jedisPool());
        manager.setExpire(0);
        return manager;
    }

    @Bean
    @Primary
    public RedisClient cacheClient() {
        RedisClient redisClient = new RedisClientImpl();
        redisClient.setRedisManager(redisManager());
        return redisClient;
    }
}
