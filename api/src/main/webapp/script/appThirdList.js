var catModel = kendo.data.Model.define({
	id : "_id",
	fields : {
		_id : {editable : false,nullable : true},
		name:{},
		size: {},
		sourceFlag:{type:"number"},
		versionName:{},
		versionCode:{type:"number"},
		downloadUrl:{}
	}
});

var dataSource = new kendo.data.DataSource({
    transport: {
        read:  {
            url: "/api/spapp/tp/searchJY.json",
            dataType: "jsonp",
            type : "post",
            data: function(){
            	return {
            		keyword: $("#search-input").val()
            	};
            }
          }
    },
    schema: {
        total: "total",
        data: function(response) {
        	if(response.data instanceof Array){
        		return response.data;
        	}else{
        		return response;
        	}
        },
        model : catModel,
        errors: function(response) {
            return response.msg;
        }
    },
    error: function(e) {
    	alert(e.errors);
    },
	pageSize : 15,
	serverPaging : true,
	serverSorting : true,
	serverFiltering : true
});

$(document).ready(function() {
	$("#grid").kendoGrid({
		dataSource : dataSource,
		pageable : pageable,
		selectable : "row",
		sortable : true,
		filterable : filterable,
		columns : [ {
			field : "name",
			title : "名字"
		} , {
			field : "size",
			title : "大小"
		}, {
			field : "sourceFlag",
			title : "来源",
			values: [ { text: "天翼", value: 1 },{ text: "百度", value: 2 },{ text: "九游", value: 3 }],
			filterable: false
		}, {
			field : "versionName",
			title : "版本名"
		}, {
			field : "versionCode",
			title : "版本号"
		}, {
			field : "downloadUrl",
			title : "下载地址",
			template :  function(dataItem) {
				return '<a class="pointer" href="' + dataItem.downloadUrl +  '" ><img height="15px" widht="15px" src="images/recycle.png" /></a>';
			}
		} ]
	});
	
});
function convert() {
	var row = getSelectedRowDataByGridWithMsg("grid");
	if (row) {
		loadPage("ht/appAdd.html", {
			_id : row._id,
			sourceFlag: row.sourceFlag,
			versionCode: row.versionCode,
			packageName: row.packageName

		});
	}
}
function search(){
	var value = $("#search-input").val();

	var searchDataSource = new kendo.data.DataSource({
		transport : {
			read:  {
		           url:  "/api/spapp/tp/search.json",
		           dataType: "jsonp",
		           type : "post",
		           data: function(){
		           	return {
		           		keyword: $("#search-input").val()
		           	};
		           }
		         }
		},
	    schema: {
	        total: "total",
	        data: function(response) {
	        	if(response.data instanceof Array){
	        		return response.data;
	        	}else{
	        		return response;
	        	}
	        },
	        model : catModel,
	        errors: function(response) {
	            return response.msg;
	        }
	    },
	    error: function(e) {
	    	alert(e.errors);
	    }
	});

	if(value != ""){
		$("#grid").kendoGrid({
			dataSource : searchDataSource,
			pageable : pageable,
			selectable : "row",
			sortable : true,
			filterable : filterable,
			columns : [ {
				field : "name",
				title : "名字"
			} , {
				field : "size",
				title : "大小"
			}, {
				field : "sourceFlag",
				title : "来源",
				values: [ { text: "天翼", value: 1 },{ text: "百度", value: 2 },{ text: "九游", value: 3 }],
				filterable: false
			}, {
				field : "versionName",
				title : "版本名"
			}, {
				field : "versionCode",
				title : "版本号"
			},{
				field : "downloadUrl",
				title : "下载地址",
				template :  function(dataItem) {
					return '<a class="pointer" href="' + dataItem.downloadUrl +  '" ><img height="15px" widht="15px" src="images/recycle.png" /></a>';
				}
			} ]
		});
	}
}