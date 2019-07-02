package com.bysj.sso.service;

import java.util.Date;
import java.util.List;

import com.bysj.common.po.User;
import com.bysj.sso.mapper.UserMapper;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.transaction.annotation.Transactional;
import redis.clients.jedis.JedisCluster;

@Service
@Transactional
public class UserServiceImpl implements UserSerive {

	@Autowired
	UserMapper usrMapper;

	@Autowired
	private JedisCluster jedisCluster;
	private ObjectMapper mapper = new ObjectMapper();

	@Override
	public boolean findCheckUser(String param, Integer type) {
		User user = new User();
		switch (type) {
		case 1:
			user.setUsername(param);
			break;
		case 2:
			user.setPhone(param);
			break;
		case 3:
			user.setEmail(param);
			break;
		}
		int count = usrMapper.selectCount(user);
		return count == 0 ? false : true;
	}

	@Override
	public void saveUser(User user) {
		// TODO Auto-generated method stub
		user.setEmail(user.getPhone()); // 暂时用电话号码代替邮箱
		user.setCreated(new Date());

		user.setUpdated(user.getCreated());
		System.out.println(user.toString());
		usrMapper.insert(user);
	}

	@Override
	public String findUserByUP(User user) {
		// TODO Auto-generated method stub
		List<User> select = usrMapper.select(user);
		if(select.size() == 0) {
			//没找到用户
			return null;
		}
		String token = "JT_TICKET_" + System.currentTimeMillis() + user.getUsername();
		token = DigestUtils.md5Hex(token);
		try {
			String userJSON = mapper.writeValueAsString(select.get(0));
			jedisCluster.setex(token, 3600 * 24 *7, userJSON);
			} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			throw new RuntimeException();
		}
		return token;
	}

	@Override
	public User findUserById(Long id) {
		return usrMapper.selectByPrimaryKey(id);
	}

	@Override
	public void changeUser(User user) {
		if(null != user.getPhone())
			usrMapper.updateByPrimaryKeySelective(user);
		if(null != user.getPassword()){
			String md5Pass = DigestUtils.md5Hex(user.getPassword());
			user.setPassword(md5Pass);
			usrMapper.updateByPrimaryKeySelective(user);
		}
	}

}
