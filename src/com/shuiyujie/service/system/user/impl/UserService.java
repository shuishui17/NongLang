package com.shuiyujie.service.system.user.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.shuiyujie.dao.DaoSupport;
import com.shuiyujie.entity.system.User;
import com.shuiyujie.service.system.user.UserManager;

/**
 * @author 弄浪的鱼
 * @date 2017年5月3日
 */
@Service("userService")
public class UserService implements UserManager{

	@Resource(name = "daoSupport")
	private DaoSupport dao;
	
	/* (non-Javadoc)
	 * @see com.shuiyujie.service.system.user.UserManager#listAllUser()
	 */
	@Override
	public List<User> listAll() throws Exception {
		return (List<User>)dao.findForList("UserMapper.listAll", null);
	}
	
	
}
