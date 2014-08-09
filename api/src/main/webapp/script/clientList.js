var myModel = kendo.data.Model.define({
    id: "_id",
    fields: {
   	   _id: {editable: false, nullable: true},
   	   nickName: {validation: {required: true},defaultValue:"青橙N1 ROM"},
       packageName: {validation: {required: true},defaultValue:"GO N1"},
       versionName: {validation: {required: true},defaultValue:0},
       oldVersionCode: {type:"number",validation: {required: true}, defaultValue:0,min:1},
       versionCode: {type:"number",validation: {required: true},min:1},
       orderIndex: {type:"number",validation: {required: true}},
       clientType: {type:"string",validation: {required: true},defaultValue:"newRom"},
       romType: {type:"string",validation: {required: true},defaultValue:"full"},
       flashType: {type:"string",validation: {required: true},defaultValue:"cardFlash"},
   	   downloadUrl:{validation: {required: true }},
   	   downloadUrl2:{},
   	   baiduYunUrl:{},
       describe:{type:"string"}
    }
});

var dataSource = new kendo.data.DataSource({
    transport: {
        read:  {
            url: "/api/sp/client/list.json",
            dataType: "jsonp",
            type : "post"
        }
    },
    batch: false,
	serverSorting: true,
	serverFiltering : true,
	group: { field: "packageName" },
    schema: {
        model: myModel,
        total: "total",
        data: function(response) {//{status:1,data:[{...},{...}]}
        	if(response.data instanceof Array){
        		return response.data;
        	}else{//{status:1,_id:"1111",name:"J.T"}
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
var clientTypes = [{
    "value": "newRom",
    "text": "尝鲜版ROM"
},{
    "value": "stableRom",
    "text": "稳定版ROM"
},{
    "value": "testRom",
    "text": "测试版ROM"
},{
    "value": "apk",
    "text": "安卓APK"
},{
    "value": "exe",
    "text": "一键升级"
}];
var romTypes = [{
    "value": "full",
    "text": "完整包"
},{
    "value": "diff",
    "text": "差分包"
}];
var flashTypes = [{
    "value": "cardFlash",
    "text": "卡刷"
},{
    "value": "lineFlash",
    "text": "线刷"
}];
$(document).ready(function () {
    $("#grid").kendoGrid({
        dataSource: dataSource,
	    resizable: true,
        sortable : true,
		filterable : filterable,
		selectable : "row",
        columns: [
            { field: "packageName", title: "机型",width:"100px"},
            { field: "clientType", title: "类型" , values: clientTypes, width:"100px",filterable : false},
            { field: "flashType", title: "刷机类型" , width:"100px", values: flashTypes,filterable : false},
            { field: "versionName", title: "版本名",width:"180px"},
            { field: "versionCode", title: "版本号(排序)",width:"100px"},
            { field: "publishDate", title: "发布日期" ,width:"120px"},
            { field: "size", title: "大小" ,width:"100px"},
            { field: "downloadUrl", title: "阿里云",width:"80px",
            	template:function(dataItem){
               		if(dataItem.downloadUrl && dataItem.downloadUrl != ""){
            			return '<a class="pointer" href="' + dataItem.downloadUrl + '"><img height="15px" widht="15px" src="images/recycle.png" /></a>';
            		} else{
            			return "无";
            		}
            	}
            },
            { field: "baiduYunUrl", title: "百度云",width:"80px",
            	template:function(dataItem){
            		if(dataItem.baiduYunUrl && dataItem.baiduYunUrl != ""){
            			return '<a class="pointer" href="' + dataItem.baiduYunUrl + '"><img height="15px" widht="15px" src="images/recycle.png" /></a>';
            		} else{
            			return "无";
            		}
            	}
            }
        ]
    });
    
});

function addClient(){
	loadPage("ht/clientAdd.html");
}
function editClient(){
	var row = getSelectedRowDataByGridWithMsg("grid");
	if (row) {
		loadPage("ht/clientAdd.html", { _id : row._id });
	}
}
function deleteClient(){
	var row = getSelectedRowDataByGridWithMsg("grid");
	if (row && confirm("确认删除？")) {
		postAjaxRequest("/api/sp/client/delete.json", {_id:row._id}, function(e){
			dataSource.read();
		});
	}
}