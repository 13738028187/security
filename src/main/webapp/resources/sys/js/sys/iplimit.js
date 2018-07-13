//jqGrid 文档 http://www.helloweba.com/view-blog-162.html
$(function () {
    $("#jqGrid").jqGrid({
        url: '../getIpBlackList',
        datatype: "json",
        colModel: [			
			{ label: '已经拦截的ip地址', name: 'ip', width: 50 }
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
        gridComplete:function(){  //数据加载完成后 
        	//隐藏grid底部滚动条
        	$("#jqGrid").closest(".ui-jqgrid-bdiv").css({ "overflow-x" : "hidden" }); 
        }
    });
});

var vm = new Vue({
	el:'#rrapp',
	data:{
		
	},
	methods: {
		
	}
});

function addInterceptedIP() {
	var ip = $("#ip").val();
	if(ip == '')
		alert("请输入IP值");
	else {
		$.ajax({
			url: '/security/addIpBlack',
			data: {'ip':ip},
			success: function(data) {
				$("#jqGrid").jqGrid().trigger("reloadGrid");
			}
		});
	}
}

function deleteInterceptedIP() {
	var rowId = $('#jqGrid').jqGrid('getGridParam', 'selarrrow');
	if(rowId == null)
		alert("请选中需要删除的数据");
	else {
		confirm('确定要删除这些记录？', function() {
			var ip = new Array();
			for (var i = 0; i < rowId.length; i++) {
				var rowData = $("#jqGrid").jqGrid('getRowData', rowId[i]);
				ip.push(rowData.ip);
			}
			$.ajax({
				url: '/security/deleteIpBlackList',
				data: {'ips':ip.join("-")},
				success: function(data) {
					$("#jqGrid").jqGrid().trigger("reloadGrid");
				}
			});
		});
	}
}