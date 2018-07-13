var mydata={};

function getMydata(log){
	$.ajax({
		type : "POST",
		async : false,
		jsonType : "jsonp",
		url : "http://192.168.1.3:9200/"+log+"/_search",
		data : JSON.stringify(vm.jsonData),
		success : function(r) {
			mydata=r;
			console.log(JSON.stringify(r));
		}
	});
}

//jqGrid 文档 http://www.helloweba.com/view-blog-162.html
$(function () {	
	changeToSysLog();
});
function changeToSysLog(){
	$.jgrid.gridUnload('#jqGrid');
	getMydata("application");
	var grid =$("#jqGrid").jqGrid({
		datatype : "local",
		colModel: [		   		
		{ label: '线程', name: '_source.thread', width: 50 },		
		{ label: '信息', name: '_source.message', width: 50 },
		{ label: '时间', name: '_source.@timestamp', width: 50 },
		{ label: '端口', name: '_source.port', width: 50 },
		{ label: '等级', name: '_source.level', width: 30,sortable:false, formatter: function(value, options, row){
			if(value=="DEBUG"){
				return "调试";
			}
			if(value=="ERROR"){
				return "错误";
			}
			if(value=="FATAL"){
				return "严重";
			}
			if(value=="INFO"){
				return "提示";
			} 
			if(value=="OFF"){
				return "关闭";
			}
			if(value=="TRACE"){
				return "低";
			}
			if(value=="WARN"){
				return "警告";
			}
			}
		}],
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
			 getMydata("application");		
			 grid.setGridParam({data: mydata.hits.hits, localReader: reader}).trigger('reloadGrid');
		},
		gridComplete : function() { // 数据加载完成后
			// 隐藏grid底部滚动条
			$("#jqGrid").closest(".ui-jqgrid-bdiv").css({
				"overflow-x" : "hidden"
			});
		}
	});	
	grid.setGridParam({data: mydata.hits.hits, localReader: reader}).trigger('reloadGrid');  
}
function changeToWebLog(){
	$.jgrid.gridUnload('#jqGrid');
	getMydata("nginx");
	var grid =$("#jqGrid").jqGrid({
		datatype : "local",
		colModel: [			   		
		   		{ label: '客户端ip', name: '_source.clientip', width: 50 },		
		   		{ label: '服务端ip', name: '_source.domain', width: 50 },
		   		{ label: '主机名', name: '_source.host', width: 30, },
		   		{ label: '端口', name: '_source.port', width: 50 },
		   		{ label: '请求方式', name: '_source.request_method', width: 50 },
		   		{ label: '时间', name: '_source.timestamp', width: 30, }
		   		
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
			 getMydata("nginx");		
			 grid.setGridParam({data: mydata.hits.hits, localReader: reader}).trigger('reloadGrid');
		},
		gridComplete : function() { // 数据加载完成后
			// 隐藏grid底部滚动条
			$("#jqGrid").closest(".ui-jqgrid-bdiv").css({
				"overflow-x" : "hidden"
			});
		}
	});
	grid.setGridParam({data: mydata.hits.hits, localReader: reader}).trigger('reloadGrid');  
}
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
var vm = new Vue({
	el:'#rrapp',
	data:{
		jsonData : {
			query : {
				bool : {
					
					filter : [ /*, {
						range : {
							"@timestamp" : {
								gte : "2017-12-25T09:25:01.000Z"
							}
						}
					} */]
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
		
		back: function (event) {
			history.go(-1);
		}
	}
});
/*var vm = new Vue({
	el : '#rrapp',
	data : {
		jsonData : {
			query : {
				match_all : {}
			},
			size : 10,
		},
		jsonWarnData : {
			query : {
				match_all : {}
			},
			size : 0,
			aggs : {
				logger_level : {
					terms : {
						field : 'priority'
					}
				}
			}
		},
		onlineStatus:{
			online:1,
			unknown:0,
			offline:0
		},
		result : {
			total : {
				syslog : '',
				application : '',
				web : '',
			},
			basic_info : {
				SupportSnmpEquipmentTotal : 0
			},
			error : {
				syslog : 0,
				application : 0,
				web : 0,
			},
			info : {
				syslog : 0,
				application : 0,
				web : 0,
			},
			device_syslog_level : {
				emergency : 0,
				alert : 0,
				critical : 0,
				error : 0,
				warning : 0,
				notice : 0,
				informational : 0,
				debug : 0
			}
		}
	},
	methods : {

	},
});
// jqGrid 文档 http://www.helloweba.com/view-blog-162.html
$(function() {
	$("#jqGrid").jqGrid({
		mtype : 'post',
		url : 'http://192.168.1.3:9200/nginx/_search',
		postData : JSON.stringify(vm.jsonData),
		datatype : "json",
		colModel : [ {
			label : 'index',
			name : '_id',
			width : 45,
			key : true
		}, {
			label : 'clientip',
			name : 'clientip',
			width : 50
		}, {
			label : 'http-version',
			name : 'httpversion',
			width : 50
		}, {
			label : 'request-length',
			name : 'logContent',
			width : 50
		}, {
			label : 'agent',
			name : 'agent',
			width : 50
		}, {
			label : 'referrer',
			name : 'referrer',
			width : 50
		}, {
			label : 'request_method',
			name : 'request_method',
			width : 50
		}, {
			label : 'status',
			name : 'status',
			width : 50
		}, {
			label : 'request_server',
			name : 'request_server',
			width : 50
		}, {
			label : 'uri',
			name : 'uri',
			width : 50
		}, {
			label : 'host',
			name : 'host',
			width : 50
		}, ],
		viewrecords : true,
		height : 450,
		rowNum : 10,
		rowList : [ 10, 30, 50 ],
		rownumbers : true,
		rownumWidth : 50,
		autowidth : true,
		multiselect : true,
		pager : "#jqGridPager",
		jsonReader : {
			root : "rows"
		},
		loadComplete : function(data) {
			console.log(data);
		},
		gridComplete : function() { // 数据加载完成后
			// 隐藏grid底部滚动条
			$("#jqGrid").closest(".ui-jqgrid-bdiv").css({
				"overflow-x" : "hidden"
			});
		}
	});
});
function changeDashLoad() {
	$.ajax({
		type : "get",
		async : true,
		jsonType : "json",
		url : "../equipment/getTotalByIp",
		success : function(r) {
			vm.onlineStatus.online = r.result.total;
			$.ajax({
				type : "get",
				async : true,
				jsonType : "json",
				url : "../equipment/getTotalByEquipment",
				success : function(r) {
					console.log(r);
					vm.result.basic_info.SupportSnmpEquipmentTotal = r.result.total;
					vm.onlineStatus.unknow=	vm.onlineStatus.online-r.result.total;
					var ctx = document.getElementById("onlineChart").getContext('2d');
					var myChart = new Chart(ctx, {
						type : 'bar',
						data : {
							labels : [ "在线", "离线", "未知"],
							datasets : [ {
								label : '实时统计',
								data : [ vm.onlineStatus.online,vm.onlineStatus.unknow, 3],
								backgroundColor : [ 'rgba(255,99,132,0.8)',
										'rgba(54,162,235,0.8)', 'rgba(255,206,862,0.8)' ],
								borderColor:[
									'rgba(255,99.132,1)','rgba(54,162,235,0.5)', 'rgba(255,206,862,0.7)' 
								],
								borderWidth : 1
							}]
						},
						options : {
							scales : {
								yAxes : [ {
									ticks : {
										beginAtZero : true
									}
								} ]
							}
						}
					});
				}
			});
		}
	});

	$.ajax({
		type : "POST",
		async : true,
		jsonType : "jsonp",
		url : "http://192.168.1.3:9200/syslog/_search",
		data : JSON.stringify({
			"query" : {
				"match_all" : {}
			},
			"size" : 0,
			"aggs" : {
				"logger_level" : {
					"terms" : {
						"field" : "priority"
					}
				}
			}
		}),
		success : function(r) {
			console.log(r);
			var sysloglevel = new Array(8);
			var levels = r.aggregations.logger_level.buckets;
			vm.result.total.syslog = r.hits.total;
			for (var i = 0; i < sysloglevel.length; i++) {
				sysloglevel[i] = 0;
			}
			for (var i = 0; i < levels.length; i++) {
				var count = levels[i].key % 8;
				sysloglevel[count] = levels[i].doc_count + sysloglevel[count];
			}
			for (var i = 0; i < sysloglevel.length; i++) {
				console.log(sysloglevel[i]);
			}
			vm.result.device_syslog_level.emergency = sysloglevel[0];
			vm.result.device_syslog_level.alert = sysloglevel[1];
			vm.result.device_syslog_level.critical = sysloglevel[2];
			vm.result.device_syslog_level.error = sysloglevel[3];
			vm.result.device_syslog_level.warnging = sysloglevel[4];
			vm.result.device_syslog_level.notice = sysloglevel[5];
			vm.result.device_syslog_level.informational = sysloglevel[6];
			vm.result.device_syslog_level.debug = sysloglevel[7];
		}
	});
	console.log("devices");
	$.ajax({
		type : "POST",
		async : true,
		jsonType : "jsonp",
		url : "http://192.168.1.3:9200/devices/_search",
		data : JSON.stringify({
			"query" : {
				"match_all" : {}
			},
			"size" : 0,
			"aggs" : {
				"device_type" : {
					"terms" : {
						"field" : "type"
					}
				}
			}
		}),
		success : function(r) {
			console.log("devices");
			console.log(r);
			var sysloglevel = new Array(8);
			for (var i = 0; i < sysloglevel.length; i++) {
				sysloglevel[i] = 0;
			}

		}
	});
	$.ajax({
		type : "POST",
		async : true,
		jsonType : "jsonp",
		url : "http://192.168.1.3:9200/application/_search",
		data : JSON.stringify(vm.jsonData),
		success : function(r) {
			console.log(r);
			vm.result.total.application = r.hits.total;
		}
	});
	$.ajax({
		type : "POST",
		async : true,
		jsonType : "jsonp",
		url : "http://192.168.1.3:9200/nginx/_search",
		data : JSON.stringify(vm.jsonData),
		success : function(r) {
			console.log(r);
			vm.result.total.web = r.hits.total;
		}
	});

	$.ajax({
		type : "POST",
		async : true,
		jsonType : "jsonp",
		url : "http://192.168.1.3:9200/nginx/_search",
		data : JSON.stringify({
			"query" : {
				"match_all" : {}
			},
			"size" : 0,
			"aggs" : {
				"logger_level" : {
					"terms" : {
						"field" : "status"
					}
				}
			}
		}),
		success : function(r) {
			console.log(r);
			var levels = r.aggregations.logger_level.buckets;
			for (var i = 0; i < levels.length; i++) {
				var count = levels[i].key / 100;
				if (count >= 4) {
					vm.result.error.web = vm.result.error.web
							+ levels[i].doc_count;
				} else {
					vm.result.info.web = vm.result.info.web
							+ levels[i].doc_count;
				}
			}
		}
	});
	$.ajax({
		type : "POST",
		async : true,
		jsonType : "jsonp",
		url : "http://192.168.1.3:9200/application/_search",
		data : JSON.stringify({
			"query" : {
				"match_all" : {}
			},
			"size" : 0,
			"aggs" : {
				"logger_level" : {
					"term" : {
						"field" : "level"
					}
				}
			}
		}),
		success : function(r) {
			console.log(r);
			var levels = r.aggregations.logger_level.buckets;
			for (var i = 0; i < levels.length; i++) {
				var count = levels[i];
				if (count == "ERROR") {
					vm.result.error.web = vm.result.error.web
							+ levels[i].doc_count;
				} else if (count == "INFO") {
					vm.result.info.web = vm.result.info.web
							+ levels[i].doc_count;
				}
			}
		}
	});
}
function changeToSysLog() {
	$.jgrid.gridUnload('#jqGrid');
	$("#jqGrid").jqGrid({
		mtype : 'post',
		url : 'http://192.168.1.3:9200/nginx/_search',
		postData : JSON.stringify(vm.jsonData),
		colModel : [ {
			label : 'index',
			name : '_id',
			width : 45,
			key : true
		}, {
			label : 'clientip',
			name : 'clientip',
			width : 50
		}, {
			label : 'http-version',
			name : 'httpversion',
			width : 50
		}, {
			label : 'request-length',
			name : 'logContent',
			width : 50
		}, {
			label : 'agent',
			name : 'agent',
			width : 50
		}, {
			label : 'referrer',
			name : 'referrer',
			width : 50
		}, {
			label : 'request_method',
			name : 'request_method',
			width : 50
		}, {
			label : 'status',
			name : 'status',
			width : 50
		}, {
			label : 'request_server',
			name : 'request_server',
			width : 50
		}, {
			label : 'uri',
			name : 'uri',
			width : 50
		}, {
			label : 'host',
			name : 'host',
			width : 50
		}, ],
		viewrecords : true,
		height : 450,
		rowNum : 10,
		rowList : [ 10, 30, 50 ],
		rownumbers : true,
		rownumWidth : 50,
		autowidth : true,
		multiselect : true,
		pager : "#jqGridPager",
		loadComplete : function(data) {
			console.log(data);
		},
		gridComplete : function() { // 数据加载完成后
			// 隐藏grid底部滚动条
			$("#jqGrid").closest(".ui-jqgrid-bdiv").css({
				"overflow-x" : "hidden"
			});
		}
	});
}

function changeToWebLog() {
	$.jgrid.gridUnload('#jqGrid');
	$("#jqGrid").jqGrid({
		mtype : 'post',
		url : 'http://192.168.1.3:9200/application/_search',
		postData : JSON.stringify(vm.jsonData),
		datatype : "json",
		colModel : [ {
			label : 'index',
			name : '_id',
			width : 45,
			key : true
		}, {
			label : 'date',
			name : '@timestamp',
			width : 50
		}, {
			label : 'host',
			name : 'host',
			width : 50
		},// 系统日志、配置日志、流量日志、攻击日志、访问日志
		{
			label : 'level',
			name : 'level',
			width : 50
		}, {
			label : 'controller',
			name : 'loggerName',
			width : 50
		}, {
			label : 'message',
			name : 'message',
			width : 50
		}, {
			label : 'thread',
			name : 'thread',
			width : 50
		}, {
			label : 'port',
			name : 'port',
			width : 50
		}, ],
		viewrecords : true,
		height : 450,
		rowNum : 10,
		rowList : [ 10, 30, 50 ],
		rownumbers : true,
		rownumWidth : 50,
		autowidth : true,
		multiselect : true,
		pager : "#jqGridPager",
		loadComplete : function(data) {
			console.log(data);
		},
		gridComplete : function() { // 数据加载完成后
			// 隐藏grid底部滚动条
			$("#jqGrid").closest(".ui-jqgrid-bdiv").css({
				"overflow-x" : "hidden"
			});
		}
	});
};

*/