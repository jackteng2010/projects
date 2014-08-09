var detailUrl = "/api/spuser/loadSelfInfo.json"
var editUrl = "/api/spuser/updateSelfInfo.json";
var userModel = kendo.data.Model.define({
	id : "_id",
	fields : {
		_id : {
			editable : false,
			nullable : true
		},
		userName:{},
		password: {},
		email:{},
		phone:{},
		regDate:{}
	}
});

var requestDataItem = undefined;
$(document).ready(function() {
	postAjaxRequest(detailUrl, redirectParams, edit);
});

function edit(data){
	if (data) requestDataItem = data;
	requestDataItem = new userModel(requestDataItem);
	kendo.bind($("#user-edit"), requestDataItem);
}

function submitUserInfo(){
	var validator = $("#user-edit").kendoValidator().data("kendoValidator");
	if (!validator.validate()) {
		return;
    } else {
    	postAjaxRequest(editUrl, {models:kendo.stringify(requestDataItem)}, function(){
    		alert("修改成功");
    	});
    }
}