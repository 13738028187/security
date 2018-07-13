var mydata={};
//条件搜索获取数据
function getMydata(){
	$.ajax({
		type : "POST",
		async : false,
		jsonType : "jsonp",
		url : "http://192.168.1.3:9200/syslog/_search",
		data : JSON.stringify(vm.jsonData),		
		success : function(r) {
			mydata=r;
		}
	});
}

//jqGrid 文档 http://www.helloweba.com/view-blog-162.html
$(function () {		
	getMydata();
	var grid =$("#jqGrid").jqGrid({
		datatype : "local",
		colModel: [				
    	{ label: '信息', name: '_source.message', width: 100 },
		{ label: '日志来源', name: '_source.host', width: 20 },				
		{ label: '时间', name: '_source.timestamp', width: 30 },
		{ label: '严重等级', name: '_source.severity', width: 20,sortable:false, formatter: function(value, options, row){
			if(value==0||value==1){
				return "重要";
			}
			if(value==2||value==3){
				return "严重";
			}
			if(value==4||value==5){
				return "一般";
			}
			if(value==6||value==7){
				return "提示";
			}
			
		} },
		{ label: '程序', name: '_source.program', width: 20, }
		],
		viewrecords : true,
		height : 450,
		rowNum : 10,
		rowList : [ 10, 30, 50 ],
		rownumbers : true,
		rownumWidth : 50,
		autowidth : true,
		multiselect : true,
		pager : "#jqGridPager",  
		onPaging:function(pgButton){
			 var startPage=$("#jqGrid").jqGrid('getGridParam').page;
			 var rowNum=JSON.stringify($("#jqGrid").jqGrid('getGridParam').rowNum);
			 if(pgButton=="first"){
				 startPage=1;
			 }
			 if(pgButton=="prev"){
				 startPage-=1;
			 }
			 if(pgButton=="next"){
				 startPage+=1;
			 }
			 if(pgButton=="last"){
				 var totalPage=JSON.stringify(Math.ceil(mydata.hits.total/rowNum));
				 startPage=totalPage;
			 }
			 if(pgButton=="user"){
				 var wantToPage=$("input[role='textbox']");	
				 startPage=wantToPage[0].value;
			 }
			 if(pgButton=="records"){
				 var size=$("select[role='listbox']");
				 var option=size.find("option:selected");
				 rowNum=option.val();
			 }
			 var offset=(startPage-1)*rowNum;
			 vm.jsonData.from=offset;
			 vm.jsonData.size=rowNum;
			 getMydata();		
			 grid.setGridParam({data: mydata.hits.hits, localReader: reader}).trigger('reloadGrid');
		},
		gridComplete : function() { // 数据加载完成后
			// 隐藏grid底部滚动条
			$("#jqGrid").closest(".ui-jqgrid-bdiv").css({
				"overflow-x" : "hidden"
			});
		}
	});
	var reader = {  
			  root: function(obj) { return mydata.hits.hits; },  
			  page: function(obj) {
				  var page=JSON.stringify($("#jqGrid").jqGrid('getGridParam').page);
				  return page; 
			  },  
			  total: function(obj) { 
				  var rowNum=JSON.stringify($("#jqGrid").jqGrid('getGridParam').rowNum);
				  var totalPage=Math.ceil(mydata.hits.total/rowNum.replace(/\"/g,""));
				  return totalPage; 
			  },
			  records: function(obj) { return mydata.hits.total; },  
			  }
	grid.setGridParam({data: mydata.hits.hits, localReader: reader}).trigger('reloadGrid');  
});
var vm = new Vue({
	el:'#rrapp',
	data:{		
		jsonData : {
			"query" : {
				"constant_score" : {
					"filter" : {
						"range" : {
							"@timestamp" : {
								"gte" : new Date($('#dateParam1').get(0).realValue).getTime(),
								"lte" : new Date($('#dateParam2').get(0).realValue).getTime()								
							}
						}
					}
				}
			},
			"size" : 10,
			"from" : 0
		},
	},
	created: function() {
		
    },
	methods: {
		show: function(){
			vm.jsonData={
					"query" : {
						"constant_score" : {
							"filter" : {
								"range" : {
									"@timestamp" : {
										"gte" : new Date($('#dateParam1').get(0).realValue).getTime(),
										"lte" : new Date($('#dateParam2').get(0).realValue).getTime()
									}
								}
							}
						}
					},
					"size" : 10,
					"from" : 0
				};
			getMydata();		
			$("#jqGrid").setGridParam({data: mydata.hits.hits, localReader: reader = {  
					  root: function(obj) { return mydata.hits.hits; },  
					  page: function(obj) {
						  var page=JSON.stringify($("#jqGrid").jqGrid('getGridParam').page);
						  return "1"; 
					  },  
					  total: function(obj) { 
						  var rowNum=JSON.stringify($("#jqGrid").jqGrid('getGridParam').rowNum);
						  var totalPage=Math.ceil(mydata.hits.total/rowNum.replace(/\"/g,""));
						  return totalPage; 
					  },
					  records: function(obj) { return mydata.hits.total; },  
					  }}).trigger('reloadGrid');
		},
		//返回上一页
		back: function (event) {
			history.go(-1);
		}
	}
});
/*$(function () {
    $("#jqGrid").jqGrid({
        url: '',
        datatype: "json",
        colModel: [			
        	{ label: '序号', name: 'id', width: 45, key: true },
			{ label: '日志来源', name: 'logFrom', width: 50 },
			{ label: '日志内容', name: 'logContent', width: 50 },
			{ label: '告警级别', name: 'warnStaus', width: 50 }
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
});*/