var listUrl = "/api/spapp/baidu/checkupdate.json"
var dataSource = new kendo.data.DataSource({
	transport : {
		read : {
			url : listUrl,
			dataType : "jsonp",
			type:"post"
		}
	},
    batch: true,
    pageSize: 10,
	serverFiltering : true,
	schema : {
		total : "total",
		data : "data",
		model : {
			fields : {
				onLine : {
					type : "number"
				},
				isCommerce:{}
			}
		}
	}
});
function checkStatus(data){
	if(data.data){
		dataSource.data(data.data);
	}
}
var appCommerceItems = [{ text: "商业应用", value :1},{ text: "非商业应用", value :0}];

$(document).ready(function() {
	
	var grid = $("#grid").kendoGrid({
		dataSource : dataSource,
		pageable : pageable,
		selectable : "row",
		sortable : true,
		columns : [ {
			field : "name",
			title : "名字",
			width: 200
		} , {
			field : "size",
			title : "大小"
		}, {
			field : "versionNameOld",
			title : "旧版本"
		}, {
			field : "versionName",
			title : "新版本"
		}, {
			field : "versionCodeOld",
			title : "旧版本号"
		}, {
			field : "versionCode",
			title : "新版本号"
		}, {
			field : "downloadUrl",
			title : "下载地址",
			template :  function(dataItem) {
				return '<a class="pointer" href="' + dataItem.downloadUrl +  '" ><img height="15px" widht="15px" src="images/recycle.png" /></a>';
			}
		} ]
	});
	
	$("#commerceSelect").kendoDropDownList({
		dataTextField : "text",
		dataValueField : "value",
		dataSource : appCommerceItems,
		change:function(){
			var value = this.value();
			$("#grid").data("kendoGrid").dataSource.filter({ field: "isCommerce", operator: "eq", value: parseInt(value) });
			if(value == "0"){//非商业应用可见
            	$("#updateAppBut").show();
            }else{
            	$("#updateAppBut").hide();
            }
		}
	});
});

function update() {
	var row = getSelectedRowDataByGridWithMsg("grid");
	if (row) {
		loadPage("ht/appAdd.html", {
			_id : row._id,
			versionCode: row.versionCode,
			packageName: row.packageName,
			sourceFlag: row.sourceFlag
		});
	}
}

function updateDownloadUrlAndVersionCode(){
	var row = getSelectedRowDataByGrid("grid");
	if (!row){
		alert("请点击选择一条记录！");
		return;
	}
	
	var param = {packageName : row.packageName, versionCode:row.versionCode, downloadUrl:row.downloadUrl, versionName:row.versionName, size:row.size};
	console.log(param);
	postAjaxRequest("/api/spapp/updatetobaidu.json", param, updateDownloadUrlAndVersionCodeCallBack);
}

function updateDownloadUrlAndVersionCodeCallBack(){
	alert("更新成功");
	dataSource.read();
}
function search(){
	var keyword= $("#search-input").val();
	postAjaxRequest(listUrl, {
		keyword : keyword
	}, checkStatus);
}