//jqGrid 文档 http://www.helloweba.com/view-blog-162.html
$(function () {
    $("#jqGrid").jqGrid({
        url: '',
        datatype: "json",
        colModel: [			
        	{ label: '序号', name: 'id', width: 45, key: true },
			{ label: '设备名称', name: '1', width: 50 },
			{ label: '设备IP', name: '2', width: 50 },
			{ label: '源IP', name: '3', width: 50 },
			{ label: '源设备', name: '4', width: 50 },
			{ label: '源端口', name: '5', width: 50 },
			{ label: '目的IP', name: '6', width: 50 },
			{ label: '目的端口', name: '7', width: 50 },
			{ label: '传输层协议', name: '8', width: 50 },
			{ label: '应用层协议', name: '9', width: 50 },
			{ label: '内容', name: '10', width: 50 },
			{ label: '处理状态', name: '11', width: 50 },
			{ label: '操作时间', name: '12', width: 50 },
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
	el:'#rrapp',
	data:{
		
	},
	methods: {
		
		
	}
});