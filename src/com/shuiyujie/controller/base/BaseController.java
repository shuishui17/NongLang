package com.shuiyujie.controller.base;

import org.springframework.web.servlet.ModelAndView;

/**
 * @author 弄浪的鱼
 * @date 2017/5/2
 */
public class BaseController {

	/**返回ModelAndView
	 * @return
	 */
	public ModelAndView getModelAndView(){
		return new ModelAndView();
	}
}
