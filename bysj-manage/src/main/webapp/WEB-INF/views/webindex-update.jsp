<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<!-- 网页使用的语言 -->
<html lang="zh-CN">
<head>
    <!-- 指定字符集 -->
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>修改用户</title>

    <link href="css/bootstrap.min.css" rel="stylesheet">
    <script src="js/jquery-2.1.0.min.js"></script>
    <script src="js/bootstrap.min.js"></script>

</head>
<body>
<div class="container" style="width: 400px;">
    <h3 style="text-align: center;">修改展示信息</h3>
    <form action="${pageContext.request.contextPath}/webindex/updateById" method="post">
        <!--  隐藏域 提交id-->
        <input type="hidden" name="id" value="${webIndex.id}">

        <div class="form-group">
            <label for="block">行：</label>
            <input type="text" class="form-control" id="block" name="block" value="${webIndex.block}"
                   readonly="readonly"/>
        </div>
        <div class="form-group">
            <label for="col">列：</label>
            <input type="text" class="form-control" id="col" name="col" value="${webIndex.col}"
                   readonly="readonly"/>
        </div>
        <div class="form-group">
            <label for="itemId">商品ID：</label>
            <input type="text" class="form-control" id="itemId" name="itemId" value="${webIndex.itemId}" placeholder="请输入商品ID"/>
        </div>

        <div class="form-group" style="text-align: center">
            <input class="btn btn-primary" type="submit" value="提交"/>
            <input class="btn btn-default" type="reset" value="重置"/>
            <input class="btn btn-default" type="button" value="返回"/>
        </div>
    </form>
</div>
</body>
</html>