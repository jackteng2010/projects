
var listUrl = "/api/sp/market/pic/listall.json"
var catModel = kendo.data.Model.define({
	id : "_id",
	fields : {
		_id : {editable : false,nullable : true},
		startDate: {},
		//imageUrl: {},
		endDate: {}
	
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
    $("#grid").kendoGrid({
		dataSource : dataSource,
		pageable : pageable,
		selectable : "row",
		sortable : true,
		resizable: true,
		filterable : filterable,
		columns : [ {
			field : "startDate",
			title : "开始时间"
		}, {
			field : "endDate",
			title : "结束时间",
			
		},{
			field : "screenShotPc",
			title : "图片",
			template : function(dataItem) {
				var str="";
				if(dataItem.screenShotPc){
					for (i = 0; i < dataItem.screenShotPc.length; i++) {
		    			str +='<img width="80" height="80" src=\'' + dataItem.screenShotPc[i] + '\'></img>';
		    		}
				}
				return str;
			}
		}]
	});
	

});

function addAppCate(){
	loadPage("ht/uploadStartMarketPictureAdd.html");
}

function deleteAppCate(){
	
	var row = getSelectedRowDataByGridWithMsg("grid");
	if(row){
		postAjaxRequest("/api/sp/market/pic/delete.json", {appHPP_id:row._id}, function(){dataSource.read();});
	}
}

function editAppCate() {
	var row = getSelectedRowDataByGridWithMsg("grid");
	if (row) {
		loadPage("ht/uploadStartMarketPictureAdd.html", {
			_id : row._id
		});
	}
}