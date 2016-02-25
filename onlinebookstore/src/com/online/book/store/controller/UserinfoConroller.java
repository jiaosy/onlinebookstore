package com.online.book.store.controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletResponse;

import com.jfinal.aop.Before;
import com.jfinal.core.Controller;
import com.online.book.store.model.Userinfo;
import com.online.book.store.vailidate.UserInfoVailidate;

/**
 * 控制用户信息
 * @author Administrator
 *
 */
public class UserinfoConroller extends Controller {
	public void index() {
		render("login.html");
	}
	
	public void login() {}
	
	public void register(){}
	
	public void captcha(){//生成验证码
		System.out.println("生成验证码");
		renderCaptcha();
	}
	
	public void captchaValidate(){//验证验证码 
 		/**
 		 * 一直验证失败 不知原因 
 		 *  String captcha1= getPara("captcha1");
		 *  System.out.println("验证结果：  "+validateCaptcha(captcha1));
		 *  原因是validateCaptcha()里面是参数名字 不是参数的值 所以不需要自己获取验证码 
 		 */
		HttpServletResponse res=getResponse();
		PrintWriter  pw = null;
		try {
			pw= res.getWriter();
			if(validateCaptcha("captcha1")){
				pw.println("1");
			}else{
				pw.write("验证码错误");
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			pw.close();
		}
		render("login.html");
	}
	
	public void check() {// 判断是否注册账户 用于登录

		Userinfo ui = getModel(Userinfo.class);
		String name = ui.getName();
 		Userinfo u = Userinfo.dao.findById(name);
		
		if (u == null) {
			setAttr("msg", "用户名不存在，请<a href="+"/userinfo/register"+">注册</a>");
			forwardAction("/userinfo/add");
		} else{
			setSessionAttr("userinfo", u);
			//为了能在页面使用session数据 须单独写个拦截器【PublicInterceptor.java】
			redirect("/book");
		}
	}
	
	@Before(UserInfoVailidate.class)
	public void save(){ //注册
		Userinfo us= getModel(Userinfo.class);
		us.save();
		redirect("/userinfo/login");
	}
	
	public void logout(){//注销
	    getSession().removeAttribute("userinfo");
	    getSession().removeAttribute("carts");
		redirect("/book");
	}
	
	public void detail(){//查看用户信息
 	}
}
