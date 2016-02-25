主要实现有 ：
1.文件上传
2.页面跳转
3.验证码生成与验证
4.邮件发送JavaMail
5.拦截器
6.在页面使用session数据 需要 拦截器【PublicInterceptor.java】
7.cookie参考CaptchaRender类
生成
	Cookie cookie = new Cookie(key, value);
	cookie.setMaxAge(-1);
	response.addCookie(cookie);
获取
	Cookie[] cookies = request.getCookies();
	for (Cookie cookie : cookies)
				if (cookie.getName().equals(name))
					return cookie;