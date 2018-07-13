//jqGrid 文档 http://www.helloweba.com/view-blog-162.htm
$(document)
    .ready(
        function () {
            $.ajax({
                url: "/security/listParentPriority",
                success: function (data) {
                    for (var i = 0; i < data.length; i++) {
                        $("#modifyPriorityCheckbox").append(
                            '<label><input type="checkbox" name="priorities" id="priority' + data[i].priorityId + '" value="' + data[i].name + '">' + data[i].name + '</label>&nbsp;'
                        );
                    }
                }
            });
            $(function () {
                $("#jqGrid").jqGrid({
                    url: '/security/limitListRoles',
                    datatype: "json",
                    colModel: [{
                        label: '角色ID',
                        name: 'id',
                        width: 40,
                        key: true
                    }, {
                        label: '角色名称',
                        name: 'roleName',
                        width: 40
                    }, {
                        label: '角色权限',
                        name: 'priorities',
                        width: 40
                    }, {
                        label: '状态',
                        name: 'avaliable',
                        width: 10,
                        formatter: function(cellValue) {
							if(cellValue == 0)
								return "<a class='btn btn-danger btn-xs'>不可用</a>";
							if(cellValue == 1)
								return "<a class='btn btn-success btn-xs'>可用</a>";
						}
                    }],
                    viewrecords: true,
                    height: 450,
                    rowNum: 10,
                    rowList: [10, 30, 50],
                    rownumbers: true,
                    rownumWidth: 50,
                    autowidth: true,
                    multiselect: true,
                    pager: "#jqGridPager",
                    gridComplete: function () { // 数据加载完成后
                        // 隐藏grid底部滚动条
                        $("#jqGrid").closest(".ui-jqgrid-bdiv").css({
                            "overflow-x": "hidden"
                        });
                    }
                });
            });

            var vm = new Vue({
                el: '#rrapp',
                data: {},
                methods: {}
            });
        });

function reset() {
    $("input[name='priorities']").each(function(){
    	$(this).prop("checked", false);
    });
}

function addRole() {
    var roleName = $("#roleName").val();
    var description = $("#description").val();

    $.ajax({
        url: "/security/addRole",
        data: {
            'roleName': roleName,
            'description': description
        },
        type: "get",
        success: function (data) {
            if (data.message == 'success'){
            	$("#jqGrid").jqGrid().trigger("reloadGrid");
            } else {
                alert(data.message);
            }
        }
    });

    $("#roleName").val("");
    $("#description").val("");
}

function openDialog() {
	var rowId = $('#jqGrid').jqGrid('getGridParam', 'selarrrow');
    if(rowId == null || rowId.length <= 0) {
        $("#modifyPriority").attr("data-target","#");
        alert("请选择一行数据");
        return;
    } else if (rowId.length > 1) {
    	$("#modifyPriority").attr("data-target","#");
    	alert("只能选择一行数据进行修改");
    	return;
    } else {
        var rowData = $("#jqGrid").jqGrid('getRowData', rowId);

        if(rowData.roleName == "系统管理员"){
            $("#modifyPriority").attr("data-target","#");
            alert("系统管理员权限无法修改");
            return;
        }

        var value = rowData.priorities;
        
        $("input[name='priorities']").each(function(){
        	if (value.indexOf($(this).val()) != -1)
        		$(this).get(0).checked = true;
        });

        $("#modifyPriority").attr("data-target","#myModal2");
    }
}

function modifyPriority() {
    var id = $('#jqGrid').jqGrid('getGridParam', 'selrow');
    var rowData = $("#jqGrid").jqGrid('getRowData', id);
    var roleid = rowData.id;
    
    var priorities = new Array();
    $("input[name='priorities']").each(function(){
    	if($(this).is(":checked")) {
    		priorities.push($(this).val());
    	}
    });

    $.ajax({
        url: "/security/modifyRolePriority",
        data: {
            'roleid': roleid,
            'priorities' : priorities.join(";")
        },
        type: "get",
        dataType : 'text',
        success: function (data) {
        	alert(data);
        	$("#jqGrid").jqGrid().trigger("reloadGrid");
        }
    });

    reset();
}

function deleteRole() {
	var rowId = $('#jqGrid').jqGrid('getGridParam', 'selrow');
	if(rowId == null || rowId.length <= 0) {
		alert("请选中需要删除的数据");
		return;
	} else {
		confirm('确定要删除这条记录？', function() {
			$.ajax({
		        url: '/security/deleteRole',
		        data: {'roleid':rowId},
		        success: function(data) {
		            if(data.message == 'success') {
		                $("#jqGrid").jqGrid().trigger("reloadGrid");
		            }
		            else
		                alert("删除失败");
		        }
		    });
		});
	}
}

