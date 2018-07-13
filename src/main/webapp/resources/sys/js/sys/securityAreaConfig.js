$(function () {
    $("#jqGrid").jqGrid({
        url: '../securityArea/listSecurityAreaConfig?securityAreaId=' + window.location.search.substring(window.location.search.indexOf("=") + 1),
        datatype: "json",
        colModel: [
        	{ label: '序号', name: 'configId', width: 45, key: true },
			{ label: '网络号', name: 'networkNumber', width: 50 },
			{ label: '操作', name: 'oprating', width: 50, sortable:false, formatter: function(value, options, row){
				return '<a class="btn btn-primary btn-xs" onclick="vm.showUpdate(' + row.configId + ')">编辑</a>';
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
		title : '',
		configId : 0,
		networkNumber : '',
		securityAreaId : 0
	},
	methods : {
		/*getInterfaces : function(selectedValue) {
			$.ajax({
				url : '../securityArea/getInterfaces',
				data : {deviceId : selectedValue},
				success : function(data) {
					$("#deviceInterfaces").empty();
					for (var i = 0; i < data.length; i++) {
						$("#deviceInterfaces").append('<label><input name="deviceInterfaces" type="checkbox" value="' + data[i] + '">' + data[i] + '</label>&nbsp;');
					}
				}
			});
		},*/
		showInsert : function() {
			vm.title='新增安全域配置';
            vm.configId = 0;
            vm.reset();
            $('#myModal').modal('show');
		},
		/*save : function(value, row) {
			vm.deviceName = value;
			vm.securityAreaId = window.location.search.substring(window.location.search.indexOf("=") + 1);
			var deviceInterfaces = new Array();
			$('input[name="deviceInterface' + row + '"]:checked').each(function () {
				deviceInterfaces.push($(this).val());
		    });
			$.ajax({
				url : '../securityArea/save',
				data : {
					deviceName : vm.deviceName,
					deviceInterface : deviceInterfaces.join(";"),
					securityAreaId : vm.securityAreaId
				},
				dataType : 'text',
				success : function(data) {
					alert(data);
					$("#jqGrid").jqGrid().trigger("reloadGrid");
				}
			});
		},
		vertify : function() {
			// 判断设备名称是否为空
			if(vm.deviceName == '' || $.trim(vm.deviceName).length == 0) {
				alert("请输入设备名称");
				return false;
			}
			// 判断设备编号是否为空
			if(vm.deviceNumber == '' || $.trim(vm.deviceNumber).length == 0) {
				alert("请输入设备编号");
				return false;
			}
			// 判断设备IP是否为空
			if(vm.deviceIP == '' || $.trim(vm.deviceIP).length == 0) {
				alert("请输入设备IP");
				return false;
			}
			//验证IP格式
			var re=/^(\d+)\.(\d+)\.(\d+)\.(\d+)$/;//正则表达式
			if(re.test(vm.deviceIP)) {   
				if(RegExp.$1<256 && RegExp.$2<256 && RegExp.$3<256 && RegExp.$4<256) 
					return true;   
			}
			alert("IP格式错误");
			return false;
		},
		add : function() {
			if(!vm.vertify())
				return;
			vm.securityAreaId = window.location.search.substring(window.location.search.indexOf("=") + 1);
			var deviceInterfaces = new Array();
			$('input[name="addDeviceInterface"]:checked').each(function () {
				deviceInterfaces.push($(this).val());
		    });
			$.ajax({
				url : '../securityArea/addDeviceInterface',
				data : {
					deviceName : vm.deviceName,
					deviceNumber : vm.deviceNumber,
					deviceIP : vm.deviceIP,
					deviceInterface : deviceInterfaces.join(";"),
					securityAreaId : vm.securityAreaId
				},
				dataType : 'text',
				success : function(data) {
					alert(data);
					$("#jqGrid").jqGrid().trigger("reloadGrid");
					vm.reset();
				}
			});
			$("#myModal").modal('hide');
		},*/
		search : function() {
			$("#jqGrid").jqGrid('setGridParam',{  // 重新加载数据
			      postData : {
			    	  networkNumber : $("#networkNumber").val().trim()
			      },
			      page : 1
			}).trigger("reloadGrid");
		},
		deleteConfig : function() {
			var rowId = $('#jqGrid').jqGrid('getGridParam', 'selarrrow');
			if(rowId == null || rowId.length <= 0) {
				alert("请选中需要删除的数据");
				return;
			} else {
				confirm('确定要删除这些配置？', function() {
					var configIds = new Array();
					for (var i = 0; i < rowId.length; i++) {
						var rowData = $("#jqGrid").jqGrid('getRowData', rowId[i]);
						configIds.push(rowData.configId);
					}
					$.ajax({
						url: '../securityArea/deleteSecurityAreaConfig',
						data: {configIds : configIds.join(";")},
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
		showUpdate : function(value){
			vm.title='编辑安全域配置';
            vm.reset();
			$.ajax({
				url : '../securityArea/querySecurityAreaConfigById',
				data : {configId : value},
				dataType : 'json',
				success : function(data) {
					if(data != '') {
						vm.configId = data.configId;
						vm.networkNumber = data.networkNumber;
					} else
						alert("无法获得该条配置的信息");
				}
			});
            $('#myModal').modal('show');
        },
        vertify : function() {
        	if(vm.networkNumber != '' && $.trim(vm.networkNumber).length > 0) {
				if(checkIP(vm.networkNumber) == false) {
					alert("网络号格式错误");
					return false;
				}
			} else {
				alert("请输入网络号");
				return false;
			}
        	return true;
		},
		insertOrUpdate : function() {
			if(!vm.vertify())
				return;
			vm.securityAreaId = window.location.search.substring(window.location.search.indexOf("=") + 1);
			if(vm.configId == 0) {
				$.ajax({
					url : '../securityArea/addSecurityAreaConfig',
					data : {
						networkNumber : vm.networkNumber,
						securityAreaId : vm.securityAreaId
					},
					dataType : 'text',
					success : function(data) {
						alert(data);
						$("#jqGrid").jqGrid().trigger("reloadGrid");
					}
				});
				$("#myModal").modal('hide');
			} else {
				$.ajax({
					url : '../securityArea/modifySecurityAreaConfig',
					data : {
						configId : vm.configId,
						networkNumber : vm.networkNumber,
						securityAreaId : vm.securityAreaId
					},
					dataType : 'text',
					success : function(data) {
						alert(data);
						$("#jqGrid").jqGrid().trigger("reloadGrid");
					}
				});
				$('#myModal').modal('hide');
			}
		},
		reset : function() {
			vm.networkNumber = '';
			vm.configId = 0;
		}
	}
});

/*$(document).ready(
		function() {
			$.ajax({
				url : '../securityArea/listDevice',
				success : function(data) {
					$("#chooseDevice").append('<option value="0">请选择</option>')
					for (var i = 0; i < data.length; i++) {
						$("#chooseDevice").append('<option value="' + data[i].deviceId + '">' + data[i].deviceName + '</option>');
					}
				}
			})
		})*/