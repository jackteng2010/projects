var loopModel = kendo.data.Model.define({
	id : "_id",
	fields : {
		_id : {
			editable : false,
			nullable : true
		},
		loopInterval : {},
		mgsFailureTime:{}
	}

});

var model;
$(document).ready(function() {
	model = new loopModel();
	kendo.bind($("#setLoopInterval"), model);
	postAjaxRequest("/api/sppush/getLoopInterval.json", {}, callback);
	if (redirectParams){
		$("#setSuccessDiv").addClass("success-div");
		$("#setSuccessDiv").html("<font style=\"color:red;size:11px\">设置成功！！</font>");
	}
});

function callback(data){
	var li = data.loopInterval;
	var mft = data.mgsFailureTime;
	$("#cLoopInterval").val(li);
	if(mft==""||mft=="undefined"||mft==null){
		mft = 0;
	}
	$("#mgsFailureTime").val(mft);
}

function reSetLoopTime(){
	var validator = $("#setLoopInterval").kendoValidator().data("kendoValidator");
	if (!validator.validate()) {
		return;
    } else {
    	postAjaxRequest("/api/sppush/setLoopInterval.json", model.toJSON(), function(data){
			loadPage("ht/pushLoopSet.html", {success:1});
    	});    	
    }	
};

function cancel(){
	loadPage("ht/pushLoopSet.html");
};
