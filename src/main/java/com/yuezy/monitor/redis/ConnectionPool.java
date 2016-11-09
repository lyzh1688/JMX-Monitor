package com.yuezy.monitor.redis;


import com.yuezy.monitor.config.DoImportProperties;
import com.yuezy.monitor.config.anno.ImportProperties;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import java.lang.reflect.InvocationTargetException;

@ImportProperties(properties = "conf/redis.properties",prefix = "redis")
public class ConnectionPool {

	private static ConnectionPool instance = new ConnectionPool();

	public static ConnectionPool getInstance() {

		return instance;
	}

	// Redis服务器IP
	private String addr;

	// Redis的端口号
	private int port;

	// 访问密码
	private String auth;

	// 可用连接实例的最大数目，默认值为8；
	// 如果赋值为-1，则表示不限制；如果pool已经分配了maxActive个jedis实例，则此时pool的状态为exhausted(耗尽)。
	private int maxActive = 8;

	// 控制一个pool最多有多少个状态为idle(空闲的)的jedis实例，默认值也是8。
	private int maxIdle = 20;

	// 等待可用连接的最大时间，单位毫秒，默认值为-1，表示永不超时。如果超过等待时间，则直接抛出JedisConnectionException；
	private int maxWait = 1000;

	private int timeout = 1000;

	// 在borrow一个jedis实例时，是否提前进行validate操作；如果为true，则得到的jedis实例均是可用的；
	private boolean testOnBorrow = false;

	private JedisPool jedisPool = null;

	public String getAddr() {
		return addr;
	}

	public void setAddr(String addr) {
		this.addr = addr;
	}

	public Integer getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public String getAuth() {
		return auth;
	}

	public void setAuth(String auth) {
		this.auth = auth;
	}

	public int getMaxActive() {
		return maxActive;
	}

	public void setMaxActive(int maxActive) {
		this.maxActive = maxActive;
	}

	public int getMaxIdle() {
		return maxIdle;
	}

	public void setMaxIdle(int maxIdle) {
		this.maxIdle = maxIdle;
	}

	public int getMaxWait() {
		return maxWait;
	}

	public void setMaxWait(int maxWait) {
		this.maxWait = maxWait;
	}

	public int getTimeout() {
		return timeout;
	}

	public void setTimeout(int timeout) {
		this.timeout = timeout;
	}

	public boolean isTestOnBorrow() {
		return testOnBorrow;
	}

	public void setTestOnBorrow(boolean testOnBorrow) {
		this.testOnBorrow = testOnBorrow;
	}

	/**
	 * 初始化Redis连接池
	 * 
	 * @throws InvocationTargetException
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 */
	private ConnectionPool() {

	}

	/**
	 * 获取Jedis实例
	 * 
	 * @return
	 */
	public synchronized Jedis getJedis() {
		try {
			if (jedisPool != null) {
				Jedis resource = jedisPool.getResource();
				return resource;
			} else {

				DoImportProperties.doImport(this, ConnectionPool.class);

				JedisPoolConfig config = new JedisPoolConfig();
				config.setMaxTotal(this.maxActive);
				config.setMaxIdle(this.maxIdle);
				config.setMaxWaitMillis(this.maxWait);
				config.setTestOnBorrow(this.testOnBorrow);
				jedisPool = new JedisPool(config, this.addr, this.port, this.timeout, this.auth);
				Jedis resource = jedisPool.getResource();
				return resource;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 释放jedis资源
	 * 
	 * @param jedis
	 */
	public void returnResource(final Jedis jedis) {
		if (jedis != null) {
			jedis.close();
		}
	}
}