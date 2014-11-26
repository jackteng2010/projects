<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="utf-8">
  <meta http-equiv="X-UA-Compatible" content="IE=edge">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <meta name="description" content="">
  <meta name="author" content="">
  <title>MVC DEMO</title>

<%  
String path = request.getContextPath();  
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";  
%>  
<base href="<%=basePath%>">  

  <!-- Bootstrap core CSS -->
  <link href="plugin/bootstrap/css/bootstrap.min.css" rel="stylesheet">
  <!-- Custom styles for this template -->
  <link href="css/customer.css" rel="stylesheet">
</head>

<body>
  <nav class="navbar navbar-fixed-top navbar-inverse" role="navigation">
    <div class="container">
      <div class="navbar-header">
        <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#navbar" aria-expanded="false" aria-controls="navbar">
          <span class="sr-only">Toggle navigation</span>
          <span class="icon-bar"></span>
          <span class="icon-bar"></span>
          <span class="icon-bar"></span>
        </button>
        <a class="navbar-brand" href="javascript:none">Welcome: ${curUserName}</a>
      </div>
      <div id="navbar" class="collapse navbar-collapse">
        <ul class="nav navbar-nav">
          <li class="active"><a href="javascript:none">Home</a></li>
          <li><a href="#about">About</a></li>
          <li><a href="#contact">Contact</a></li>
        </ul>
      </div><!-- /.nav-collapse -->
   </div><!-- /.container -->
 </nav><!-- /.navbar -->

 <div class="container">
   <div class="row row-offcanvas row-offcanvas-right">
     <div class="col-xs-12 col-sm-9">
       <p class="pull-right visible-xs">
         <button type="button" class="btn btn-primary btn-xs" data-toggle="offcanvas">Toggle nav</button>
       </p>
<!-- header up up up -->
       
		<form id="uploadForm" name="uploadForm" method="post" action="user/uploadFile.do" class="form-horizontal"	role="form" enctype="multipart/form-data">
			<div class="form-group">
				<label for="fileName" class="col-sm-2 control-label">文件名称</label>
				<div class="col-sm-10">
					<input name="fileName" type="text" value="${request.fileName}" class="form-control" placeholder="" required="required">
				</div>
			</div>
			<div class="form-group">
				<label for="uploadFile" class="col-sm-2 control-label">文件</label>
				<div class="col-sm-10">
					<input name="uploadFile" type="file" required="required">
					<p class="help-block">Example block-level help text here.</p>
				</div>
			</div>
			<div class="form-group">
				<div class="col-sm-offset-2 col-sm-10">
					<button type="submit" class="btn btn-default">上传</button>
				</div>
			</div>
			<div class="form-group">
				<div class="col-sm-10">
					<div class="well well-lg">${message}</div>
				</div>
			</div>
		</form>

<!-- footer down down -->
     </div><!--/.col-xs-12.col-sm-9-->
     <div class="col-xs-6 col-sm-3 sidebar-offcanvas" id="sidebar" role="navigation">
       <div class="list-group">
         <a href="javascript:none" class="list-group-item active">Link</a>
         <a href="javascript:none" class="list-group-item">Link</a>
         <a href="javascript:none" class="list-group-item">Link</a>
         <a href="javascript:none" class="list-group-item">Link</a>
         <a href="javascript:none" class="list-group-item">Link</a>
         <a href="javascript:none" class="list-group-item">Link</a>
         <a href="javascript:none" class="list-group-item">Link</a>
         <a href="javascript:none" class="list-group-item">Link</a>
         <a href="javascript:none" class="list-group-item">Link</a>
         <a href="javascript:none" class="list-group-item">Link</a>
       </div>
     </div><!--/.sidebar-offcanvas-->
   </div><!--/row-->

   <hr>

   <footer>
     <p>&copy; Company 2014</p>
   </footer>

 </div><!--/.container-->


<!-- Bootstrap core JavaScript================================================== -->
<!-- Placed at the end of the document so the pages load faster -->
<script src="plugin/jquery-1.11.1.min.js"></script>
<script src="plugin/bootstrap/js/bootstrap.min.js"></script>
<script type="text/javascript">
$(document).ready(function () {
	  $('[data-toggle="offcanvas"]').click(function () {
	    $('.row-offcanvas').toggleClass('active')
	  });
	});
</script>
</body>
</html>