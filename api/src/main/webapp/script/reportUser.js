$(document).ready(function () {

$("#grid").kendoGrid({
    dataSource: {
	    transport: {
	        read:  {
	            url: "/api/spreport/listdailymobile.json",
	            dataType: "jsonp",
	            type : "get"
	        },
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
	    },
	    error: function(e) {
	    	if(e.errors=="请先登录") window.location = "login.jsp";
	    	else alert(e.errors);	
	    },
        aggregate: [ 
   		        { field: "registerCount", aggregate: "sum"},
		        { field: "loginCount", aggregate: "sum"},
		        { field: "imeiCount", aggregate: "sum"}
        ]
	},
    groupable: {
        messages: {
        	empty: "可 将 标 题 拖 动 到 此 处 进 行 分 组 统 计"
        }
    },
    columns: [
        { field: "month", title: "月份"},
        { field: "day", title: "日期"},
        { field: "clientName", title: "客户端",
			template :  function(e) {
				if(e.clientName == 'com.greenorange.yuleclient') return '游戏中心';
				if(e.clientName == 'com.greenorange.appmarket') return '应用市场';
				if(e.clientName == 'com.greenorange.assistivetouch') return '橙心小助手';
				return e.clientName;
			}	
        },
        { field: "channelId", title: "渠道号",
        	template :  function(e) {
        		var channelName = e.channelId;
        		if(channelName == 'CXW01') return '通用版本';
        		if(channelName == 'CXW02') return '青橙市场';
        		if(channelName == 'CXW03') return '91市场';
        		if(channelName == 'CXW04') return '百度市场';
        		if(channelName == 'CXW05') return '360市场';
        		if(channelName == 'CXW06') return '豌豆荚市场';
        		
         		if(channelName == 'ZMW01') return '通用版本';
        		if(channelName == 'ZMW02') return '青橙市场';
        		if(channelName == 'ZMW03') return '91市场';
        		if(channelName == 'ZMW04') return '百度市场';
        		if(channelName == 'ZMW05') return '360市场';
        		if(channelName == 'ZMW06') return '豌豆荚市场';
        		return channelName;
			}	
        },
        { field: "registerCount", title: "激活数",
        	groupable: false,
            aggregates: ["sum"],
            groupFooterTemplate: "共: #:sum#",
            footerTemplate:"总数: #: sum #"
        },
        { field: "loginCount", title: "启动量",
        	groupable: false,
            aggregates: [ "sum"],
            groupFooterTemplate: "共: #:sum#",
            footerTemplate:"总数: #: sum #"
        },
        { field: "imeiCount", title: "IMEI启动量",
        	template :  function(c) {
        		if(c.imeiCount ==""||c.imeiCount=="undefined"||c.imeiCount==null){return 0;}else{return c.imeiCount;}
        	},
        	groupable: false,
            aggregates: [ "sum"],
            groupFooterTemplate: "共: #:sum#",
            footerTemplate:"总数: #: sum #"
        }
    ]
});


});

