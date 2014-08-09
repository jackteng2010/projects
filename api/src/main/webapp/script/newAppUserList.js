var myModel = kendo.data.Model.define({
	id : "_id",
	fields : {
		_id : {editable : false,nullable : true},
		romVersion:{type:"string",editable : false},
		productModel: {editable : false}
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
/*	$("#startDateRom").kendoDatePicker({
		value: new Date(),
    	format: "yyyy-MM-dd",
    	culture: "de-DE"
    });
	$("#endDateRom").kendoDatePicker({
		value: new Date(),
    	format: "yyyy-MM-dd",
    	culture: "de-DE"
    });*/
	
	$("#grid").kendoGrid({
	    dataSource: {
		    transport: {
		        read:  {
		            url: "/api/spreport/app/clientDetailCount.json",
		            dataType: "jsonp",
		            type : "get"
		        }
		    },
		    schema: {
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
		    }
		},
		autoBind: false,
	    columns: [
	        { field: "clientName", title: "客户端",
				template :  function(e) {
					if(e.clientName == 'com.greenorange.yuleclient') return '游戏中心';
					if(e.clientName == 'com.greenorange.appmarket') return '应用市场';
					if(e.clientName == 'com.greenorange.assistivetouch') return '橙心小助手';
					if(e.test11 == null) return 'MyUI主题壁纸';
					return e.clientName;
				} },
				{ field: "GO NX", title: "GO NX",
					template :  function(e) {
						return e.msg['GO NX'];
					}
				},
				{ field: "GO N1-Y", title: "GO N1-Y",
					template :  function(e) {
						return e.msg['GO N1-Y'];
					}
				},
				{ field: "GO N1-T", title: "GO N1-T",
					template :  function(e) {
						return e.msg['GO N1-T'];
					}
				},
				{ field: "GO N1", title: "GO N1",
					template :  function(e) {
						return e.msg['GO N1'];
					}
				},
				{ field: "GO M3", title: "GO M3",
					template :  function(e) {
						return e.msg['GO M3'];
					}
				},
				{ field: "GO M2S", title: "GO M2S",
					template :  function(e) {
						return e.msg['GO M2S'];
					}
				},
				{ field: "others", title: "其他机型",
					template :  function(e) {
						return e.msg.others;
					}
				},
				{ field: "total", title: "总数",
					template :  function(e) {
						return e.msg.total;
					}
				}
	    ]
	});

	$("#gridRom").kendoGrid({
	    dataSource: {
		    transport: {
		        read:  {
		            url: "/api/spreport/app/pushRomCount.json",
		            dataType: "jsonp",
		            type : "get"
		        },
		        model: myModel
		    },
		    aggregate: [{ field: "count", aggregate: "sum"}],
		    schema: {
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
		    }
		},
		autoBind: false,
		selectable : "row",
		sortable : true,
//		resizable: true,
//		filterable : filterable,
	    columns: [
	        { field: "romVersion", title: "Rom版本",
				template :  function(e) {
					return e.romVersion;
				} },
				{ field: "productModel", title: "手机型号",
					template :  function(e) {
						return e.productModel;
					}
				},
				{ field: "count", title: "总数",
					template :  function(e) {
						return e.count;
					},
		            footerTemplate:"总数: #: sum #"
				}
	    ]
	});
	
	$("#searchBt").click(function(){
		var s = $("#startDate").val();
		var e = $("#endDate").val();
		if(s == "" || e == ""){
			alert("日期不可为空！");
		}
		$("#grid").data("kendoGrid").dataSource.read({ "startDate": s, "endDate": e});
	});

	$("#searchBtRom").click(function(){
		/*var s = $("#startDateRom").val();
		var e = $("#endDateRom").val();
		if(s == "" || e == ""){
			alert("日期不可为空！");
		}*/
		$("#gridRom").data("kendoGrid").dataSource.read();
	});
});


