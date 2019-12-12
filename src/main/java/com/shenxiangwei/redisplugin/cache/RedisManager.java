package com.shenxiangwei.redisplugin.cache;

import redis.clients.jedis.JedisPool;

import java.util.Set;

/** <p>
 * 标题: 通用缓存底层接口
 * @author sxw
 * @date 2019-5-15 下午7:18:04 */
public interface RedisManager {

	/** <p>
	 * 方法名称：根据key获取value值
	 * @param key 键
	 * @return 结果
	 * @author sxw
	 * @date 2019-5-15 下午7:30:22 */
    byte[] get(byte[] key);

	/** <p>
	 * 方法名称：设置一个不过期的记录
	 * @param key 键
	 * @param value 值
	 * @return 值
	 * @author sxw
	 * @date 2019-5-15 下午7:31:08 */
    byte[] set(byte[] key, byte[] value);

	/** <p>
	 * 方法名称：设置一个带失效时间的记录
	 * @param key 键
	 * @param value 值
	 * @param expire 过期时间
	 * @return 值
	 * @author sxw
	 * @date 2019-5-15 下午7:32:01 */
    byte[] set(byte[] key, byte[] value, int expire);

	/** <p>
	 * 方法名称：根据key删除记录
	 * @param key 键
	 * @author sxw
	 * @date 2019-5-15 下午7:32:31 */
    void del(byte[] key);

	/**
	 * 方法名称：删除当前库中的所有元素
	 * @author sxw
	 * @date 2019-5-15 下午7:49:10 */
    void flushdb();

	/** <p>
	 * 方法名称：根据前缀查询对应的key
	 * @param pattern 模式
	 * @return key集合
	 * @author sxw
	 * @date 2019-5-15 下午8:00:36 */
    Set<byte[]> keys(String pattern);

	/** 获取tair中key的数量<br>
	 * @return 数量
	 * @author sxw
	 * @date 2019-5-26 上午12:55:08 */
    Long dbSize();
}
