<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@include file="common/header.jsp" %>

<div id="page-wrapper"  style="padding-top: 5px">
    <h1>你的权限不够!</h1>  
    <p>只有拥有Admin权限才能访问!</p>  
    <a href="/user/loginout.htm">退出登录</a>  
</div>

<%@include file="common/footer.jsp" %>