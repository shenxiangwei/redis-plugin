package com.shenxiangwei.redisplugin.cache;

/**
 * Redis类
 */
public interface RedisClient {

    /**
     * 往缓存中存放值（永不过期）<br>
     * @param key 键
     * @param value 值
     * @author shenxiangwei
     * @date 2019/12/12 9:19 上午 */
    void set(String key, Object value);

    /**
     * 往缓存中存放值（一段时间内有效）<br>
     * 适用场景: 需要常驻缓存中的数据<br>
     * @param key 键
     * @param value 值
     * @param expire 过期时间
     * @author shenxiangwei
     * @date 2019/12/12 9:20 上午 */
    void set(String key, Object value, int expire);

    /**
     * 从redis获取值
     * @param key 键
     * @return 值
     * @author shenxiangwei
     * @date 2019/12/12 9:21 上午 */
    Object get(String key);

    /**
     * @param key 键
     * @param requiredType 类型
     * @return 值
     * @author shenxiangwei
     * @date 2019/12/12 9:22 上午 */
    <T> T get(String key, Class<T> requiredType);

    /**
     * 删除指定key
     * @param key 键
     */
    void delete(String key);

    void setRedisManager(RedisManager redisManager);

}