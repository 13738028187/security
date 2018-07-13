//jqGrid 文档 http://www.helloweba.com/view-blog-162.html
$(function () {
	$("#jqGrid").jqGrid({
        url: '../whitelistRule/listOPCrules',
        datatype: "json",
        colModel: [			
        	{ label: '序号', name: 'whitelistRuleOPCId', key: true },
        	{ label: '规则名称', name: 'ruleName' },
			{ label: '源IP', name: 'originIP' },
			{ label: '目的IP', name: 'goalIP' },
			{ label: '传输层协议', name: 'protocol' },
			{ label: '接口名', name: 'interfaces' },
			{ label: '方法名', name: 'method' },
			{ label: '操作', name: 'operating', formatter: function(value, options, row) {
				return '<a class="btn btn-primary btn-xs" onclick="vm.openOPCModify(' + row.whitelistRuleOPCId + ')">编辑</a>'
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
        url: '../whitelistRule/listOPCrules',
        datatype: "json",
        colModel: [			
        	{ label: '序号', name: 'whitelistRuleOPCId', key: true },
        	{ label: '规则名称', name: 'ruleName' },
			{ label: '源IP', name: 'originIP' },
			{ label: '目的IP', name: 'goalIP' },
			{ label: '传输层协议', name: 'protocol' },
			{ label: '接口名', name: 'interfaces' },
			{ label: '方法名', name: 'method' },
			{ label: '操作', name: 'operating', formatter: function(value, options, row) {
				return '<a class="btn btn-primary btn-xs" onclick="vm.openOPCModify(' + row.whitelistRuleOPCId + ')">编辑</a>'
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
}

function changeToS7() {
	$.jgrid.gridUnload('#jqGrid');
	$("#jqGrid").jqGrid({
        url: '../whitelistRule/listS7rules',
        datatype: "json",
        colModel: [			
        	{ label: '序号', name: 'whitelistRuleS7Id', key: true },
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
				return '<a class="btn btn-primary btn-xs" onclick="vm.openS7Modify(' + row.whitelistRuleS7Id + ')">编辑</a>'
			} }
        ],
		viewrecords: true,
		autowidth: true,
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
}

function changeToMODBUS() {
	$.jgrid.gridUnload('#jqGrid');
	$("#jqGrid").jqGrid({
        url: '../whitelistRule/listMODBUSrules',
        datatype: "json",
        colModel: [			
        	{ label: '序号', name: 'whitelistRuleMODBUSId', key: true },
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
				return '<a class="btn btn-primary btn-xs" onclick="vm.openMODBUSModify(' + row.whitelistRuleMODBUSId + ')">编辑</a>'
			} }
        ],
		viewrecords: true,
        height: 450,
        rowNum: 10,
		rowList : [10,30,50],
        rownumbers: true, 
        rownumWidth: 50, 
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

function changeToCIP() {
	$.jgrid.gridUnload('#jqGrid');
	$("#jqGrid").jqGrid({
        url: '../whitelistRule/listCIPrules',
        datatype: "json",
        colModel: [			
        	{ label: '序号', name: 'whitelistRuleCIPId', key: true },
        	{ label: '规则名称', name: 'ruleName' },
			{ label: '源IP', name: 'originIP' },
			{ label: '目的IP', name: 'goalIP' },
			{ label: '源IP掩码', name: 'originIPmac' },
			{ label: '目的IP掩码', name: 'goalIPmac' },
			{ label: '功能码', name: 'functionCode' },
			{ label: '点名', name: 'pointName' },
			{ label: '点类型', name: 'pointType', formatter: function(value, options, row) {
				if(value == 'singlePoint')
					return '单点';
				else if(value == 'arrayPoint')
					return '数组点';
			} },
			{ label: '起始地址', name: 'initialAddr' },
			{ label: '结束地址', name: 'endAddr' },
			{ label: '传输层协议', name: 'protocol' },
			{ label: '操作', name: 'operating', formatter: function(value, options, row) {
				return '<a class="btn btn-primary btn-xs" onclick="vm.openCIPModify(' + row.whitelistRuleCIPId + ')">编辑</a>'
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
}

function changeToICMP() {
	$.jgrid.gridUnload('#jqGrid');
	$("#jqGrid").jqGrid({
        url: '../whitelistRule/listICMPrules',
        datatype: "json",
        colModel: [			
        	{ label: '序号', name: 'whitelistRuleICMPId', key: true },
        	{ label: '规则名称', name: 'ruleName' },
			{ label: '源IP', name: 'originIP' },
			{ label: '目的IP', name: 'goalIP' },
			{ label: '源IP掩码', name: 'originIPmac' },
			{ label: '目的IP掩码', name: 'goalIPmac' },
			{ label: '操作', name: 'operating', formatter: function(value, options, row) {
				return '<a class="btn btn-primary btn-xs" onclick="vm.openICMPModify(' + row.whitelistRuleICMPId + ')">编辑</a> <a class="btn btn-primary btn-xs" onclick="vm.executeICMPrule(' + row.whitelistRuleICMPId + ')">执行</a>'
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
}

function changeToTCP() {
	$.jgrid.gridUnload('#jqGrid');
	$("#jqGrid").jqGrid({
        url: '../whitelistRule/listTCPrules',
        datatype: "json",
        colModel: [			
        	{ label: '序号', name: 'whitelistRuleTCPId', key: true },
        	{ label: '规则名称', name: 'ruleName' },
			{ label: '源IP', name: 'originIP' },
			{ label: '目的IP', name: 'goalIP' },
			{ label: '源IP掩码', name: 'originIPmac' },
			{ label: '目的IP掩码', name: 'goalIPmac' },
			{ label: '操作', name: 'operating', formatter: function(value, options, row) {
				return '<a class="btn btn-primary btn-xs" onclick="vm.openTCPModify(' + row.whitelistRuleTCPId + ')">编辑</a> <a class="btn btn-primary btn-xs" onclick="vm.executeTCPrule(' + row.whitelistRuleTCPId + ')">执行</a>'
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
}

function changeToUDP() {
	$.jgrid.gridUnload('#jqGrid');
	$("#jqGrid").jqGrid({
        url: '../whitelistRule/listUDPrules',
        datatype: "json",
        colModel: [			
        	{ label: '序号', name: 'whitelistRuleUDPId', key: true },
        	{ label: '规则名称', name: 'ruleName' },
			{ label: '源IP', name: 'originIP' },
			{ label: '目的IP', name: 'goalIP' },
			{ label: '源IP掩码', name: 'originIPmac' },
			{ label: '目的IP掩码', name: 'goalIPmac' },
			{ label: '操作', name: 'operating', formatter: function(value, options, row) {
				return '<a class="btn btn-primary btn-xs" onclick="vm.openUDPModify(' + row.whitelistRuleUDPId + ')">编辑</a> <a class="btn btn-primary btn-xs" onclick="vm.executeUDPrule(' + row.whitelistRuleUDPId + ')">执行</a>'
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
}

var vm = new Vue({
	el : '#rrapp',
	data : {
		opcRule : {
			whitelistRuleOPCId : 0,
			ruleName : '',
			originIP : '',
			goalIP : '',
			protocol : 'tcp',
			interfaces : '',
			method : '',
		},
		s7Rule : {
			whitelistRuleS7Id : 0,
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
			whitelistRuleMODBUSId : 0,
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
		cipRule : {
			whitelistRuleCIPId : 0,
			ruleName : '',
			originIP : '',
			goalIP : '',
			originIPmac : '',
			goalIPmac : '',
			functionCode : '',
			pointName : '',
			pointType : '',
			initialAddr : '',
			endAddr : '',
			protocol : 'tcp'
		},
		icmpRule : {
			whitelistRuleICMPId : 0,
			ruleName : '',
			originIP : '',
			goalIP : '',
			originIPmac : '',
			goalIPmac : ''
		},
		tcpRule : {
			whitelistRuleTCPId : 0,
			ruleName : '',
			originIP : '',
			goalIP : '',
			originIPmac : '',
			goalIPmac : ''
		},
		udpRule : {
			whitelistRuleUDPId : '',
			ruleName : '',
			originIP : '',
			goalIP : '',
			originIPmac : '',
			goalIPmac : ''
		},
		title : ''
	},
	methods : {
		resetOPC : function() {
			vm.opcRule.whitelistRuleOPCId = 0;
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
						opcRuleIds.push(rowData.whitelistRuleOPCId);
					}
					$.ajax({
						url: '../whitelistRule/deleteOPCrules',
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
	        vm.opcRule.whitelistRuleOPCId = value;
	        $.ajax({
	    		url : '../whitelistRule/queryOPCwhitelistRuleById',
	    		async : false,
	    		data : {whitelistRuleOPCId : vm.opcRule.whitelistRuleOPCId},
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
			if(vm.opcRule.whitelistRuleOPCId == 0) {
				$.ajax({
					url : '../whitelistRule/addOPCrule',
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
					url : '../whitelistRule/modifyOPCrule',
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
			vm.s7Rule.whitelistRuleS7Id = 0;
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
						s7RuleIds.push(rowData.whitelistRuleS7Id);
					}
					$.ajax({
						url: '../whitelistRule/deleteS7rules',
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
	        vm.s7Rule.whitelistRuleS7Id = value;
	        $.ajax({
	    		url : '../whitelistRule/queryS7whitelistRuleById',
	    		async : false,
	    		data : {whitelistRuleS7Id : vm.s7Rule.whitelistRuleS7Id},
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
			if(vm.s7Rule.whitelistRuleS7Id == 0) {
				$.ajax({
					url : '../whitelistRule/addS7rule',
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
					url : '../whitelistRule/modifyS7rule',
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
			vm.modbusRule.whitelistRuleMODBUSId = 0;
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
						modbusRuleIds.push(rowData.whitelistRuleMODBUSId);
					}
					$.ajax({
						url: '../whitelistRule/deleteMODBUSrules',
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
	        vm.modbusRule.whitelistRuleMODBUSId = value;
	        $.ajax({
	    		url : '../whitelistRule/queryMODBUSwhitelistRuleById',
	    		async : false,
	    		data : {whitelistRuleMODBUSId : vm.modbusRule.whitelistRuleMODBUSId},
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
					alert("源IP掩码格式错误");
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
			if(vm.modbusRule.whitelistRuleMODBUSId == 0) {
				$.ajax({
					url : '../whitelistRule/addMODBUSrule',
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
					url : '../whitelistRule/modifyMODBUSrule',
					data : vm.modbusRule,
					dataType : 'text',
					success : function(data) {
						alert(data);
						$("#jqGrid").jqGrid().trigger("reloadGrid");
					}
				});
				$("#modbusModal").modal("hide");
			}
		},
		resetCIP : function() {
			vm.cipRule.whitelistRuleCIPId = 0;
			vm.cipRule.ruleName = '';
			vm.cipRule.originIP = '';
			vm.cipRule.goalIP = '';
			vm.cipRule.originIPmac = '';
			vm.cipRule.goalIPmac = '';
			vm.cipRule.functionCode = '';
			vm.cipRule.pointName = '';
			vm.cipRule.pointType = '';
			vm.cipRule.initialAddr = '';
			vm.cipRule.endAddr = '';
			vm.cipRule.protocol = 'tcp';
		},
		deleteCIPrule : function() {
			var rowId = $('#jqGrid').jqGrid('getGridParam', 'selarrrow');
			if(rowId == null || rowId.length <= 0) {
				alert("请选中需要删除的数据");
				return;
			} else {
				confirm('确定要删除这些规则？', function() {
					var cipRuleIds = new Array();
					for (var i = 0; i < rowId.length; i++) {
						var rowData = $("#jqGrid").jqGrid('getRowData', rowId[i]);
						cipRuleIds.push(rowData.whitelistRuleCIPId);
					}
					$.ajax({
						url: '../whitelistRule/deleteCIPrules',
						data: {cipRuleIds : cipRuleIds.join(";")},
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
		searchCIPrule : function() {
			$("#jqGrid").jqGrid('setGridParam',{  // 重新加载数据
			      postData : {
			    	  ruleName : $("#cipRuleName").val().trim()
			      },
			      page : 1
			}).trigger("reloadGrid");
		},
		openCIPInsert : function() {
			vm.title = "新增规则";
			vm.resetCIP();
			$('#cipModal').modal('show');
		},
		openCIPModify : function(value) {
			vm.resetCIP();
	        vm.title='编辑规则';
	        vm.cipRule.whitelistRuleCIPId = value;
	        $.ajax({
	    		url : '../whitelistRule/queryCIPwhitelistRuleById',
	    		async : false,
	    		data : {whitelistRuleCIPId : vm.cipRule.whitelistRuleCIPId},
	    		dataType : 'json',
	    		success : function(data) {
	    			if(data != '') {
	    				vm.cipRule = data;
	    				$("#cipModal").modal('show');
	    			} else
	    				alert("无法获得该条规则的信息");
	    		}
	    	});
		},
		vertifyCIP : function() {
			// 判断策略名称是否为空
			if(vm.cipRule.ruleName == '' || $.trim(vm.cipRule.ruleName).length == 0) {
				alert("请输入规则名称");
				return false;
			}
			// 判断IP格式是否正确
			if(vm.cipRule.originIP != '' && $.trim(vm.cipRule.originIP).length > 0) {
				if(checkIP(vm.cipRule.originIP) == false) {
					alert("源IP格式错误");
					return false;
				}
			} else {
				alert("请输入源IP");
				return false;
			}
			if(vm.cipRule.goalIP != '' && $.trim(vm.cipRule.goalIP).length > 0) {
				if(checkIP(vm.cipRule.goalIP) == false) {
					alert("目的IP格式错误");
					return false;
				}
			} else {
				alert("请输入目的IP");
				return false;
			}
			if(vm.cipRule.originIPmac != '' && $.trim(vm.cipRule.originIPmac).length > 0) {
				if(checkIP(vm.cipRule.originIPmac) == false) {
					alert("源IP掩码格式错误");
					return false;
				}
			} else {
				alert("请输入源IP掩码");
				return false;
			}
			if(vm.cipRule.goalIPmac != '' && $.trim(vm.cipRule.goalIPmac).length > 0) {
				if(checkIP(vm.cipRule.goalIPmac) == false) {
					alert("目的IP掩码格式错误");
					return false;
				}
			} else {
				alert("请输入目的IP掩码");
				return false;
			}
			if(vm.cipRule.functionCode == '' || $.trim(vm.cipRule.functionCode).length <= 0) {
				alert("请选择功能码");
				return false;
			}
			if(vm.cipRule.pointName == '' || $.trim(vm.cipRule.pointName).length <= 0) {
				alert("请输入点名");
				return false;
			}
			if(vm.cipRule.pointType == '' || $.trim(vm.cipRule.pointType).length <= 0) {
				alert("请选择点类型");
				return false;
			}
			/*if(vm.cipRule.initialAddr != '' && $.trim(vm.cipRule.initialAddr).length > 0) {
				if(checkIP(vm.cipRule.initialAddr) == false) {
					alert("起始地址格式错误");
					return false;
				}
			} else {
				alert("请输入起始地址");
				return false;
			}
			if(vm.cipRule.endAddr != '' && $.trim(vm.cipRule.endAddr).length > 0) {
				if(checkIP(vm.cipRule.endAddr) == false) {
					alert("结束地址格式错误");
					return false;
				}
			} else {
				alert("请输入结束地址");
				return false;
			}*/
			return true;
		},
		insertOrUpdateCIP : function() {
			if(!vm.vertifyCIP())
				return;
			if(vm.cipRule.whitelistRuleCIPId == 0) {
				$.ajax({
					url : '../whitelistRule/addCIPrule',
					data : vm.cipRule,
					dataType : 'text',
					success : function(data) {
						alert(data);
						$("#jqGrid").jqGrid().trigger("reloadGrid");
						vm.resetCIP();
					}
				});
				$("#cipModal").modal("hide");
			} else {
				$.ajax({
					url : '../whitelistRule/modifyCIPrule',
					data : vm.cipRule,
					dataType : 'text',
					success : function(data) {
						alert(data);
						$("#jqGrid").jqGrid().trigger("reloadGrid");
					}
				});
				$("#cipModal").modal("hide");
			}
		},
		resetICMP : function() {
			vm.icmpRule.whitelistRuleICMPId = 0;
			vm.icmpRule.ruleName = '';
			vm.icmpRule.originIP = '';
			vm.icmpRule.goalIP = '';
			vm.icmpRule.originIPmac = '';
			vm.icmpRule.goalIPmac = '';
		},
		deleteICMPrule : function() {
			var rowId = $('#jqGrid').jqGrid('getGridParam', 'selarrrow');
			if(rowId == null || rowId.length <= 0) {
				alert("请选中需要删除的数据");
				return;
			} else {
				confirm('确定要删除这些规则？', function() {
					var icmpRuleIds = new Array();
					for (var i = 0; i < rowId.length; i++) {
						var rowData = $("#jqGrid").jqGrid('getRowData', rowId[i]);
						icmpRuleIds.push(rowData.whitelistRuleICMPId);
					}
					$.ajax({
						url: '../whitelistRule/deleteICMPrules',
						data: {icmpRuleIds : icmpRuleIds.join(";")},
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
		searchICMPrule : function() {
			$("#jqGrid").jqGrid('setGridParam',{  // 重新加载数据
			      postData : {
			    	  ruleName : $("#icmpRuleName").val().trim()
			      },
			      page : 1
			}).trigger("reloadGrid");
		},
		openICMPInsert : function() {
			vm.title = "新增规则";
			vm.resetICMP();
			$('#icmpModal').modal('show');
		},
		openICMPModify : function(value) {
			vm.resetICMP();
	        vm.title='编辑规则';
	        vm.icmpRule.whitelistRuleICMPId = value;
	        $.ajax({
	    		url : '../whitelistRule/queryICMPwhitelistRuleById',
	    		async : false,
	    		data : {whitelistRuleICMPId : vm.icmpRule.whitelistRuleICMPId},
	    		dataType : 'json',
	    		success : function(data) {
	    			if(data != '') {
	    				vm.icmpRule = data;
	    				$("#icmpModal").modal('show');
	    			} else
	    				alert("无法获得该条规则的信息");
	    		}
	    	});
		},
		vertifyICMP : function() {
			// 判断策略名称是否为空
			if(vm.icmpRule.ruleName == '' || $.trim(vm.icmpRule.ruleName).length == 0) {
				alert("请输入规则名称");
				return false;
			}
			// 判断IP格式是否正确
			if(vm.icmpRule.originIP != '' && $.trim(vm.icmpRule.originIP).length > 0) {
				if(checkIP(vm.icmpRule.originIP) == false) {
					alert("源IP格式错误");
					return false;
				}
			} else {
				alert("请输入源IP");
				return false;
			}
			if(vm.icmpRule.goalIP != '' && $.trim(vm.icmpRule.goalIP).length > 0) {
				if(checkIP(vm.icmpRule.goalIP) == false) {
					alert("目的IP格式错误");
					return false;
				}
			} else {
				alert("请输入目的IP");
				return false;
			}
			if(vm.icmpRule.originIPmac != '' && $.trim(vm.icmpRule.originIPmac).length > 0) {
				if(checkIP(vm.icmpRule.originIPmac) == false) {
					alert("源IP掩码格式错误");
					return false;
				}
			} else {
				alert("请输入源IP掩码");
				return false;
			}
			if(vm.icmpRule.goalIPmac != '' && $.trim(vm.icmpRule.goalIPmac).length > 0) {
				if(checkIP(vm.icmpRule.goalIPmac) == false) {
					alert("目的IP掩码格式错误");
					return false;
				}
			} else {
				alert("请输入目的IP掩码");
				return false;
			}
			return true;
		},
		insertOrUpdateICMP : function() {
			if(!vm.vertifyICMP())
				return;
			if(vm.icmpRule.whitelistRuleICMPId == 0) {
				$.ajax({
					url : '../whitelistRule/addICMPrule',
					data : vm.icmpRule,
					dataType : 'text',
					success : function(data) {
						alert(data);
						$("#jqGrid").jqGrid().trigger("reloadGrid");
						vm.resetICMP();
					}
				});
				$("#icmpModal").modal("hide");
			} else {
				$.ajax({
					url : '../whitelistRule/modifyICMPrule',
					data : vm.icmpRule,
					dataType : 'text',
					success : function(data) {
						alert(data);
						$("#jqGrid").jqGrid().trigger("reloadGrid");
					}
				});
				$("#icmpModal").modal("hide");
			}
		},
		executeICMPrule : function(whitelistRuleICMPId) {
			$.ajax({
				url : '../whitelistRule/executeICMPrule',
				data : {whitelistRuleICMPId : whitelistRuleICMPId},
				dataType : 'text',
				success : function(data) {
					alert(data);
				}
			});
		},
		resetTCP : function() {
			vm.tcpRule.whitelistRuleTCPId = 0;
			vm.tcpRule.ruleName = '';
			vm.tcpRule.originIP = '';
			vm.tcpRule.goalIP = '';
			vm.tcpRule.originIPmac = '';
			vm.tcpRule.goalIPmac = '';
		},
		deleteTCPrule : function() {
			var rowId = $('#jqGrid').jqGrid('getGridParam', 'selarrrow');
			if(rowId == null || rowId.length <= 0) {
				alert("请选中需要删除的数据");
				return;
			} else {
				confirm('确定要删除这些规则？', function() {
					var tcpRuleIds = new Array();
					for (var i = 0; i < rowId.length; i++) {
						var rowData = $("#jqGrid").jqGrid('getRowData', rowId[i]);
						tcpRuleIds.push(rowData.whitelistRuleTCPId);
					}
					$.ajax({
						url: '../whitelistRule/deleteTCPrules',
						data: {tcpRuleIds : tcpRuleIds.join(";")},
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
		searchTCPrule : function() {
			$("#jqGrid").jqGrid('setGridParam',{  // 重新加载数据
			      postData : {
			    	  ruleName : $("#tcpRuleName").val().trim()
			      },
			      page : 1
			}).trigger("reloadGrid");
		},
		openTCPInsert : function() {
			vm.title = "新增规则";
			vm.resetTCP();
			$('#tcpModal').modal('show');
		},
		openTCPModify : function(value) {
			vm.resetTCP();
	        vm.title='编辑规则';
	        vm.tcpRule.whitelistRuleTCPId = value;
	        $.ajax({
	    		url : '../whitelistRule/queryTCPwhitelistRuleById',
	    		async : false,
	    		data : {whitelistRuleTCPId : vm.tcpRule.whitelistRuleTCPId},
	    		dataType : 'json',
	    		success : function(data) {
	    			if(data != '') {
	    				vm.tcpRule = data;
	    				$("#tcpModal").modal('show');
	    			} else
	    				alert("无法获得该条规则的信息");
	    		}
	    	});
		},
		vertifyTCP : function() {
			// 判断策略名称是否为空
			if(vm.tcpRule.ruleName == '' || $.trim(vm.tcpRule.ruleName).length == 0) {
				alert("请输入规则名称");
				return false;
			}
			// 判断IP格式是否正确
			if(vm.tcpRule.originIP != '' && $.trim(vm.tcpRule.originIP).length > 0) {
				if(checkIP(vm.tcpRule.originIP) == false) {
					alert("源IP格式错误");
					return false;
				}
			} else {
				alert("请输入源IP");
				return false;
			}
			if(vm.tcpRule.goalIP != '' && $.trim(vm.tcpRule.goalIP).length > 0) {
				if(checkIP(vm.tcpRule.goalIP) == false) {
					alert("目的IP格式错误");
					return false;
				}
			} else {
				alert("请输入目的IP");
				return false;
			}
			if(vm.tcpRule.originIPmac != '' && $.trim(vm.tcpRule.originIPmac).length > 0) {
				if(checkIP(vm.tcpRule.originIPmac) == false) {
					alert("源IP掩码格式错误");
					return false;
				}
			} else {
				alert("请输入源IP掩码");
				return false;
			}
			if(vm.tcpRule.goalIPmac != '' && $.trim(vm.tcpRule.goalIPmac).length > 0) {
				if(checkIP(vm.tcpRule.goalIPmac) == false) {
					alert("目的IP掩码格式错误");
					return false;
				}
			} else {
				alert("请输入目的IP掩码");
				return false;
			}
			return true;
		},
		insertOrUpdateTCP : function() {
			if(!vm.vertifyTCP())
				return;
			if(vm.tcpRule.whitelistRuleTCPId == 0) {
				$.ajax({
					url : '../whitelistRule/addTCPrule',
					data : vm.tcpRule,
					dataType : 'text',
					success : function(data) {
						alert(data);
						$("#jqGrid").jqGrid().trigger("reloadGrid");
						vm.resetTCP();
					}
				});
				$("#tcpModal").modal("hide");
			} else {
				$.ajax({
					url : '../whitelistRule/modifyTCPrule',
					data : vm.tcpRule,
					dataType : 'text',
					success : function(data) {
						alert(data);
						$("#jqGrid").jqGrid().trigger("reloadGrid");
					}
				});
				$("#tcpModal").modal("hide");
			}
		},
		executeTCPrule : function(whitelistRuleTCPId) {
			$.ajax({
				url : '../whitelistRule/executeTCPrule',
				data : {whitelistRuleTCPId : whitelistRuleTCPId},
				dataType : 'text',
				success : function(data) {
					alert(data);
				}
			});
		},
		resetUDP : function() {
			vm.udpRule.whitelistRuleUDPId = 0;
			vm.udpRule.ruleName = '';
			vm.udpRule.originIP = '';
			vm.udpRule.goalIP = '';
			vm.udpRule.originIPmac = '';
			vm.udpRule.goalIPmac = '';
		},
		deleteUDPrule : function() {
			var rowId = $('#jqGrid').jqGrid('getGridParam', 'selarrrow');
			if(rowId == null || rowId.length <= 0) {
				alert("请选中需要删除的数据");
				return;
			} else {
				confirm('确定要删除这些规则？', function() {
					var udpRuleIds = new Array();
					for (var i = 0; i < rowId.length; i++) {
						var rowData = $("#jqGrid").jqGrid('getRowData', rowId[i]);
						udpRuleIds.push(rowData.whitelistRuleUDPId);
					}
					$.ajax({
						url: '../whitelistRule/deleteUDPrules',
						data: {udpRuleIds : udpRuleIds.join(";")},
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
		searchUDPrule : function() {
			$("#jqGrid").jqGrid('setGridParam',{  // 重新加载数据
			      postData : {
			    	  ruleName : $("#udpRuleName").val().trim()
			      },
			      page : 1
			}).trigger("reloadGrid");
		},
		openUDPInsert : function() {
			vm.title = "新增规则";
			vm.resetUDP();
			$('#udpModal').modal('show');
		},
		openUDPModify : function(value) {
			vm.resetUDP();
	        vm.title='编辑规则';
	        vm.udpRule.whitelistRuleUDPId = value;
	        $.ajax({
	    		url : '../whitelistRule/queryUDPwhitelistRuleById',
	    		async : false,
	    		data : {whitelistRuleUDPId : vm.udpRule.whitelistRuleUDPId},
	    		dataType : 'json',
	    		success : function(data) {
	    			if(data != '') {
	    				vm.udpRule = data;
	    				$("#udpModal").modal('show');
	    			} else
	    				alert("无法获得该条规则的信息");
	    		}
	    	});
		},
		vertifyUDP : function() {
			// 判断策略名称是否为空
			if(vm.udpRule.ruleName == '' || $.trim(vm.udpRule.ruleName).length == 0) {
				alert("请输入规则名称");
				return false;
			}
			// 判断IP格式是否正确
			if(vm.udpRule.originIP != '' && $.trim(vm.udpRule.originIP).length > 0) {
				if(checkIP(vm.udpRule.originIP) == false) {
					alert("源IP格式错误");
					return false;
				}
			} else {
				alert("请输入源IP");
				return false;
			}
			if(vm.udpRule.goalIP != '' && $.trim(vm.udpRule.goalIP).length > 0) {
				if(checkIP(vm.udpRule.goalIP) == false) {
					alert("目的IP格式错误");
					return false;
				}
			} else {
				alert("请输入目的IP");
				return false;
			}
			if(vm.udpRule.originIPmac != '' && $.trim(vm.udpRule.originIPmac).length > 0) {
				if(checkIP(vm.udpRule.originIPmac) == false) {
					alert("源IP掩码格式错误");
					return false;
				}
			} else {
				alert("请输入源IP掩码");
				return false;
			}
			if(vm.udpRule.goalIPmac != '' && $.trim(vm.udpRule.goalIPmac).length > 0) {
				if(checkIP(vm.udpRule.goalIPmac) == false) {
					alert("目的IP掩码格式错误");
					return false;
				}
			} else {
				alert("请输入目的IP掩码");
				return false;
			}
			return true;
		},
		insertOrUpdateUDP : function() {
			if(!vm.vertifyUDP())
				return;
			if(vm.udpRule.whitelistRuleUDPId == 0) {
				$.ajax({
					url : '../whitelistRule/addUDPrule',
					data : vm.udpRule,
					dataType : 'text',
					success : function(data) {
						alert(data);
						$("#jqGrid").jqGrid().trigger("reloadGrid");
						vm.resetUDP();
					}
				});
				$("#udpModal").modal("hide");
			} else {
				$.ajax({
					url : '../whitelistRule/modifyUDPrule',
					data : vm.udpRule,
					dataType : 'text',
					success : function(data) {
						alert(data);
						$("#jqGrid").jqGrid().trigger("reloadGrid");
					}
				});
				$("#udpModal").modal("hide");
			}
		},
		executeUDPrule : function(whitelistRuleUDPId) {
			$.ajax({
				url : '../whitelistRule/executeUDPrule',
				data : {whitelistRuleUDPId : whitelistRuleUDPId},
				dataType : 'text',
				success : function(data) {
					alert(data);
				}
			});
		}
	}
})