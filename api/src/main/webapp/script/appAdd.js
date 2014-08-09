var detailUrl = "/api/spapp/detail.json"
var addUrl = "/api/spapp/add.json";
var requestModel = kendo.data.Model.define({
	id : "_id",
	fields : {
		_id: {editable : false, nullable : true},
		packageName: {},
		versionCode: {},
		downloadUrl: {},
		versionName: {},
		type:{type:"number"},
		fee:{},
		developer:{},
		size: {},
		changelog:{},
		secureLevel:{},
		ad:{}
	}
});
var cateDataSource = new kendo.data.DataSource({
	transport : {
		read : {
			url : "/api/spapp/cate/listforsearch.json",
			dataType : "jsonp"
		}
	},
	schema : {
		total: "total", // total is returned in the "total" field of the response
		data: "data"
	}
});
var screenDataSource = new kendo.data.DataSource({
	schema : {
		model : {
			id : "imgUrl"
		}
	}
});
var requestDataItem = new requestModel();
mergeDefault(requestDataItem);
$(document).ready(function() {

	// 选项卡
	if (!$("#tabstrip").data("kendoTabStrip")) {
		$("#tabstrip").kendoTabStrip({
			animation : {
				open : {
					effects : "fadeIn"
				}
			}
		});
	}

	$("#categoryIds").kendoMultiSelect({
		dataTextField : "appCategoryName",
		dataValueField : "_id",
		dataSource : cateDataSource
	});

	$("#imgList").kendoListView({
		dataSource : screenDataSource,
		template : kendo.template($("#template").html())
	});
	
	if (redirectParams) {
		redirectParams.fromAdmin = true;
		postAjaxRequest(detailUrl, redirectParams, edit);
	}else{
		kendo.bind($("#app-edit"), requestDataItem);
	}	
});

function edit(data) {
	if (data) {
		requestDataItem = new requestModel(data.data);
	}
	mergeDefault(requestDataItem);
	kendo.bind($("#app-edit"), requestDataItem);
	
	$("#logoUrl").attr("src", requestDataItem.logoUrl);
	$("#activityImage").attr("src", requestDataItem.activityImage);
	var screenShotPc = requestDataItem.screenShotPc;
	if(screenShotPc && screenShotPc != null){
		for (i = 0; i < screenShotPc.length; i++) {
	
			screenDataSource.add({
				imgUrl : screenShotPc[i]
			});
		}
	}
	addHover();
}

function mergeDefault(requestDataItem){
	if(requestDataItem.requiredSystem == null) requestDataItem.requiredSystem = "4.0";
	if(requestDataItem.type == null) requestDataItem.type = 1;
	if(requestDataItem.fee == null||requestDataItem.fee==1) requestDataItem.fee = "完全免费";
	if(requestDataItem.secureLevel == null) requestDataItem.secureLevel = "未检测";
	if(requestDataItem.ad == null) requestDataItem.ad = "未知";
	if(requestDataItem.totalDownloadTimes == null) requestDataItem.totalDownloadTimes = 1;
	if(requestDataItem.score == null || requestDataItem.score > 5 || requestDataItem.score < 1) requestDataItem.score = 5;
	if(requestDataItem.developer == null) requestDataItem.developer = "Unknow";
}

function addHover() {
	$("#app-edit img").hover(function() {
		kendo.fx(this).zoom("out").startValue(1.5).endValue(1).play();
	}, function() {
		kendo.fx(this).zoom("in").startValue(1).endValue(1.5).play();
	});
}

function uploadApk() {
	var options = {
		width : "680px",
		height : "400px",
		title : "上传APK"
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
		requestDataItem.set("packageName", app.packageName);
		requestDataItem.set("versionCode", app.versionCode);
		requestDataItem.set("downloadUrl", url);
		requestDataItem.set("versionName", app.versionName);
		requestDataItem.set("size", app.size);
	}
}

function uploadIcon() {
	var options = {
		width : "680px",
		height : "400px",
		title : "上传ICON"
	};
	var params = {
		upload_url : imgUploadUrl,
		callBack : uploadIconCallBack,
		type : "2",
		limit : 1
	};

	openRemotePageWindow(options, "upload_up", params);
}

function uploadIconCallBack(data) {

	if (data.length > 0) {
		var item = data[0];
		var url = item.img_url;
		if(url.indexOf("http") == -1){
			url = imageUrl + url;
		}
		requestDataItem.set("logoUrl", url);
		$("#logoUrl").attr("src",url);
	}
}

function uploadScreenShot() {
	var options = {
		width : "680px",
		height : "400px",
		title : "上传游戏截图"
	};
	var params = {
		upload_url : imgUploadUrl,
		callBack : uploadScreenShotCallBack,
		type : "1",
		limit : 5
	};
	openRemotePageWindow(options, "upload_up", params);
}

function uploadScreenShotCallBack(data) {

	if (data.length > 0) {
		for (i = 0; i < data.length; i++) {
			var item = data[i];
			var url = item.img_url;
			if(url.indexOf("http") == -1){
				url = imageUrl + url;
			}
			var addImg = {
				imgUrl : url
			}
			screenDataSource.add(addImg);
		}
	}
}

function uploadActivityImage() {
	var options = {
		width : "680px",
		height : "400px",
		title : "活动图片上传 "
	};
	var params = {
		upload_url : imgUploadUrl,
		callBack : uploadActivityImageCallBack,
		type : "1",
		limit : 5
	};
	openRemotePageWindow(options, "upload_up", params);
}

function uploadActivityImageCallBack(data) {
	if (data.length > 0) {
		var item = data[0];
		var url = item.img_url;
		if(url.indexOf("http") == -1){
			url = imageUrl + url;
		}
		requestDataItem.set("activityImage", url);
		$("#activityImage").attr("src",url);
	}
}

function saveApp() {
	var validator = $("#app-edit").kendoValidator().data("kendoValidator");
	if (validator.validate()) {
		var ids = [];
		
		var selectedValues = $("#categoryIds").data("kendoMultiSelect").value();
		if (!selectedValues || selectedValues.length == 0) {
			alert("分类不能为空");
			return;
		}

		var mypics = new Array();
		var data = screenDataSource.data();
		for (i = 0; i < data.length; i++) {
			mypics[i] = data[i].imgUrl;
		}
		
		requestDataItem.category = selectedValues;
		requestDataItem.fromAdmin = true;
		requestDataItem.screenShotPc = mypics;
		requestDataItem.screenShotMobile = mypics;
		postAjaxRequest(addUrl, {models : kendo.stringify(requestDataItem)}, saveSucess);
		
	} else {
		console.log("validate failed");
	}
}

function deleteScreenPic(imgUrl) {
	var data = screenDataSource.data();
	for(i=0; i<data.length; i++){
		if(data[i].imgUrl == imgUrl){
			screenDataSource.remove(data[i]);
		}
	}
}
