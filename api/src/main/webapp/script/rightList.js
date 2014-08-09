var listUrl = "/api/spright/list.json";
var catModel = kendo.data.Model.define({
	id : "_id",
	fields : {
		_id : {editable : false,nullable : true},
		rightName:{},
		rightDesc: {},
		rightUrl: {},
		common: {common:"number"},

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
	pageSize : 15,
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
			title : "名字"
		},{
			field : "rightUrl",
			title : "权限路径",
		}, 
		{
			field : "rightDesc",
			title : "URL描述",
		},
		{
			field: "common",
			title: "是否是公共资源",
			template :  function(dataItem) {
				if(!dataItem.common || dataItem.common == 0 || dataItem.common == "0"){
					return '不是';
				}else{
					return '是';
				}
			}
		}
		]
	});


});	




function addRight(){
	loadPage("ht/rightAdd.html");
}

function deleteRight(){
	
	var row = getSelectedRowDataByGridWithMsg("grid");
	if(row){
		postAjaxRequest("/api/spright/delete.json", {right_id:row._id,rightUrl:row.rightUrl}, function(){dataSource.read();});
	}
}


function editRight() {
	
	var row = getSelectedRowDataByGridWithMsg("grid");
	if (row) {
		loadPage("ht/rightAdd.html", {
			_id : row._id
		});
	}
}