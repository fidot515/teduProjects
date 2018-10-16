package com.jt.test;

import java.util.HashSet;
import java.util.Set;

import org.junit.Test;

import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.JedisCluster;

public class TestCluster {
	
	@Test
	public void test01(){
		Set<HostAndPort> nodes = new HashSet<>();
		nodes.add(new HostAndPort("192.168.126.166", 7000));
		nodes.add(new HostAndPort("192.168.126.166", 7001));
		nodes.add(new HostAndPort("192.168.126.166", 7002));
		nodes.add(new HostAndPort("192.168.126.166", 7003));
		nodes.add(new HostAndPort("192.168.126.166", 7004));
		nodes.add(new HostAndPort("192.168.126.166", 7005));
		nodes.add(new HostAndPort("192.168.126.166", 7006));
		nodes.add(new HostAndPort("192.168.126.166", 7007));
		nodes.add(new HostAndPort("192.168.126.166", 7008));
		JedisCluster jedisCluster = new JedisCluster(nodes);
		
		
		jedisCluster.set("1805", "学习集群使用");
		System.out.println("获取数据:"+jedisCluster.get("1805"));
	}
}
