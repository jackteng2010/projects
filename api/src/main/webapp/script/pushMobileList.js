$(document).ready(function() {
	var dataSource = new LocalDataSource({
		read: "/api/sppush/listMobile.json"
	});
	$("#pushMobileGrid").kendoGrid({
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
			field : "versionCode",
			title : "push版本",
			width:"130px"
		},{
			field : "productModel",
			title : "手机型号",
			width:"130px"
		},{
			field : "romVersion",
			title : "ROM",
			width:"180px"
		},{
			field : "aliRomVersion",
			title : "阿里ROM",
			width:"150px"		
		},{
			field : "address",
			title : "城市",
			width:"100px"
		},{
			field : "netType",
			title : "网络",
			width:"80px"
		},{
			field : "ipAddress",
			title : "IP",
			width:"130px"
		},{
			field : "simcardType",
			title : "卡1供应商",
			width:"100px"
		},{
			field : "simcardType2",
			title : "卡2供应商",
			width:"100px"
		},{
			field : "screenSize",
			title : "屏幕大小",
			width:"100px"
		},{
			field : "resolution",
			title : "分辨率",
			width:"100px"
		},{
			field : "channel",
			title : "渠道",
			width:"150px"
		}
		]
	});
});