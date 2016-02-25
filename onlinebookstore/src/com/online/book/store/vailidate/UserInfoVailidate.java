package com.online.book.store.vailidate;

import com.jfinal.core.Controller;
import com.jfinal.validate.Validator;
import com.online.book.store.model.Userinfo;

public class UserInfoVailidate extends Validator {

	@Override
	public void validate(Controller c) {
	//	validateRequiredString("userinfo.name", "name", "�������û���");
		
		validateString("userinfo.name", 2, 6, "name", "�û�����2-6λ��ĸ");
		validateString("userinfo.number", 2, 6, "number", "�û�������2-6λ����");
		validateString("userinfo.relname", 2, 6, "relname", "��ʵ������2-6λ��ĸ");
 		validateRequiredString("userinfo.addr", "addr", "�������ַ");
		validateEmail("userinfo.mail","mail", "�����������");
		validateRegex("userinfo.telephe", "^(13|14|15|18)\\d{9}$", "telephe", "�绰�����ʽ����");
   
	}

	@Override
	public void handleError(Controller c) {
		// TODO Auto-generated method stub
		c.keepModel(Userinfo.class);
		c.render("register.html");
	}

}
