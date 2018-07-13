//jqGrid 文档 http://www.helloweba.com/view-blog-162.html
$(function () {
    $("#jqGrid").jqGrid({
        url: '../webCount/list',
        datatype: "json",
        colModel: [			
        	{ label: '序号', name: 'id', width: 45, key: true },
        	{ label: '策略名称', name: 'ruleName', width: 45 },
			{ label: '源IP', name: 'originIp', width: 50 },
			{ label: '目的IP', name: 'goalIp', width:50 },
			{ label: '源IP掩码', name: 'originIpMask', width: 50 },
			{ label: '目的IP掩码', name: 'goalIpMask', width: 50 },
			{ label: '协议', name: 'protocol', width: 50 },
			{ label: '开始源端口', name: 'startOriginPort', width: 50 },
			{ label: '结束源端口', name: 'endOriginPort', width:50 },
			{ label: '开始目的端口', name: 'startGoalPort', width: 50 },
			{ label: '结束目的端口', name: 'endGoalPort', width: 50 },
			{ label: '操作', name: 'operating', width: 30, sortable:false, formatter: function(value, options, row){
				return '<a class="btn btn-primary btn-xs" onclick=vm.showUpdate("'+row.id+'")>编辑</a>';
			}}
        ],
		viewrecords: true,
        height: 450,
        rowNum: 10,
		rowList : [10,30,50],
        rownumbers: true, 
        rownumWidth: 50, 
        autowidth:true,
        multiselect: true,
        pager: "#jqGridPager",
        jsonReader : { 
            root: "page.list",
            page: "page.currPage",
            total: "page.totalPage",
            records: "page.totalCount"
        },
        prmNames : {//后台分页参数的名字
            page:"page", 
            rows:"limit", 
            order: "order"
        },
        gridComplete:function(){  //数据加载完成后 
        	//隐藏grid底部滚动条
        	$("#jqGrid").closest(".ui-jqgrid-bdiv").css({ "overflow-x" : "hidden" }); 
        }
    });
});

var vm = new Vue({
	el : '#rrapp',
	data : {
		webCount : {
			id:0,
			ruleName: '',
			originIp : '',
			goalIp : '',
			originIpMask : '',
			goalIpMask : '',
			protocol : '',
			startOriginPort : '',
			endOriginPort : '',
			startGoalPort : '',
			endGoalPort : '',
		},
		 title:''
	},
	methods : {
		showInsert : function(value){
            vm.title='新增策略';
            vm.webCount.id=0;
            vm.reset();
            $('#editModal').modal('show');

        },
        showUpdate : function(value){
            vm.reset();
            vm.title='编辑策略';
            var id=value;
            $.ajax({
                type: "POST",
                url: "../webCount/getWebCount?id="+id,
                success: function(r){
                   if(r.code==0){
                	   vm.webCount=r.data;
                   }
                }
            });
            $('#editModal').modal('show');

        },
		deleted : function(id) {
			var rowId = $('#jqGrid').jqGrid('getGridParam', 'selarrrow');
			if(rowId == null || rowId.length <= 0) {
				alert("请选中需要删除的数据");
				return;
			} else {
				confirm('确定要删除这些记录？', function() {
					var ids = new Array();
					for (var i = 0; i < rowId.length; i++) {
						var rowData = $("#jqGrid").jqGrid('getRowData', rowId[i]);
						ids.push(rowData.id);
					}
					$.ajax({
						url : "../webCount/delete?ids=" + ids.join(";"),
						type : 'GET',
						success : function(r) {
							if (r.code === 0) {
								alert('删除成功');
								$("#jqGrid").jqGrid("setGridParam", {
									search : true
								}).trigger("reloadGrid");
							} else {
								alert(r.msg);
							}

						}
					});
				});
			}
		},
		vertify : function() {
			// 判断策略名称是否为空
			if(vm.webCount.ruleName == '' || $.trim(vm.webCount.ruleName).length == 0) {
				alert("请输入策略名称");
				return false;
			}
			// 判断IP格式是否正确
			if(vm.webCount.originIp != '' && $.trim(vm.webCount.originIp).length > 0) {
				if(checkIP(vm.webCount.originIp) == false) {
					alert("源IP格式错误");
					return false;
				}
			} else {
				alert("请输入源IP");
				return false;
			}
			if(vm.webCount.goalIp != '' && $.trim(vm.webCount.goalIp).length > 0) {
				if(checkIP(vm.webCount.goalIp) == false) {
					alert("目的IP格式错误");
					return false;
				}
			} else {
				alert("请输入目的IP");
				return false;
			}
			if(vm.webCount.originIpMask != '' && $.trim(vm.webCount.originIpMask).length > 0) {
				if(checkIP(vm.webCount.originIpMask) == false) {
					alert("源IP掩码格式错误");
					return false;
				}
			} else {
				alert("请输入源IP掩码");
				return false;
			}
			if(vm.webCount.goalIpMask != '' && $.trim(vm.webCount.goalIpMask).length > 0) {
				if(checkIP(vm.webCount.goalIpMask) == false) {
					alert("目的IP掩码格式错误");
					return false;
				}
			} else {
				alert("请输入目的IP掩码");
				return false;
			}
			//判断协议
			if(vm.webCount.protocol == '' || $.trim(vm.webCount.protocol).length <= 0) {
				alert("请选择协议");
				return false;
			}
			//判断端口格式
			if(vm.webCount.startOriginPort != '' && $.trim(vm.webCount.startOriginPort).length > 0) {
				if(/^\d+$/.test(vm.webCount.startOriginPort)) {
					if(vm.webCount.startOriginPort < 0 || vm.webCount.startOriginPort > 65535) {
						alert("开始源端口为0 - 65535的整数");
						return false;
					}
				} else {
					alert("开始源端口为0 - 65535的整数");
					return false;
				}
			} else {
				alert("请输入开始源端口");
				return false;
			}
			if(vm.webCount.endOriginPort != '' && $.trim(vm.webCount.endOriginPort).length > 0) {
				if(/^\d+$/.test(vm.webCount.endOriginPort)) {
					if(vm.webCount.endOriginPort < 0 || vm.webCount.endOriginPort > 65535) {
						alert("结束源端口为0 - 65535的整数");
						return false;
					}
				} else {
					alert("结束源端口为0 - 65535的整数");
					return false;
				}
			} else {
				alert("请输入结束源端口");
				return false;
			}
			if(vm.webCount.startGoalPort != '' && $.trim(vm.webCount.startGoalPort).length > 0) {
				if(/^\d+$/.test(vm.webCount.startGoalPort)) {
					if(vm.webCount.startGoalPort < 0 || vm.webCount.startGoalPort > 65535) {
						alert("开始目的端口为0 - 65535的整数");
						return false;
					}
				} else {
					alert("开始目的端口为0 - 65535的整数");
					return false;
				}
			} else {
				alert("请输入开始目的端口");
				return false;
			}
			if(vm.webCount.endGoalPort != '' && $.trim(vm.webCount.endGoalPort).length > 0) {
				if(/^\d+$/.test(vm.webCount.endGoalPort)) {
					if(vm.webCount.endGoalPort < 0 || vm.webCount.endGoalPort > 65535) {
						alert("结束目的端口为0 - 65535的整数");
						return false;
					}
				} else {
					alert("结束目的端口为0 - 65535的整数");
					return false;
				}
			} else {
				alert("请输入结束目的端口");
				return false;
			}
			return true;
		},
		insertOrUpdate : function () {

			if(!vm.vertify())
				return;
            var url = vm.webCount.id == 0 ? "../webCount/insertWebCount" : "../webCount/updateWebCount";

            $.ajax({
                type: "POST",
                url: url,
                data: vm.webCount,
                dataType: "json",
                contentType: "application/x-www-form-urlencoded",
                cache: false,
                success: function(r){
                    if(r.code === 0){
                        alert('操作成功');
                        $("#editModal").modal('hide');
                      //重载JQGrid
         		       $("#jqGrid").jqGrid("setGridParam", { search: true }).trigger("reloadGrid", [{ page: 1}]);  
         				
                    }else{
                        alert(r.msg);
                    }
                }
            });
        },
		searcher: function(){
			var param={};
			var ruleName=$('#ruleName').val().trim();
			param.ruleName=ruleName;
			var postData = $("#jqGrid").jqGrid("getGridParam", "postData");
			   //发送 数据
		       $.extend(postData,param);
		       //重载JQGrid
		       $("#jqGrid").jqGrid("setGridParam", { search: true }).trigger("reloadGrid", [{ page: 1}]);  
				
        },
        reset: function(value){
        	vm.webCount.ruleName='';
        	vm.webCount.originIp='';
        	vm.webCount.goalIp='';
        	vm.webCount.originIpMask='';
        	vm.webCount.goalIpMask='';
        	vm.webCount.protocol='';
        	vm.webCount.startOriginPort='';
        	vm.webCount.endOriginPort='';
        	vm.webCount.startGoalPort='';
        	vm.webCount.endGoalPort='';

        }
	}
});