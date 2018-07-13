$(function () {
	$("#jqGrid").jqGrid({
        url: '../userDefined/listOPCrules',
        datatype: "json",
        colModel: [			
        	{ label: '序号', name: 'userDefinedOPCId', key: true },
        	{ label: '规则名称', name: 'ruleName' },
			{ label: '源IP', name: 'originIP' },
			{ label: '目的IP', name: 'goalIP' },
			{ label: '传输层协议', name: 'protocol' },
			{ label: '接口名', name: 'interfaces' },
			{ label: '方法名', name: 'method' },
			{ label: '操作', name: 'operating', formatter: function(value, options, row) {
				return '<a class="btn btn-primary btn-xs" onclick="vm.openOPCModify(' + row.userDefinedOPCId + ')">编辑</a>'
			} }
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

function changeToOPC() {
	$.jgrid.gridUnload('#jqGrid');
	$("#jqGrid").jqGrid({
        url: '../userDefined/listOPCrules',
        datatype: "json",
        colModel: [			
        	{ label: '序号', name: 'userDefinedOPCId', key: true },
        	{ label: '规则名称', name: 'ruleName' },
			{ label: '源IP', name: 'originIP' },
			{ label: '目的IP', name: 'goalIP' },
			{ label: '传输层协议', name: 'protocol' },
			{ label: '接口名', name: 'interfaces' },
			{ label: '方法名', name: 'method' },
			{ label: '操作', name: 'operating', formatter: function(value, options, row) {
				return '<a class="btn btn-primary btn-xs" onclick="vm.openOPCModify(' + row.userDefinedOPCId + ')">编辑</a>'
			} }
        ],
		viewrecords: true,
        height: 450,
        rowNum: 10,
		rowList : [10,30,50],
        rownumbers: true, 
        rownumWidth: 25, 
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
}

function changeToS7() {
	$.jgrid.gridUnload('#jqGrid');
	$("#jqGrid").jqGrid({
        url: '../userDefined/listS7rules',
        datatype: "json",
        colModel: [			
        	{ label: '序号', name: 'userDefinedS7Id', key: true },
        	{ label: '规则名称', name: 'ruleName' },
			{ label: '源IP', name: 'originIP' },
			{ label: '目的IP', name: 'goalIP' },
			{ label: '源IP掩码', name: 'originIPmac' },
			{ label: '目的IP掩码', name: 'goalIPmac' },
			{ label: '读写属性', name: 'readwriteAttr', formatter: function(value, options, row){
				if(value == 'read')
					return '读';
				else if(value == 'write')
					return '写';
			} },
			{ label: '寄存器区', name: 'registerArea' },
			{ label: 'DB区区号', name: 'dbAreaNum' },
			{ label: '点类型', name: 'pointType' },
			{ label: '起始地址', name: 'initialAddr' },
			{ label: '结束地址', name: 'endAddr' },
			{ label: '传输层协议', name: 'protocol' },
			{ label: '操作', name: 'operating', formatter: function(value, options, row) {
				return '<a class="btn btn-primary btn-xs" onclick="vm.openS7Modify(' + row.userDefinedS7Id + ')">编辑</a>'
			} }
        ],
		viewrecords: true,
		autowidth: true,
        height: 450,
        rowNum: 10,
		rowList : [10,30,50],
        rownumbers: true, 
        rownumWidth: 25, 
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
}

function changeToMODBUS() {
	$.jgrid.gridUnload('#jqGrid');
	$("#jqGrid").jqGrid({
        url: '../userDefined/listMODBUSrules',
        datatype: "json",
        colModel: [			
        	{ label: '序号', name: 'userDefinedMODBUSId', key: true },
        	{ label: '规则名称', name: 'ruleName' },
			{ label: '源IP', name: 'originIP' },
			{ label: '目的IP', name: 'goalIP' },
			{ label: '源IP掩码', name: 'originIPmac' },
			{ label: '目的IP掩码', name: 'goalIPmac' },
			{ label: '功能码', name: 'functionCode' },
			{ label: '起始地址', name: 'initialAddr' },
			{ label: '结束地址', name: 'endAddr' },
			{ label: '传输层协议', name: 'protocol' },
			{ label: '操作', name: 'operating', formatter: function(value, options, row) {
				return '<a class="btn btn-primary btn-xs" onclick="vm.openMODBUSModify(' + row.userDefinedMODBUSId + ')">编辑</a>'
			} }
        ],
		viewrecords: true,
        height: 450,
        rowNum: 10,
		rowList : [10,30,50],
        rownumbers: true, 
        rownumWidth: 25, 
        autowidth: true,
        multiselect: true,
        pager: "#jqGridPager",
        jsonReader : { 
            root: "page.list",
            page: "page.currPage",
            total: "page.totalPagemodbus",
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
}

var vm = new Vue({
	el : '#rrapp',
	data : {
		opcRule : {
			userDefinedOPCId : 0,
			ruleName : '',
			originIP : '',
			goalIP : '',
			protocol : 'tcp',
			interfaces : '',
			method : '',
		},
		s7Rule : {
			userDefinedS7Id : 0,
			ruleName : '',
			originIP : '',
			goalIP : '',
			originIPmac : '',
			goalIPmac : '',
			readwriteAttr : '',
			registerArea : '',
			dbAreaNum : '',
			pointType : '',
			initialAddr : '',
			endAddr : '',
			protocol : 'tcp'
		},
		modbusRule : {
			userDefinedMODBUSId : 0,
			ruleName : '',
			originIP : '',
			goalIP : '',
			originIPmac : '',
			goalIPmac : '',
			functionCode : '',
			initialAddr : '',
			endAddr : '',
			protocol : 'tcp'
		},
		title : ''
	},
	methods : {
		resetOPC : function() {
			vm.opcRule.userDefinedOPCId = 0;
			vm.opcRule.ruleName = '';
			vm.opcRule.originIP = '';
			vm.opcRule.goalIP = '';
			vm.opcRule.protocol = 'tcp';
			vm.opcRule.interfaces = '';
			vm.opcRule.method = '';
		},
		deleteOPCrule : function() {
			var rowId = $('#jqGrid').jqGrid('getGridParam', 'selarrrow');
			if(rowId == null || rowId.length <= 0) {
				alert("请选中需要删除的数据");
				return;
			} else {
				confirm('确定要删除这些规则？', function() {
					var opcRuleIds = new Array();
					for (var i = 0; i < rowId.length; i++) {
						var rowData = $("#jqGrid").jqGrid('getRowData', rowId[i]);
						opcRuleIds.push(rowData.userDefinedOPCId);
					}
					$.ajax({
						url: '../userDefined/deleteOPCrules',
						data: {opcRuleIds : opcRuleIds.join(";")},
						dataType : 'text',
						success: function(data) {
							if(data != '删除成功')
								alert(data);
							else
								$("#jqGrid").jqGrid().trigger("reloadGrid");
						}
					});
				});
			}
		},
		searchOPCrule : function() {
			$("#jqGrid").jqGrid('setGridParam',{  // 重新加载数据
			      postData : {
			    	  ruleName : $("#opcRuleName").val().trim()
			      },
			      page : 1
			}).trigger("reloadGrid");
		},
		openOPCInsert : function() {
			vm.title = "新增规则";
			vm.resetOPC();
			$('#opcModal').modal('show');
		},
		openOPCModify : function(value) {
			vm.resetOPC();
	        vm.title='编辑规则';
	        vm.opcRule.userDefinedOPCId = value;
	        $.ajax({
	    		url : '../userDefined/queryOPCuserDefinedById',
	    		async : false,
	    		data : {userDefinedOPCId : vm.opcRule.userDefinedOPCId},
	    		dataType : 'json',
	    		success : function(data) {
	    			if(data != '') {
	    				vm.opcRule = data;
	    				$("#opcModal").modal('show');
	    			} else
	    				alert("无法获得该条规则的信息");
	    		}
	    	});
		},
		vertifyOPC : function() {
			// 判断策略名称是否为空
			if(vm.opcRule.ruleName == '' || $.trim(vm.opcRule.ruleName).length == 0) {
				alert("请输入规则名称");
				return false;
			}
			// 判断IP格式是否正确
			if(vm.opcRule.originIP != '' && $.trim(vm.opcRule.originIP).length > 0) {
				if(checkIP(vm.opcRule.originIP) == false) {
					alert("源IP格式错误");
					return false;
				}
			} else {
				alert("请输入源IP");
				return false;
			}
			if(vm.opcRule.goalIP != '' && $.trim(vm.opcRule.goalIP).length > 0) {
				if(checkIP(vm.opcRule.goalIP) == false) {
					alert("目的IP格式错误");
					return false;
				}
			} else {
				alert("请输入目的IP");
				return false;
			}
			if(vm.opcRule.interfaces == '' || $.trim(vm.opcRule.interfaces).length <= 0) {
				alert("请选择接口名");
				return false;
			}
			if(vm.opcRule.method == '' || $.trim(vm.opcRule.method).length <= 0) {
				alert("请选择方法名");
				return false;
			}
			return true;
		},
		insertOrUpdateOPC : function() {
			if(!vm.vertifyOPC())
				return;
			if(vm.opcRule.userDefinedOPCId == 0) {
				$.ajax({
					url : '../userDefined/addOPCrule',
					data : vm.opcRule,
					dataType : 'text',
					success : function(data) {
						alert(data);
						$("#jqGrid").jqGrid().trigger("reloadGrid");
						vm.resetOPC();
					}
				});
				$("#opcModal").modal("hide");
			} else {
				$.ajax({
					url : '../userDefined/modifyOPCrule',
					data : vm.opcRule,
					dataType : 'text',
					success : function(data) {
						alert(data);
						$("#jqGrid").jqGrid().trigger("reloadGrid");
					}
				});
				$("#opcModal").modal("hide");
			}
		},
		resetS7 : function() {
			vm.s7Rule.userDefinedS7Id = 0;
			vm.s7Rule.ruleName = '';
			vm.s7Rule.originIP = '';
			vm.s7Rule.goalIP = '';
			vm.s7Rule.originIPmac = '';
			vm.s7Rule.goalIPmac = '';
			vm.s7Rule.readwriteAttr = '';
			vm.s7Rule.registerArea = '';
			vm.s7Rule.dbAreaNum = '';
			vm.s7Rule.pointType = '';
			vm.s7Rule.initialAddr = '';
			vm.s7Rule.endAddr = '';
			vm.s7Rule.protocol = 'tcp';
		},
		deleteS7rule : function() {
			var rowId = $('#jqGrid').jqGrid('getGridParam', 'selarrrow');
			if(rowId == null || rowId.length <= 0) {
				alert("请选中需要删除的数据");
				return;
			} else {
				confirm('确定要删除这些规则？', function() {
					var s7RuleIds = new Array();
					for (var i = 0; i < rowId.length; i++) {
						var rowData = $("#jqGrid").jqGrid('getRowData', rowId[i]);
						s7RuleIds.push(rowData.userDefinedS7Id);
					}
					$.ajax({
						url: '../userDefined/deleteS7rules',
						data: {s7RuleIds : s7RuleIds.join(";")},
						dataType : 'text',
						success: function(data) {
							if(data != '删除成功')
								alert(data);
							else
								$("#jqGrid").jqGrid().trigger("reloadGrid");
						}
					});
				});
			}
		},
		searchS7rule : function() {
			$("#jqGrid").jqGrid('setGridParam',{  // 重新加载数据
			      postData : {
			    	  ruleName : $("#s7RuleName").val().trim()
			      },
			      page : 1
			}).trigger("reloadGrid");
		},
		openS7Insert : function() {
			vm.title = "新增规则";
			vm.resetS7();
			$('#s7Modal').modal('show');
		},
		openS7Modify : function(value) {
			vm.resetS7();
	        vm.title='编辑规则';
	        vm.s7Rule.userDefinedS7Id = value;
	        $.ajax({
	    		url : '../userDefined/queryS7userDefinedById',
	    		async : false,
	    		data : {userDefinedS7Id : vm.s7Rule.userDefinedS7Id},
	    		dataType : 'json',
	    		success : function(data) {
	    			if(data != '') {
	    				vm.s7Rule = data;
	    				$("#s7Modal").modal('show');
	    			} else
	    				alert("无法获得该条规则的信息");
	    		}
	    	});
		},
		vertifyS7 : function() {
			// 判断策略名称是否为空
			if(vm.s7Rule.ruleName == '' || $.trim(vm.s7Rule.ruleName).length == 0) {
				alert("请输入规则名称");
				return false;
			}
			// 判断IP格式是否正确
			if(vm.s7Rule.originIP != '' && $.trim(vm.s7Rule.originIP).length > 0) {
				if(checkIP(vm.s7Rule.originIP) == false) {
					alert("源IP格式错误");
					return false;
				}
			} else {
				alert("请输入源IP");
				return false;
			}
			if(vm.s7Rule.goalIP != '' && $.trim(vm.s7Rule.goalIP).length > 0) {
				if(checkIP(vm.s7Rule.goalIP) == false) {
					alert("目的IP格式错误");
					return false;
				}
			} else {
				alert("请输入目的IP");
				return false;
			}
			if(vm.s7Rule.originIPmac != '' && $.trim(vm.s7Rule.originIPmac).length > 0) {
				if(checkIP(vm.s7Rule.originIPmac) == false) {
					alert("源IP掩码格式错误");
					return false;
				}
			} else {
				alert("请输入源IP掩码");
				return false;
			}
			if(vm.s7Rule.goalIPmac != '' && $.trim(vm.s7Rule.goalIPmac).length > 0) {
				if(checkIP(vm.s7Rule.goalIPmac) == false) {
					alert("目的IP掩码格式错误");
					return false;
				}
			} else {
				alert("请输入目的IP掩码");
				return false;
			}
			if(vm.s7Rule.readwriteAttr == '' || $.trim(vm.s7Rule.readwriteAttr).length <= 0) {
				alert("请选择读写属性");
				return false;
			}
			if(vm.s7Rule.registerArea == '' || $.trim(vm.s7Rule.registerArea).length <= 0) {
				alert("请选择寄存器区");
				return false;
			}
			if(vm.s7Rule.dbAreaNum == '' || $.trim(vm.s7Rule.dbAreaNum).length <= 0) {
				alert("请输入DB区区号");
				return false;
			}
			if(vm.s7Rule.pointType == '' || $.trim(vm.s7Rule.pointType).length <= 0) {
				alert("请选择点类型");
				return false;
			}
			/*if(vm.s7Rule.initialAddr != '' && $.trim(vm.s7Rule.initialAddr).length > 0) {
				if(checkIP(vm.s7Rule.initialAddr) == false) {
					alert("起始地址格式错误");
					return false;
				}
			} else {
				alert("请输入起始地址");
				return false;
			}
			if(vm.s7Rule.endAddr != '' && $.trim(vm.s7Rule.endAddr).length > 0) {
				if(checkIP(vm.s7Rule.endAddr) == false) {
					alert("结束地址格式错误");
					return false;
				}
			} else {
				alert("请输入结束地址");
				return false;
			}*/
			return true;
		},
		insertOrUpdateS7 : function() {
			if(!vm.vertifyS7())
				return;
			if(vm.s7Rule.userDefinedS7Id == 0) {
				$.ajax({
					url : '../userDefined/addS7rule',
					data : vm.s7Rule,
					dataType : 'text',
					success : function(data) {
						alert(data);
						$("#jqGrid").jqGrid().trigger("reloadGrid");
						vm.resetS7();
					}
				});
				$("#s7Modal").modal("hide");
			} else {
				$.ajax({
					url : '../userDefined/modifyS7rule',
					data : vm.s7Rule,
					dataType : 'text',
					success : function(data) {
						alert(data);
						$("#jqGrid").jqGrid().trigger("reloadGrid");
					}
				});
				$("#s7Modal").modal("hide");
			}
		},
		resetMODBUS : function() {
			vm.modbusRule.userDefinedMODBUSId = 0;
			vm.modbusRule.ruleName = '';
			vm.modbusRule.originIP = '';
			vm.modbusRule.goalIP = '';
			vm.modbusRule.originIPmac = '';
			vm.modbusRule.goalIPmac = '';
			vm.modbusRule.functionCode = '';
			vm.modbusRule.initialAddr = '';
			vm.modbusRule.endAddr = '';
			vm.modbusRule.protocol = 'tcp';
		},
		deleteMODBUSrule : function() {
			var rowId = $('#jqGrid').jqGrid('getGridParam', 'selarrrow');
			if(rowId == null || rowId.length <= 0) {
				alert("请选中需要删除的数据");
				return;
			} else {
				confirm('确定要删除这些规则？', function() {
					var modbusRuleIds = new Array();
					for (var i = 0; i < rowId.length; i++) {
						var rowData = $("#jqGrid").jqGrid('getRowData', rowId[i]);
						modbusRuleIds.push(rowData.userDefinedMODBUSId);
					}
					$.ajax({
						url: '../userDefined/deleteMODBUSrules',
						data: {modbusRuleIds : modbusRuleIds.join(";")},
						dataType : 'text',
						success: function(data) {
							if(data != '删除成功')
								alert(data);
							else
								$("#jqGrid").jqGrid().trigger("reloadGrid");
						}
					});
				});
			}
		},
		searchMODBUSrule : function() {
			$("#jqGrid").jqGrid('setGridParam',{  // 重新加载数据
			      postData : {
			    	  ruleName : $("#modbusRuleName").val().trim()
			      },
			      page : 1
			}).trigger("reloadGrid");
		},
		openMODBUSInsert : function() {
			vm.title = "新增规则";
			vm.resetMODBUS();
			$('#modbusModal').modal('show');
		},
		openMODBUSModify : function(value) {
			vm.resetMODBUS();
	        vm.title='编辑规则';
	        vm.modbusRule.userDefinedMODBUSId = value;
	        $.ajax({
	    		url : '../userDefined/queryMODBUSuserDefinedById',
	    		async : false,
	    		data : {userDefinedMODBUSId : vm.modbusRule.userDefinedMODBUSId},
	    		dataType : 'json',
	    		success : function(data) {
	    			if(data != '') {
	    				vm.modbusRule = data;
	    				$("#modbusModal").modal('show');
	    			} else
	    				alert("无法获得该条规则的信息");
	    		}
	    	});
		},
		vertifyMODBUS : function() {
			// 判断策略名称是否为空
			if(vm.modbusRule.ruleName == '' || $.trim(vm.modbusRule.ruleName).length == 0) {
				alert("请输入规则名称");
				return false;
			}
			// 判断IP格式是否正确
			if(vm.modbusRule.originIP != '' && $.trim(vm.modbusRule.originIP).length > 0) {
				if(checkIP(vm.modbusRule.originIP) == false) {
					alert("源IP格式错误");
					return false;
				}
			} else {
				alert("请输入源IP");
				return false;
			}
			if(vm.modbusRule.goalIP != '' && $.trim(vm.modbusRule.goalIP).length > 0) {
				if(checkIP(vm.modbusRule.goalIP) == false) {
					alert("目的IP格式错误");
					return false;
				}
			} else {
				alert("请输入目的IP");
				return false;
			}
			if(vm.modbusRule.originIPmac != '' && $.trim(vm.modbusRule.originIPmac).length > 0) {
				if(checkIP(vm.modbusRule.originIPmac) == false) {
					alert("源IP格式掩码错误");
					return false;
				}
			} else {
				alert("请输入源IP掩码");
				return false;
			}
			if(vm.modbusRule.goalIPmac != '' && $.trim(vm.modbusRule.goalIPmac).length > 0) {
				if(checkIP(vm.modbusRule.goalIPmac) == false) {
					alert("目的IP掩码格式错误");
					return false;
				}
			} else {
				alert("请输入目的IP掩码");
				return false;
			}
			if(vm.modbusRule.functionCode == '' || $.trim(vm.modbusRule.functionCode).length <= 0) {
				alert("请选择功能码");
				return false;
			}
			/*if(vm.modbusRule.initialAddr != '' && $.trim(vm.modbusRule.initialAddr).length > 0) {
				if(checkIP(vm.modbusRule.initialAddr) == false) {
					alert("起始地址格式错误");
					return false;
				}
			} else {
				alert("请输入起始地址");
				return false;
			}
			if(vm.modbusRule.endAddr != '' && $.trim(vm.modbusRule.endAddr).length > 0) {
				if(checkIP(vm.modbusRule.endAddr) == false) {
					alert("结束地址格式错误");
					return false;
				}
			} else {
				alert("请输入结束地址");
				return false;
			}*/
			return true;
		},
		insertOrUpdateMODBUS : function() {
			if(!vm.vertifyMODBUS())
				return;
			if(vm.modbusRule.userDefinedMODBUSId == 0) {
				$.ajax({
					url : '../userDefined/addMODBUSrule',
					data : vm.modbusRule,
					dataType : 'text',
					success : function(data) {
						alert(data);
						$("#jqGrid").jqGrid().trigger("reloadGrid");
						vm.resetMODBUS();
					}
				});
				$("#modbusModal").modal("hide");
			} else {
				$.ajax({
					url : '../userDefined/modifyMODBUSrule',
					data : vm.modbusRule,
					dataType : 'text',
					success : function(data) {
						alert(data);
						$("#jqGrid").jqGrid().trigger("reloadGrid");
					}
				});
				$("#modbusModal").modal("hide");
			}
		}
	}
})