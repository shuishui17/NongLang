package com.shuiyujie.controller.system.login;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.shuiyujie.controller.base.BaseController;
import com.shuiyujie.service.system.user.UserManager;
import com.shuiyujie.util.PageData;

/**
 * 总入口
 * @author 弄浪的鱼
 * @date 2017年5月18日
 */
@Controller
public class LoginController extends BaseController {
	
	@Resource(name="userService")
	private UserManager userService;
	
	/**
	 * 访问登录页面
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/login_toLogin")
	public ModelAndView toLogin() throws Exception{
		
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		//pd.put("SYSNAME", Tools.readTxtFile(Const.SYSNAME)); //读取系统名称
		mv.setViewName("system/index/login");
		mv.addObject("pd",pd);
		return mv;
	}
}
