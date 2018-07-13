var timer1=window.setInterval(function() {},30000);
//jqGrid 文档 http://www.helloweba.com/view-blog-162.html
$(function () {
	changeToGK();
});
function changeToGK() {
	$.jgrid.gridUnload('#jqGrid');
	$("#jqGrid").jqGrid({
        url: '../equipment/resources',
        datatype: "json",
        width: $(window).width()-350, 
        colModel: [			
        	{ label: '序号', name: 'id', width: 20, key: true },
        	{ label: '设备名称', name: 'equipmentName', width: 50 },		
        	{ label: '设备IP', name: 'ip', width: 50 },		
			{ label: '在线状态', name: 'onlineStatus', width: 50 },
			{ label: '工作模式', name: 'workPattern', width: 50 },
			{ label: '上线时间', name: 'onlineTime', width: 50 },
			{ label: '操作', name: 'ip', width: 30, sortable:false, formatter: function(value, options, row){
				return '<a class="btn btn-primary btn-xs" onclick=vm.show("'+value+'")>查看</a>';
			}}
        ],
		viewrecords: true,
        height: 450,
        rowNum: 10,
		rowList : [10,30,50],
        rownumbers: true, 
        rownumWidth: 50, 
        autowidth: false,
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
        	var ips=$("[aria-describedby='jqGrid_ip']"); 
        	//console.log(JSON.stringify(ips[0]));
        	if(ips[0]!=null){
        		var ip=ips[0].innerHTML;           	
            	vm.show(ip);
        	}
        	
        	

        }
    });
}
function changeToJK() {
	$.jgrid.gridUnload('#jqGrid');
	$("#jqGrid").jqGrid({
        url: '',
        datatype: "json",
        width: $(window).width()-350, 
        colModel: [			
        	{ label: '序号', name: 'id', width: 20, key: true,},
        	{ label: '智能监测终端名称', name: 'terminalName', width: 60 },
			{ label: '智能监测终端ID', name: 'terminalID', width: 60 },
			{ label: '智能监测终端IP', name: 'terminalIP', width:60},			
			{ label: '在线状态', name: 'onlineStatus', width: 40 },
			{ label: '工作状态', name: 'workPattern', width: 40 },
			{ label: '操作', name: 'id', width: 40, sortable:false, formatter: function(value, options, row){
				return '<a class="btn btn-primary btn-xs">查看详情</a><a class="btn btn-primary btn-xs">升级</a><a class="btn btn-primary btn-xs">授权</a>';
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
        gridComplete:function(){  //数据加载完成后 
        	//隐藏grid底部滚动条
        	$("#jqGrid").closest(".ui-jqgrid-bdiv").css({ "overflow-x" : "hidden" }); 
        }
    });
}
function changeToRQ() {
	$.jgrid.gridUnload('#jqGrid');
	$("#jqGrid").jqGrid({
        url: '',
        datatype: "json",
        width: $(window).width()-350, 
        colModel: [			
        	{ label: '序号', name: 'id', width: 20, key: true,},
        	{ label: '智能监测终端名称', name: 'terminalName', width: 60 },
			{ label: '智能监测终端ID', name: 'terminalID', width: 60 },
			{ label: '智能监测终端IP', name: 'terminalIP', width:60},			
			{ label: '在线状态', name: 'onlineStatus', width: 40 },
			{ label: '工作状态', name: 'workPattern', width: 40 },
			{ label: '操作', name: 'id', width: 40, sortable:false, formatter: function(value, options, row){
				return '<a class="btn btn-primary btn-xs">查看详情</a><a class="btn btn-primary btn-xs">升级</a><a class="btn btn-primary btn-xs">授权</a>';
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
        gridComplete:function(){  //数据加载完成后 
        	//隐藏grid底部滚动条
        	$("#jqGrid").closest(".ui-jqgrid-bdiv").css({ "overflow-x" : "hidden" }); 
        }
    });
}
var vm = new Vue({
	el : '#rrapp',
	data : {
		Equipment : {
			equipmentName : "",
			ip : "",
			workPattern : "",
			onlineStatus : ""
		},
		situation : {
			equipmentName : "",
			ip : "",
			hrProcessorLoadAvg : "",
			memAvailReal : ""
		},
		jsonData : {
			query : {
				bool : {
					must : {
						match : {
							host : "192.168.1.3"
						}
					},
					filter : [ {
						range : {
							severity : {
								gte : 4,
								lte : 5
							}
						}
					} ]
				}
			},
			size : 0,
		},
		/*
		 * jsonData : { query : { match : { host : '192.168.1.3' } }, size : 0,
		 * aggs : { logger_level : { terms : { field : 'priority' } } } },
		 */

		equipTypes : [ {
			"key" : 0,
			"value" : "全部"
		}, {
			"key" : 1,
			"value" : "安全设备"
		}, {
			"key" : 2,
			"value" : "工控设备"
		}, {
			"key" : 3,
			"value" : "网络设备"
		} ]
	// 搜索下拉框初始值

	},
	created : function() {

	},
	methods : {
		/**
		 * 获取日志总量
		 * @param a 告警区间a
		 * @param b 告警区间b
		 */
		getTotal : function(a, b) {
			var total;
			vm.jsonData.query.bool.filter[0].range.severity.gte = a;
			vm.jsonData.query.bool.filter[0].range.severity.lte = b;			
			$.ajax({
				type : "POST",
				async : false,
				jsonType : "jsonp",
				url : "http://192.168.1.3:9200/syslog/_search",
				data : JSON.stringify(vm.jsonData),
				success : function(r) {
					total= r.hits.total;
					
				}
			});
			return total;
		},
		/**
		 * 获取不同日志的总量并画图
		 * @param ip 设备ip
		 */
		getLogAmount : function(ip) {
			// vm.resetCounter();
			var important;
			var severity;
			var common;
			var reminder;
			vm.jsonData.query.bool.must.match.host = ip;
			important = vm.getTotal(0, 1);
			severity = vm.getTotal(2, 3);
			common = vm.getTotal(4, 5);
			reminder = vm.getTotal(6, 7);
				
			// 设定0-1为重要 2-3 为严重 4-5警告 6-7提示
			/*
			 * emerg 0 系统不可用 alert 1 必须马上采取行动的事件 crit 2 关键的事件 err 3 错误事件 warning
			 * 4 警告事件 notice 5 普通但重要的事件 info 6 有用的信息 debug 7 调试信息
			 */
			
			var c1 = vm.draw("circle1", important);
			var c2 = vm.draw("circle2", severity);
			var c3 = vm.draw("circle3", common);
			var c4 = vm.draw("circle4", reminder);

		},
		//跳转到该设备日志的页面
		changeToLog : function(n) {
			location.href = "equipmentLog.html?n=" + n + vm.situation.ip;
		},
		//条件搜索
		search : function() {
			//console.log(JSON.stringify(vm.Equipment));
			var r = /^\+?[1-9][0-9]*$/;//正整数 
		      
			if(vm.Equipment.onlineStatus.trim()!=""&&!r.test(vm.Equipment.onlineStatus.trim())){
				alert("请输入数字");
				return;
			}
			var postData = $("#jqGrid").jqGrid("getGridParam", "postData");
			// 发送 数据
			$.extend(postData, vm.Equipment);
			// 重载JQGrid
			$("#jqGrid").jqGrid("setGridParam", {
				search : true
			}).trigger("reloadGrid", [ {
				page : 1
			} ]);
		},
		//获取设备 名称、cpu、内存等属性
		getSituation : function(ip) {
			$.ajax({
				type : "POST",
				async : false,
				url : "../equipment/getSituation?ip=" + ip,
				success : function(r) {
					if (r.code == 0) {
						vm.situation = r.situation;
						console.log(JSON.stringify(vm.situation));
					}
				}
			});
		},
		setColor:function(x){
			var color='green';
			if(x<60||x==60){
				color='green';
			}
			if(x>60&x<80){
				color='yellow';
			}
			if(x>80||x==80){
				color='red';
			}
			return color;
		},
		show : function(ip) {
			vm.getSituation(ip);
			//设置CPU负载颜色
			var percentHP=vm.situation.hrProcessorLoadAvg;
			
			//设置内存图颜色
			var MAR=vm.situation.memAvailReal;
			
			// 进入页面时立马就有数据
			charts[0].setOption({
				series : [ {
					data : [ percentHP / 100 ],
					color : [vm.setColor(percentHP)],
				} ]
			});
			charts[1].setOption({
				series : [ {
					data : [ MAR ],
					color : [vm.setColor(MAR*100)],
				} ]
			});
			vm.getLogAmount(ip);// 获取日志数量
			// 每隔30秒更新一次数据
			window.clearInterval(timer1);
			timer1 = window.setInterval(function() {// 设置cpu内存
				vm.getSituation(ip);
				var cpuData = [];
				cpuData.push(vm.situation.hrProcessorLoadAvg / 100);
				var memData = [];
				memData.push(vm.situation.memAvailReal);
				charts[0].setOption({
					series : [ {
						data : cpuData,
						color : [vm.setColor(vm.situation.hrProcessorLoadAvg)],
					} ]
				});
				charts[1].setOption({
					series : [ {
						data : memData,
						color : [vm.setColor(vm.situation.memAvailReal*100)],
					} ]
				});
				vm.getLogAmount(ip);// 获取日志数量
			}, 30000)

		},
		/**
		 * 画圈
		 * @param id 元素id
		 * @param text 文本
		 */
		draw : function(id, text) {
			var x;
			if(text.toString().length<7){
				x=150-text.toString().length*20;
			}else{
				x=0;
			}	
			var c = document.getElementById(id);
			c.width = c.width;// 清空数据
			var ctx = c.getContext("2d");
			ctx.font = "80px Arial";
			ctx.fillText(text, x, 100);
		},
		//重置数据
		resetCounter : function() {
			vm.counter.important = 0;
			vm.counter.severity = 0;
			vm.counter.common = 0;
			vm.counter.reminder = 0;
		}
	}
});

//echarts生成cpu的图
var bgColor = '#E3F7FF';
var containers = document.getElementsByClassName('chart');
var options = [{
    series: [{
        type: 'liquidFill',
        data: [0.6],
        radius: '70%',
        color : ['green'],
        outline: {
            show: false
        }
    }]
}, {
    series: [{
        type: 'liquidFill',
        data: [0.6],
        radius: '70%',
        color : ['green'],
        phase: 0,
        period: 5000,
        outline: {
            show: false
        }
    }]
}];

var charts = [];
for (var i = 0; i < options.length; ++i) {
    var chart = echarts.init(containers[i]);

    if (i > 0) {
        options[i].series[0].silent = true;
    }
    chart.setOption(options[i]);
    chart.setOption({
        baseOption: options[i],
        media: [{
            query: {
                maxWidth: 300
            },
            option: {
                series: [{
                    label: {
                        normal: {
                            textStyle: {
                                fontSize: 26
                            }
                        }
                    }
                }]
            }
        }]
    });

    charts.push(chart);
}