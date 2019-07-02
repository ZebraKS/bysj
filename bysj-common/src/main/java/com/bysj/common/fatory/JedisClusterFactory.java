package com.bysj.common.fatory;

import java.io.IOException;
import java.util.HashSet;
import java.util.Properties;
import java.util.Set;

import org.springframework.beans.factory.FactoryBean;
import org.springframework.core.io.Resource;

import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.JedisPoolConfig;

public class JedisClusterFactory implements FactoryBean<JedisCluster> {

	// 导入配置文件加载节点信息
	private Resource propertySource; // 导入配置文件
	private JedisPoolConfig poolConfig; // 注入链接池
	private String redisNodePrefix; // 节点前缀

	@Override
	public JedisCluster getObject() throws Exception {
		// TODO Auto-generated method stub
		Set<HostAndPort> nodes = getNodes();
		return new JedisCluster(nodes,poolConfig);
	}

	private Set<HostAndPort> getNodes() {
		Set<HostAndPort> nodes = new HashSet<>();
		Properties properties = new Properties();
		try {
			properties.load(propertySource.getInputStream());
			for (Object key : properties.keySet()) {
				String strKey = (String) key;
				if(strKey.startsWith(redisNodePrefix)) {
					String value = properties.getProperty(strKey);
					String[] args = value.split(":");
					nodes.add(new HostAndPort(args[0], Integer.parseInt(args[1])));
				}
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return nodes;
	}

	@Override
	public Class<?> getObjectType() {
		// TODO Auto-generated method stub
		return JedisCluster.class;
	}

	@Override
	public boolean isSingleton() {
		// TODO Auto-generated method stub
		return false;
	}

	public Resource getPropertySource() {
		return propertySource;
	}

	public void setPropertySource(Resource propertySource) {
		this.propertySource = propertySource;
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

}
