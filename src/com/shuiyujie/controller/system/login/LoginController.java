package com.shuiyujie.controller.system.login;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.shuiyujie.controller.base.BaseController;
import com.shuiyujie.entity.system.User;
import com.shuiyujie.service.system.user.UserManager;
import com.shuiyujie.util.AppUtil;
import com.shuiyujie.util.Const;
import com.shuiyujie.util.DateUtil;
import com.shuiyujie.util.Jurisdiction;
import com.shuiyujie.util.PageData;
import com.shuiyujie.util.Tools;

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
	
	@RequestMapping(value="login_login",produces="application/json;charset=UTF-8")//接收 json 格式数据 && 编码格式为 UTF-8
	@ResponseBody
	public Object login() throws Exception{
		
		Map<String,String> map = new HashMap<String,String>();
		PageData pd = new PageData();
		pd = this.getPageData();// 获取页面参数，封装在一个 map 中
		String errInfo = ""; // 返回消息
		String[] KEYDATA = pd.getString("KEYDATA").split(",");
		if(KEYDATA != null && KEYDATA.length == 3){
			// 获取 shiro 的 session:SecurityUtils.getSubject().getSession();
			Session session = Jurisdiction.getSession();
			String sessionCode = (String) session.getAttribute(Const.SESSION_SECURITY_CODE); //获取session 中的验证码
			String code = KEYDATA[2]; // 输入的验证码
			if(code == null || code.equals("")){
				errInfo = "nullcode"; // 验证码为空
			}else{
				String USERNAME = KEYDATA[0];
				String PASSWORD = KEYDATA[1];
				pd.put("USERNAME", USERNAME);
				if(Tools.notEmpty(sessionCode) && sessionCode.equals(code)){ // 验证码校验
					//密码加密
					String passwd = new SimpleHash("SHA-1", USERNAME, PASSWORD).toString();
					pd.put("PASSWORD", passwd);
					pd = userService.getUserByNameAndPwd(pd);
					if(pd != null){
						pd.put("LAST_LOGIN",DateUtil.getTime().toString());
						userService.updateLastLogin(pd);
						User user = new User();
						user.setUSER_ID(pd.getString("USER_ID"));
						user.setUSERNAME(pd.getString("USERNAME"));
						user.setPASSWORD(pd.getString("PASSWORD"));
						user.setNAME(pd.getString("NAME"));
						user.setRIGHTS(pd.getString("RIGHTS"));
						user.setROLE_ID(pd.getString("ROLE_ID"));
						user.setLAST_LOGIN(pd.getString("LAST_LOGIN"));
						user.setIP(pd.getString("IP"));
						user.setSTATUS(pd.getString("STATUS"));
						session.setAttribute(Const.SESSION_USER, user);			//把用户信息放session中
						session.removeAttribute(Const.SESSION_SECURITY_CODE);	//清除登录验证码的session
						//shiro加入身份验证
						Subject subject = SecurityUtils.getSubject(); 
					    UsernamePasswordToken token = new UsernamePasswordToken(USERNAME, PASSWORD); 
					    try { 
					        subject.login(token); 
					    } catch (AuthenticationException e) { 
					    	errInfo = "身份验证失败！";
					    }
					}else{
						errInfo = "usererror"; //用户名或者密码错误
						//logBefore(logger, USERNAME+"登录系统密码或用户名错误");
					}
					
				}else{
					errInfo = "codeerror"; // 验证码有误
				}
				if(Tools.isEmpty(errInfo)){
					errInfo = "success";					//验证成功
					//logBefore(logger, USERNAME+"登录系统");
				}
			}
		}else{
			errInfo = "error";	//缺少参数
		}
		
		map.put("result", errInfo);
		return AppUtil.returnObject(new PageData(), map);
	}
	
	@RequestMapping("/main/index")
	public ModelAndView login_index(){
		
		ModelAndView mv = new ModelAndView();
		
		PageData pd = new PageData();
		pd = this.getPageData();
		// 从 session 中获取用户
		Session session = Jurisdiction.getSession();
		User user = (User) session.getAttribute(Const.SESSION_USER);
		if(user != null){
			mv.setViewName("system/index/main");
		}else{
			mv.setViewName("system/index/login");//session失效后跳转登录页面
		}
		
		return mv;
	}
	
	/**
	 * 进入tab标签
	 * @return
	 */
	@RequestMapping(value="/tab")
	public String tab(){
		return "system/index/tab";
	}
	
	/**
	 * 进入首页后的默认页面
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping(value="/login_default")
	public ModelAndView defaultPage() throws Exception{
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd.put("userCount", Integer.parseInt(userService.getUserCount("").get("userCount").toString())-1);				//系统用户数
		//pd.put("appUserCount", Integer.parseInt(appuserService.getAppUserCount("").get("appUserCount").toString()));	//会员数
		mv.addObject("pd",pd);
		mv.setViewName("system/index/default");
		return mv;
	}
}
