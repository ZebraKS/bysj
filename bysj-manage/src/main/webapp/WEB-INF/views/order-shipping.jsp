<%@ page language="java" import="java.util.*" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page isELIgnored="false" %>
<html>
	<head>
	<meta http-equiv="content-type" content="text/html; charset=UTF-8">
		<title>订单详情</title>
	</head>
	<body>
		<table width="60%" algin="center" border="1">
			<tr align="center">
				<td colspan="5"><h2>物流信息</h2></td>
			</tr>
			<tr align="center">
				<td>订单ID</td>
				<td>收货人全名</td>
				<td>移动电话</td>
				<td>省份</td>
				<td>城市</td>
				<td>区/县</td>
				<td>收货地址</td>
			</tr>
			<tr align="center">
				<td>${orderShipping.orderId}</td>
				<td>${orderShipping.receiverName}</td>
				<td>${orderShipping.receiverMobile}</td>
				<td>${orderShipping.receiverState}</td>
				<td>${orderShipping.receiverCity}</td>
				<td>${orderShipping.receiverDistrict}</td>
				<td>${orderShipping.receiverAddress}</td>
			</tr>
		</table>

		<table width="60%" algin="center" border="1">
			<tr align="center">
				<td colspan="5"><h2>商品信息</h2></td>
			</tr>
			<tr align="center">
				<td>序号</td>
				<td>图片</td>
				<td>标题</td>
				<td>数量</td>
				<td>价格</td>
			</tr>
			<c:forEach items="${orderItems}" var="orderItem" varStatus="status">
				<tr align="center">
					<td>${status.index+1}</td>
					<td><img src="${orderItem.picPath}"/></td>
					<td>${orderItem.title}</td>
					<td>${orderItem.num}</td>
					<td>${orderItem.price/100}</td>
				</tr>
			</c:forEach>
		</table>
	</body>
</html>