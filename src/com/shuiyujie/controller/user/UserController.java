package com.shuiyujie.controller.user;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.shuiyujie.controller.base.BaseController;

/**
 * @author 弄浪的鱼
 * @date 2017年5月2日
 * 用户
 */
@Controller
@RequestMapping(value="/user")
public class UserController extends BaseController{

	@RequestMapping("/list")
	public ModelAndView list(){
		
		ModelAndView mv = this.getModelAndView();
		
		mv.setViewName("system/user/user_list");
		
		return mv;
	}
}
