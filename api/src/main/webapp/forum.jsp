<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="qx" uri="http://myui.qingcheng.com" %>
<!DOCTYPE html>
<html lang="en">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>数据中心后台</title>
<%
if(session.getAttribute("userId") == null){
	response.sendRedirect("login.jsp");
}
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<base href="<%=basePath%>">
<!-- Bootstrap core CSS -->
<link href="css/bootstrap.min.css" rel="stylesheet">
<!-- kendo ui -->
<link href="css/kendo.common-bootstrap.min.css" rel="stylesheet">
<link href="css/kendo.bootstrap.min.css" rel="stylesheet">
<!-- Custom styles for this template -->
<link href="css/offcanvas.css" rel="stylesheet">
<!-- ====== Bootstrap core JavaScript============ -->
<script src="script/base/jquery.min.js"></script>
<script src="script/base/kendo.web.js"></script>
<script src="script/base/kendo.all.min.js"></script>
<script src="script/base/jquery.url.js"></script>
<script src="script/common.js"></script>
</head>
<body>
  <div class="navbar navbar-inverse" role="navigation">
    <div class="container">
      <div class="navbar-header">
        <a class="navbar-brand" href="javascript:void;">数据中心</a>
      </div>
      <div class="collapse navbar-collapse">
        <ul class="nav navbar-nav">
          <li class="active"><a href="forum.jsp">首页</a></li>
          <li><a href="javascript:void;">关于</a></li>
          <li><a href="javascript:void;">联系</a></li>
        </ul>
        <div class="navbar-form navbar-right">
			<button onclick="logoutbt()" class="btn btn-success">登出</button>          
        </div>
      </div><!-- /.nav-collapse -->
    </div><!-- /.container -->
  </div><!-- /.navbar -->

  <div class="container-fluid">
    <div class="row row-offcanvas">
      <div class="col-sm-2 sidebar-offcanvas" id="sidebar" role="navigation">
	<div class="well sidebar-nav">
		<ul class="nav nav-list">
		 <li class="nav-header">应用管理</li>
		 <qx:pathTag name="/api/spapp/listall.json"><li><a href="ht/appList.html">应用列表</a></li>       </qx:pathTag>
		 <qx:pathTag name="/api/spapp/cate/listall.json"><li><a href="ht/cateList.html">分类列表</a></li>      </qx:pathTag>
		 <qx:pathTag name="/api/spapp/baidu/checkupdate.json"><li><a href="ht/appUpdateList.html">应用更新</a></li> </qx:pathTag>
		 <qx:pathTag name="/api/spapp/tp/searchJY.json"><li><a href="ht/appThirdList.html">第三方应用</a></li> </qx:pathTag>
		 <qx:pathTag name="/api/sp/market/pic/listall.json"><li><a href="ht/uploadStartMarketPicture.html">软件启动页</a></li></qx:pathTag>
		 <li class="nav-header">统计管理</li>
		 <qx:pathTag name="/api/spreport/app/count.json"><li><a href="ht/reportApp.html">应用统计</a></li> </qx:pathTag>
		 <qx:pathTag name="ht/reportUser.html"><li><a href="ht/reportUser.html">用户分析</a></li>          </qx:pathTag>
		 <qx:pathTag name="/api/spuser/mobile/list.json"><li><a href="ht/userList.html">用户列表</a></li>  </qx:pathTag>
		 <qx:pathTag name="/api/spreport/app/clientDetailCount.json"><li><a href="ht/newAppUserList.html">用户新增详情</a></li>  </qx:pathTag>
		 <li class="nav-header">后台管理</li>
		 <qx:pathTag name="/api/spuser/loadSelfInfo.json"><li><a href="ht/userEdit.html">个人信息</a></li> </qx:pathTag>
		 <qx:pathTag name="/api/sp/client/list.json"><li><a href="ht/clientList.html">客户端列表</a></li> </qx:pathTag>
		 <qx:pathTag name="/api/sp/config/list.json"><li><a href="ht/configList.html">系统参数配置</a></li> </qx:pathTag>
		 <qx:pathTag name="/api/spright/list.json"><li><a href="jsp/rightList.jsp">权限列表</a></li>       </qx:pathTag>
		 <qx:pathTag name="/api/sprole/list.json"><li><a href="jsp/roleList.jsp">角色列表</a></li>         </qx:pathTag>
		 <qx:pathTag name="/api/spuser/list.json"><li><a href="jsp/userList.jsp">系统用户列表</a></li>         </qx:pathTag>
		                                                      
		 <li class="nav-header">PUSH管理</li>                    
		 <qx:pathTag name="/api/sppush/listMsg.json"><li><a href="ht/pushMsgList.html">消息列表</a></li>    </qx:pathTag>
   		<!-- 
		  <qx:pathTag name="ht/pushAddMsg.html"><li><a href="ht/pushAddMsg.html">添加消息</a></li>      </qx:pathTag>
		 -->
		 <qx:pathTag name="/api/sppush/getLoopInterval.json"><li><a href="ht/pushLoopSet.html">间隔设置</a></li>    </qx:pathTag>
		 <qx:pathTag name="/api/sppush/listMobile.json"><li><a href="ht/pushMobileList.html">手机列表</a></li>  </qx:pathTag>
		 <qx:pathTag name="/api/sppush/listUserFeedbackPushMsg.json"><li><a href="ht/pushUserFeedbackMsgList.html">意见反馈</a></li>  </qx:pathTag>
		 <li class="nav-header">MyUI管理</li>
		 <qx:pathTag name="/api/spuser/listMyuiUser.json"><li><a href="ht/myuiUserList.html">MyUI用户</a></li>  </qx:pathTag>
		 <%-- <qx:pathTag name="/api/spreport//retentionRateList.json"><li><a href="ht/retentionRateList.html">MyUI周留存率</a></li>  </qx:pathTag> --%>
		</ul>
	</div>
      </div><!--/span-->

      <div class="col-sm-10">
      <div class="panel panel-default">
  <!-- Default panel contents -->
  <div id="maintitle" class="panel-heading">&nbsp;</div>
  <div id="mainiframe" class="panel-body">
  	<div class="jumbotron">
  		<h1>数据中心后台管理</h1>
  	</div>
  </div>
  <div id="imageload" class="imageLoad"></div>
		</div>
      </div><!--/span-->
    </div><!--/row-->
  </div><!--/.container-->
<div id="popup"></div>
 </body>
</html>