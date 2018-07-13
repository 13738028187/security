//jqGrid 文档 http://www.helloweba.com/view-blog-162.html
$(function() {
	$("#jqGrid")
			.jqGrid(
					{
						url : '/security/listStrategies',
						postData : {
							name : vm.name
						},
						datatype : "json",
						colModel : [
								{
									label : '序号',
									name : 'strategyId',
									width : 45,
									key : true
								},
								{
									label : '策略名称',
									name : 'name',
									width : 50
								},
								{
									label : '策略类型',
									name : 'type',
									width : 50,
									formatter : function(value, options, row) {
										if (value == 0)
											return '端口策略';
										else if (value == 1)
											return 'IP策略';
									}
								},
								{
									label : '操作',
									name : 'operating',
									width : 50,
									sortable : false,
									formatter : function(value, options, row) {
										return '<a class="btn btn-primary btn-xs" onclick=vm.showDetails("'
												+ row.strategyId
												+ '")>详情</a> <a class="btn btn-primary btn-xs" onclick=vm.openModifyDialog("'
												+ row.strategyId
												+ '")>编辑</a> <a class="btn btn-primary btn-xs" onclick=vm.openIssuedDialog("'
												+ row.strategyId + '")>下发</a>';
									}
								} ],
						viewrecords : true,
						height : 450,
						rowNum : 10,
						rowList : [ 10, 30, 50 ],
						rownumbers : true,
						rownumWidth : 50,
						autowidth : true,
						multiselect : true,
						pager : "#jqGridPager",
						gridComplete : function() { // 数据加载完成后
							// 隐藏grid底部滚动条
							$("#jqGrid").closest(".ui-jqgrid-bdiv").css({
								"overflow-x" : "hidden"
							});
						}
					});
});

var vm = new Vue({
	el : '#rrapp',
	data : {
		strategyId : 0,
		name : '',
		type : 'none',
		ips : '',
		ipStatus : 0,
		ports : '',
		portStatus : 0,
		title : ''
	},
	methods : {
		openAddDialog : function() {
			vm.reset();
			vm.title = '新增策略';
			$('#addStrategy').modal('show');
		},
		hidden : function(obj) {
			$(obj).css('display', 'none');
		},
		show : function(obj) {
			$(obj).css('display', 'block');
		},
		switchStrategyType : function() {
			var sel_val = $('#sel').val();
			if (sel_val == 'none') {
				vm.hidden('#portPolicy');
				vm.hidden('#ipPolicy');
			}
			if (sel_val == 0) {
				vm.show('#portPolicy');
				vm.hidden('#ipPolicy');
			}
			if (sel_val == 1) {
				vm.hidden('#portPolicy');
				vm.show('#ipPolicy');
			}
		},
		switchStrategyType2 : function() {
			var sel_val = $('#sel2').val();
			if (sel_val == 'none') {
				vm.hidden('#portPolicy2');
				vm.hidden('#ipPolicy2');
			}
			if (sel_val == 0) {
				vm.show('#portPolicy2');
				vm.hidden('#ipPolicy2');
			}
			if (sel_val == 1) {
				vm.hidden('#portPolicy2');
				vm.show('#ipPolicy2');
			}
		},
		vertify : function(remarks) {
			// 判断策略名称是否为空
			if (vm.name == '' || $.trim(vm.name).length == 0) {
				alert("请输入策略名称");
				return false;
			}
			// 判断策略类型是否选择
			if (remarks == 'add') {
				if ($('#sel').val() == 'none') {
					alert("请选择策略类型");
					return false;
				}
			} else if (remarks == 'modify') {
				if ($('#sel2').val() == 'none') {
					alert("请选择策略类型");
					return false;
				}
			}
			// 判断IP格式是否正确
			if (vm.ips != '' && $.trim(vm.ips).length > 0) {
				var ips = vm.ips.split(";");
				for (var i = 0; i < ips.length; i++) {
					if (checkIP(ips[i]) == false) {
						alert("IP格式错误，需以“;”进行分隔");
						return false;
					}
				}
			}
			// 判断端口格式
			if (vm.type == '0') {
				if (vm.ports != '' && $.trim(vm.ports).length > 0) {
					var ports = vm.ports.split(";");
					for (var i = 0; i < ports.length; i++) {
						if (/^\d+$/.test(ports[i])) {
							if (ports[i] < 0 || ports[i] > 65535) {
								alert("端口为0 - 65535的整数，需以“;”进行分隔");
								return false;
							}
						} else {
							alert("开放端口为0 - 65535的整数，需以“;”进行分隔");
							return false;
						}
					}
				} else {
					alert("端口不能为空");
					return false;
				}
			}
			return true;
		},
		addStrategy : function() {
			if (!vm.vertify('add'))
				return;
			$.ajax({
				url : '/security/addStrategy',
				data : {
					name : vm.name,
					type : $('#sel').val(),
					ips : vm.ips,
					ipStatus : vm.ipStatus,
					ports : vm.ports,
					portStatus : vm.portStatus
				},
				dataType : 'text',
				success : function(data) {
					if (data != '添加策略成功')
						alert(data);
					else {
						$('#addStrategy').modal('hide');
						$("#jqGrid").jqGrid().trigger("reloadGrid");
					}
				}
			});
		},
		searchStrategy : function() {
			$("#jqGrid").jqGrid('setGridParam', {
				postData : {
					name : $('#name').val()
				}
			}).trigger("reloadGrid");
		},
		deleteStrategies : function() {
			var rowId = $('#jqGrid').jqGrid('getGridParam', 'selarrrow');
			if (rowId == null || rowId.length <= 0) {
				alert("请选中需要删除的数据");
				return;
			} else {
				confirm('确定要删除这些策略？', function() {
					var strategyIds = new Array();
					for (var i = 0; i < rowId.length; i++) {
						var rowData = $("#jqGrid").jqGrid('getRowData',
								rowId[i]);
						strategyIds.push(rowData.strategyId);
					}
					$.ajax({
						url : '/security/deleteStrategies',
						data : {
							strategyIds : strategyIds.join(";")
						},
						dataType : 'text',
						success : function(data) {
							if (data != '删除配置成功')
								alert(data);
							else
								$("#jqGrid").jqGrid().trigger("reloadGrid");
						}
					});
				});
			}
		},
		openModifyDialog : function(value) {
			vm.reset();
			vm.title = '编辑策略';
			vm.strategyId = value;
			$.ajax({
				url : '/security/showDetails',
				data : {
					strategyId : vm.strategyId
				},
				success : function(data) {
					if (data.message != 'success')
						alert(data.message);
					else {
						vm.name = data.strategy.name;
						vm.type = data.strategy.type;
						vm.ips = data.strategy.ips;
						vm.ipStatus = data.strategy.ipStatus;
						vm.ports = data.strategy.ports;
						vm.portStatus = data.strategy.portStatus;
						if (vm.type == 0) {
							$('#sel2').val(0);
							vm.show('#portPolicy2');
							vm.hidden('#ipPolicy2');
						} else if (vm.type == 1) {
							$('#sel2').val(1);
							vm.hidden('#portPolicy2');
							vm.show('#ipPolicy2');
						}
					}
				}
			});
			$('#modifyStrategy').modal('show');
		},
		modifyStrategy : function() {
			if (vm.name == '' || $.trim(vm.name).length == 0) {
				alert("请输入策略名称");
				return;
			}
			if ($('#sel2').val() == 'none') {
				alert("请选择策略类型");
				return;
			}
			if ($('#sel2').val() == 0) {
				vm.ipStatus = 0;
			}
			if ($('#sel2').val() == 1) {
				vm.ports = '';
				vm.portStatus = 0;
			}
			if (!vm.vertify('modify'))
				return;
			$.ajax({
				url : '/security/modifyStrategy',
				data : {
					strategyId : vm.strategyId,
					name : vm.name,
					type : $('#sel2').val(),
					ips : vm.ips,
					ipStatus : vm.ipStatus,
					ports : vm.ports,
					portStatus : vm.portStatus
				},
				dataType : 'text',
				success : function(data) {
					alert(data);
				}
			});
			$('#modifyStrategy').modal('hide');
			$("#jqGrid").jqGrid().trigger("reloadGrid");
		},
		showDetails : function(value) {
			vm.reset();
			vm.title = '策略详情';
			vm.strategyId = value
			$.ajax({
				url : '/security/showDetails',
				data : {
					strategyId : vm.strategyId
				},
				success : function(data) {
					if (data.message != 'success')
						alert(data.message);
					else {
						vm.name = data.strategy.name;
						vm.type = data.strategy.type;
						vm.ips = data.strategy.ips;
						vm.ipStatus = data.strategy.ipStatus;
						vm.ports = data.strategy.ports;
						vm.portStatus = data.strategy.portStatus;
						if (vm.type == 0) {
							$('#sel3').val(0);
							vm.show('#portPolicy3');
							vm.hidden('#ipPolicy3');
						} else if (vm.type == 1) {
							$('#sel3').val(1);
							vm.hidden('#portPolicy3');
							vm.show('#ipPolicy3');
						}
					}
				}
			});
			$('#showDetails').modal('show');
		},
		openIssuedDialog : function(value) {
			vm.reset();
			vm.title = '策略下发';
			vm.strategyId = value;
			$('#sel4').val('none');
			vm.hidden('#CentOS');
			vm.hidden('#Ubuntu');
			$("[name='device']").removeAttr("checked");
			$('#strategyIssued').modal('show');
		},
		switchDeviceType : function() {
			var sel_val = $('#sel4').val();
			if (sel_val == 'none') {
				vm.hidden('#CentOS');
				vm.hidden('#Ubuntu');
			}
			if (sel_val == 0) {
				vm.show('#CentOS');
				vm.hidden('#Ubuntu');
			}
			if (sel_val == 1) {
				vm.hidden('#CentOS');
				vm.show('#Ubuntu');
			}
		},
		strategyIssued : function() {
			var deviceIds = $('input[name="device"]:checked').map(function() {
				return this.value;
			}).get().join(";");
			$.ajax({
				url : '/security/strategyIssued',
				data : {
					strategyId : vm.strategyId,
					deviceIds : deviceIds
				},
				dataType : 'text',
				success : function(data) {
					alert(data);
				}
			});
			$('#strategyIssued').modal('hide');
		},
		reset : function() {
			vm.title = '';
			vm.strategyId = 0;
			vm.name = '';
			vm.type = 'none';
			vm.ips = '';
			vm.ipStatus = 0;
			vm.ports = '';
			vm.portStatus = 0;
			$('#sel').val('none');
			vm.hidden('#portPolicy');
			vm.hidden('#ipPolicy');
		}
	}
});

$(document).ready(
		function() {
			$.ajax({
				url : '/security/listDevices',
				success : function(data) {
					for (var i = 0; i < data.length; i++) {
						if (data[i].type == 'CentOS')
							$('#CentOS').append(
									'<label><input type="checkbox" name="device" value="'
											+ data[i].deviceId + '">'
											+ data[i].deviceName
											+ '</label>&nbsp;')
						if (data[i].type == 'Ubuntu')
							$('#Ubuntu').append(
									'<label><input type="checkbox" name="device" value="'
											+ data[i].deviceId + '">'
											+ data[i].deviceName
											+ '</label>&nbsp;')
					}
				}
			});
		})