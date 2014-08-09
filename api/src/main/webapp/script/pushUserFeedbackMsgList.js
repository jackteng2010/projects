var feedbackTypes = [];
var listUrl =  "/api/sppush/listUserFeedbackPushMsg.json" ;
	var myModel = kendo.data.Model.define({
	    id: "_id",
	    fields: {
	   	   _id: {editable: false, nullable: true},
	       imei: {},
	       clientName: {},
	       channelId: {}
	    }
	});
	var dataSource = new kendo.data.DataSource({
	    transport: {
	        read:  {
	            url: listUrl,
	            dataType: "jsonp",
	            type : "post"
	        }
	    },
	    pageSize: 10,
	    batch: false,
		serverPaging: true,
		serverSorting: true,
		serverFiltering : true,
	    schema: {
	        model: myModel,
	        total: "total",
	        data: function(response) {
	        	if(response.data instanceof Array){
	        		feedbackTypes = response.feedbackPushTypes;
	        		return response.data;
	        	}else{
	        		return response;
	        	}
	        },
	        errors: function(response) {
	            return response.msg;
	        }
	    },
	    error: function(e) {
	    	alert(e.errors);
	    	this.read();
	    }
	});
$(document).ready(function() {
	var filterType = {
			ui: function(e){
				e.kendoDropDownList({
					dataSource : feedbackTypes,
					optionLabel : "全部"
				});
			}
	};
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
	
	$("#excelBt").click(function(){
		var query=$('#searchForm').serialize();
		$(this).attr('href',"/api/spreport/push/userFeedbackMsg/excel?"+query); 
	});
	
	$("#msgListGrid").kendoGrid({
		dataSource : dataSource,
		pageable : pageable,
		sortable : true,
		resizable: true,
		filterable : filterable,
		selectable: "row",
		columns : [ {
			field : "deviceId",
			title : "IMEI",
			width : "120px"
		}, 
		{
			field : "isAli",
			title : "是否阿里",
			width : "80px",
			template : function(dataItem) {
				if(dataItem.isAli == true) return "是";
				if(dataItem.isAli == false) return "否";
				return "未知";
			},
			filterable : filterYesNo
		},
		{
			field : "feedbackPushType",
			title : "反馈类型",
			width : "120px",
			filterable : filterType
			
		}, 
		{
			field : "feedbackPushEmail",
			title : "联系方式",
			width : "150px"
		},
		{
			field : "feedbackPushPicture",
			title : "反馈图片",
			width : "200px",
			template : function(dataItem) {
				if(dataItem.feedbackPushPicture==""||dataItem.feedbackPushPicture==null){
					return " 无 " ;
				}
				return '<img width="80" height="80" src=\'' + dataItem.feedbackPushPicture + '\'></img>';
			}
		},
		{
			field : "feedbackPushDescription",
			title : "反馈描述",
			width : "200px"
		},
		{
			field : "createdDate",
			title : "创建时间",
			width : "100px"		
		},
		{
			field : "productModel",
			title : "机型",
			width : "120px",			
		}, 
		{
			field : "romVersion",
			title : "版本号",
			width : "120px"			
		},
		{
			field : "versionCode",
			title : "PUSH版本",
			width : "180px"			
		}]
	});
});

function deleteUserFeedbackMsg(){
	var row = getSelectedRowDataByGridWithMsg("msgListGrid");
	if(row){
		postAjaxRequest("/api/sppush/deleteUserFeedbackMsg.json", {categoryId:row._id}, function(){dataSource.read();});
	}
}

