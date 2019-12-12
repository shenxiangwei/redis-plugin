package com.shenxiangwei.redisplugin.cache.impl;

import com.shenxiangwei.redisplugin.cache.RedisManager;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import java.util.Set;

/** redis管理类
 * 
 * @author sxw
 * */
public class RedisManagerImpl implements RedisManager {

	/** 主机名称或者ip地址 */
	private String    host      = "127.0.0.1";

	/** 端口 */
	private int       port      = 6379;

	/** 失效时间 0为永不失效 */
	private int       expire    = 0;

	/** 连接超时时间 会自动重连 */
	private int       timeout   = 0;

	/** 密码 */
	private String    password  = "pass";

	/** 连接池 */
	private JedisPool jedisPool = null;

	public RedisManagerImpl() {

	}

	public RedisManagerImpl(String host, int port, String password) {
		this.host = host;
		this.port = port;
		this.password = password;
	}

	public RedisManagerImpl(String host, int port, String password, int expire, int timeout) {
		this.host = host;
		this.port = port;
		this.password = password;
		this.expire = expire;
		this.timeout = timeout;
	}

	/** 初始化方法 */
	public void init() {
		if (jedisPool == null) {
			if (password != null && !"".equals(password)) {
				jedisPool = new JedisPool(new JedisPoolConfig(), host, port, timeout, password);
			}
			else if (timeout != 0) {
				jedisPool = new JedisPool(new JedisPoolConfig(), host, port, timeout);
			}
			else {
				jedisPool = new JedisPool(new JedisPoolConfig(), host, port);
			}

		}
	}

	@Override
	public byte[] get(byte[] key) {
		byte[] value = null;
		Jedis jedis = null;
		try {
			jedis = jedisPool.getResource();
			value = jedis.get(key);
		}
		finally {
			if (jedis != null) {
				jedis.close();
			}
		}
		return value;
	}

	@Override
	public byte[] set(byte[] key, byte[] value) {
		Jedis jedis = null;
		try {
			jedis = jedisPool.getResource();
			jedis.set(key, value);
			if (this.expire != 0) {
				jedis.expire(key, this.expire);
			}
		}
		finally {
			if (jedis != null) {
				jedis.close();
			}
		}
		return value;
	}

	@Override
	public byte[] set(byte[] key, byte[] value, int expire) {
		Jedis jedis = null;
		try {
			jedis = jedisPool.getResource();
			jedis.set(key, value);
			if (expire != 0) {
				jedis.expire(key, expire);
			}
		}
		finally {
			if (jedis != null) {
				jedis.close();
			}
		}
		return value;
	}

	@Override
	public void del(byte[] key) {
		Jedis jedis = null;
		try {
			jedis = jedisPool.getResource();
			jedis.del(key);
		}
		finally {
			if (jedis != null) {
				jedis.close();
			}
		}
	}

	/** flush */
	@Override
	public void flushdb() {
		Jedis jedis = null;
		try {
			jedis = jedisPool.getResource();
			jedis.flushDB();
		}
		finally {
			if (jedis != null) {
				jedis.close();
			}
		}
	}

	/** size */
	@Override
	public Long dbSize() {
		Long dbSize = 0L;
		Jedis jedis = null;
		try {
			jedis = jedisPool.getResource();
			dbSize = jedis.dbSize();
		}
		finally {
			if (jedis != null) {
				jedis.close();
			}
		}
		return dbSize;
	}

	@Override
	public Set<byte[]> keys(String pattern) {
		Set<byte[]> keys = null;
		Jedis jedis = null;
		try {
			jedis = jedisPool.getResource();
			keys = jedis.keys(pattern.getBytes());
		}
		finally {
			if (jedis != null) {
				jedis.close();
			}
		}
		return keys;
	}

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public int getExpire() {
		return expire;
	}

	public void setExpire(int expire) {
		this.expire = expire;
	}

	public int getTimeout() {
		return timeout;
	}

	public void setTimeout(int timeout) {
		this.timeout = timeout;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public JedisPool getJedisPool() {
		return jedisPool;
	}

	public void setJedisPool(JedisPool jedisPool) {
		this.jedisPool = jedisPool;
	}

}
