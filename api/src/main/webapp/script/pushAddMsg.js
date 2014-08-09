var msgModel = kendo.data.Model.define({
	id : "_id",
	fields : {
		_id : {
			editable : false,
			nullable : true
		},
		pushVersion : {},
		title : {
			validation : {
				required : true
			}
		},
		description : {
			validation : {
				required : true
			}
		},
		pushType : {
			validation : {
				required : true
			},
			defaultValue:"2"
		},
		url : {},
		imei : {},
		icon:{},
		downloadUrl:{},
		downloadType:{type:"string",defaultValue:"2"}
	}
});
var model = new msgModel();
$(document).ready(function() {
	$("#pushType").change(function(){
		var v = $(this).children('option:selected').val();
		if("3" == v){//url
			$("#pushurldiv").show();
			$("#pushappdiv").hide();
		}else if("2" == v){//text
			$("#pushurldiv").hide();
			$("#pushappdiv").hide();
		} else if("1" == v){//app
			$("#pushappdiv").show();
			$("#pushurldiv").hide();
			initAppSelect();
		}
	});
	$("#startDate").kendoDatePicker({
		value: new Date(),
    	format: "yyyy-MM-dd",
    	 culture: "de-DE"
    });
	$("#endDate").kendoDatePicker({
		value: new Date(),
    	format: "yyyy-MM-dd",
    	 culture: "de-DE"
    });
	$("#pushVersion").kendoMultiSelect({
		dataTextField : "versionCodeText",
		dataValueField : "versionCode",
		dataSource : {
			transport : {
				read : {
					url : "/api/sppush/listpushversions.json",
					dataType : "json"
				}
			},
			schema: {
			    data: "data"
			}
		}
	});
	$("#productModel").kendoMultiSelect({
		dataTextField : "productModelText",
		dataValueField : "productModel",
		dataSource : {
			transport : {
				read : {
					url : "/api/sppush/listproductmodels.json",
					dataType : "jsonp"
				}
			},
			schema: {
			    data: "data"
			}
		}
	});
    $("#pushAddress").kendoMultiSelect({
        dataSource: {
    		transport : {
    			read : {
    				url : "/api/sppush/listCitys.json",
    				dataType : "json"
    			}
    		},
    		schema: {
    		    data: "data"
    		}
        }
    });
    
	kendo.bind($("#addMsg"), model);
	
	function initAppSelect(){
		if (!$("#appGrid").data("kendoGrid")){
			$("#appGrid").kendoGrid({
				dataSource : new LocalDataSource({
					read: "/api/sppush/listCommerceApp.json",
					pageSize:5
				}),
				pageable : true,
				sortable : true,
				filterable : filterable,
				selectable: "row",
				columns : [ 
				    {field : "name", title : "应用名称"},
				    {field : "logoUrl",title : "图标",
				    	template : function(dataItem) {
				    		return '<img width="50" height="50" src=\'' + dataItem.logoUrl + '\'></img>';
				    	}
				    },
				]
			});
		}
	}
	
});
function sendPush(){
	var validator = $("#addMsg").kendoValidator().data("kendoValidator");
	var v = $(this).children('option:selected').val();
	if("1" == v){//url
		var url = $("#url").val();
		if(! isURL(url)){
			alert("请验证Url");
		}
	}else if("2" == v){//text
		
	} else if("3" == v){//app
		var appId = $("#referAppId").val();
		if(appId.length==0){
			alert("请选择应用");
		}
	}
	var imei = $("#imei").val();
	var versions = $("#pushVersion").data("kendoMultiSelect").value();
	var pms = $("#productModel").data("kendoMultiSelect").value();
	var citys = $("#pushAddress").data("kendoMultiSelect").value();
	if(imei.length==0 && versions.length==0 && pms.length==0 && citys.length==0){
		alert("请选择接收方imei");
	}
	
	var startDate = $("#startDate").val();
	var endDate = $("#endDate").val();
	
	model.set("startDate",startDate);
	model.set("endDate",endDate);
	model.set("pushVersion",versions);
	model.set("productModel",pms);
	model.set("pushAddress",citys);
	if (!validator.validate()) {
		return;
    } else{
    	postAjaxRequest("/api/sppush/addMsg.json", {model : kendo.stringify(model)}, function(data){
			loadPage("ht/pushMsgList.html");
    	},true);
    }
};

function selectApp(){
	var rowData = getSelectedRowDataByGrid("appGrid");
	if (rowData == null){
		alert("请点击选择一条项目记录！");
		return;
	}
	model.set("title",rowData.name);
	model.set("icon",rowData.logoUrl);
	model.set("referAppId",rowData._id);
	model.set("description",rowData.description);
}

