$(document).ready(function() {
	var dataSource = new LocalDataSource({
		read: "/api/spreport/retentionRateList.json"
	});
	$("#retentionRateGrid").kendoGrid({
		dataSource : dataSource,
		pageable : true,
		sortable : true,
		resizable: true,
		filterable : filterable,
		selectable: "row",
		columns : [ {
			field : "startDate",
			title : "时间",
			width:"230px",
			template :  function(data) {
					return data.startDate+"至"+data.endDate;
				}
		},{
			field : "zhouFiled",
			title : "周期",
			width:"130px"
		},{
			field : "regCount",
			title : "每周新增数",
			width:"130px"
		},{
			field : "loginCount",
			title : "次周登陆数",
			width:"180px"
		},{
			field : "retentionRate",
			title : "留存率",
			width:"150px"		
		}
		]
	});
});

function pushRetentionRate(){
	var dataSource = new LocalDataSource({
		read: "/api/spreport/pushRetentionRate.json"
	});
	$("#retentionRateGrid").kendoGrid({
		dataSource : dataSource,
		pageable : true,
		sortable : true,
		resizable: true,
		filterable : filterable,
		selectable: "row",
		columns : [ {
			field : "startDate",
			title : "时间",
			width:"230px",
			template :  function(data) {
					return data.startDate+"至"+data.endDate;
				}
		},{
			field : "zhouFiled",
			title : "周期",
			width:"130px"
		},{
			field : "regCount",
			title : "每周新增数",
			width:"130px"
		},{
			field : "loginCount",
			title : "次周登陆数",
			width:"180px"
		},{
			field : "retentionRate",
			title : "留存率",
			width:"150px"		
		}
		]
	});
	
}
