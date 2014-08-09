var detailUrl = "/api/spuser/detail.json";
var addUrl = "/api/spuser/addOrUpdate.json";
var catModel = kendo.data.Model.define({
	id : "_id",
	fields : {
		_id : {
			editable : false,
			nullable : true
		},
		userName:{},
		email: {},
		phone: {},
		roles: {},
		regDate: {},
	
	}
});

var roleDataSource = new kendo.data.DataSource({
	transport : {
		read : {
			url : "/api/spuser/role/listforsearch.json",
			dataType : "jsonp"
		}
	},
	schema : {
		total: "total", // total is returned in the "total" field of the response
		data: "data"
	}
});

var requestDataItem = new catModel();


$(document).ready(function() {

	$("#roleName").kendoMultiSelect({
		dataTextField : "roleName",
		dataValueField : "_id",
		dataSource : roleDataSource
	});
	
	if(redirectParams){
		
		postAjaxRequest(detailUrl, redirectParams, edit);
	}
	
	kendo.bind($("#cate-edit"), requestDataItem);
	
});

function edit(data){
	if (data) {
		requestDataItem = data;
	}
	requestDataItem = new catModel(requestDataItem);
	kendo.bind($("#cate-edit"), requestDataItem);
}


function saveUser(){
	var validator = $("#cate-edit").kendoValidator().data("kendoValidator");
	if (!validator.validate()) {
		return;
    } else {
    	if(!requestDataItem.userName){
    		alert("用户名不能为空");
    	}else{
    		var selectedValues = $("#roleName").data("kendoMultiSelect").value();
    		if (!selectedValues || selectedValues.length == 0) {
    			alert("角色名不能为空");
    			return;
    		}
    		requestDataItem.roles = selectedValues;
    		
    		postAjaxRequest(addUrl, {models:kendo.stringify(requestDataItem)}, saveSucess);
    	}
    }
}