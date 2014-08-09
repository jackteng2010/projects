var detailUrl = "/api/spapp/cate/detail.json"
var addUrl = "/api/spapp/cate/add.json";
var catModel = kendo.data.Model.define({
	id : "_id",
	fields : {
		_id : {
			editable : false,
			nullable : true
		},
		appCategoryName:{},
		imageUrl: {},
		type:{type:"number"},
		priority:{type:"number"},
		activity:{type:"number"},
		gamerAmount:{type:"number"}
	}
});

var requestDataItem = undefined;
$(document).ready(function() {
	if(redirectParams){
		postAjaxRequest(detailUrl, redirectParams, edit);
	}
	requestDataItem = new catModel();
	requestDataItem.set("priority", 100);
	kendo.bind($("#cate-edit"), requestDataItem);
});

function edit(data){
	if (data) {
		requestDataItem = data;
	}
	$("#imageUrl").attr("src",  requestDataItem.imageUrl);
	requestDataItem = new catModel(requestDataItem);
	kendo.bind($("#cate-edit"), requestDataItem);
}

function uploadFile() {
	var options = {
		width : "680px",
		height : "400px",
		title : "上传图片"
	};
	var params = {
			upload_url : imgUploadUrl,
			callBack : uploadCallBack,
			limit : 1
	};
	openRemotePageWindow(options, "upload_up", params);
}

function uploadCallBack(data) {
	if (data.length == 1) {
		var cateImg = imageUrl + data[0].img_url;
		$("#imageUrl").attr("src", cateImg);
		requestDataItem.set("imageUrl", cateImg);
	}
}

function saveAppCate(){
	var validator = $("#cate-edit").kendoValidator().data("kendoValidator");
	if (!validator.validate()) {
		return;
    } else {
    	if(!requestDataItem.imageUrl){
    		alert("图片不能为空");
    	}else{
    		postAjaxRequest(addUrl, {models:kendo.stringify(requestDataItem)}, saveSucess);
    	}
    }
}