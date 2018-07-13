//jqGrid 文档 http://www.helloweba.com/view-blog-162.html
$(function () {
    $("#jqGrid").jqGrid({
        url: '../noFlow/list',
        datatype: "json",
        colModel: [
            {label: '序号', name: 'id', key: true},
            {label: '策略名称', name: 'ruleName'},
            {label: '源IP', name: 'originIp'},
            {label: '目的IP', name: 'goalIp'},
            {label: '源IP掩码', name: 'originIpMask'},
            {label: '目的IP掩码', name: 'goalIpMask'},
            {label: '目的端口', name: 'goalADSL'},
            {label: '协议', name: 'protocol'},
            {label: '无流量间隔（秒）', name: 'interval'},
            {
                label: '操作', name: 'operating', sortable: false, formatter: function (value, options, row) {
                    var a = '<a class="btn btn-primary btn-xs" onclick=vm.showUpdate("' + row.id + '")>编辑</a>';
                    var b, c;
                    if (row.status == 0) {
                        b = '<a id="excutebtn' + row.id + '" class="btn btn-primary btn-xs" onclick=vm.execute("' + row.id + '")>执行</a>';
                        c = '<a id="stopbtn' + row.id + '" class="btn btn-primary btn-xs" disabled="disabled" onclick=vm.stop("' + row.id + '")>停止</a>';
                    } else if (row.status == 1) {
                        b = '<a id="excutebtn' + row.id + '" class="btn btn-primary btn-xs" disabled="disabled" onclick=vm.execute("' + row.id + '")>执行</a>';
                        c = '<a id="stopbtn' + row.id + '" class="btn btn-primary btn-xs" onclick=vm.stop("' + row.id + '")>停止</a>';
                    }
                    return a + '&nbsp;' + b + '&nbsp;' + c;
                }
            }
        ],
        viewrecords: true,
        height: 450,
        rowNum: 10,
        rowList: [10, 30, 50],
        rownumbers: true,
        rownumWidth: 50,
        autowidth: true,
        multiselect: true,
        pager: "#jqGridPager",
        jsonReader: {
            root: "page.list",
            page: "page.currPage",
            total: "page.totalPage",
            records: "page.totalCount"
        },
        prmNames: {//后台分页参数的名字
            page: "page",
            rows: "limit",
            order: "order"
        },
        gridComplete: function () {  //数据加载完成后
            //隐藏grid底部滚动条
            $("#jqGrid").closest(".ui-jqgrid-bdiv").css({"overflow-x": "hidden"});
        }
    });
});

var vm = new Vue({
    el: '#rrapp',
    data: {
        noFlow: {
            id: 0,
            ruleName: '',
            originIp: '',
            goalIp: '',
            originIpMask: '',
            goalIpMask: '',
            goalADSL: '',
            protocol: '',
            interval: '',
            status: 0
        },
        title: ''
    },
    methods: {
        reset: function (value) {
            vm.noFlow.ruleName = '';
            vm.noFlow.originIp = '';
            vm.noFlow.goalIp = '';
            vm.noFlow.originIpMask = '';
            vm.noFlow.goalIpMask = '';
            vm.noFlow.goalADSL = '';
            vm.noFlow.protocol = '';
            vm.noFlow.interval = '';
        },
        showInsert: function (value) {
            vm.title = '新增策略';
            vm.noFlow.id = 0;
            vm.reset();
            $('#editModal').modal('show');

        },
        showUpdate: function (value) {
            vm.reset();
            vm.title = '编辑策略';
            var id = value;
            $.ajax({
                type: "POST",
                url: "../noFlow/getNoFlow?id=" + id,
                success: function (r) {
                    if (r.code == 0) {
                        vm.noFlow = r.data;
                    }
                }
            });
            $('#editModal').modal('show');

        },
        deleted: function (id) {
            var rowId = $('#jqGrid').jqGrid('getGridParam', 'selarrrow');
            if (rowId == null || rowId.length <= 0) {
                alert("请选中需要删除的数据");
                return;
            } else {
                confirm('确定要删除这条记录？', function () {
                    var ids = new Array();
                    for (var i = 0; i < rowId.length; i++) {
                        var rowData = $("#jqGrid").jqGrid('getRowData', rowId[i]);
                        ids.push(rowData.id);
                    }
                    $.ajax({
                        url: "../noFlow/delete?ids=" + ids.join(";"),
                        type: 'GET',
                        success: function (r) {
                            if (r.code === 0) {
                                alert('删除成功');
                                $("#jqGrid").jqGrid("setGridParam", {
                                    search: true
                                }).trigger("reloadGrid");
                            } else {
                                alert(r.msg);
                            }

                        }
                    });
                });
            }
        },
        vertify: function () {
            // 判断策略名称是否为空
            if (vm.noFlow.ruleName == '' || $.trim(vm.noFlow.ruleName).length == 0) {
                alert("请输入策略名称");
                return false;
            }
            // 判断IP格式是否正确
            if (vm.noFlow.originIp != '' && $.trim(vm.noFlow.originIp).length > 0) {
                if (checkIP(vm.noFlow.originIp) == false) {
                    alert("源IP格式错误");
                    return false;
                }
            } else {
                alert("请输入源IP");
                return false;
            }
            if (vm.noFlow.goalIp != '' && $.trim(vm.noFlow.goalIp).length > 0) {
                if (checkIP(vm.noFlow.goalIp) == false) {
                    alert("目的IP格式错误");
                    return false;
                }
            } else {
                alert("请输入目的IP");
                return false;
            }
            if (vm.noFlow.originIpMask != '' && $.trim(vm.noFlow.originIpMask).length > 0) {
                if (checkIP(vm.noFlow.originIpMask) == false) {
                    alert("源IP掩码格式错误");
                    return false;
                }
            } else {
                alert("请输入源IP掩码");
                return false;
            }
            if (vm.noFlow.goalIpMask != '' && $.trim(vm.noFlow.goalIpMask).length > 0) {
                if (checkIP(vm.noFlow.goalIpMask) == false) {
                    alert("目的IP掩码格式错误");
                    return false;
                }
            } else {
                alert("请输入目的IP掩码");
                return false;
            }
            //判断端口格式
            if (vm.noFlow.goalADSL != '' && $.trim(vm.noFlow.goalADSL).length > 0) {
                if (/^\d+$/.test(vm.noFlow.goalADSL)) {
                    if (vm.noFlow.goalADSL < 0 || vm.noFlow.goalADSL > 65535) {
                        alert("目的端口为0 - 65535的整数");
                        return false;
                    }
                } else {
                    alert("目的端口为0 - 65535的整数");
                    return false;
                }
            } else {
                alert("请输入目的端口");
                return false;
            }
            //判断协议
            if (vm.noFlow.protocol == '' || $.trim(vm.noFlow.protocol).length <= 0) {
                alert("请选择协议");
                return false;
            }
            //判断间隔时间
            if (vm.noFlow.interval != '' && $.trim(vm.noFlow.interval).length > 0) {
                if (/^\d+$/.test(vm.noFlow.interval)) {
                    if (vm.noFlow.interval <= 0) {
                        alert("间隔时间为大于0的整数");
                        return false;
                    }
                } else {
                    alert("间隔时间为大于0的整数");
                    return false;
                }
            } else {
                alert("请输入间隔时间");
                return false;
            }
            return true;
        },
        insertOrUpdate: function () {

            if (!vm.vertify())
                return;
            /* var data=JSON.stringify(vm.noFlow)*/
            var url = vm.noFlow.id == 0 ? "../noFlow/insertNoFlow" : "../noFlow/updateNoFlow";

            $.ajax({
                type: "POST",
                url: url,
                data: vm.noFlow,
                dataType: "json",
                contentType: "application/x-www-form-urlencoded",
                cache: false,
                success: function (r) {
                    if (r.code === 0) {
                        alert('操作成功');
                        $("#editModal").modal('hide');
                        //重载JQGrid
                        $("#jqGrid").jqGrid("setGridParam", {search: true}).trigger("reloadGrid", [{page: 1}]);

                    } else {
                        alert(r.msg);
                    }
                }
            });
            $('#excutebtn' + vm.noFlow.id).attr("disabled", false);
            $('#stopbtn' + vm.noFlow.id).attr("disabled", true);
        },
        searcher: function () {
            var param = {};
            var ruleName = $('#ruleName').val().trim();
            param.ruleName = ruleName;
            var postData = $("#jqGrid").jqGrid("getGridParam", "postData");
            //发送 数据
            $.extend(postData, param);
            //重载JQGrid
            $("#jqGrid").jqGrid("setGridParam", {search: true}).trigger("reloadGrid", [{page: 1}]);

        },
        execute: function (value) {
            $('#excutebtn' + value).attr("disabled", true);
            $.ajax({
                url: '../noFlow/execute',
                data: {noFlowId: value},
                dataType: 'text',
                success: function (data) {
                    if (data != "执行成功") {
                        $('#excutebtn' + value).attr("disabled", false);
                        $('#stopbtn' + value).attr("disabled", true);
                    }
                    alert(data);
                }
            });
            setTimeout(() => {
                $('#stopbtn' + value).attr("disabled",false);
        }, 15000);
        },
        stop: function (value) {
            $('#excutebtn' + value).attr("disabled", false);
            $('#stopbtn' + value).attr("disabled", true);
            $.ajax({
                url: '../noFlow/stop',
                data: {noFlowId: value},
                dataType: 'text',
                success: function (data) {
                    alert(data);
                }
            });
        }
    }
});