<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page isELIgnored="false" %>
<!DOCTYPE html>
<!-- 网页使用的语言 -->
<html lang="zh-CN">
<head>
    <title>Title</title>
    <script src="../../js/jquery-3.2.1.min.js"></script>
    <script>
        /*$(function() {
            $("#changePhoneBtn").on("click",function(){
                $.ajax({
                    url: "doFindPageObjects.do",
                    data: params,
                    success: function(result){
                        if(result.getMsg() == 200){
                            alert("修改成功！");
                        }
                    }
                })
            })
        )
        $("#changePasswordBtn").on("click",function(){
        })*/

        function changePhone() {
            var params ={ id:${map.get("id")},phone: $("#phone").val()};
            $.ajax({
                type: "GET",
                url: "http://www.bssso.com/user/changeUser",
                dataType:"jsonp",
                jsonp:"callback",
                data: params,
                success: function(result){
                    if(result.status == 200){
                        alert("修改成功！");
                        window.location.reload();
                    }
                    if(result.status == 201){
                        alert("修改失败，手机号已绑定！");
                        window.location.reload();
                    }
                },
                error: function () {
                    alert("修改失败！");

                }
            })
        }

        function changePassword() {
            var params ={ id:${map.get("id")},password: $("#password").val()};
            $.ajax({
                type: "GET",
                url: "http://www.bssso.com/user/changeUser",
                dataType:"jsonp",
                jsonp:"callback",
                data: params,
                success: function(result){
                    if(result.status == 200){
                        alert("修改成功！");
                        $.ajax({
                            type: "GET",
                            url: "http://www.bsweb.com/user/logout.html",
                            data: {},
                            success: function(result){
                                window.location.reload();
                            }
                        })
                    }
                },
                error: function () {
                    alert("修改失败！");

                }
            })
        }
    </script>
</head>
<body>
<%--主体部分--%>
<jsp:include page="../commons/indexmid.jsp"/>
<br>
    <div>个人信息</div>
    <div>用户名：${map.get("username")}</div>
    <div>手机号：${map.get("phone")} </div>
    <div>新手机号：<input type="text" id="phone">&nbsp;<button id="changePhoneBtn" onclick="changePhone();">修改手机号</button></div>
    <div>新密码：<input type="text" id="password">&nbsp;<button id="changePasswordBtn" onclick="changePassword();">修改密码</button></div>
</body>
</html>
