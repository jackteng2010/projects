var myModel = kendo.data.Model.define({
    id: "_id",
    fields: {
   	   _id: {editable: false, nullable: true},
       imei: {},
       packageName: {},
       versionCode: {},
       channelId: {},
       uuid: {},
       phone: {},
       sysVersion: {},
       createdOn:{},
       updatedOn:{}
    }
});
var myModel02 = kendo.data.Model.define({
    id: "_id",
    fields: {
   	   _id: {editable: false, nullable: true},
   	   GreenOrange: {type:"number",defaultValue:0},
   	   GOAppMarket:{type:"number",defaultValue:0}
    }
});
var mobileNames = [];
var dataSource = new kendo.data.DataSource({
    transport: {
        read:  {
            url: "/api/spuser/mobile/list.json",
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
        		mobileNames = response.mobileNames;
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

var dataSource02 = new LocalDataSource({
	read: "/api/spreport/mobile/count.json",
	model: myModel02
});


$(document).ready(function () {
    $("#grid").kendoGrid({
        dataSource: dataSource,
	    pageable: true,
	    resizable: true,
        sortable : true,
		filterable : filterable,
        columns: [
            { field: "channelId", title: "渠道" ,values:["111","222"]},
            { field: "imei", title: "IMEI"},
            { field: "uuid", title: "UUID"},
            { field: "createdDate", title: "添加时间"},
            { field: "updatedDate", title: "更新时间"}
        ],
        selectable: "row"
    });
    
    
    $("#grid02").kendoGrid({
        dataSource: dataSource02,
        columns: [
            { field: "month", title: "日期" },
            { field: "GreenOrange", title: "娱乐中心用户数量"},
            { field: "GOAppMarket", title: "应用市场用户数量"}
        ],
        selectable: "row"
    });
    

    $("#categoryType").kendoDropDownList({
        dataTextField: "text",
        dataValueField: "value",
        dataSource: [{text:"月",value:"month"},{text:"日",value:"day"},{text:"年",value:"year"}],
        change: function() {
            var value = this.value();
            if (value) {
            	dataSource02.filter({ rt: value});
            } else {
            	dataSource02.dataSource.filter({});
            }
        }
    });
    
});


