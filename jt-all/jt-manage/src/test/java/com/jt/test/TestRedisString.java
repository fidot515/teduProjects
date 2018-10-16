package com.jt.test;

import java.util.Map;

import org.junit.Test;

import redis.clients.jedis.Jedis;

public class TestRedisString {
	
	/**
	 * 步骤:
	 * 	1.连接Redis  IP:端口
	 */
	@Test
	public void test01(){
		Jedis jedis = new Jedis("192.168.126.166", 6379);
		jedis.set("1805", "好好学习,早日找个异性朋友");
		System.out.println(jedis.get("1805"));
	}
	
	
	//操作Hash
	@Test
	public void test02(){
		Jedis jedis = new Jedis("192.168.126.166", 6379);
		jedis.hset("user", "id", "100");
		jedis.hset("user", "name", "1805班");
		
		Map<String,String> userMap = jedis.hgetAll("user");
		System.out.println(userMap);
	}
	
	@Test
	public void testList(){
		Jedis jedis = new Jedis("192.168.126.166", 6379);
		jedis.lpush("1805List", "1,2,2,2,2,2","2","3");
		System.out.println(jedis.rpop("1805List"));
		System.out.println(jedis.rpop("1805List"));
	}
}
