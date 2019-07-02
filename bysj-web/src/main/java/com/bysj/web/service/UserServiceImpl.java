package com.bysj.web.service;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import com.bysj.common.po.User;
import com.bysj.common.service.HttpClientService;
import com.bysj.common.vo.SysResult;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private HttpClientService httpClient;
	
	private ObjectMapper mapper = new ObjectMapper();

	@Override
	public void saveUser(User user) {
		// TODO Auto-generated method stub
		String url = "http://www.bssso.com/user/register";
		Map<String, String> params = new HashMap<>();
		params.put("username", user.getUsername());
		String md5Pass = DigestUtils.md5Hex(user.getPassword());
		params.put("password", md5Pass);
		params.put("phone", user.getPhone());
		params.put("email", user.getEmail());
		String resultJSON = null;
		try {
			resultJSON = httpClient.doPost(url, params);
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		//判断入库是否成功
		try {
			SysResult sysResult = mapper.readValue(resultJSON, SysResult.class);
			if(sysResult.getStatus() != 200) {
				//抛出异常
				throw new RuntimeException();
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new RuntimeException();
		}
	}

	@Override
	public String findUserByUP(User user) {
		// 把user传到后台 查询
		String token = null;
		String url = "http://www.bssso.com/user/login";
		String md5Pass = DigestUtils.md5Hex(user.getPassword());
		Map<String, String> params = new HashMap<>();
		params.put("username", user.getUsername());
		params.put("password", md5Pass);
		try {
			String resultJSON = httpClient.doPost(url,params);
			SysResult sysResult = mapper.readValue(resultJSON,SysResult.class);
			if(sysResult.getStatus() == 200) {
				token = (String) sysResult.getData();
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new RuntimeException();
		}
		return token;
	}

	@Override
	public Map<String, String> findUserById(Long userId) {
		String url = "http://www.bssso.com/user/myInformation";
		User user = null;
		Map<String, String> params = new HashMap<>();
		Map<String,String> map = new HashMap<>();
		params.put("id", userId.toString());
		try {
			String resultJSON = httpClient.doPost(url,params);
			SysResult sysResult = mapper.readValue(resultJSON,SysResult.class);
			if(sysResult.getStatus() == 200) {
				map = (Map<String,String>) sysResult.getData();
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			//throw new RuntimeException();
		}
		return map;
	}

}
