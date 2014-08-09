$(document).ready(function() {
	var dataSource = new kendo.data.DataSource({
		transport : {
			read : {
				url:"http://myui1.qingcheng.com/deskapi/listAllUser.action",
				dataType : "jsonp",
				type:"post"
			}
		},
		schema : {
			total : "pagination.total",
	        data: "data"
		},
		pageSize : 10,
		serverPaging : true,
		serverSorting : true,
		serverFiltering : true
	});
	$("#grid").kendoGrid({
		dataSource : dataSource,
		pageable : true,
		sortable : true,
		resizable: true,
		filterable : filterable,
		selectable: "row",
		columns : [ {
			field : "deviceId",
			title : "标识IMEI",
			width:"130px"
		},{
			field : "currentTime",
			title : "激活时间",
			width:"130px"
		},{
			field : "productModel",
			title : "手机型号",
			width:"130px"
		},{
			field : "versionCode",
			title : "ROM",
			width:"180px"	
		},{
			field : "address",
			title : "城市",
			width:"100px"
		},{
			field : "ipAddress",
			title : "IP",
			width:"130px"
		},{
			field : "createdDate",
			title : "创建时间",
			width:"100px"
		},{
			field : "updatedDate",
			title : "更新时间",
			width:"100px"
		}]
	});
});