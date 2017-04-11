package com.guonima.wxapp.service;

import com.guonima.wxapp.config.Environment;
import com.guonima.wxapp.dao.DaoSupport;
import com.guonima.wxapp.domain.User;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;


/***
 * 这个类就跟平时一样调用dao增删改数据库中数据，如下我自己调用的 DaoSupport
 * @author Carry xie
 *
 */
@Service("userServiceImpl")
public class UserServiceImpl implements UserService {

    @Resource(name = "daoSupport")
    private DaoSupport dao;

	@Override
	public User getUserById(Long userId) {
		System.out.println(Environment.BAIDU_AK);

		User user = new User();
		user.setBirthday(new Date());
		user.setEmail("100000@qq.com");
		user.setGender("男");
		user.setLoginName("guonima");
		user.setPassword("123456");
		user.setUserId(1111111L);
		user.setUserName("GuoNiMei");
		return user;
		// TODO Auto-generated method stub
//		 return (User)dao.findForObject("UserMapper.findByUiId", userId);
	}

	@Override
	public User getUserByLoginName(String loginName) {
		// TODO Auto-generated method stub
		return (User)dao.findForObject("UserMapper.getUserByLoginName",loginName);
	}

}
