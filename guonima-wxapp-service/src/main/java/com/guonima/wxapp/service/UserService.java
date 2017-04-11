package com.guonima.wxapp.service;


import com.guonima.wxapp.domain.User;

public interface UserService {

	/**
	 * 根据userId获取用户信息
	 *@param userId 
	 *@return 
	*/
	User getUserById(Long userId);
	
	/** 根据登录名获取用户信息 
	 *@param loginName 登录名 
	 *@return 
	*/
	User getUserByLoginName(String loginName);
}
