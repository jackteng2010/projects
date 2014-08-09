<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="qx" uri="http://myui.qingcheng.com" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script src="script/roleList.js"></script>
</head>
<body>
	<div id="salesContractList" class="k-content">
		<div class="k-toolbar k-grid-toolbar">
			<qx:pathTag name="/api/sprole/addOrUpdate.json"><button class="k-button " onclick="addRole();">新增</button></qx:pathTag>
			<qx:pathTag name="/api/sprole/detail.json"><button class="k-button " onclick="editRole();">修改</button></qx:pathTag>
			<qx:pathTag name="/api/sprole/delete.json"><button class="k-button " onclick="deleteRole();">删除</button></qx:pathTag>
	
		</div>
		<div id="grid"></div>
	</div>
</body>
