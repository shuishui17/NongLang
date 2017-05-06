package com.shuiyujie.controller.user;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.shuiyujie.controller.base.BaseController;
import com.shuiyujie.entity.system.User;
import com.shuiyujie.service.system.user.UserManager;

/**
 * @author Ū�˵���
 * @date 2017��5��2��
 * �û�
 */
@Controller
@RequestMapping(value="/user")
public class UserController extends BaseController{

	@Resource(name="userService")
	private UserManager userService;
	
	@RequestMapping("/list")
	public ModelAndView list() throws Exception{
		
		//列表所有的用户
		List<User> userList = userService.listAll();
		
		ModelAndView mv = this.getModelAndView();
		mv.addObject("userList", userList);
		mv.setViewName("system/user/user_list");
		
		return mv;
	}
}
