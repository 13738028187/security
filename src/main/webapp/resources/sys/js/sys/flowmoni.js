/*var timer1=window.setInterval(function() {},3000);*/
//jqGrid 文档 http://www.helloweba.com/view-blog-162.html
$(function () {
	changeToMoni();
});

function changeToMoni(){
	$.jgrid.gridUnload('#jqGrid');
	/*window.clearInterval(timer1);*/
    $("#jqGrid").jqGrid({
        url: '../equipment/getInterfacesEntry',
        datatype: "json",
        postData:{
        	ipAddress : vm.ip,
			community : vm.community
        },
        colModel: [			
			{ label: '接口名', name: 'interfaceName', width: 50 },
			{ label: '接口类型', name: 'ifType', width:50 },
			{ label: '最大数据包', name: 'ifMtu', width: 50 },
			{ label: '带宽', name: 'ifLastChange', width: 50 },
			{ label: '接收字节数', name: 'ifInOctets', width: 50 },
			{ label: '接收数据包数', name: 'ifInUcastPkts', width:50 },			
			{ label: '丢包数', name: 'ifInUnknownProtos', width: 50 },
			{ label: '发出字节数', name: 'ifOutOctets', width: 50 },
			{ label: '发出数据包数', name: 'ifOutUcastPkts', width:50 }		
			/*{ label: '操作', name: '', width: 30, sortable:false, formatter: function(value, options, row){
				return '<a class="btn btn-primary btn-xs" onclick=vm.deleted("'+value+'")>删除</a>';
			}}*/
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
function changeToTCP(){
	/*window.clearInterval(timer1);*/
	$.jgrid.gridUnload('#jqGrid');
    $("#jqGrid").jqGrid({
        url: '../equipment/getTcpEntry',
        postData:{
        	ipAddress : vm.ip,
			community : vm.community
        },
        datatype: "json",
        colModel: [	
            { label: '本地ip', name: 'tcpConnLocalAddress', width: 50 },
            { label: '远程ip', name: 'tcpConnRemAddress', width: 50 },			      
			{ label: '本地端口', name: 'tcpConnLocalPort', width: 50 },
			{ label: '远程端口', name: 'tcpConnRemPort', width: 50 },
			{ label: '连接状态', name: 'tcpConnState', width:50 , formatter: function(value, options, row){
					if(value==1){return 'closed';}
					if(value==2){return 'listen';}
					if(value==3){return 'synSent';}
					if(value==4){return 'synReceived';}
					if(value==5){return 'established';}
					if(value==6){return 'finWait1';}
					if(value==7){return 'finWait2';}
					if(value==8){return 'closeWait';}
					if(value==9){return 'lastAck';}
					if(value==10){return 'closing';}
					if(value==11){return 'timeWait';}
					if(value==12){return 'deleteTCB';}			
				}				
			},
			
			/*		
			{ label: '操作', name: '', width: 30, sortable:false, formatter: function(value, options, row){
				return '<a class="btn btn-primary btn-xs" onclick=vm.deleted("'+value+'")>删除</a>';
			}}*/
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
function changeToUDP(){
	/*window.clearInterval(timer1);	*/
	$.jgrid.gridUnload('#jqGrid');
    $("#jqGrid").jqGrid({
    	type : "GET",
        url: '../equipment/getUdpEntry',
        postData:{
        	ipAddress : vm.ip,
			community : vm.community
        },
        datatype: "json",
        colModel: [			
			{ label: '本地端口', name: 'port', width: 50 },
			{ label: '本地ip', name: 'ip', width:50 },
			
			/*{ label: '操作', name: '', width: 30, sortable:false, formatter: function(value, options, row){
				return '<a class="btn btn-primary btn-xs" onclick=vm.deleted("'+value+'")>删除</a>';
			}}*/
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
var vm = new Vue({
	el : '#rrapp',
	data : {
		ip:'192.168.2.1',
		community:'public'
	},
	methods : {

		dingshi: function(){
			window.clearInterval(timer1);
			timer1 = window.setInterval(function() {
			var param={};
			//param.ipAddress="10.0.0.9";
			var postData = $("#jqGrid").jqGrid("getGridParam", "postData");
			   //发送 数据
		       $.extend(postData,param);
		       //重载JQGrid
		       $("#jqGrid").jqGrid("setGridParam", { search: true }).trigger("reloadGrid", [{ page: 1}]);  
			},3000)	
        },
        reset: function(value){
        	

        }
	}
});
/*var vm = new Vue(
		{
			el : '#rrapp',
			data : {
				ip : '10.0.0.9',
				community : 'public',
				udpEntry : {
					udpInDatagrams : '',// 传递给UDP用户的UDP数据报的总数 1.3.6.1.2.1.7.1
					udpNoPorts : '',// 在目标端口上没有应用程序的接收到的UDP数据报的总数
					// 1.3.6.1.2.1.7.2
					udpInErrors : '',// 由于目标端口缺少应用程序以外的原因而无法传送的接收UDP数据报的数量
					// 1.3.6.1.2.1.7.3
					udpOutDatagrams : '',// 从此实体发送的UDP数据报的总数 1.3.6.1.2.1.7.4
					udpTable : {
						udpLocalAddress : [],// 此UDP侦听器的本地IP地址。
						// 如果UDP侦听器愿意接受与节点关联的任何IP接口的数据报，则使用值0.0.0.0
						// 1.3.6.1.2.1.7.5.1.1
						udpLocalPort : []
					// 此UDP侦听器的本地端口号 1.3.6.1.2.1.7.5.1.2
					}
				},
				interfacesEntry : {
					ifNumber : '',
					ifTable : {
						ifIndex : '',// 1.3.6.1.2.1.2.2.1
						ifDescr : '',// 1.3.6.1.2.1.2.2.2
						ifType : '',// 1.3.6.1.2.1.2.2.3
						ifMtu : '',// 1.3.6.1.2.1.2.2.4
						ifSpeed : '',// 1.3.6.1.2.1.2.2.5
						ifPhysAddress : '',// 1.3.6.1.2.1.2.2.6
						ifAdminStatus : '',// 1.3.6.1.2.1.2.2.7
						ifOperStatus : '',// 1.3.6.1.2.1.2.2.8
						ifLastChange : '',// 1.3.6.1.2.1.2.2.9
						ifInOctets : '',// 1.3.6.1.2.1.2.2.10
						ifInUcastPkts : '',// 1.3.6.1.2.1.2.2.11
						ifInNUcastPkts : '',// 1.3.6.1.2.1.2.2.12
						ifInDiscards : '',// 1.3.6.1.2.1.2.2.13
						ifInErrors : '',// 1.3.6.1.2.1.2.2.14
						ifInUnknownProtos : '',// 1.3.6.1.2.1.2.2.15
						ifOutOctets : '',// 1.3.6.1.2.1.2.2.16
						ifOutUcastPkts : '',// 1.3.6.1.2.1.2.2.17
						ifOutNUcastPkts : '',// 1.3.6.1.2.1.2.2.18
						ifOutDiscards : '',// 1.3.6.1.2.1.2.2.19
						ifOutErrors : '',// 1.3.6.1.2.1.2.2.20
						ifOutQLen : '',// 1.3.6.1.2.1.2.2.21
						ifSpecific : '',// 1.3.6.1.2.1.2.2.22
					}
				},
				atEntry : {
					atTable : {
						atIfIndex : [],// 由该索引的特定值标识的接口是相同的,接口由相同的值标识
						// 1.3.6.1.2.1.3.1.1.1
						atPhysAddress : [],// 媒体相关的”物理“地址 1.3.6.1.2.1.3.1.1.2
						atNetAddress : []
					// 媒体相关的”网络“地址1.3.6.1.2.1.3.1.1.3
					}
				},
				icmpEntry : {
					icmpInMsgs : '',// 1.3.6.1.2.1.5.1
					icmpInErrors : '',// 1.3.6.1.2.1.5.2
					icmpInDestUnreachs : '',// 1.3.6.1.2.1.5.3
					icmpInTimeExcds : '',// 1.3.6.1.2.1.5.4
					icmpInParmProbs : '',// 1.3.6.1.2.1.5.5
					icmpInSrcQuenchs : '',// 1.3.6.1.2.1.5.6
					icmpInRedirects : '',// 1.3.6.1.2.1.5.7
					icmpInEchos : '',// 1.3.6.1.2.1.5.8
					icmpInEchoReps : '',// 1.3.6.1.2.1.5.9
					icmpInTimestamps : '',// 1.3.6.1.2.1.5.10
					icmpInTimestampReps : '',// 1.3.6.1.2.1.5.11
					icmpInAddrMasks : '',// 1.3.6.1.2.1.5.12
					icmpInAddrMaskReps : '',// 1.3.6.1.2.1.5.13
					icmpOutMsgs : '',// 1.3.6.1.2.1.5.14
					icmpOutErrors : '',// 1.3.6.1.2.1.5.15
					icmpOutDestUnreachs : '',// 1.3.6.1.2.1.5.16
					icmpOutTimeExcds : '',// 1.3.6.1.2.1.5.17
					icmpOutParmProbs : '',// 1.3.6.1.2.1.5.18
					icmpOutSrcQuenchs : '',// 1.3.6.1.2.1.5.19
					icmpOutRedirects : '',// 1.3.6.1.2.1.5.20
					icmpOutEchos : '',// 1.3.6.1.2.1.5.21
					icmpOutEchoReps : '',// 1.3.6.1.2.1.5.22
					icmpOutTimestamps : '',// 1.3.6.1.2.1.5.23
					icmpOutTimestampReps : '',// 1.3.6.1.2.1.5.24
					icmpOutAddrMasks : '',// 1.3.6.1.2.1.5.25
					icmpOutAddrMaskReps : '',// 1.3.6.1.2.1.5.26

				},
				tcpEntry : {
					tcpRtoAlgorithm : '',// 此UDP侦听器的本地端口号 1.3.6.1.2.1.6.1
					tcpRtoMin : '',// TCP实现允许的重传超时的最小值，以毫秒为单位1.3.6.1.2.1.6.2
					tcpRtoMax : '',// TCP实现允许的重传超时的最大值，以毫秒为单位1.3.6.1.2.1.6.3
					tcpMaxConn : '',// 实体可以支持的TCP连接总数的限制。
					// 在最大连接数为动态的实体中，此对象应包含值-1 1.3.6.1.2.1.6.4
					tcpActiveOpens : '',// TCP连接从CLOSED状态直接转换到SYN-SENT状态的次数
					// 1.3.6.1.2.1.6.5
					tcpPassiveOpens : '',// TCP连接从LISTEN状态直接转换到SYN-RCVD状态的次数1.3.6.1.2.1.6.6
					tcpAttemptFails : '',// TCP连接从ESTABLISHED状态或CLOSE-WAIT状态直接转换到CLOSED状态的次数
					// 1.3.6.1.2.1.6.7
					tcpEstabResets : '',// TCP连接从ESTABLISHED状态或CLOSE-WAIT状态直接转换到CLOSED状态的次数
					// 1.3.6.1.2.1.6.8
					tcpCurrEstab : '',// 当前状态为ESTABLISHED或CLOSE-WAIT的TCP连接数
					// 1.3.6.1.2.1.6.9
					tcpInSegs : '',// 收到的细分总数，包括收到错误的细分总数。 此计数包括在当前建立的连接上收到的段
					// 1.3.6.1.2.1.6.10
					tcpOutSegs : '',// 发送的段总数，包括当前连接的段数，但不包括仅包含重传字节的段
					// 1.3.6.1.2.1.6.11
					tcpRetransSegs : '',// 重发的分段总数 - 即包含一个或多个先前传输的八比特组的传输的TCP分段数
					// 1.3.6.1.2.1.6.12
					tcpInErrs : '',// 收到的分段总数错误1.3.6.1.2.1.6.14
					tcpOutRsts : '',// 包含RST标志的TCP段的数量1.3.6.1.2.1.6.15
					tcpConnTable : {
						tcpConnEntry : {
							tcpConnState : [],// 这个TCP连接的状态
							// 1.3.6.1.2.1.6.13.1.1
							tcpConnLocalAddress : [],// 此TCP连接的本地IP地址。
							// 在监听状态下的连接愿意接受与该节点相关的任何IP接口的连接时，使用值0.0.0.0
							// 1.3.6.1.2.1.6.13.1.2
							tcpConnLocalPort : [],// 此TCP连接的本地端口号
							// 1.3.6.1.2.1.6.13.1.3
							tcpConnRemAddress : [],// 此TCP连接的远程IP地址
							// 1.3.6.1.2.1.6.13.1.4
							tcpConnRemPort : []
						// 此TCP连接的远程端口号 1.3.6.1.2.1.6.13.1.5
						}
					}
				},
				graph_one_content : {
					map : [],
				},
				flux_basic_info : {
					ifPhysAddress : [],
					ifDescr : [],
					ifType : [],
					IfOperStatus : [],
				},

				flux_detail_info : [],

				jixianTypes : [ {
					"key" : 1,
					"value" : "日基线"
				}, {
					"key" : 2,
					"value" : "周基线"
				}, {
					"key" : 3,
					"value" : "月基线"
				}, {
					"key" : 4,
					"value" : "年基线"
				} ]
			// 搜索优惠类型下拉框初始值
			},
			methods : {
				
				getDeviceStaticResources : function() {
					var time = window.setInterval(function() {
						$.ajax({
							type : "GET",
							url : "../equipment/getDeviceStaticResources",
							data : {
								ipAddress : vm.ip,
								community : vm.community
							},
							success : function(r) {
								console.log(r);
							}
						});
					}, 3000);
				},
				getUdpEntry : function() {
					var time = window
							.setInterval(
									function() {
										$
												.ajax({
													type : "GET",
													url : "../equipment/getUdpEntry",
													data : {
														ipAddress : vm.ip,
														community : vm.community
													},
													success : function(r) {
														console.log(r);
														vm.udpEntry.udpInDatagrams = r.map["1.3.6.1.2.1.7.1.0"];接收数据报
														vm.udpEntry.udpNoPorts = r.map["1.3.6.1.2.1.7.2.0"];
														vm.udpEntry.udpInErrors = r.map["1.3.6.1.2.1.7.3.0"];接收失败
														vm.udpEntry.udpOutDatagrams = r.map["1.3.6.1.2.1.7.4.0"];发送数据报
														vm.udpEntry.udpTable.udpLocalAddress = r.udpTable.map["1.3.6.1.2.1.7.5.1.2"];
														vm.udpEntry.udpTable.udpLocalPort = r.udpTable.map["1.3.6.1.2.1.7.5.1.1"];
													}
												});
									}, 3000)
				},
				getTcpEntry : function() {
					var time = window
							.setInterval(
									function() {
										$
												.ajax({
													type : "GET",
													url : "../equipment/getTcpEntry",
													data : {
														ipAddress : vm.ip,
														community : vm.community
													},
													success : function(r) {
														console.log(r);
														vm.tcpEntry.tcpRtoAlgorithm = r.map["1.3.6.1.2.1.6.1.0"];
														vm.tcpEntry.tcpRtoMin = r.map["1.3.6.1.2.1.6.2.0"];
														vm.tcpEntry.tcpRtoMax = r.map["1.3.6.1.2.1.6.3.0"];
														vm.tcpEntry.tcpMaxConn = r.map["1.3.6.1.2.1.6.4.0"];
														vm.tcpEntry.tcpActiveOpens = r.map["1.3.6.1.2.1.6.5.0"];
														vm.tcpEntry.tcpPassiveOpens = r.map["1.3.6.1.2.1.6.6.0"];
														vm.tcpEntry.tcpAttemptFails = r.map["1.3.6.1.2.1.6.7.0"];
														vm.tcpEntry.tcpEstabResets = r.map["1.3.6.1.2.1.6.8.0"];
														vm.tcpEntry.tcpCurrEstab = r.map["1.3.6.1.2.1.6.9.0"];
														vm.tcpEntry.tcpInSegs = r.map["1.3.6.1.2.1.6.10.0"];
														vm.tcpEntry.tcpOutSegs = r.map["1.3.6.1.2.1.6.11.0"];
														vm.tcpEntry.tcpRetransSegs = r.map["1.3.6.1.2.1.6.12.0"];
														vm.tcpEntry.tcpInErrs = r.map["1.3.6.1.2.1.6.14.0"];
														vm.tcpEntry.tcpOutRsts = r.map["1.3.6.1.2.1.6.15.0"];
														vm.tcpEntry.tcpConnTable.tcpConnEntry.tcpConnState = r.tcpTable.map["1.3.6.1.2.1.6.13.1.1"];
														vm.tcpEntry.tcpConnTable.tcpConnEntry.tcpConnLocalAddress = r.tcpTable.map["1.3.6.1.2.1.6.13.1.2"];
														vm.tcpEntry.tcpConnTable.tcpConnEntry.tcpConnLocalPort = r.tcpTable.map["1.3.6.1.2.1.6.13.1.3"];
														vm.tcpEntry.tcpConnTable.tcpConnEntry.tcpConnRemAddress = r.tcpTable.map["1.3.6.1.2.1.6.13.1.4"];
														vm.tcpEntry.tcpConnTable.tcpConnEntry.tcpConnRemPort = r.tcpTable.map["1.3.6.1.2.1.6.13.1.5"];
														console
																.log(vm.tcpEntry);
													}
												});
									}, 3000)
				},
				getAtEntry : function() {
					var time = window
							.setInterval(
									function() {
										$
												.ajax({
													type : "GET",
													url : "../equipment/getAtEntry",
													data : {
														ipAddress : vm.ip,
														community : vm.community
													},
													success : function(r) {
														console.log(r);
														vm.atEntry.atTable.atIfIndex = r.atTable.map["1.3.6.1.2.1.3.1.1.1"];
														vm.atEntry.atTable.atPhysAddress = r.atTable.map["1.3.6.1.2.1.3.1.1.2"];
														vm.atEntry.atTable.atNetAddress = r.atTable.map["1.3.6.1.2.1.3.1.1.3"];
													}
												});
									}, 3000)
				},
				getIcmpEntry : function() {
					var time = window
							.setInterval(
									function() {
										$
												.ajax({
													type : "GET",
													url : "../equipment/getIcmpEntry",
													data : {
														ipAddress : vm.ip,
														community : vm.community
													},
													success : function(r) {
														console.log(r);
														vm.icmpEntry.icmpInMsgs = r.map["1.3.6.1.2.1.5.1.0"];
														vm.icmpEntry.icmpInErrors = r.map["1.3.6.1.2.1.5.2.0"];
														vm.icmpEntry.icmpInDestUnreachs = r.map["1.3.6.1.2.1.5.3.0"];
														vm.icmpEntry.icmpInTimeExcds = r.map["1.3.6.1.2.1.5.4.0"];
														vm.icmpEntry.icmpInParmProbs = r.map["1.3.6.1.2.1.5.5.0"];
														vm.icmpEntry.icmpInSrcQuenchs = r.map["1.3.6.1.2.1.5.6.0"];
														vm.icmpEntry.icmpInRedirects = r.map["1.3.6.1.2.1.5.7.0"];
														vm.icmpEntry.icmpInEchos = r.map["1.3.6.1.2.1.5.8.0"];
														vm.icmpEntry.icmpInEchoReps = r.map["1.3.6.1.2.1.5.9.0"];
														vm.icmpEntry.icmpInTimestamps = r.map["1.3.6.1.2.1.5.10.0"];
														vm.icmpEntry.icmpInTimestampReps = r.map["1.3.6.1.2.1.5.11.0"];
														vm.icmpEntry.icmpInAddrMasks = r.map["1.3.6.1.2.1.5.12.0"];
														vm.icmpEntry.icmpInAddrMaskReps = r.map["1.3.6.1.2.1.5.13.0"];
														vm.icmpEntry.icmpOutMsgs = r.map["1.3.6.1.2.1.5.14.0"];
														vm.icmpEntry.icmpOutErrors = r.map["1.3.6.1.2.1.5.15.0"];
														vm.icmpEntry.icmpOutDestUnreachs = r.map["1.3.6.1.2.1.5.16.0"];
														vm.icmpEntry.icmpOutTimeExcds = r.map["1.3.6.1.2.1.5.17.0"];
														vm.icmpEntry.icmpOutParmProbs = r.map["1.3.6.1.2.1.5.18.0"];
														vm.icmpEntry.icmpOutSrcQuenchs = r.map["1.3.6.1.2.1.5.19.0"];
														vm.icmpEntry.icmpOutRedirects = r.map["1.3.6.1.2.1.5.20.0"];
														vm.icmpEntry.icmpOutEchos = r.map["1.3.6.1.2.1.5.21.0"];
														vm.icmpEntry.icmpOutEchoReps = r.map["1.3.6.1.2.1.5.22.0"];
														vm.icmpEntry.icmpOutTimestamps = r.map["1.3.6.1.2.1.5.23.0"];
														vm.icmpEntry.icmpOutTimestampReps = r.map["1.3.6.1.2.1.5.24.0"];
														vm.icmpEntry.icmpOutAddrMasks = r.map["1.3.6.1.2.1.5.25.0"];
														vm.icmpEntry.icmpOutAddrMaskReps = r.map["1.3.6.1.2.1.5.26.0"];
													}
												});
									}, 3000)
				},
				getInterfacesEntry : function() {
					var time = window
							.setInterval(
									function() {
										$
												.ajax({
													type : "GET",
													url : "../equipment/getInterfacesEntry",
													data : {
														ipAddress : "10.0.0.9",
														community : "public"
													},
													success : function(r) {
														console.log(r);
														vm.interfacesEntry.ifNumber = r.map["1.3.6.1.2.1.2.2.1"];
														vm.interfacesEntry.ifTable.ifIndex = r.ifTable.map["1.3.6.1.2.1.2.2.1.1"];
														vm.interfacesEntry.ifTable.ifDescr = r.ifTable.map["1.3.6.1.2.1.2.2.1.2"];
														vm.interfacesEntry.ifTable.ifType = r.ifTable.map["1.3.6.1.2.1.2.2.1.3"];
														vm.interfacesEntry.ifTable.ifMtu = r.ifTable.map["1.3.6.1.2.1.2.2.1.4"];
														vm.interfacesEntry.ifTable.ifSpeed = r.ifTable.map["1.3.6.1.2.1.2.2.1.5"];
														vm.interfacesEntry.ifTable.ifPhysAddress = r.ifTable.map["1.3.6.1.2.1.2.2.1.6"];
														vm.interfacesEntry.ifTable.ifAdminStatus = r.ifTable.map["1.3.6.1.2.1.2.2.1.7"];
														vm.interfacesEntry.ifTable.ifOperStatus = r.map["1.3.6.1.2.1.2.2.1.8"];
														vm.interfacesEntry.ifTable.ifLastChange = r.ifTable.map["1.3.6.1.2.1.2.2.1.9"];
														vm.interfacesEntry.ifTable.ifInOctets = r.ifTable.map["1.3.6.1.2.1.2.2.1.10"];
														vm.interfacesEntry.ifTable.ifInUcastPkts = r.ifTable.map["1.3.6.1.2.1.2.2.1.11"];
														vm.interfacesEntry.ifTable.ifInNUcastPkts = r.ifTable.map["1.3.6.1.2.1.2.2.1.12"];
														vm.interfacesEntry.ifTable.ifInDiscards = r.ifTable.map["1.3.6.1.2.1.2.2.1.13"];
														vm.interfacesEntry.ifTable.ifInErrors = r.ifTable.map["1.3.6.1.2.1.2.2.1.14"];
														vm.interfacesEntry.ifTable.ifInUnknownProtos = r.ifTable.map["1.3.6.1.2.1.2.2.1.15"];
														vm.interfacesEntry.ifTable.ifOutOctets = r.ifTable.map["1.3.6.1.2.1.2.2.1.16"];
														vm.interfacesEntry.ifTable.ifOutUcastPkts = r.ifTable.map["1.3.6.1.2.1.2.2.1.17"];
														vm.interfacesEntry.ifTable.ifOutNUcastPkts = r.ifTable.map["1.3.6.1.2.1.2.2.1.18"];
														vm.interfacesEntry.ifTable.ifOutDiscards = r.ifTable.map["1.3.6.1.2.1.2.2.1.19"];
														vm.interfacesEntry.ifTable.ifOutErrors = r.ifTable.map["1.3.6.1.2.1.2.2.1.20"];
														vm.interfacesEntry.ifTable.ifOutQLen = r.ifTable.map["1.3.6.1.2.1.2.2.1.21"];
														vm.interfacesEntry.ifTable.ifSpecific = r.ifTable.map["1.3.6.1.2.1.2.2.1.22"];
														console
																.log(vm.interfacesEntry.ifTable);
													}
												});
									}, 3000)
				},
			},
			created : function() {
				this.getInterfacesEntry();
				this.getIcmpEntry();
				this.getAtEntry();
				this.getTcpEntry();
				this.getUdpEntry();
			}
		});*/
/*var graph_one_chart = echarts.init(document.getElementById('graph_one'));
graph_one_option = {
	title : {
		text : 'udp数据包',
		subtext : '流量监控'
	},
	tooltip : {
		trigger : 'axis'
	},
	legend : {
		data : [ 'udpInDatagrams', 'udpNoPorts', 'udpInErrors',
				'udpOutDatagrams' ]
	},
	toolbox : {
		show : true,
		feature : {
			mark : {
				show : true
			},
			dataView : {
				show : true,
				readOnly : false
			},
			magicType : {
				show : true,
				type : [ 'line', 'bar' ]
			},
			restore : {
				show : true
			},
			saveAsImage : {
				show : true
			}
		}
	},
	calculable : true,
	xAxis : [ {
		type : 'category',
		data : vm.graph_one_content.ifDescr
	} ],
	yAxis : [ {
		type : 'value'
	} ],
	series : [ {
		name : '蒸发量',
		type : 'bar',
		data : vm.graph_one_content.ifInUcastPkts,
	} ]
};
graph_one_chart.setOption(graph_one_option);
*/