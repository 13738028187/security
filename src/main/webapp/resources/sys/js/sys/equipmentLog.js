//获取跳转网页时所带的参数
var n = T.p("n");
var mydata={};
//获取ip
var status=n.substring(0,1);
var ip=n.substring(1,20);
//获取设备日志
function getMydata(){
	$.ajax({
		type : "POST",
		async : false,
		jsonType : "jsonp",
		url : "http://192.168.1.3:9200/syslog/_search",
		data : JSON.stringify(vm.jsonData),
		success : function(r) {
			console.log(JSON.stringify(r));
			mydata=r;
		}
	});
}
//jqGrid 文档 http://www.helloweba.com/view-blog-162.html
$(function () {
		vm.jsonData.query.bool.must.match.host=ip;	
		//判断搜索区间
	if (status == 1) {
		vm.jsonData.query.bool.filter[1].range.severity.gte=0;
		vm.jsonData.query.bool.filter[1].range.severity.lte=1;	
	}
	if (status == 2) {
		vm.jsonData.query.bool.filter[1].range.severity.gte=2;
		vm.jsonData.query.bool.filter[1].range.severity.lte=3;		
	}
	if (status == 3) {
		vm.jsonData.query.bool.filter[1].range.severity.gte=4;
		vm.jsonData.query.bool.filter[1].range.severity.lte=5;		
	}
	if (status == 4) {
		vm.jsonData.query.bool.filter[1].range.severity.gte=6;
		vm.jsonData.query.bool.filter[1].range.severity.lte=7;		
	}
	getMydata();
	var grid =$("#jqGrid").jqGrid({
		datatype : "local",
		colModel: [				
    	{ label: '信息', name: '_source.message', width: 100},
		{ label: '优先级', name: '_source.priority', width: 20 },				
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
		//分页前后页、首页末页、自定义页数按钮
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
			query : {
				bool : {
					must : {
						match : {
							host : ""
						}
					},
					filter : [
					     {
								"range" : {
									"@timestamp" : {
										"gte" : new Date($('#dateParam1').get(0).realValue).getTime(),
										"lte" : new Date($('#dateParam2').get(0).realValue).getTime()								
									}
								}
					     }     
						,{ 
							range : {
							severity : {
								gte : 4,
								lte : 5
							}
						}
					}]
				}
			},
			size : 10,
			sort : {
				"@timestamp" : "desc"
			},
			from : 0
		}
	},
	created: function() {
		
    },
	methods: {
		//展示数据
		show: function(){
			vm.jsonData.query.bool.filter[0].range={
					"@timestamp" : {
						"gte" : new Date($('#dateParam1').get(0).realValue).getTime(),
						"lte" : new Date($('#dateParam2').get(0).realValue).getTime()								
					}
				}
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
		//返回到设备监测页面
		back: function (event) {
			history.go(-1);
		}
	}
});