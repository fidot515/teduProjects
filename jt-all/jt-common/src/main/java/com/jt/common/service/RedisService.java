package com.jt.common.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisSentinelPool;
import redis.clients.jedis.ShardedJedis;
import redis.clients.jedis.ShardedJedisPool;

@Service
public class RedisService {
	
	//注入分片的池对象
	@Autowired(required=false)  //使用时才注入对象
	//public ShardedJedisPool shardedJedisPool;
	private JedisSentinelPool jedisSentinelPool;  //连接哨兵
	
	public void set(String key,String value){
		Jedis jedis = jedisSentinelPool.getResource();
		jedis.set(key, value);
		jedis.close();
	}
	
	public String get(String key){
		Jedis jedis = jedisSentinelPool.getResource();
		String result = jedis.get(key);
		jedis.close();
		return result;
	}
	
	
	/*public void set(String key,String value){
		ShardedJedis shardedJedis = shardedJedisPool.getResource();
		shardedJedis.set(key, value);
		shardedJedis.close();
	}
	
	public String get(String key){
		ShardedJedis shardedJedis = shardedJedisPool.getResource();
		String result = shardedJedis.get(key);
		shardedJedis.close();
		return result;
	}
	
	//设定超时时间的方法
	public void set(String key,String value,int seconds){
		ShardedJedis shardedJedis = shardedJedisPool.getResource();
		shardedJedis.setex(key, seconds, value);
		shardedJedis.close();
	}*/
}
