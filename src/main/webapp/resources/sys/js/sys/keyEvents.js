//jqGrid 文档 http://www.helloweba.com/view-blog-162.html
$(function () {
    $("#jqGrid").jqGrid({
        url: '../keyEvents/list',
        datatype: "json",
        colModel: [			
        	{ label: '序号', name: 'id', width: 45, key: true ,},
        	{ label: '策略名称', name: 'ruleName', width: 45 },
			{ label: '源IP', name: 'originIp', width: 50 },
			{ label: '目的IP', name: 'goalIp', width:50 },
			{ label: '源IP掩码', name: 'originIpMask', width: 50 },
			{ label: '目的IP掩码', name: 'goalIpMask', width: 50 },
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
		keyEvents : {
			id:0,
			ruleName: '',
			originIp : '',
			goalIp : '',
			originIpMask : '',
			goalIpMask : '',
		},
		 title:''
	},
	methods : {
		showInsert : function(value){
            vm.title='新增策略';
            vm.keyEvents.id=0;
            vm.reset();
            $('#editModal').modal('show');

        },
        showUpdate : function(value){
            vm.reset();
            vm.title='编辑策略';
            var id=value;           
            $.ajax({
                type: "POST",
                url: "../keyEvents/getKeyEvents?id="+id,
                success: function(r){
                   if(r.code==0){
                	   vm.keyEvents=r.data;
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
						url : "../keyEvents/delete?ids=" + ids.join(";"),
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
			if(vm.keyEvents.ruleName == '' || $.trim(vm.keyEvents.ruleName).length == 0) {
				alert("请输入策略名称");
				return false;
			}
			// 判断IP格式是否正确
			if(vm.keyEvents.originIp != '' && $.trim(vm.keyEvents.originIp).length > 0) {
				if(checkIP(vm.keyEvents.originIp) == false) {
					alert("源IP格式错误");
					return false;
				}
			} else {
				alert("请输入源IP");
				return false;
			}
			if(vm.keyEvents.goalIp != '' && $.trim(vm.keyEvents.goalIp).length > 0) {
				if(checkIP(vm.keyEvents.goalIp) == false) {
					alert("目的IP格式错误");
					return false;
				}
			} else {
				alert("请输入目的IP");
				return false;
			}
			if(vm.keyEvents.originIpMask != '' && $.trim(vm.keyEvents.originIpMask).length > 0) {
				if(checkIP(vm.keyEvents.originIpMask) == false) {
					alert("源IP掩码格式错误");
					return false;
				}
			} else {
				alert("请输入源IP掩码");
				return false;
			}
			if(vm.keyEvents.goalIpMask != '' && $.trim(vm.keyEvents.goalIpMask).length > 0) {
				if(checkIP(vm.keyEvents.goalIpMask) == false) {
					alert("目的IP掩码格式错误");
					return false;
				}
			} else {
				alert("请输入目的IP掩码");
				return false;
			}
			return true;
		},
		insertOrUpdate : function () {

			if(!vm.vertify())
				return;
            var url = vm.keyEvents.id == 0 ? "../keyEvents/insertKeyEvents" : "../keyEvents/updateKeyEvents";

            $.ajax({
                type: "POST",
                url: url,
                data: vm.keyEvents,
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
			var ruleName=$('#ruleName').val().trim()
			param.ruleName=ruleName;
			var postData = $("#jqGrid").jqGrid("getGridParam", "postData");
			   //发送 数据
		       $.extend(postData,param);
		       //重载JQGrid
		       $("#jqGrid").jqGrid("setGridParam", { search: true }).trigger("reloadGrid", [{ page: 1}]);  
				
        },
        reset: function(value){
        	vm.keyEvents.ruleName='';
        	vm.keyEvents.originIp='';
        	vm.keyEvents.goalIp='';
        	vm.keyEvents.originIpMask='';
        	vm.keyEvents.goalIpMask='';

        }
	}
});