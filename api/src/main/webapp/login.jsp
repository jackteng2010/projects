<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>数据中心</title>
<%
if(session.getAttribute("userId") != null){
	response.sendRedirect("forum.jsp"); 
}
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<base href="<%=basePath%>">
<!-- Bootstrap core CSS -->
<link href="css/bootstrap.min.css" rel="stylesheet">
    
<style type="text/css">
body {
  padding-top: 40px;
  padding-bottom: 40px;
  background-color: #eee;
}
.form-signin {
  max-width: 330px;
  padding: 15px;
  margin: 0 auto;
}
.form-signin .form-signin-heading,
.form-signin .checkbox {
  margin-bottom: 10px;
}
.form-signin .checkbox {
  font-weight: normal;
}
.form-signin .form-control {
  position: relative;
  font-size: 16px;
  height: auto;
  padding: 10px;
  -webkit-box-sizing: border-box;
     -moz-box-sizing: border-box;
          box-sizing: border-box;
}
.form-signin .form-control:focus {
  z-index: 2;
}
.form-signin input[type="text"] {
  margin-bottom: -1px;
  border-bottom-left-radius: 0;
  border-bottom-right-radius: 0;
}
.form-signin input[type="password"] {
  margin-bottom: 10px;
  border-top-left-radius: 0;
  border-top-right-radius: 0;
}
</style>    
</head>
<body>
    <div class="container" >
      <form class="form-signin" >
      	<p id="error" class="alert alert-danger" style="visibility:hidden">&nbsp;</p>
        <h2 class="form-signin-heading"><b>数据管理平台</b></h2>
        <input id="userName" type="text" value="admin" class="form-control"  placeholder="用户名" autofocus>
        <input id="password" type="password" value="123456" class="form-control"  placeholder="密码">
        <input id="submitfm" class="btn btn-lg btn-primary btn-block" type="submit" value="登录" />
      </form>
    </div> <!-- /container -->
<script src="script/base/jquery.min.js"></script>
<script type="text/javascript">
$(document).ready(function() {
	$("#submitfm").click(function(){
		var name = $("#userName").val().trim();
		var pwd = $("#password").val().trim();
		if(name=="" || pwd==""){
			$("#error").html("用户名或密码不能为空").css("visibility", "visible");
		} else {
			$.ajax({
				type : "POST",
				url : "/api/spuser/login.json",
				success : function(result) {
					if (result.msg) {
						$("#error").html(result.msg).css("visibility", "visible");
					} else {
						window.location = "forum.jsp";
					}
				},
				error : function(){
					$("#error").html("连接Service失败").css("visibility", "visible");
				},
				data : {
					userName : name,
					password : pwd
				}
			});
		}
		return false;
	});
});
</script>
</body>
</html>