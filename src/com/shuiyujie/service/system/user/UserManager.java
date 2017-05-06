package com.shuiyujie.service.system.user;

import java.util.List;

import com.shuiyujie.entity.system.User;

/**用户接口类
 * @author 弄浪的鱼
 * @date 2017年5月3日
 */
public interface UserManager {
	
	/**
	 * 查找所有用户
	 * @return 用户列表
	 * @throws Exception
	 */
	public List<User> listAll() throws Exception;
}
