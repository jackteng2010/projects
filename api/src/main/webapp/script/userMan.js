
var groupDataSource = new kendo.data.DataSource({
	transport : {
		read : {
			url : "/service/user/group/list",
			dataType : "jsonp"
		}
	},
	requestEnd: function(e) {
		console.log("................");
	},
	schema : {
		total: "total", // total is returned in the "total" field of the response
		data: "data"
	}
});
var dataSource = new kendo.data.DataSource({
	transport : {
		read : {
			url : "/service/user/list",
			dataType : "jsonp"
		}
	},
	pageSize : 100,
	batch : true,
	schema : {				
		total: "total", // total is returned in the "total" field of the response
		data: "data"
	}
});

$(document).ready(function() {	
	groupDataSource.read();

	$("#grid").kendoGrid({
		dataSource : dataSource,
		pageable : pageable,
		selectable : "row",
		height: "600px",
		columns : [ {
			field : "userName",
			title : "用户名"
		}, {
			field : "phone",
			title : "手机"
		}, {
			field : "email",
			title : "Email"
		}, {
			field : "department",
			title : "部门",
			template : function(dataItem) {
				  var departNames = "";

				  if(dataItem.department){
					  for(i=0; i<dataItem.department.length; i++){						  
						  departNames = departNames + dataItem.department[i] + " ";							  
					  }
				 }
			      return "<strong>" + departNames + "</strong>";
					
		   }
		}, {
			field : "groups",
			title : "角色",
			template : function(dataItem) {
				  if(dataItem.groups){
					  var groups = dataItem.groups;
					  var groupNames = "";
					  for(i=0; i<groups.length; i++){						  
						  for(j=0; j<groupDataSource.data().length; j++){
							  if(groupDataSource.data()[j]._id == groups[i]){
								  groupNames = groupNames + groupDataSource.data()[j].groupName + " ";
							  }
						  }
					  }
				      return "<strong>" + groupNames + "</strong>";
				 }else{
					 return "<strong>....</strong>";
				 }
		    }
			
		}]
	});
});


function add(){
	loadPage("ht/userEdit.html");
}

function edit(){
	var row = getSelectedRowDataByGridWithMsg("grid");	
	if(row){
		loadPage("ht/userEdit.html", {
			_id : row._id
		});
	}
}

function del() {
	var row = getSelectedRowDataByGridWithMsg("grid");
	postAjaxRequest("/service/user/delete", {_id : row._id} , saveSuccess);	

}

function saveSuccess(){
	loadPage("ht/userMan.html");
}
