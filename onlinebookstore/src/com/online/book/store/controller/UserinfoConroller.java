package com.online.book.store.controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletResponse;

import com.jfinal.aop.Before;
import com.jfinal.core.Controller;
import com.online.book.store.model.Userinfo;
import com.online.book.store.vailidate.UserInfoVailidate;

/**
 * �����û���Ϣ
 * @author Administrator
 *
 */
public class UserinfoConroller extends Controller {
	public void index() {
		render("login.html");
	}
	
	public void login() {}
	
	public void register(){}
	
	public void captcha(){//������֤��
		System.out.println("������֤��");
		renderCaptcha();
	}
	
	public void captchaValidate(){//��֤��֤�� 
 		/**
 		 * һֱ��֤ʧ�� ��֪ԭ�� 
 		 *  String captcha1= getPara("captcha1");
		 *  System.out.println("��֤�����  "+validateCaptcha(captcha1));
		 *  ԭ����validateCaptcha()�����ǲ������� ���ǲ�����ֵ ���Բ���Ҫ�Լ���ȡ��֤�� 
 		 */
		HttpServletResponse res=getResponse();
		PrintWriter  pw = null;
		try {
			pw= res.getWriter();
			if(validateCaptcha("captcha1")){
				pw.println("1");
			}else{
				pw.write("��֤�����");
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			pw.close();
		}
		render("login.html");
	}
	
	public void check() {// �ж��Ƿ�ע���˻� ���ڵ�¼

		Userinfo ui = getModel(Userinfo.class);
		String name = ui.getName();
 		Userinfo u = Userinfo.dao.findById(name);
		
		if (u == null) {
			setAttr("msg", "�û��������ڣ���<a href="+"/userinfo/register"+">ע��</a>");
			forwardAction("/userinfo/add");
		} else{
			setSessionAttr("userinfo", u);
			//Ϊ������ҳ��ʹ��session���� �뵥��д����������PublicInterceptor.java��
			redirect("/book");
		}
	}
	
	@Before(UserInfoVailidate.class)
	public void save(){ //ע��
		Userinfo us= getModel(Userinfo.class);
		us.save();
		redirect("/userinfo/login");
	}
	
	public void logout(){//ע��
	    getSession().removeAttribute("userinfo");
	    getSession().removeAttribute("carts");
		redirect("/book");
	}
	
	public void detail(){//�鿴�û���Ϣ
 	}
}
