package com.hzdy.redis.service;

import java.util.Set;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.hzdy.redis.dao.IpBlack;
import com.hzdy.redis.dao.RedisOvsOperate;

@Service
public class IpBlackService {
	@Resource
	RedisOvsOperate redisOvsOpreate;
	
	public void save(String key, String member) {
		redisOvsOpreate.SADD(key,member);
	}
	public void delete(String key) {
		redisOvsOpreate.DEL(key);
	}
	public void remove(String key,String member) {
		redisOvsOpreate.SREM(key, member);
	}
	public IpBlack queryAll(String key){
		IpBlack ib=new IpBlack();
		ib.setKey(key);
		ib.setVlaue(redisOvsOpreate.SMEMEBERS(key));
		return ib;
	}
}
