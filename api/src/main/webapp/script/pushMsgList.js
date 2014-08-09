var msgListDS = new kendo.data.DataSource({
	transport : {
		read : {
			url : "/api/sppush/listMsg.json",
			dataType : "jsonp",
			type : "post"
		}
	},
	schema: {
	    total: "total", // total is returned in the "total" field of the response
	    data: "data"
	},
	pageSize: 10,
	serverPaging: true,
	serverSorting: true,
	serverFiltering : true,
	batch : true,
	parameterMap : function(options, operation) {
		if (operation !== "read" && options.models) {
			return {
				models : kendo.stringify(options.models)
			};
		}
	}
});

$(document).ready(function() {
	checkRoles();
	$("#msgListGrid").kendoGrid({
		dataSource : msgListDS,
		pageable : true,
		sortable : true,
		filterable : filterable,
		detailTemplate: kendo.template($("#template").html()),
        detailInit: detailInit,
		columns : [ {
			field : "title",
			title : "标题",
			width : "300px"
		}, {
			field : "pushType",
			title : "类型",
			values: [{text:"应用",value:"1"},{text:"文本",value:"2"},{text:"超链接",value:"3"}]
		}, {
			field : "totalCount",
			title : "总数量",
			width : "100px"
		}, {
			field : "receivedCount",
			title : "已发数量",
			width : "100px"
		}, {
			field : "startDate",
			title : "开始日期",
			width : "100px"
		}, {
			field : "endDate",
			title : "结束日期",
			width : "100px"
		}, {
			field : "createdDate",
			title : "创建时间",
			width : "100px"
		}]
	});
});
function detailInit(e) {
    var detailRow = e.detailRow;
    detailRow.find("#pushtabtrip").kendoTabStrip({
        animation: {
            open: { effects: "fadeIn" }
        }
    });
    detailRow.find("#pushitemgrid").kendoGrid({
        dataSource: {
        	transport : {
        		read : {
        			url : "/api/sppush/listMsgItem.json?_id="+e.data._id,
        			dataType : "jsonp",
        			type : "post"
        		}
        	},
        	schema: {
        	    total: "total",
        	    data: "data"
        	},
        	pageSize: 5,
        	serverPaging: true,
        	serverSorting: true,
        	serverFiltering : true,
        	batch : true,
        	parameterMap : function(options, operation) {
        		if (operation !== "read" && options.models) {
        			return {
        				models : kendo.stringify(options.models)
        			};
        		}
        	}
        },
        filterable : filterable,
        scrollable: false,
        sortable: true,
        pageable: true,
        columns: [
            { field: "deviceId", title:"接收方"},
            { field: "sendDate", title:"发送时间" }
        ]
    });

}

function addMsg(){
	loadPage("ht/pushAddMsg.html");
}	