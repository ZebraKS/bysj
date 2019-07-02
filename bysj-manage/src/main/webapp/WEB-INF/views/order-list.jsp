<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<!-- 网页使用的语言 -->
<html lang="zh-CN">
<head>
    <!-- 指定字符集 -->
    <meta charset="utf-8">
    <!-- 使用Edge最新的浏览器的渲染方式 -->
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <!-- viewport视口：网页可以根据设置的宽度自动进行适配，在浏览器的内部虚拟一个容器，容器的宽度与设备的宽度相同。
    width: 默认宽度与设备的宽度相同
    initial-scale: 初始的缩放比，为1:1 -->
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <!-- 上述3个meta标签*必须*放在最前面，任何其他内容都*必须*跟随其后！ -->
    <title>用户信息管理系统</title>

    <!-- 1. 导入CSS的全局样式 -->
    <link href="/css/bootstrap.min.css" rel="stylesheet">
    <!-- 2. jQuery导入，建议使用1.9以上的版本 -->
    <script src="/js/jquery-2.1.0.min.js"></script>
    <!-- 3. 导入bootstrap的js文件 -->
    <script src="/js/bootstrap.min.js"></script>
    <style type="text/css">
        td, th {
            text-align: center;
        }
    </style>

    <script>
        function sendItem(orderId) {
            alert("发货");
            var params = {"orderId":orderId};
            $.ajax({
                type: "GET",
                url: "http://www.bsmanage.com/order/sendItem",
                dataType:"json",
                data: params,
                success: function(result){
                    if(result.status == 200){
                        alert("发货成功！");
                        window.location.reload();
                    }
                },
                error: function () {
                    alert("失败！");
                }
            })
        }
        $("#my-select").on("change",function () {//使用on进行事件绑定事件。
            $("select option:selected").each(function () {//jq中的each()方法是遍历的对象的
                $("#my-input").val($(this).text());//val()设置input的值，text()获取元素的文本内容。
            })
        });
        function deleteUser(id){
            //用户安全提示
            if(confirm("您确定要删除吗？")){
                //访问路径
                location.href="${pageContext.request.contextPath}/order/deleteOrder?orderId="+id;
            }
        }

        window.onload = function(){
            //给删除选中按钮添加单击事件
            document.getElementById("delSelected").onclick = function(){
                if(confirm("您确定要删除选中条目吗？")){

                   var flag = false;
                    //判断是否有选中条目
                    var cbs = document.getElementsByName("orderIds");
                    for (var i = 0; i < cbs.length; i++) {
                        if(cbs[i].checked){
                            //有一个条目选中了
                            flag = true;
                            break;
                        }
                    }

                    if(flag){//有条目被选中
                        //表单提交
                        document.getElementById("form").submit();
                    }

                }

            }
            //1.获取第一个cb
            document.getElementById("firstCb").onclick = function(){
                //2.获取下边列表中所有的cb
                var cbs = document.getElementsByName("orderIds");
                //3.遍历
                for (var i = 0; i < cbs.length; i++) {
                    //4.设置这些cbs[i]的checked状态 = firstCb.checked
                    cbs[i].checked = this.checked;

                }

            }
        }


    </script>
</head>
<body>
<div class="container">
    <h3 style="text-align: center">订单信息列表</h3>

    <div style="float: left;">

        <form class="form-inline" action="${pageContext.request.contextPath}/order/orderList" method="post">
            <input type="hidden" name="row" value="10" class="form-control">
            <div class="form-group">
                <label for="orderIdInput">订单号</label>
                <input type="text" name="orderId" class="form-control" id="orderIdInput" >
            </div>
            <div class="form-group">
                <label for="orderStatusInput">支付状态</label>&nbsp;
                <select name="status" id="orderStatusInput" class="selectpicker show-tick form-control" data-actions-box="true">
                    <option value="">请选择</option>
                    <option value="1">未付款</option>
                    <option value="2">已付款</option>
                    <option value="3">未发货</option>
                    <option value="4">已发货</option>
                    <option value="5">交易成功</option>
                    <option value="6">交易关闭</option>
                </select>
            </div>
            <button type="submit" class="btn btn-default">查询</button>
        </form>

    </div>

    <div style="float: right;margin: 5px;">

        <%--<a class="btn btn-primary" href="${pageContext.request.contextPath}/add.jsp">添加联系人</a>--%>
        <a class="btn btn-primary" href="javascript:void(0);" id="delSelected">删除选中</a>

    </div>
    <form id="form" action="${pageContext.request.contextPath}/order/deleteOrders" method="post">
        <table border="1" class="table table-bordered table-hover">
        <tr class="success">
            <th><input type="checkbox" id="firstCb"></th>
            <th>序号</th>
            <th>订单号</th>
            <th>总金额</th>
            <th>交易状态</th>
            <th>创建时间</th>
            <th>操作</th>
        </tr>

        <c:forEach items="${pb.list}" var="order" varStatus="s">
            <tr>
                <td><input type="checkbox" name="orderIds" value="${order.orderId}"></td>
                <td>${s.count + pb.rows * (pb.currentPage - 1) }</td>
                <td>${order.orderId}</td>
                <td>${order.payment}</td>
                <td>
                    <c:choose>
                        <c:when test="${order.status == 1}">未付款</c:when>
                        <c:when test="${order.status == 2}">已付款</c:when>
                        <c:when test="${order.status == 3}">未发货</c:when>
                        <c:when test="${order.status == 4}">已发货</c:when>
                        <c:when test="${order.status == 5}">交易成功</c:when>
                        <c:when test="${order.status == 6}">交易关闭</c:when>
                        <c:otherwise>未知状态</c:otherwise>
                    </c:choose>
                </td>
                <td>${order.created}</td>
                <%--target="_blank" 浏览器新窗口打开--%>
                <td>
                    <a class="btn btn-default btn-sm" target="_blank" href="${pageContext.request.contextPath}/order/findOrderShippingByOrderId/${order.orderId}">查看详情</a>
                    <a class="btn btn-default btn-sm" href="javascript:deleteUser(${order.orderId});">删除</a>
                    <c:if test="${order.status == 2}">
                        <a class="btn btn-default btn-sm" name="sendItem" id="sendItem" onclick="sendItem(${order.orderId});">发货</a>
                    </c:if>
                </td>
            </tr>

        </c:forEach>


        </table>
    </form>
    <div>
        <nav aria-label="Page navigation">
            <ul class="pagination">
                <c:if test="${pb.currentPage == 1}">
                    <li class="disabled">
                </c:if>

                <c:if test="${pb.currentPage != 1}">
                    <li>
                </c:if>


                    <a href="${pageContext.request.contextPath}/order/orderList?currentPage=${pb.currentPage - 1}&row=10" aria-label="Previous">
                        <span aria-hidden="true">&laquo;</span>
                    </a>
                </li>


                <c:forEach begin="1" end="${pb.totalPage}" var="i" >


                    <c:if test="${pb.currentPage == i}">
                        <li class="active"><a href="${pageContext.request.contextPath}/order/orderList?currentPage=${i}&row=10">${i}</a></li>
                    </c:if>
                    <c:if test="${pb.currentPage != i}">
                        <li><a href="${pageContext.request.contextPath}/order/orderList?currentPage=${i}&row=10">${i}</a></li>
                    </c:if>

                </c:forEach>

                <c:if test="${pb.currentPage == pb.totalPage}">
                <li class="disabled">
                    </c:if>

                    <c:if test="${pb.currentPage != pb.totalPage}">
                <li>
                    </c:if>
                    <a href="${pageContext.request.contextPath}/order/orderList?currentPage=${pb.currentPage + 1}&row=10" aria-label="Next">
                        <span aria-hidden="true">&raquo;</span>
                    </a>
                </li>
                <span style="font-size: 25px;margin-left: 5px;">
                    共${pb.totalCount}条记录，共${pb.totalPage}页
                </span>

            </ul>
        </nav>


    </div>


</div>


</body>
</html>
