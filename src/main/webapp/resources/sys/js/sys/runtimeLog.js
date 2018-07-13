//jqGrid 文档 http://www.helloweba.com/view-blog-162.html
$(function () {
	changeToEqipment();
});
function changeToEqipment() {
	$("#jqGrid").jqGrid({
        url: '',
        datatype: "json",
        colModel: [			
        	{ label: '序号', name: 'id', width: 45, key: true },
			{ label: '设备名称', name: '1', width: 50 },
			{ label: '设备IP', name: '2', width: 50 },
			{ label: '内容', name: '3', width: 50 },
			{ label: '操作时间', name: '4', width: 50 }
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
function changeToIntelligence() {
	$("#jqGrid").jqGrid({
        url: '',
        datatype: "json",
        colModel: [			
        	{ label: '序号', name: 'id', width: 45, key: true },
			{ label: '设备名称', name: '1', width: 50 },
			{ label: '设备IP', name: '2', width: 50 },
			{ label: '内容', name: '3', width: 50 },
			{ label: '操作时间', name: '4', width: 50 }
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
var vm = new Vue({
	el:'#rrapp',
	data:{
		equipStatus:[{key:0,value:'请选择'},{key:1,value:'上线'},{key:2,value:'下线'},{key:3,value:'bypass切换失败'},{key:4,value:'回切失败'},{key:5,value:'切换到bypass成功'},{key:6,value:'回切成功'}]
	},
	methods: {
		
		
	}
});