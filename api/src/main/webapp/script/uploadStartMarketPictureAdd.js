var detailUrl = "/api/sp/market/pic/detail.json"
var addUrl = "/api/sp/market/pic/add.json";
var catModel = kendo.data.Model.define({
	id : "_id",
	fields : {
		_id : {
			editable : false,
			nullable : true
		},
		//startDate:{},
		//imageUrl: {},
		//endDate:{}
	}
});
var screenDataSource = new kendo.data.DataSource({
	schema : {
		model : {
			id : "imageUrl"
		}
	}
});

var requestDataItem = new catModel();
$(document).ready(function() {
	$("#startDate").kendoDatePicker({
		value: new Date(),
    	format: "yyyy-MM-dd HH:mm:ss",
    	 culture: "de-DE"
    });
	$("#endDate").kendoDatePicker({
		value: new Date(),
    	format: "yyyy-MM-dd HH:mm:ss",
    	 culture: "de-DE"
    });
	
	$("#imgList").kendoListView({
		dataSource : screenDataSource,
		template : kendo.template($("#template").html())
	});
	
	if(redirectParams){
		postAjaxRequest(detailUrl, redirectParams, edit);
	}
	requestDataItem = new catModel();
	kendo.bind($("#cate-edit"), requestDataItem);
});

function edit(data){
	if (data) {
		requestDataItem = new catModel(data);
	}
	
	kendo.bind($("#cate-edit"), requestDataItem);
	
	var screenShotPc = requestDataItem.screenShotPc;
	if(screenShotPc && screenShotPc != null){
		for (i = 0; i < screenShotPc.length; i++) {
	
			screenDataSource.add({
				imageUrl : screenShotPc[i]
			});
		}
	}
	addHover();
}

function addHover() {

	$("#app-edit img").hover(function() {
		kendo.fx(this).zoom("out").startValue(1.5).endValue(1).play();
	}, function() {
		kendo.fx(this).zoom("in").startValue(1).endValue(1.5).play();
	});

}

function uploadFile() {
	var options = {
		width : "680px",
		height : "400px",
		title : "上传图片"
	};
	var params = {
			upload_url : imgUploadUrl,
			callBack : uploadCallBack,
			type : "1",
			limit : 5
	};
	openRemotePageWindow(options, "upload_up", params);
}

function uploadCallBack(data) {
	
	if (data.length > 0) {
		for (i = 0; i < data.length; i++) {
			var item = data[i];
			var url = item.img_url;
			if(url.indexOf("http") == -1){
				url = imageUrl + url;
			}
			var addImg = {
				imageUrl : url
			}
			screenDataSource.add(addImg);
		}
	}
}

function saveAppCate(){
	//console.log($("#startDate").val());
	var validator = $("#cate-edit").kendoValidator().data("kendoValidator");
	if (!validator.validate()) {
		return;
    } else {
    	var mypics = new Array();
    	var data = screenDataSource.data();
    	
    	if (!data || data.length == 0) {
			alert("图片不能为空");
			return;
		}
    	if (compareDate($("#startDate").val(),$("#endDate").val())>0) {
			alert("对不起，开始时间不能大于失效时间！");
			return;
		}
    		for (i = 0; i < data.length; i++) {
    			mypics[i] = data[i].imageUrl;
    		}
    		
    		requestDataItem.screenShotPc = mypics;//screenShotPc:mypics,startDate:$("#startDate").val(), endDate:$("#endDate").val()
    		requestDataItem.startDate = $("#startDate").val();
    		requestDataItem.endDate = $("#endDate").val();
    		postAjaxRequest(addUrl, {models : kendo.stringify(requestDataItem)}, saveSucess);
    }

}

function compareDate(strDate1,strDate2)
     {
          var date1 = new Date(strDate1.replace(/\-/g, "\/"));
          var date2 = new Date(strDate2.replace(/\-/g, "\/"));
          return date1-date2;
      }


function deleteScreenPic(imageUrl) {
	var data = screenDataSource.data();
	for(i=0; i<data.length; i++){
		if(data[i].imageUrl == imageUrl){
			screenDataSource.remove(data[i]);
		}
	}
}
