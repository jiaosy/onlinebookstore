<%@page import="java.util.HashMap"%>
<%@page import="com.online.book.store.model.Goods"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<script type="text/javascript">
	window.onload=function(){
		var ipts = document.getElementsByTagName("input"); 
		for(var i=0,j=ipts.lenth;i <j;i++) 
		if(ipts[i].type=="text"){ 
			ipts[i].onclick=function(){
				ipts.getElementByValue="";
			}; 
	 	} 
	}
</script>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>确认订单信息</title>
</head>
<body>
<h3 align="center">确认订单信息</h3>
<form action="/onlinebookstore/shop/order">
<p style="margin-left: 300px">
姓名：        <input type="text" name="orders.Customer" value="${userinfo.name}" ><br/>
发货地址：<input type="text" name="orders.ShipAddr" value="${userinfo.addr}" ><br/>
联系方式：<input type="text" name="orders.telephe" value="${userinfo.telephe}" ><br/>
 
</p>
<table border="1" cellpadding="10" cellspacing="0" align="center" width="800px">
<tr>
	<th>编号</th>
	<th>书名</th>
	<th>定价</th>
	<th>数量</th>
	<th>小计</th>
</tr>
<c:forEach var="gs" items="${carts}"  varStatus="s" >
<tr align="center">
	<td>${gs.key.sid }</td>
	<td>${gs.key.sname}</td>
	<td>${gs.key.sprice}</td>
	<td>${gs.value}</td>
	<td>${gs.key.sprice*gs.value}</td>
</tr>
</c:forEach>
</table>
<%
	HashMap<Goods, Integer> carts=(HashMap<Goods, Integer>)request.getSession().getAttribute("carts");
	int sum_num=0;//循环加值统计总数量
	double sum_price=0;//总价钱统计
	for (Goods g : carts.keySet()) {
		int num=carts.get(g);
		 sum_num+=num;
		 sum_price+=Double.parseDouble(g.getSprice())*num;
	}
%>
<p align="right" style="margin-right: 100px">总数量：<%=sum_num %>  总价格： <%=sum_price %> </p>
<input type="submit" style="margin-right: 100px;float: right;" value="提交订单">
<button style="margin-right: 20px;float: right;" onclick="window.open('/onlinebookstore/shop/carts')">返回购物车</button>
</form>
<hr>
<a href="/onlinebookstore/book">返回主页</a> 
<%--
订单编号
顾客
日期
发货日期
发货状态
发货地址
发货费用
CREATE TABLE Orders 
(id int(4) primary key, 
Customer varchar(10), 
OrderDate DATE NOT NULL, 
ShipDate DATE, 
ShipAddr VARCHAR(18), 
ShipState VARCHAR(2), 
ShipCost int(4),
constraint o_u foreign key(Customer) references userinfo(name)
)engine=innodb default charset=utf8;
CONSTRAINT orders_order#_pk PRIMARY KEY(order#)); 

alter table orders add constraint FK_B foreign key  (customer) references userinfo(name)
primary key (字段名)
  
 create table order1(
 id int primary key,
 name varchar(10) ,
 addr varchar(50) not null,
 telephe varchar(20) not null,
 num int(5),
 price varchar(20),
constraint o_u foreign key(name) references userinfo(name));
 --%>
</body>
</html>