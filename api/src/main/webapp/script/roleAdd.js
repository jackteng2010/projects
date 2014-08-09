var detailUrl = "/api/sprole/detail.json";
var addUrl = "/api/sprole/addOrUpdate.json";
var catModel = kendo.data.Model.define({
	id : "_id",
	fields : {
		_id : {
			editable : false,
			nullable : true
		},
		roleName:{},
		roleValue: {},
		//rights: {},
		roleDesc: {},
	
	}
});

var roleDataSource = new kendo.data.DataSource({
	transport : {
		read : {
			url : "/api/sprole/right/listforsearch.json",
			dataType : "jsonp"
		}
	},
	schema : {
		total: "total", // total is returned in the "total" field of the response
		data: "data"
	}
});

//var requestDataItem = undefined;
var requestDataItem = new catModel();

$(document).ready(function() {

	$("#rights").kendoMultiSelect({
		dataTextField : "rightName",
		dataValueField : "_id",
		dataSource : roleDataSource
	});
	
	if(redirectParams){
		
		postAjaxRequest(detailUrl, redirectParams, edit);
	}
	
//	requestDataItem = new catModel();
//	requestDataItem.set("priority", 100);
	kendo.bind($("#cate-edit"), requestDataItem);
	
});

function edit(data){
	if (data) {
		requestDataItem = data;
	}
	requestDataItem = new catModel(requestDataItem);
	kendo.bind($("#cate-edit"), requestDataItem);
}


function saveRole(){
	var validator = $("#cate-edit").kendoValidator().data("kendoValidator");
	if (!validator.validate()) {
		return;
    } else {
		if(!requestDataItem.roleName){
    		alert("角色名不能为空");
    	}else{
    		
    		var selectedValues = $("#rights").data("kendoMultiSelect").value();
    		if (!selectedValues || selectedValues.length == 0) {
    			alert("权限URL不能为空");
    			return;
    		}

    		requestDataItem.rights = selectedValues;
    		postAjaxRequest(addUrl, {models:kendo.stringify(requestDataItem)}, saveSucess);
    	}
    }
}