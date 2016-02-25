<%@page import="java.util.concurrent.ConcurrentHashMap"%>
<%@page import="java.util.HashMap"%>
<%@page import="java.lang.Integer"%>
<%@page import="com.online.book.store.model.Goods"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>${userinfo.name}的购物车</title>
</head>
<body>
<h3 align="center">${userinfo.name}的购物车</h3>
 
<c:if test="${empty carts}"><h3>购物车里好冷清，点击<a href="/onlinebookstore//book">返回主页</a>进行购买</h3></c:if>
<c:if test="${not empty carts}">
<table border="1" cellpadding="10" cellspacing="0" align="center" width="800px">
<tr><th>序号</th>
	<th>编号</th>
	<th>书名</th>
	<th>定价</th>
	<th>数量</th>
</tr>
<c:forEach var="gs" items="${carts}"  varStatus="s" >
<tr align="center">
	<td>${s.count}</td>
	<td>${gs.key.sid }</td>
	<td>${gs.key.sname}</td>
	<td>${gs.key.sprice}</td>
	<td><input type="button" value="-" onclick="window.open('/onlinebookstore/shop/sub/${gs.key.sid}')"/> 
	&nbsp;&nbsp;&nbsp;${gs.value}&nbsp;&nbsp;&nbsp; 
	<input type="button" value="+" onclick="window.open('/onlinebookstore/shop/add/${gs.key.sid}-1')" /></td>
</tr>
</c:forEach>
</table>

 <%	HashMap<Goods, Integer> car=(HashMap<Goods, Integer>)request.getSession().getAttribute("carts"); 
 ConcurrentHashMap<Goods, Integer> carts=new ConcurrentHashMap<Goods, Integer>();
 carts.putAll(car);
 %>
<%	int sum_num=0;//循环加值统计总数量s
	double sum_price=0;//总价钱统计
	for (Goods g : carts.keySet()) {
		int num=carts.get(g);
		sum_num=sum_num+num;
		sum_price+=Double.parseDouble(g.getSprice())*num;
	}
%>
<p>总数量<%=sum_num %>, 总价格<%=sum_price %></p>
<button style="margin-right: 150px;float: right;" onclick="window.open('/onlinebookstore/shop/buy')">去支付</button>
</c:if>
<hr>
<a href="/onlinebookstore/book">返回主页</a> 
</body>
</html>