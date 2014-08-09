var listUrl = "/api/spreport/client.json";
var dataSource = new kendo.data.DataSource({
    pageSize: 10
});
$(document).ready(function() {
	checkRoles();
	$("#startDate").kendoDatePicker({
	    format: "yyyy/MM/dd",
	    parseFormats: ["yyyy/MM/dd"]
	});
	$("#endDate").kendoDatePicker({
	    format: "yyyy/MM/dd",
	    parseFormats: ["yyyy/MM/dd"]
	});
	$("#grid").kendoGrid({
		dataSource : dataSource,
		pageable : true,
		selectable : "row",
		height : "400px",
		sortable : true,
		columns : [{
			field : "title",
			title : "维度"
		}, {
			field : "mobileCount",
			title : "激活数"
		}, {
			field : "launchCount",
			title : "启动次数"
		}]
	});
	kendo.ui.progress($("#main_right"), true);
	postAjaxRequest(listUrl, {test:"test"}, loadReport);
});
function loadReport(data){
	dataSource.data(data.data);
	kendo.ui.progress($("#main_right"), false);
}
function search(){
	postAjaxRequest(listUrl, {startDate:$("#startDate").val(), endDate:$("#endDate").val()}, loadReport);
}
function exportSS() {
	var link = listUrl+"/export"+'?'+'startDate='+$('#startDate').val()+'&endDate='+$('#endDate').val();
	window.open(link);
	return false;
}