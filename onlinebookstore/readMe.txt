��Ҫʵ���� ��
1.�ļ��ϴ�
2.ҳ����ת
3.��֤����������֤
4.�ʼ�����JavaMail
5.������
6.��ҳ��ʹ��session���� ��Ҫ ��������PublicInterceptor.java��
7.cookie�ο�CaptchaRender��
����
	Cookie cookie = new Cookie(key, value);
	cookie.setMaxAge(-1);
	response.addCookie(cookie);
��ȡ
	Cookie[] cookies = request.getCookies();
	for (Cookie cookie : cookies)
				if (cookie.getName().equals(name))
					return cookie;