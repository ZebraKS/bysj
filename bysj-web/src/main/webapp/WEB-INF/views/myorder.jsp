<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@page isELIgnored="false" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
    <title>我的订单</title>
    <script src="../../js/jquery-3.2.1.min.js"></script>
    <script>
        function receivingGoods(orderId) {
            $.ajax({
                type: "GET",
                dataType:"json",
                url: "${pageContext.request.contextPath}/order/receivingGoods/"+orderId+".html",
                success: function(result){
                    if(result.status == 200){
                        alert("收货成功！");
                        window.location.reload();
                    }
                    if(result.status == 201){
                        alert("收货失败！");
                        window.location.reload();
                    }
                },
                error: function () {
                    //alert("收货失败！");
                    alert("收货成功！");
                    window.location.reload();
                }
            })
        }
        function deleteOrder(orderId) {
            $.ajax({
                type: "GET",
                dataType:"json",
                url: "${pageContext.request.contextPath}/order/deleteOrder/"+orderId+".html",
                success: function(result){
                    if(result.status == 200){
                        alert("删除成功！");
                        window.location.reload();
                    }
                    if(result.status == 201){
                        alert("删除失败！");
                        window.location.reload();
                    }
                },
                error: function () {
                    //alert("收货失败！");
                    alert("删除成功！");
                    window.location.reload();
                }
            })
        }
    </script>
</head>
<body>
    <input type="button" value="跳转首页" onclick="javascrtpt:window.location.href='http://www.bsweb.com/'">
    <table>
        <tr>
            <td>订单号</td>
            <td>金额</td>
            <td>订单状态</td>
            <td>操作</td>
        </tr>
        <c:forEach items="${orderList}" var="l">
            <tr>
                <td>${l.orderId}</td>
                <td>${l.payment}</td>
                <c:if test="${l.status==1}">
                    <td>未支付</td>
                </c:if>
                <c:if test="${l.status==2}">
                    <td>已支付</td>
                </c:if>
                <c:if test="${l.status==4}">
                    <td>已发货</td>
                </c:if>
                <c:if test="${l.status==5}">
                    <td>交易成功</td>
                </c:if>
                <td>
                    <button onclick="deleteOrder(${l.orderId});">删除</button>
                    <c:if test="${l.status==1}">
                    <a href="${pageContext.request.contextPath}/pay/payorder/${l.orderId}.html">去支付</a>
                    </c:if>
                    <a target="_blank" href="${pageContext.request.contextPath}/order/findOrderShippingByOrderId/${l.orderId}.html">查看详情</a>
                    <c:if test="${l.status==4}">
                        <button onclick="receivingGoods(${l.orderId});">确认收货</button>
                    </c:if>
                </td>
            </tr>
        </c:forEach>
    </table>
</body>
</html>
