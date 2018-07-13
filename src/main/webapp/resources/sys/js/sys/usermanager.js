//jqGrid 文档 http://www.helloweba.com/view-blog-162.html
var systemRoles = new Array();
var systemRoleId = new Array();

$(document)
    .ready(
        function () {
            $.ajax({
                url: "/security/listAllRoles",
                success: function (data) {
                    for (var i = 0; i < data.length; i++) {
                        $("#addUserCheckbox").append(
                            '<label><input type="checkbox" name="systemRoles" value="' + data[i].id + '">' + data[i].roleName + '</label>&nbsp;'
                        );
                        $("#modifyRoleCheckbox").append(
                            '<label><input type="checkbox" name="systemRoles" id="systemRole' + i + '" value="' + data[i].id + '">' + data[i].roleName + '</label>&nbsp;'
                        );
                        systemRoles[i] = data[i].roleName;
                    }
                }
            });
            $(function () {
                $("#jqGrid").jqGrid({
                    url: '/security/limitListUser',
                    mtype: "GET",
                    datatype: "json",
                    colModel: [{
                        label: '管理员ID',
                        name: 'id',
                        width: 40,
                        key: true
                    }, {
                        label: '管理员名称',
                        name: 'loginName',
                        width: 40
                    }, {
                        label: '管理员角色',
                        name: 'systemRoles',
                        width: 40
                    }, {
                        label: '状态',
                        name: 'accountStatus',
                        width: 10,
                        formatter: function(cellValue) {
							if(cellValue == 1)
								return "<a class='btn btn-success btn-xs'>在线</a>";
							if(cellValue == 2)
								return "<a class='btn btn-danger btn-xs'>离线</a>";
							if(cellValue == 3)
								return "<a class='btn btn-primary btn-xs'>冻结</a>"
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
	for (var i = 0; i < systemRoles.length; i++) {
		$("#systemRole" + i).get(0).checked = false;
    }
}

function addUser() {
    var loginName = $("#loginName").val();
    var password = $("#password").val();
    var sex = $('input:radio[name="sex"]:checked').val();
    var userName = $("#userName").val();
    $('input[name="systemRoles"]:checked').each(function () {
        systemRoleId.push($(this).val());
    });

    $.ajax({
        traditional: false,
        contentType:'application/json;charset=UTF-8',
        url: "/security/addSysUser",
        data: {
            'loginName': loginName,
            'password': password,
            'sex': sex,
            'userName': userName,
            'systemRoles': systemRoleId.join("-")
        },
        dataType: "json",
        type: "get",
        success: function (data) {
            if (data.message == "success") {
            	$("#jqGrid").jqGrid().trigger("reloadGrid");
            } else {
                alert(data.message);
            }
        }
    });

    $("#loginName").val("");
    $("#password").val("");
    $("#userName").val("");
}

function openDialog() {
	var rowId = $('#jqGrid').jqGrid('getGridParam', 'selarrrow');
    if(rowId == null || rowId.length <= 0) {
        $("#modifyRole").attr("data-target","#");
        alert("请选择一行数据");
        return;
    } else if (rowId.length > 1) {
    	$("#modifyRole").attr("data-target","#");
    	alert("只能选择一行数据进行修改");
    	return;
    } else {
        var rowData = $("#jqGrid").jqGrid('getRowData', rowId);

        if(rowData.loginName == "admin"){
            $("#modifyRole").attr("data-target","#");
            alert("admin角色无法修改");
            return;
        }

        var value = rowData.systemRoles;
        for (var i = 0; i < systemRoles.length; i++) {
            if (value.indexOf(systemRoles[i]) != -1)
                $("#systemRole" + i).get(0).checked = true;
        }
        $("#modifyRole").attr("data-target","#myModal2");
    }
}

function modifyRole() {
    var systemRole = new Array();
    var roleId = new Array();

    for (var i = 0; i < systemRoles.length; i++) {
        systemRole[i] = $("#systemRole" + i).get(0).checked;
        roleId[i] = $("#systemRole" + i).val();
    }

    var id = $('#jqGrid').jqGrid('getGridParam', 'selrow');
    var rowData = $("#jqGrid").jqGrid('getRowData', id);
    var userid = rowData.id;

    $.ajax({
        traditional: true,
        contentType:'application/json;charset=UTF-8',
        url: "/security/modifyRole",
        data: {
            'userid': userid,
            'roleId': roleId,
            'systemRoles': systemRole
        },
        dataType: "json",
        type: "get",
        success: function (data) {
            if(data.message == "success")
            	$("#jqGrid").jqGrid().trigger("reloadGrid");
        }
    });
    systemRole = [];
    roleId = [];
    for (var i = 0; i < systemRoles.length; i++) {
    	$("#systemRole" + i).get(0).checked = false;
    }
}

function deleteUser() {
	var rowId = $('#jqGrid').jqGrid('getGridParam', 'selrow');
	if(rowId == null || rowId.length <= 0) {
		alert("请选中需要删除的数据");
		return;
	} else {
		confirm('确定要删除这条记录？', function() {
			$.ajax({
		        url: '/security/deleteSystemUser',
		        data: {'userid':rowId},
		        success: function(data) {
		            if(data.message == 'success') {
		                $("#jqGrid").jqGrid().trigger("reloadGrid");
		            }
		            else
		                alert("删除失败");
		        }
		    });
		})
	}
}
