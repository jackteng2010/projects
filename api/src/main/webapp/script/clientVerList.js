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
   	   downloadUrl:{validation: {required: true }},
   	   downloadUrl2:{},
   	   baiduYunUrl:{},
       describe:{}
    }
});

var dataSource = new kendo.data.DataSource({
    transport: {
        read:  {
            url: "/api/sp/client/list.json",
            dataType: "jsonp",
            type : "post"
        },
        update: {
            url:  "/api/sp/client/save.json",
            dataType: "jsonp",
            type : "post"
        },
        destroy: {
            url: "/api/sp/client/delete.json",
            dataType: "jsonp",
            type : "post"
        },
        create: {
            url: "/api/sp/client/save.json",
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
$(document).ready(function () {
    $("#grid").kendoGrid({
        dataSource: dataSource,
	    resizable: true,
        sortable : true,
		filterable : filterable,
        toolbar: [{name: "save", text: "保存"},{name: "cancel", text: "取消"},{ name: "create", text: "新增" }],
        columns: [
            { field: "packageName", title: "客户端",width:"250px"},
            { field: "clientType", title: "类型" , values: clientTypes, width:"100px",filterable : false},
            { field: "romType", title: "类型" , values: romTypes, width:"100px",filterable : false},
            { field: "versionCode", title: "版本号",width:"100px"},
            { field: "versionName", title: "版本名",width:"180px"},
            { field: "oldVersionName", title: "旧版本名",width:"180px"},
            { field: "publishDate", title: "发布日期" ,width:"120px"},
            { field: "size", title: "大小M" ,width:"100px"},
            { field: "downloadUrl", title: "默认地址",width:"100px",
            	template:function(dataItem){
               		if(dataItem.downloadUrl && dataItem.downloadUrl != ""){
            			return '<a class="pointer" href="' + dataItem.downloadUrl + '"><img height="15px" widht="15px" src="images/recycle.png" /></a>';
            		} else{
            			return "无";
            		}
            	}
            },
            { field: "downloadUrl2", title: "内网地址",width:"100px",
            	template:function(dataItem){
               		if(dataItem.downloadUrl2 && dataItem.downloadUrl2 != ""){
            			return '<a class="pointer" href="' + dataItem.downloadUrl2 + '"><img height="15px" widht="15px" src="images/recycle.png" /></a>';
            		} else{
            			return "无";
            		}
            	}
            },
            { field: "baiduYunUrl", title: "百度云地址",width:"100px",
            	template:function(dataItem){
            		if(dataItem.baiduYunUrl && dataItem.baiduYunUrl != ""){
            			return '<a class="pointer" href="' + dataItem.baiduYunUrl + '"><img height="15px" widht="15px" src="images/recycle.png" /></a>';
            		} else{
            			return "无";
            		}
            	}
            },
            { field: "describe", title: "备注",width:"300px"},
            { command: [{ name: "destroy", text: "删除" }], width:"90px" }
        ],
        editable: {
        	confirmation:"删除之后请保存? "
        }
    });
    
});

function uploadFile() {
	var options = {
		width : "680px",
		height : "400px",
		title : "上传APK EXE"
	};
	var params = {
		upload_url : apkUploadUrl,
		callBack : uploadAppCallBack,
		limit : 1
	};
	openRemotePageWindow(options, "upload_up", params);
}
function uploadAppCallBack(data) {
	if (data.length > 0) {
		var app = data[0];
		var url = app.apk_url;
		if(url.indexOf("http") == -1){
			url = apkUrl + url;
		}
		$("#t_packageName").html(app.packageName);
		$("#t_versionName").html(app.versionName);
		$("#t_versionCode").html(app.versionCode);
		$("#t_downloadUrl").html(url);
	}
}
