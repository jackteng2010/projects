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
		
       <div class="jumbotron">
         <h1>Hello, Upload! <a href="user/uploadFile.htm" target="_blank" class="btn btn-primary btn-lg">文件上传DEMO</a></h1>
         <h1>Hello, Excel ! <a href="excel/index.excel" target="_blank" class="btn btn-primary btn-lg">Excel下载Demo</a></h1>
         <p>This is an example to show the potential of an offcanvas layout pattern in Bootstrap. Try some responsive-range viewport sizes to see it in action.</p>
       </div>
       <div class="row">
         <div class="col-xs-6 col-lg-4">
           <h2>Heading</h2>
           <p>Donec id elit non mi porta gravida at eget metus. Fusce dapibus, tellus ac cursus commodo, tortor mauris condimentum nibh, ut fermentum massa justo sit amet risus. Etiam porta sem malesuada magna mollis euismod. Donec sed odio dui. </p>
           <p><a class="btn btn-default" href="javascript:none" role="button">View details &raquo;</a></p>
         </div><!--/.col-xs-6.col-lg-4-->
         <div class="col-xs-6 col-lg-4">
           <h2>Heading</h2>
           <p>Donec id elit non mi porta gravida at eget metus. Fusce dapibus, tellus ac cursus commodo, tortor mauris condimentum nibh, ut fermentum massa justo sit amet risus. Etiam porta sem malesuada magna mollis euismod. Donec sed odio dui. </p>
           <p><a class="btn btn-default" href="javascript:none" role="button">View details &raquo;</a></p>
         </div><!--/.col-xs-6.col-lg-4-->
         <div class="col-xs-6 col-lg-4">
           <h2>Heading</h2>
           <p>Donec id elit non mi porta gravida at eget metus. Fusce dapibus, tellus ac cursus commodo, tortor mauris condimentum nibh, ut fermentum massa justo sit amet risus. Etiam porta sem malesuada magna mollis euismod. Donec sed odio dui. </p>
           <p><a class="btn btn-default" href="javascript:none" role="button">View details &raquo;</a></p>
         </div><!--/.col-xs-6.col-lg-4-->
         <div class="col-xs-6 col-lg-4">
           <h2>Heading</h2>
           <p>Donec id elit non mi porta gravida at eget metus. Fusce dapibus, tellus ac cursus commodo, tortor mauris condimentum nibh, ut fermentum massa justo sit amet risus. Etiam porta sem malesuada magna mollis euismod. Donec sed odio dui. </p>
           <p><a class="btn btn-default" href="javascript:none" role="button">View details &raquo;</a></p>
         </div><!--/.col-xs-6.col-lg-4-->
         <div class="col-xs-6 col-lg-4">
           <h2>Heading</h2>
           <p>Donec id elit non mi porta gravida at eget metus. Fusce dapibus, tellus ac cursus commodo, tortor mauris condimentum nibh, ut fermentum massa justo sit amet risus. Etiam porta sem malesuada magna mollis euismod. Donec sed odio dui. </p>
           <p><a class="btn btn-default" href="javascript:none" role="button">View details &raquo;</a></p>
         </div><!--/.col-xs-6.col-lg-4-->
         <div class="col-xs-6 col-lg-4">
           <h2>Heading</h2>
           <p>Donec id elit non mi porta gravida at eget metus. Fusce dapibus, tellus ac cursus commodo, tortor mauris condimentum nibh, ut fermentum massa justo sit amet risus. Etiam porta sem malesuada magna mollis euismod. Donec sed odio dui. </p>
           <p><a class="btn btn-default" href="javascript:none" role="button">View details &raquo;</a></p>
         </div><!--/.col-xs-6.col-lg-4-->
       </div><!--/row-->
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
