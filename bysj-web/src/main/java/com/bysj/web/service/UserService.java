package com.bysj.web.service;


import com.bysj.common.po.User;

import java.util.Map;

public interface UserService {

	void saveUser(User user);

	String findUserByUP(User user);

	Map<String, String> findUserById(Long userId);
}
