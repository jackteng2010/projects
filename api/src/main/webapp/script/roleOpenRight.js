var listUrl = "/api/sprole/roleOpenRight.json";
var catModel = kendo.data.Model.define({
	id : "_id",
	fields : {
		_id : {editable : false,nullable : true},
		rightName:{},
		rightUrl: {},

	}
});

var dataSource = new kendo.data.DataSource({
	transport : {
		read : {
			url : listUrl,
			dataType : "jsonp",
			type:"post"
		}
	},
	schema : {
		total : "total",
		data : "data",
		model : catModel
	},
	pageSize : 10,
	serverPaging : true,
	serverSorting : true,
	serverFiltering : true
});

$(document).ready(function() {
	checkRoles();
	$("#grid").kendoGrid({
		dataSource : dataSource,
		pageable : pageable,
		selectable : "row",
		sortable : true,
		resizable: true,
		filterable : filterable,
		columns : [ {
			field : "rightName",
			title : "权限名"
		},{
			field : "rightUrl",
			title : "权限URL",
		},
		{
			field: "rightDesc",
			title: "权限说明",
		}
		]
	});


});	
