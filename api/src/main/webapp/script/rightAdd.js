var detailUrl = "/api/spright/detail.json";
var addUrl = "/api/spright/addOrUpdate.json";
var catModel = kendo.data.Model.define({
	id : "_id",
	fields : {
		_id : {
			editable : false,
			nullable : true
		},
		rightName:{},
		rightUrl: {},
		common: {common:"number"},
		rightDesc: {},
	
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
	// 初始化空对象
	if (data) {
		requestDataItem = data;
	}
	
	//$("#imageUrl").attr("src",  requestDataItem.imageUrl);
	
	requestDataItem = new catModel(requestDataItem);
	kendo.bind($("#cate-edit"), requestDataItem);
}


function saveRight(){
	var validator = $("#cate-edit").kendoValidator().data("kendoValidator");
	if (!validator.validate()) {
		return;
    } else {
    	if(!requestDataItem.rightUrl){
    		alert("URL不能为空");
    	}else{
    		postAjaxRequest(addUrl, {models:kendo.stringify(requestDataItem)}, saveSucess);
    	}
    }
}