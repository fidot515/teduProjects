package com.jt.common.facotry;

import java.util.HashSet;
import java.util.Properties;
import java.util.Set;

import org.springframework.beans.factory.FactoryBean;
import org.springframework.core.io.Resource;

import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.JedisPoolConfig;

public class JedisClusterFactory implements FactoryBean<JedisCluster>{

	private JedisPoolConfig poolConfig; //注入配置文件
	private String redisNodePrefix;		//注入配置前缀
	private Resource propertySource;	//注入redis配置文件
	
	@Override
	public JedisCluster getObject() throws Exception {
		
		Set<HostAndPort> nodes = getNodes();
		JedisCluster jedisCluster = new JedisCluster(nodes, poolConfig);
		return jedisCluster;
	}
	
	/**
	 * 1.定义Set集合
	 * 2.获取配置文件中的IP和端口
	 * 3.封装Set集合
	 * @return
	 */
	public Set<HostAndPort> getNodes(){
		Set<HostAndPort> nodes = new HashSet<>();
		try {
			//获取Property数据
			Properties properties = new Properties();
			properties.load(propertySource.getInputStream());
			
			//获取Redis节点数据
			for (Object key : properties.keySet()) {
				String strKey = (String) key;
				//判断当前遍历的key是否为redis节点的key
				if(strKey.startsWith(redisNodePrefix)){
					//IP:端口号
					String value = properties.getProperty(strKey);
					String[] args = value.split(":");
					HostAndPort hostAndPort = 
					new HostAndPort(args[0],Integer.parseInt(args[1]));
					nodes.add(hostAndPort);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return nodes;
	}
	
	@Override
	public Class<?> getObjectType() {
		
		return JedisCluster.class;
	}

	@Override
	public boolean isSingleton() {
		
		return false;
	}

	public JedisPoolConfig getPoolConfig() {
		return poolConfig;
	}

	public void setPoolConfig(JedisPoolConfig poolConfig) {
		this.poolConfig = poolConfig;
	}

	public String getRedisNodePrefix() {
		return redisNodePrefix;
	}

	public void setRedisNodePrefix(String redisNodePrefix) {
		this.redisNodePrefix = redisNodePrefix;
	}

	public Resource getPropertySource() {
		return propertySource;
	}

	public void setPropertySource(Resource propertySource) {
		this.propertySource = propertySource;
	}
}
