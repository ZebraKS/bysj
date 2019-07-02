package com.bysj.web.thread;


import com.bysj.common.po.User;

public class UserThreadLocal {

	private static ThreadLocal<User> userThread = new ThreadLocal<>();
	
	public static void setUser(User user) {
		userThread.set(user);
	}
	
	public static User getUser() {
		return userThread.get();
	}
	
	//防止内存泄漏
	public static void remove() {
		userThread.remove();
	}
}
