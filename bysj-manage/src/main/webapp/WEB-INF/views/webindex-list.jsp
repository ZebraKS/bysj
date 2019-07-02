<%@ page language="java" import="java.util.*" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <meta http-equiv="content-type" content="text/html; charset=UTF-8">
    <title>前台首页配置</title>
</head>
<body>
<table width="60%" algin="center" border="1">
    <tr align="center">
        <td colspan="5"><h2>前台首页配置</h2></td>
    </tr>
    <tr align="center">
        <td>序号</td>
        <td>行</td>
        <td>列</td>
        <td>商品ID</td>
        <td>操作</td>
    </tr>
    <c:forEach items="${webIndexList}" var="webIndex" varStatus="status">
        <tr align="center">
            <td>${status.index+1}</td>
            <td>${webIndex.block}</td>
            <td>${webIndex.col}</td>
            <td>${webIndex.itemId}</td>
            <td>
                <a class="btn btn-default btn-sm" target="_blank" href="${pageContext.request.contextPath}/webindex/showUpdateById?id=${webIndex.id}&block=${webIndex.block}&col=${webIndex.col}&itemId=${webIndex.itemId}">修改</a>&nbsp;
            </td>
        </tr>
    </c:forEach>
</table>
</body>
</html>