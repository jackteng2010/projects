var listUrl = "/api/spapp/listall.json";
var updateUrl = "/api/spapp/update.json";
var myModel = kendo.data.Model.define({
	id : "_id",
	fields : {
		_id : {editable : false,nullable : true},
		name:{type:"string",editable : false},
		packageName: {editable : false},
		versionName: {editable : false},
		versionCode: {editable : false},
		downloadUrl: {editable : false},
		size: {editable : false},
		description:{editable : false},
		index:{type:"number",editable:false},
		isTop:{type:"number",editable : false},
		onLine:{type:"number",editable : false},
		activity:{type:"number",editable : false},
		adminScore:{type:"number",editable : true},
		isCommerce:{type:"number",editable : false},
		commendation:{type:"number",editable : false},
		isHottest:{type:"number",editable : false},
		hottestScore:{type:"number",editable : true},
		isLatest:{type:"number",editable : false},
		latestScore:{type:"number",editable : true},
		totalDownloadTimes:{type:"number",editable : false},
		category:{editable : false},
		isHotkeyWord:{type:"number",editable : false},
		type:{type:"number"}
	}
});

var dataSource = new LocalDataSource({
	read: listUrl,
	model: myModel
});


var cateDataSource = new kendo.data.DataSource({
	transport : {
		read : {url : "/api/spapp/cate/listforsearch.json", dataType : "jsonp", type:"post"}
	},
	schema : {total: "total", data: "data"}
});
var ids = new Array();

var initType = 1;
if(redirectParams && (redirectParams.type == 0 || redirectParams.type == 1)){
	initType = redirectParams.type;
}

$(document).ready(function() {
	checkRoles();
	var grid = $("#grid").kendoGrid({
		dataSource : dataSource,
		autoBind: false,
		pageable : pageable,
		selectable : "row",
		sortable : true,
		resizable: true,
		filterable : filterable,
		columns : [ {
			field : "name",
			title : "名字",
			width: "200px",
			template :  function(dataItem) {
				return insertTemplate("name", dataItem.name, dataItem._id);
			}
		}, {
			field : "type",
			title : "类型",
			width: "80px",
			template :  function(dataItem) {
				return insertTemplate("type", dataItem.type, dataItem._id);
			},
			filterable : false
		}, {
			field : "onLine",
			title : "上线",
			width: "100px",
			template :  function(dataItem) {
				return insertTemplate("onLine", dataItem.onLine, dataItem._id);
			},
			filterable : filterYesNo
		}, {
			field : "isCommerce",
			title : "商务",
			width: "100px",
			template :  function(dataItem) {
				return insertTemplate("isCommerce", dataItem.isCommerce, dataItem._id);
			},
			filterable : filterYesNo
		}, {
			field : "size",
			title : "大小",
			width: "100px"
		}, {
			field : "isTop",
			title : "排行榜",
			width: "100px",
			template :  function(dataItem) {
				return insertTemplate("isTop", dataItem.isTop, dataItem._id);
			},
			filterable : filterYesNo
		},  {
			field : "index",
			title : "默认排序",
			width: "100px",
			template :  function(dataItem) {
				return insertTemplate("index", dataItem.index, dataItem._id);
			}
		}, {
			field : "isHotkeyWord",
			title : "热搜App",
			width: "100px",
			template :  function(dataItem) {
				return insertTemplate("isHotkeyWord", dataItem.isHotkeyWord, dataItem._id);
			},
			filterable : filterYesNo
		},{
			field : "totalDownloadTimes",
			title : "下载次数",
			width: "100px",
			template :  function(dataItem) {
				return insertTemplate("totalDownloadTimes", dataItem.totalDownloadTimes, dataItem._id);
			}
		}, {
			field : "isRecommend",
			title : "精选",
			width: "100px",
			template :  function(dataItem) {
				return insertTemplate("isRecommend", dataItem.isRecommend, dataItem._id);
			},
			filterable : filterYesNo
		},{
			field : "adminScore",
			title : "精品评分",
			width: "100px",
			template :  function(dataItem) {
				return insertTemplate("adminScore", dataItem.adminScore, dataItem._id);
			}
		},{
			field : "isHottest",
			title : "最热",
			width: "100px",
			template :  function(dataItem) {
				return insertTemplate("isHottest", dataItem.isHottest, dataItem._id);
			},
			filterable : filterYesNo
		},{
			field : "hottestScore",
			title : "最热评分",
			width: "100px",
			template :  function(dataItem) {
				return insertTemplate("hottestScore", dataItem.hottestScore, dataItem._id);
			}
		},{			
			field : "isLatest",
			title : "最新",
			width: "100px",
			template :  function(dataItem) {
				return insertTemplate("isLatest", dataItem.isLatest, dataItem._id);
			},
			filterable : filterYesNo
		},{
			field : "latestScore",
			title : "最新评分",
			width: "100px",
			template :  function(dataItem) {
				return insertTemplate("latestScore", dataItem.latestScore, dataItem._id);
			}
		},{
			field : "activity",
			title : "活动",
			width: "100px",
			template :  function(dataItem) {
				return insertTemplate("activity", dataItem.activity, dataItem._id);
			},
			filterable : filterYesNo
		},{
			field : "activityPriority",
			title : "活动评分",
			width: "100px",
			template :  function(dataItem) {
				return insertTemplate("activityPriority", dataItem.activityPriority, dataItem._id);
			}
		}, {
			field : "category",
			title : "分类",
			width: "300px",
			template :  function(dataItem) {
				return insertTemplate("category", dataItem.category, dataItem._id);
			},
			filterable : {
				ui: function(e){
					e.kendoDropDownList({
						optionLabel: "全部",
						dataTextField : "appCategoryName",
						dataValueField : "_id",
						dataSource : cateDataSource
					});
				}
			}
		}, {
			field : "downloadUrl",
			title : "下载地址",
			width: "120px",
			template :  function(dataItem) {
				return insertTemplate("downloadUrl", dataItem.downloadUrl, dataItem._id);
			}
		} ],
		dataBound : function(e){
		    $(".draggable").kendoDraggable({
		        hint: function(element) {
		            return element.clone();
		        }
		    });
		}
	});
	
	$("#draggable-container").kendoDropTarget({
	    dragenter: function(e){
	    	var id = e.draggable.element[0].id;
	    	var text = e.draggable.element[0].innerText;
	    	var find = false;
	    	for (i in ids) {
	    		if (ids[i] == id) {
	    			find = true;
	    			break;
	    		}
	    	}
	    	if (!find) {
	    		var old = $("#draggable-container").text();
	    		if (ids.length == 0) {
	    			$("#draggable-container").text(text);
	    		} else {
	    			$("#draggable-container").text(old+","+text);
	    		}
	    		ids.push(id);
	    	}
	    }
	});
    
    $("#categoryType").kendoDropDownList({
        dataTextField: "text",
        dataValueField: "value",
        optionLabel: "全部",
        dataSource: [{text:"应用",value:1},{text:"游戏",value:0}],
        change: function() {
            var value = this.value();
            if (value) {
                grid.data("kendoGrid").dataSource.filter({ field: "type", operator: "eq", value: parseInt(value) });
            } else {
                grid.data("kendoGrid").dataSource.filter({});
            }
        }
    });
    
    grid.data("kendoGrid").dataSource.filter({ field: "type", operator: "eq", value: initType });
    $("#categoryType").data("kendoDropDownList").value(initType);
    
});// end dom ready

function insertTemplate(field, value, id){
	var div = "";
	if(field == "name"){
		div = '<a class="draggable" style="color: #2d64b3;text-decoration: none;" id="'+ id + '" data="' + value + '">' + value + '</a>';
	}else if(field == "type"){
		div = (value == 1) ? "应用" : "游戏";
	}else if(field == "downloadUrl"){
		div = '<a class="pointer" href="' + value + '" + title="' + value + '"><img height="15px" widht="15px" src="images/recycle.png" /></a>';
	}else if(field == "category"){
		div = value.join(",");
	}else if(field == "index" || field == "adminScore" || field == "hottestScore" || field == "latestScore" || field == "totalDownloadTimes" || field == "activityPriority"){
		div = '<div class="pointer" onclick="setScore(' 
			+ "'" + field + "'," + value + ",'" + id + "')" 
			+ '">' + value + '</div>';
	}else {
		var image = (value == 1) ? "images/check.png" : "images/cross.png";
		div =	'<div><img class="pointer" src="'
			+	image + '" onclick="setTrueFalse('
			+	"'" + field + "'," + value + "," + "'" + id + "')"
			+	'" height="15px" width="15px" /></div>';
	}
	return div;
}

function addApp() {
	loadPage("ht/appAdd.html");
}

function editApp() {
	// 如果是从订单列表页点击edit过来的数据
	var row = getSelectedRowDataByGridWithMsg("grid");
	if (row) {
		loadPage("ht/appAdd.html", { _id : row._id });
	}
}

function saveSuccess(){
	dataSource.read();
}

function setTrueFalse(field, value, id){
	var nv = 0;
	if(value == 0 || value == "0" || value == undefined || value == "undefined"){
		nv = 1;
	}
	if(confirm("确认提交 ! (ˇ?ˇ) ")){
		var url = updateUrl + "?_id=" + id + "&" + field + "=" + nv;
		postAjaxRequest(url, {_id : id, field : nv}, saveSuccess);
	}
}

function setScore(field, value, id){
	var nv = prompt("请输入评分：", value);
	if (nv && nv != value) {
		var url = updateUrl + "?_id=" + id + "&" + field + "=" + nv;
		postAjaxRequest(url, null, saveSuccess);
	}
}

function bactchUpdate() {
	if (ids.length == 0) {
		alert("没有选择任何app");
	} else {
		var type = $("#batchType").val();
		var params = { type : type, ids : ids};
		postAjaxRequest( 
			"/api/spapp/updatebatch.json", 
			{ models : kendo.stringify(params) }, 
			function(data) {
				alert("设置成功");
				dataSource.read();
			}
		);
	}
}

