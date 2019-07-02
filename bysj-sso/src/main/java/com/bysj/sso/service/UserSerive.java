package com.bysj.sso.service;


import com.bysj.common.po.User;

public interface UserSerive {

	boolean findCheckUser(String param, Integer type);

	void saveUser(User user);

	String findUserByUP(User user);

    User findUserById(Long id);

    void changeUser(User user);
}
