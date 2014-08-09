var listUrl = "/api/sprole/list.json";
var catModel = kendo.data.Model.define({
	id : "_id",
	fields : {
		_id : {editable : false,nullable : true},
		roleName:{},
		roleDesc: {},
		roleValue: {},
		rights: {},

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
			field : "roleName",
			width: "150px",
			title : "角色名"
		},{
			field : "roleDesc",
			width: "200px",
			title : "角色的描述",
		},
		{
			field: "rights",
			title: "角色中权限",
			template :  function(dataItem) {
				var str = "";
				for(var i=0;i<dataItem.rights.length;i++){
					str+=dataItem.rights[i].rightName+" ," ;
				}
				return  str.substring(0,str.length-1) ;
				//"<button onclick='openRights("+dataItem._id+")' type='button' class='btn btn-default'>点击查看权限</button>";
			}
		}
		]
	});


});	

function openRights(id){
	alert("id = "+id);
	postAjaxRequest("/api/sprole/delete.json", {role_id:id}, function(){dataSource.read();});
}


function addRole(){
	loadPage("ht/roleAdd.html");
}

function deleteRole(){
	
	var row = getSelectedRowDataByGridWithMsg("grid");
	if(row){
		postAjaxRequest("/api/sprole/delete.json", {_id:row._id}, function(){dataSource.read();});
	}
}


function editRole() {
	
	var row = getSelectedRowDataByGridWithMsg("grid");
	if (row) {
		loadPage("ht/roleAdd.html", {
			_id : row._id
		});
	}
}