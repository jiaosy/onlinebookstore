package com.online.book.store.vailidate;

import com.jfinal.core.Controller;
import com.jfinal.validate.Validator;
import com.online.book.store.model.Userinfo;

public class UserInfoVailidate extends Validator {

	@Override
	public void validate(Controller c) {
	//	validateRequiredString("userinfo.name", "name", "请输入用户名");
		
		validateString("userinfo.name", 2, 6, "name", "用户名需2-6位字母");
		validateString("userinfo.number", 2, 6, "number", "用户编码需2-6位数字");
		validateString("userinfo.relname", 2, 6, "relname", "真实姓名需2-6位字母");
 		validateRequiredString("userinfo.addr", "addr", "请输入地址");
		validateEmail("userinfo.mail","mail", "邮箱输入错误");
		validateRegex("userinfo.telephe", "^(13|14|15|18)\\d{9}$", "telephe", "电话号码格式错误");
   
	}

	@Override
	public void handleError(Controller c) {
		// TODO Auto-generated method stub
		c.keepModel(Userinfo.class);
		c.render("register.html");
	}

}
