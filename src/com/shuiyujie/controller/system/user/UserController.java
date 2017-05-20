package com.shuiyujie.controller.system.user;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.shuiyujie.controller.base.BaseController;
import com.shuiyujie.entity.Page;
import com.shuiyujie.entity.system.User;
import com.shuiyujie.service.system.user.UserManager;
import com.shuiyujie.util.PageData;

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
	public ModelAndView list(Page page) throws Exception{
		
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		page.setPd(pd); //用于分页 
		
		//列表所有的用户
		List<PageData> varList = userService.listAll(pd);
		
		mv = this.getModelAndView();
//		mv.addObject("userList", userList);
		mv.addObject("varList", varList);
		mv.addObject("pd", pd);
		mv.setViewName("system/user/user_list");
		
		return mv;
	}
}
