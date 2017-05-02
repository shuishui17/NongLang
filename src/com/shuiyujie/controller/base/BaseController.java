package com.shuiyujie.controller.base;

import org.springframework.web.servlet.ModelAndView;

/**
 * @author 弄浪的鱼
 * @date 2017年5月2日
 */
public class BaseController {

	/**得到ModelAndView
	 * @return
	 */
	public ModelAndView getModelAndView(){
		return new ModelAndView();
	}
}
