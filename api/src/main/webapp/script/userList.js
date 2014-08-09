var listUrl = "/api/spuser/list.json";
var pwrtUrl = "/api/spuser/passwordReset.json";
var catModel = kendo.data.Model.define({
	id : "_id",
	fields : {
		_id : {editable : false,nullable : true},
		userName:{},
		email: {},
		phone: {},
		roles: {editable : false},
		roleNames: {},
		regDate: {},

	}
});

var dataSource = new kendo.data.DataSource({
	transport : {
		read : {
			url : listUrl,
			dataType : "jsonp",
			type:"post"
		}
	},
	schema : {
		total : "total",
		data : "data",
		model : catModel
	},
	pageSize : 10,
	serverPaging : true,
	serverSorting : true,
	serverFiltering : true
});
 
$(document).ready(function() {
	checkRoles();
	$("#grid").kendoGrid({
		dataSource : dataSource,
		pageable : pageable,
		selectable : "row",
		sortable : true,
		resizable: true,
		filterable : filterable,
		columns : [ {
			field : "userName",
			title : "用户名"
		},{
			field : "email",
			title : "Email",
		}, 
		{
			field : "phone",
			title : "电话",
		},
		{
			field : "roleNames",
			title : "用户角色",
//			template :  function(dataItem) {
				//return (dataItem.roleNames).join(",");
				//var re = "aa";
//				for(i in dataItem.roleNames){
//					re += i + ",";
//				}
//				return dataItem.roleNames;
//			}
		},
		{
			field: "regDate",
			title: "注册时间",
			
		}
		]
	});


});	




function addUser(){
	loadPage("ht/userAdd.html");
}

function deleteUser(){
	
	var row = getSelectedRowDataByGridWithMsg("grid");
	if(row){
		postAjaxRequest("/api/spuser/delete.json", {user_id:row._id}, function(){dataSource.read();});
	}
}


function editUser() {
	
	var row = getSelectedRowDataByGridWithMsg("grid");
	if (row) {
		loadPage("ht/userAdd.html", {
			_id : row._id
		});
	}
}

function passwordReset() {
	
	var row = getSelectedRowDataByGridWithMsg("grid");
	if (row) {
		postAjaxRequest(pwrtUrl, {_id : row._id}, function(){
    		alert("密码重置成功！");
    	});
		
	}
}