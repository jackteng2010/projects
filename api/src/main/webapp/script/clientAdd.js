var detailUrl = "/api/sp/client/detail.json"
var saveUrl = "/api/sp/client/save.json";
var model = kendo.data.Model.define({
	id : "_id",
	fields : {
		_id : {
			editable : false,
			nullable : true
		},
		packageName: {},
		versionName: {},
		versionCode:{type:"number"},
		clientType:{type:"string",defaultValue:"newRom"},
		flashType:{type:"string",defaultValue:"cardFlash"}
	}
});
var entity = new model();
$(document).ready(function() {
	$("#describeHtml").kendoEditor({
		tools: [
                "bold",
                "italic",
                "underline",
                "strikethrough",
                "justifyLeft",
                "justifyCenter",
                "justifyRight",
                "justifyFull",
                "insertUnorderedList",
                "insertOrderedList",
                "indent",
                "outdent",
                "createLink",
                "unlink",
                "insertImage",
                "subscript",
                "superscript",
                "createTable",
                "addRowAbove",
                "addRowBelow",
                "addColumnLeft",
                "addColumnRight",
                "deleteRow",
                "deleteColumn",
                "viewHtml",
                "formatting",
                "fontName",
                "fontSize",
                "foreColor",
                "backColor"
            ]
	});
	
	$("#diffChild").kendoGrid({
		toolbar: [{ name: "create", text: "新增" }],
		columns : [ {
			field : "oldVersionName",
			title : "差分版本"
		},{
			field : "size",
			title : "大小"
		},{
			field : "downloadUrl",
			title : "阿里云"
		},{
			field : "baiduYunUrl",
			title : "百度云"
		},{
			command: [{ name: "destroy", text: "" }]
		}],
		editable: {
        	confirmation: false
        }
	});
	if(redirectParams){
		postAjaxRequest(detailUrl, redirectParams, function(e){
			console.log(e);
			entity = new model(e);
			kendo.bind($("#editform"), entity);
			if(e.diffChild){
				$("#diffChild").data("kendoGrid").dataSource.data(e.diffChild);
			}
		});
	} else {
		kendo.bind($("#editform"), entity)
	}
});

function save(){
	var validator = $("#editform").kendoValidator().data("kendoValidator");
	if (validator.validate()) {
		var tt = $("#diffChild").data("kendoGrid").dataSource.data().toJSON();
		entity.diffChild = tt;
		postAjaxRequest(saveUrl, {models:kendo.stringify(entity)}, saveSucess);
    }
}