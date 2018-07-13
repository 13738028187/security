package com.hzdy.redis.dao;

import java.util.Set;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
@Repository
public class RedisOvsOperate {
	@Autowired
	private JedisPool jedisPool;// 注入JedisPool

	public void SADD(String key, String members) {

		Jedis jedis = jedisPool.getResource();
        //存入键值对
        jedis.sadd(key, members);
        //回收ShardedJedis实例
        jedis.close();
	}

	public void DEL(String key) {
		Jedis jedis = jedisPool.getResource();
        jedis.del(key);
        //回收ShardedJedis实例
        jedis.close();

	}

	public Set<String> SMEMEBERS(String key) {
		System.out.println("获取成功");
		Jedis jedis = jedisPool.getResource();
		Set<String> lists= jedis.smembers(key);
        //回收ShardedJedis实例
        jedis.close();
        return lists;
	}

	public void SREM(String key, String members) {
		Jedis jedis = jedisPool.getResource();
		jedis.srem(key, members);
        //回收ShardedJedis实例
        jedis.close();
	}

}
