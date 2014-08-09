var swfu;
var upDataSource = new kendo.data.DataSource({});

$(document).ready(function() {

	var settings = {
		post_params : popupParams,
		upload_url : popupParams.upload_url,
		file_types : "*.*", // 过滤文件类型
		file_upload_limit : 100, // 成功上传的文件数
		file_queue_limit : 100, // 队列中的文件数
		custom_settings : {},
		debug : false,

		// 按钮设置
		button_image_url : "images/TestImageNoText_65x29.png",
		button_width : "65",
		button_height : "29",
		button_placeholder_id : "spanButtonPlaceHolder",
		button_text : '<span class="theFont">上传</span>',
		button_text_style : ".theFont { font-size: 16; }",
		button_text_left_padding : 12,
		button_text_top_padding : 3,
		custom_settings : {
			progressTarget : "fsUploadProgress"
		},

		// handler中的方法，依次自动调用
		file_queued_handler : fileQueued,
		file_queue_error_handler : fileQueueError,
		file_dialog_complete_handler : fileDialogComplete,
		upload_start_handler : uploadStart,
		upload_progress_handler : uploadProgress,
		upload_error_handler : uploadError,
		upload_success_handler : uploadSuccess,
		upload_complete_handler : uploadComplete,
		queue_complete_handler : queueComplete
	};
	swfu = new SWFUpload(settings);

	$("#listView").kendoGrid({
		dataSource : upDataSource,
		selectable : "row",
		columns : [ {
			field : "url",
			title : "地址",
			template : function(dataItem) {
				var url = "";
				if(dataItem.apk_url){
					url =  '<a  href="http://apk.qingcheng.com/' + dataItem.apk_url +'">'+ dataItem.apk_url + '</a>';
				}
				
				if(dataItem.img_url){
					url =  '<a  href="http://apk.qingcheng.com/' + dataItem.img_url +'">'+ dataItem.img_url + '</a>';
				}
				return url;
			}
		}]
	
	});

});


function callUpCallBack(){
	if (popupParams) {

		var data = upDataSource.data();

		if (popupParams.limit && popupParams.limit < data.length) {
			alert("上传图片过多");
		}

		var window = $("#popup");

		var kendoWindow = window.data("kendoWindow");

		popupParams.callBack(data);

		if (kendoWindow) {
			kendoWindow.close();
		}
	}
}