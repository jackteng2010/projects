var myModel = kendo.data.Model.define({
    id: "_id",
    fields: {
   	   _id: {editable: false, nullable: true},
       name: {validation: {required: true}},
   	   value:{validation: {required: true }},
       describe:{}
    }
});

var dataSource = new LocalDataSource({
	read: "/api/sp/config/list.json",
	create: "/api/sp/config/save.json",
	update: "/api/sp/config/save.json",
	destroy: "/api/sp/config/delete.json",
	model: myModel
});

$(document).ready(function () {
    $("#grid").kendoGrid({
        dataSource: dataSource,
	    pageable: true,
	    resizable: true,
        sortable : true,
		filterable : filterable,
        toolbar: [{ name: "create", text: "新增" }],
        columns: [
            { field: "name", title: "键名(KEY)" },
            { field: "value", title: "键值(VALUE)"},
            { field: "describe", title: "描述"},
            { command: [{ name: "edit", text: "编辑" },{ name: "destroy", text: "删除" }] }
        ],
        selectable: "row",
        editable: {
        	 mode: "inline",
        	 confirmation:"警告：删除有风险 ！ ≡(▔﹏▔)≡ "
        }
    });
});


