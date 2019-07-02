<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page isELIgnored="false" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <title>毕设前台主页面</title>
    <meta name="description"
          content="专业的综合网上销售平台，在线销售家电、数码通讯、电脑、家居百货、服装服饰、母婴、图书、食品、在线旅游等数万个品牌千万种优质商品。便捷、诚信的服务，为您提供愉悦的网上购物体验! ">
    <meta name="Keywords" content="网上购物,网上商城,手机,笔记本,电脑,MP3,CD,VCD,DV,相机,数码,配件,手表,存储卡,京淘商城">

    <link href="/css/jt.css" rel="stylesheet"/>
    <style type="text/css">
        #categorys-2013 .mc {
            display: block;
        }

        #categorys-2013 .mt {
            background: 0
        }
    </style>
</head>
<body>
<!-- header start -->
<jsp:include page="../commons/header.jsp"/>
<!-- header end -->

<%--主体部分--%>
<jsp:include page="../commons/indexmid.jsp"/>

<%--商品部分--%>
<jsp:include page="../commons/indexmain.jsp"/>

<%--最底部--%>
<jsp:include page="../commons/indexfooter.jsp"/>

<!-- ad start -->
<div class="sm sm1 brands" clstag="homepage|keycount|home2013|19a"/>
    <!-- footer start -->
    <jsp:include page="../commons/footer.jsp"/>
    <!-- footer end -->
    <script type="text/javascript" src="/js/home.js" charset="utf-8"></script>
</body>
</html>