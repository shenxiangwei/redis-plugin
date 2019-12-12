package com.shenxiangwei.redisplugin.cache.impl;


import com.shenxiangwei.redisplugin.cache.RedisClient;
import com.shenxiangwei.redisplugin.cache.RedisManager;
import com.shenxiangwei.redisplugin.util.SerializeUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;



public class RedisClientImpl implements RedisClient {

    private final static Logger log = LoggerFactory.getLogger(RedisClientImpl.class);

    /**
     * 缓存底层接口
     */
    private RedisManager redisManager;

    /**
     * 返回 缓存底层接口
     *
     * @return 缓存底层接口
     */
    public RedisManager getRedisManager() {
        return redisManager;
    }

    /**
     * 设置 缓存底层接口
     *
     * @param redisManager 缓存底层接口
     */
    @Override
    public void setRedisManager(RedisManager redisManager) {
        this.redisManager = redisManager;
    }

    @Override
    public void set(String key, Object value) {
        redisManager.set(key.getBytes(), SerializeUtil.objectToBytes(value));

    }


    @Override
    public void set(String key, Object value, int expire) {
        redisManager.set(key.getBytes(), SerializeUtil.objectToBytes(value), expire);
    }

    @Override
    public Object get(String key) {

        byte[] value = redisManager.get(key.getBytes());

        if (value == null) {
            return null;
        }
        return SerializeUtil.bytesToObject(value);
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T> T get(String key, Class<T> requiredType) {
        byte[] value = redisManager.get(key.getBytes());

        if (value == null) {
            return null;
        }
        return (T) SerializeUtil.bytesToObject(value);
    }

    @Override
    public void delete(String key) {
        redisManager.del(key.getBytes());
    }

}

