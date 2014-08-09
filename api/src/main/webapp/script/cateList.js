//销售合同主要列表页 dataSource
var listUrl = "/api/spapp/cate/listall.json"
var catModel = kendo.data.Model.define({
	id : "_id",
	fields : {
		_id : {editable : false,nullable : true},
		appCategoryName:{},
		imageUrl: {},
		type:{type:"number"},
		priority:{type:"number"},
		activity:{type:"number"},
		gamerAmount:{type:"number"}
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
	pageSize : 5,
	serverPaging : true,
	serverSorting : true,
	serverFiltering : true
});

$(document).ready(function() {
	checkRoles();
	var grid = $("#grid").kendoGrid({
		dataSource : dataSource,
		pageable : pageable,
		selectable : "row",
		sortable : true,
		resizable: true,
		filterable : filterable,
		columns : [ {
			field : "appCategoryName",
			title : "名字"
		}, {
			field : "type",
			title : "类型",
			width: "80px",
			values: [{text:"游戏",value:0},{text:"应用",value:1}]
		},{
			field: "priority",
			title: "优先级"
		},{
			field : "imageUrl",
			title : "图片",
			template : function(dataItem) {
				return '<img width="80" height="80" src=\'' + dataItem.imageUrl + '\'></img>';
			}
		}]
	});
	
});

function addAppCate(){
	loadPage("ht/cateAdd.html");
}

function deleteAppCate(){
	
	var row = getSelectedRowDataByGridWithMsg("grid");
	if(row){
		postAjaxRequest("/api/spapp/cate/delete.json", {categoryId:row._id}, function(){dataSource.read();});
	}
}

function editAppCate() {
	var row = getSelectedRowDataByGridWithMsg("grid");
	if (row) {
		loadPage("ht/cateAdd.html", {
			_id : row._id
		});
	}
}