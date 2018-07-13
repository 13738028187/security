//jqGrid 文档 http://www.helloweba.com/view-blog-162.html
$(function () {
    $("#jqGrid").jqGrid({
        url: '/security/apparatus/list',
        //mtype: "GET",
        //postData: {"equipmentName":postDat[0],"network":postData[1],"type":postData[2],"onlineStatus":postData[3]},
        datatype: "json",
        colModel: [			
        	{ label: '设备IP', name: 'apparatusId', width: 20 },
			{ label: '编号', name: 'number', width: 20 },
			{ label: '名称', name: 'name', width: 20 },
			{ label: '类型', name: 'type', width: 20 },
			{ label: '金额', name: 'money', width: 20 },
			{ label: '存放地点', name: 'address', width: 20 },
			{ label: '负责人', name: 'principal', width: 20 },
			{ label: '操作', name: 'apparatusId', width: 20, formatter: function(value){ 
				return '<a class="btn btn-primary btn-xs">编辑</a>&nbsp&nbsp<a class="btn btn-primary btn-xs" onclick=vm.viewDetails("'+value+'")>查看详情</a>'
			} },
        ],
		viewrecords: true,
        height: 450,
        rowNum: 10,
		rowList : [10,30,50],
        rownumbers: true, 
        rownumWidth: 50, 
        autowidth:true,
        pager: "#jqGridPager",
        jsonReader : { 
            root: "page.list",
            page: "page.currPage",
            total: "page.totalPage",
            records: "page.totalCount"
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
		networks:[{"key":0,"value":"全部"},{"key":1,"value":"杭州发射站"},{"key":2,"value":"杭州发射站2"},{"key":3,"value":"杭州发射站3"}],
		equipTypes:[{"key":0,"value":"全部"},{"key":1,"value":"安全设备"},{"key":2,"value":"工控设备"},{"key":1,"value":"网络设备"}],
		onlineStatus:[{"key":0,"value":"全部"},{"key":1,"value":"在线"},{"key":2,"value":"掉线"},{"key":3,"value":"未知"}],
		equipment:{
			apparatus_id:'',
			number:'',
			name:'',
			type:'',
			money:'',
			principal:'',
		}
	},
	methods: {
		viewDetails:function(value){
			$.ajax({
				url: '/security/listEquipmentById?id=' + value,
				success: function(data) {
					if(data.message == 'success'){
						vm.equipment=data.equipment;
						$('#myModal').modal('show');
					}
				}
			});
		}
	}
});

$("#search").button().click(function() {
	var equipmentName = $("#equipmentName").val();
	var network = $("#networks").val();
	var type = $("#equipTypes").val();
	var onlineStatus = $("#onlineStatus").val();
	$("#jqGrid").jqGrid('setGridParam',{
		datatype: "json",
		postData: {'equipmentName':equipmentName,'network':network,'type':type,'onlineStatus':onlineStatus}
	}).trigger("reloadGrid");
});