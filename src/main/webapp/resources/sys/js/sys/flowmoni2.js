//jqGrid 文档 http://www.helloweba.com/view-blog-162.html
$(function () {
    $("#jqGrid").jqGrid({
        url: '../equipment/getFlowMoni',
        datatype: "json",
        colModel: [			
        	{ label: '序号', name: 'id', width: 10, key: true},
			{ label: '设备名称', name: 'ifMTU', width:20 },
			{ label: '设备名称', name: 'ifSpeed', width:20 },
			{ label: '设备名称', name: 'ifOperStatus', width:20 },
			{ label: '设备名称', name: 'ifInOctet', width:20 },
			{ label: '设备名称', name: 'ifOutOctet', width:20 },
			{ label: '设备名称', name: 'ifInUcastPkts', width:20 },
			{ label: '设备名称', name: 'ifOutUcastPkts', width:20 },
			{ label: '设备流量图', name: 'ip', width:50,formatter: function(value, options, row){
				return value === 0 ? 
						'<span class=""></span>' : 
						'<span class=""></span>';
				}},
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
        	 $('#jqGrid').closest("div.ui-jqgrid-view") 
        	 	.children("div.ui-jqgrid-titlebar")
        	 	.css("text-align", "center")
        	 	.children("span.ui-jqgrid-title")
        	 	.css("float", "none");
        }
    });
});

var vm = new Vue({
	el:'#rrapp',
	data:{
		jixianTypes:[{"key":1,"value":"日基线"},{"key":2,"value":"周基线"},{"key":3,"value":"月基线"},{"key":4,"value":"年基线"}] //搜索优惠类型下拉框初始值
	},
	methods: {

	}
});