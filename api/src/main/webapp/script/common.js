//记录页面跳转参数
var redirectParams = undefined;
//记录弹出窗口（远程页面）参数
var popupParams = undefined;
//记录跳转的页面
var redirecPage = undefined;
//记录跳转前的页面
var fromPage = undefined;
//用户的权限，用来显示隐藏按钮，菜单等
var userRoles = undefined;
//定义菜单所需权限，目前写死在JS文件中, KEY对应menus变量中的菜单ID
var accessRoles = {};
var user = undefined;

var imageUrl = "http://img.qingcheng.com";
var apkUrl = "http://apk.qingcheng.com";
var imgUploadUrl = "http://img.qingcheng.com/uploader/img_upload.php";
var apkUploadUrl = "http://img.qingcheng.com/uploader/apk_upload.php";

var gameCC = [{text:"青橙渠道",value:"GreenOrange"}];
var marketCC = [{text:"青橙渠道",value:"GOAppMarket"}];
var assistCC = [{text:"青橙渠道",value:"GOAssistiveTouch"}];

//分页栏配置
var pageable = {pageSizes: [5,10,20,50,100], messages: {display: "共 {2} 条记录",itemsPerPage: "条/页"}};

var appActiveItems = [ { text: "否", value :0}, { text: "是", value :1}];
var appOnlineItems = [{ text: "否", value :0}, { text: "是", value :1}];
var appRecommendItems = [{ text: "否", value :0}, { text: "是", value :1}];
var isData=[{ text: "否", value :0}, { text: "是", value :1}];
var pushTypeItems=[{ text: "应用", value :"1"}, { text: "文本", value :"2"}, { text: "外链", value :"3"}];
//var canPauseItems=[{ text: "能暂停", value : "1"}, { text: "不能暂停", value : "0"}];
var canPauseItems=[{ text: "能暂停", value : "1"}];
//服务器搜索过滤的配置
var filterable = {
	extra : false,
	operators : {
		string : {is : "等于", like : "匹配", neq : "不等于" },
		number : { eq : "等于", lt : "小于", gt : "大于" }
	},
	messages : { filter : "过滤", clear : "清除", info : "" }
};

/**远程CRUD的简单封装*/
function LocalDataSource(options){
	if(options.pageSize == undefined) options.pageSize=10; 
	var ds = new kendo.data.DataSource({
	    transport: {
	        read:  {
	            url: options.read,
	            dataType: "jsonp",
	            type : "post"
	        },
	        update: {
	            url:  options.update,
	            dataType: "jsonp",
	            type : "post"
	        },
	        destroy: {
	            url: options.destroy,
	            dataType: "jsonp",
	            type : "post"
	        },
	        create: {
	            url: options.create,
	            dataType: "jsonp",
	            type : "post"
	        }
	    },
	    pageSize: options.pageSize,
	    batch: false,
		serverPaging: true,
		serverSorting: true,
		serverFiltering : true,
	    schema: {
	        model: options.model,
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
	    	if(e.errors=="请先登录"){
	    		window.location = "login.jsp";
	    	} else {
	    		alert(e.errors);
	    	}
	    }
	});
	return ds;
}

var filterYesNo = {
	ui: function(e){
		e.kendoDropDownList({
			dataSource : isData,
			optionLabel : "全部",
			dataTextField : "text",
			dataValueField : "value"
		});
	}
}


$(document).ready(function() {
	$(document).keydown(function(event) {
		var target = event.target, tag = event.target.tagName.toUpperCase();  
		if ((event.altKey) && ((event.keyCode==37) || (event.keyCode==39))){//屏蔽 Alt+ 方向键 ← 和 Alt+ 方向键 → 
	       event.returnValue = false;
	       return false;
	    }
/*	    if(event.keyCode==8){//屏蔽退格删除键
	       if((tag == 'INPUT' && !A(target).attr("readonly")) || (tag == 'TEXTAREA' && !A(target).attr("readonly"))){  
		       if((target.type.toUpperCase() == "RADIO") || (target.type.toUpperCase() == "CHECKBOX")){
		           return false;
		       }else{  
		           return true;
		       }
	       }else{
	           return false;
	       }
	    }  */
	    if(event.keyCode == 116){//兼容IE
	    	event = window.event || event;
	    	event.keyCode = 0;
	        event.returnValue = false;
	        event.cancelBubble = true;
	        $("#popup").hide();
	        loadPage(redirecPage,redirectParams);
	        return false;//屏蔽F5刷新键   
	    }
	    if((event.ctrlKey) && (event.keyCode==82)){
	        return false;//屏蔽Alt+R   
	    }
	});

	/**HTML FORM JSON序列化**/
	$.fn.serializeJson=function(){
		var serializeObj={};
		var array=this.serializeArray();
		var str=this.serialize();
		$(array).each(function(){
			if(serializeObj[this.name]){
				serializeObj[this.name]=serializeObj[this.name] + "," + this.value;
			}else{
				serializeObj[this.name]=this.value;
			}
		});
		return serializeObj;
	};
	
	/**菜单点击事件*/
	$(".nav-list li a").click(function(){
		$(".nav li").removeClass("active");
		$(this).parent().addClass("active");
		$("#maintitle").html($(this).html());
		loadPage($(this).attr("href"));
		return false;
	});
	
});

function displayMsg(result) {
	alert(result.msg);
}

function loadPage(page, parameters, popupDiv) {
	if ($(".k-window").length > 0 && $(".k-overlay").length > 0) {
		$(".k-window").hide();
		$(".k-overlay").hide();
	}
	if (!popupDiv) {
		fromPage = redirecPage;
		redirecPage = page;
		redirectParams = parameters;
	}
	var uid = kendo.guid();
	var url = page;
	if (url.indexOf("?") != -1) {
		url = url + "&_uid=" + uid;
	}else{
		url = url + "?uid=" + uid;
	}
	$.ajax({
		url : url,
		cache: false,
		beforeSend: function(){
			showLoading();
		},
		success : function(data) {
			try {
				if (popupDiv) {
					$("#" + popupDiv).html(data);
				} else {
					$("#mainiframe").html(data);
				}
			} catch(err) {
				//do nothing
			} finally{
				hideLoading();
			}
		},
		error : function() {
			console.log("连接Service失败");
			hideLoading();
		}
	});
}

function showLoading(){
	$("#imageload").show();
}
function hideLoading(){
	$("#imageload").hide();
}

function blankFunction(){}

function getSelectedRowDataByGrid(gridId) {
	var grid = $("#" + gridId).data("kendoGrid");
	var row = grid.select();
	return grid.dataItem(row);
}

function getSelectedRowDataByGridWithMsg(gridId) {
	var grid = $("#" + gridId).data("kendoGrid");
	var row = grid.select();
	var response = grid.dataItem(row);

	if (!response) {
		alert("点击列表可以选中数据");
	}
	return response;
}

function openWindow(options) {
	var window = $("#" + options.id);
	$("#" + options.id).show();
	var onActivate = onWindowDefaultActivate;
	if (options.activate) {
		onActivate = options.activate;
	}

	var onClose = onWindowDefaultClose;
	if (options.close) {
		onClose = options.close;
	}

	var kendoWindow = window.data("kendoWindow");
	if (!kendoWindow) {
		window.kendoWindow({
			width : options.width,
			height : options.height,
			title : options.title,
			modal : true,
			activate : onActivate,
			close : onClose,
			actions : [ "Maximize", "Close" ]
		});
		kendoWindow = window.data("kendoWindow");
		kendoWindow.setOptions({
			modal : true
		});
		kendoWindow.center();
	} else {
		kendoWindow.setOptions({
			modal : true
		});
		kendoWindow.open();
		kendoWindow.center();
	}
}

function postAjaxRequest(url, parameters, callback, loading) {
	$.ajax({
		url : url,
		dataType : "json",
		data : parameters,
		type : "POST",
		beforeSend: function(){
			if(loading) showLoading();
		},
		success : function(result) {
			hideLoading();
			if (result.status == "0" && result.msg) {
				alert(result.msg);
			} else {
				eval("callback(result)");
			}
		},
		error : function() {
			console.log("连接Service失败");
			hideLoading();
		}
	});
}
function getAjaxRequest(url, parameters, callback) {
	$.ajax({
		url : url,
		dataType : "json",
		data : parameters,
		type : "GET",
		success : function(result) {
			if (result.status == "0" && result.msg) {
				alert(result.msg);
			} else {
				eval("callback(result)");
			}
		},
		error : function() {
			console.log("连接Service失败");
		}
	});
}

function onWindowDefaultActivate(e) {}

function onWindowDefaultClose(e) {}

function checkRoles() {
	var buttons = $('button[access]');
	buttons.each(function(index) {
		var node = jQuery(buttons[index]);
		var roleId = node.attr("access");
		var hasAccess = false;
		for (i in userRoles) {
			if (userRoles[i].roleID == roleId) {
				hasAccess = true;
				break;
			}
		}

		if (!hasAccess) {
			node.hide();
		} else {
			if (user.userName == "admin") {
				node.hide();
			} else {
				node.show();
			}
		}

	});
}

function back() {
	if (fromPage) {
		loadPage(fromPage);
	} else {
		loadPage(redirectPage);
	}
}

function openRemotePageWindow(options, page, parameter) {
	if("upload_up" == page){
		page = "ht/uploadUp.html";
	}
	var window = $("#popup");
	$("#popup").html("")
	$("#popup").show();

	var kendoWindow = window.data("kendoWindow");
	if (!kendoWindow) {
		window.kendoWindow({
			width : options.width,
			height : options.height,
			title : options.title,
			close : function(e) {
				popupParams = undefined;
			},
			actions : [ "Maximize", "Close" ]
		});
		kendoWindow = window.data("kendoWindow");
		kendoWindow.center();
	} else {
		kendoWindow.setOptions(options);
		kendoWindow.open();
		kendoWindow.center();
	}

	popupParams = parameter;
	loadPage(page, null, "popup");

}
function logoutbt(){
	postAjaxRequest("/api/spuser/logout.json",{},function(){
		window.location = "login.jsp";
	});
}
function saveSucess(data) {
	loadPage(fromPage);
}
String.prototype.endWith = function(s) {
	if (s == null || s == "" || this.length == 0 || s.length > this.length)
		return false;
	if (this.substring(this.length - s.length) == s)
		return true;
	else
		return false;
	return true;
}
/***格式化输出JSON**/
var JsonUti = {
	    n: "\n",//定义换行符
	    t: "\t",//定义制表符
	    convertToString: function(obj) {//转换String
	        return JsonUti.__writeObj(obj, 1);
	    },
	    //写对象
	    __writeObj: function(obj //对象
	    , level //层次（基数为1）
	    , isInArray) { //此对象是否在一个集合内
	        //如果为空，直接输出null
	        if (obj == null) {
	            return "null";
	        }
	        //为普通类型，直接输出值
	        if (obj.constructor == Number || obj.constructor == Date || obj.constructor == String || obj.constructor == Boolean) {
	            var v = obj.toString();
	            var tab = isInArray ? JsonUti.__repeatStr(JsonUti.t, level - 1) : "";
	            if (obj.constructor == String || obj.constructor == Date) {
	                //时间格式化只是单纯输出字符串，而不是Date对象
	                return tab + ("\"" + v + "\"");
	            }
	            else if (obj.constructor == Boolean) {
	                return tab + v.toLowerCase();
	            }
	            else {
	                return tab + (v);
	            }
	        }
	        //写Json对象，缓存字符串
	        var currentObjStrings = [];
	        //遍历属性
	        for (var name in obj) {
	            var temp = [];
	            //格式化Tab
	            var paddingTab = JsonUti.__repeatStr(JsonUti.t, level);
	            temp.push(paddingTab);
	            //写出属性名
	            temp.push("\"" + name + "\" : ");
	            var val = obj[name];
	            if (val == null) {
	                temp.push("null");
	            }
	            else {
	                var c = val.constructor;
	                if (c == Array) { //如果为集合，循环内部对象
	                    temp.push(JsonUti.n + paddingTab + "[" + JsonUti.n);
	                    var levelUp = level + 2; //层级+2
	                    var tempArrValue = []; //集合元素相关字符串缓存片段
	                    for (var i = 0; i < val.length; i++) {
	                        //递归写对象
	                        tempArrValue.push(JsonUti.__writeObj(val[i], levelUp, true));
	                    }
	                    temp.push(tempArrValue.join("," + JsonUti.n));
	                    temp.push(JsonUti.n + paddingTab + "]");
	                }
	                else if (c == Function) {
	                    temp.push("[Function]");
	                }
	                else {
	                    //递归写对象
	                    temp.push(JsonUti.__writeObj(val, level + 1));
	                }
	            }
	            //加入当前对象“属性”字符串
	            currentObjStrings.push(temp.join(""));
	        }
	        return (level > 1 && !isInArray ? JsonUti.n: "") //如果Json对象是内部，就要换行格式化
	        + JsonUti.__repeatStr(JsonUti.t, level - 1) + "{" + JsonUti.n //加层次Tab格式化
	        + currentObjStrings.join("," + JsonUti.n) //串联所有属性值
	        + JsonUti.n + JsonUti.__repeatStr(JsonUti.t, level - 1) + "}"; //封闭对象
	    },
	    __isArray: function(obj) {
	        if (obj) {
	            return obj.constructor == Array;
	        }
	        return false;
	    },
	    __repeatStr: function(str, times) {
	        var newStr = [];
	        if (times > 0) {
	            for (var i = 0; i < times; i++) {
	                newStr.push(str);
	            }
	        }
	        return newStr.join("");
	    }
};
