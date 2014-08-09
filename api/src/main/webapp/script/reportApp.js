var myModel = kendo.data.Model.define({
    id: "_id",
    fields: {
   	   _id: {editable: false, nullable: true},
       name: {},
       createdDate: {},
       viewCount:{},
       downloadCount:{},
       convertPercent:{}
    }
});
var dataSource = new kendo.data.DataSource({
    transport: {
        read:  {
            url: "/api/spreport/app/count.json",
            dataType: "jsonp",
            type : "post",
            data: function(){
            	return $("#searchForm").serializeJson();
            }
          }
    },
    aggregate: [
	    { field: "viewCount", aggregate: "sum" },
	    { field: "downloadCount", aggregate: "sum" }
    ],
    schema: {
        model: myModel,
        total: "total",
        data: function(response) {
        	if(response.data instanceof Array){
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
    }
});
$(document).ready(function () {
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
	$("#channelIds").kendoMultiSelect({
		placeholder: "全部",
		dataTextField: "text",
        dataValueField: "value",
		dataSource:gameCC
	});
	$("#clientName").change(function(){
		var value= $(this).val();
		console.log(value);
		$("#channelIds").data("kendoMultiSelect").value([]);
		if("com.greenorange.appmarket"==value){
			$("#channelIds").data("kendoMultiSelect").setDataSource(marketCC);
		} else if("com.greenorange.yuleclient"==value){
			$("#channelIds").data("kendoMultiSelect").setDataSource(gameCC);
		} else if("com.greenorange.assistivetouch"==value){
			$("#channelIds").data("kendoMultiSelect").setDataSource(assistCC);
		}
	});
	$("#searchBt").click(function(){
		dataSource.read();
	});
	$("#excelBt").click(function(){
		var query=$('#searchForm').serialize();
		$(this).attr('href',"/api/spreport/app/count/excel?"+query); 
	});
	
    $("#grid").kendoGrid({
        dataSource: dataSource,
        sortable : true,
        height: 400,
        columns: [
            { 
            	field: "name", title: "统计",
    			template : function(dataItem) {
    				if(dataItem.name){
    					return dataItem.name;
    				}else{
    					return dataItem.createdDate
    				}
    			},	
            },
            { field: "viewCount", title: "阅读量(次)",footerTemplate: "总量: #= sum #"},
            { field: "downloadCount", title: "下载量(次)",footerTemplate: "总量: #= sum #"},
            { field: "convertPercent", title: "转化量(%)"}
        ],
        selectable: "row"
    });
    
});


