package com.shuiyujie.interceptor;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.shuiyujie.entity.system.User;
import com.shuiyujie.util.Const;
import com.shuiyujie.util.Jurisdiction;

/**
 * 登录过滤，权限校验
 * 
 * @author 弄浪的鱼
 * @date 2017年5月18日
 */
public class LoginHandlerInterceptor extends HandlerInterceptorAdapter {

	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws IOException {

		String path = request.getServletPath();

		if (path.matches(Const.NO_INTERCEPTOR_PATH)) {
			return true;
		} else {
			// 获取使用该 session 的用户
			User user = (User) Jurisdiction.getSession().getAttribute(
					Const.SESSION_USER);
			if (user != null) {
				path = path.substring(1,path.length());
				boolean b = Jurisdiction.hasJurisdiction(path); //访问权限校验
				if(!b){
					response.sendRedirect(request.getContextPath() + Const.LOGIN);
				}
						
			} else {
				// 登陆过滤
				response.sendRedirect(request.getContextPath() + Const.LOGIN);
				return false;
			}
		}

		return false;

	}
}
