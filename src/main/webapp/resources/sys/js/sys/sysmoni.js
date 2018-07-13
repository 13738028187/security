
$(document)
		.ready(
				function() {
					$(function() {
						var sysInfo = new Vue(
								{
									el : '#sysInfo',
									data : {
										info : [ {
											name : '开启时间',
											value : ''
										}, {
											name : 'cpu数量',
											value : ''
										}, {
											name : '操作系统',
											value : ''
										}, {
											name : '主机',
											value : ''
										}, {
											name : 'Java版本',
											value : ''
										}, {
											name : 'Jvm版本',
											value : ''
										}, {
											name : '进程号',
											value : ''
										}, {
											name : 'Jvm参数',
											value : ''
										} ]
									},
									created : function() {
										let self = this;
										$
												.ajax({
													method : "GET",
													url : "/security/monitor/systemInfo",
													success : function(data) {
														var newDate = new Date();
														newDate
																.setTime(data.startDate);
														self.info[0].value = newDate
																.toUTCString();
														self.info[1].value = data.availableProcessors;
														self.info[2].value = data.os;
														self.info[3].value = data.host;
														self.info[4].value = data.javaVersion;
														self.info[5].value = data.jvmVersion;
														self.info[6].value = data.pid;
														self.info[7].value = data.jvmArguments;
													}
												});
									}
								});
						this.info.splice(index, 1);

						var vm = new Vue({
							el : '#rrapp',
							data : {

							},
							methods : {

							}
						});
					});
					// end
					var memoryInfo = new Vue({
						el : '#system_memory_info',
						data : {
							info : [ {
								name : '获取时间',
								value : ''
							}, {
								name : '最大物理内存',
								value : ''
							}, {
								name : '最大交换内存',
								value : ''
							}, {
								name : '最大Jvm内存',
								value : ''
							}, {
								name : '被使用物理内存',
								value : ''
							}, {
								name : '被使用交换内存',
								value : ''
							}, {
								name : '被使用Jvm内存',
								value : ''
							} ],
							activeUser : '',
							mostVisitedIP : ''
						}
					});
					var systemCpuLoadGraph = echarts.init(document
							.getElementById('system_cpu_load_graph'));
					systemCpuLoadGraph.showLoading();
					
					var ddata = [];
					var systemCpuLoad = [];
					for(var a=0;a<5;a++){
						ddata.push(a);
						systemCpuLoad.push(0);
					}
					//定时获取CPU负载并画图展示在前台
					setInterval(
							function() {
								
										$.ajax({
											method : "GET",
											url : "/security/monitor/systemActualInfo",
											cache : false,
											success : function(msg) {
											
												var m = eval('(' + msg[msg.length-1] + ')');
												
												if(ddata.length>5){
													ddata.splice(0,1);
													systemCpuLoad.splice(0,1)
												}	
												ddata.push(m["startDate"]);
												systemCpuLoad.push(m.systemCpuLoad);
												var systemLoadAverage = [];
												
												memoryInfo.info[0].value = m.startDate;
												memoryInfo.info[1].value = m.maxPhysicalMemorySize
														+ "M";
												memoryInfo.info[2].value = m.maxSwapSpaceSize
														+ "M";
												memoryInfo.info[3].value = m.maxMemory
														+ "M";
												memoryInfo.info[4].value = m.usedPhysicalMemorySize
														+ "M";
												memoryInfo.info[5].value = m.usedSwapSpaceSize
														+ "M";
												memoryInfo.info[6].value = m.usedMemory
														+ "M";
												systemLoadAverage.push(m.systemLoadAverage);
												
												
												
												
												var colors = [ '#5793f3',
														'#d14a61', '#675bba' ];
												systemCpuLoadGraph
														.hideLoading();
												systemCpuLoadGraph
														.setOption({
															color : colors,
															title : {
																text : 'CPU负载情况',
																left : '1%',
																top : '2%',
																textStyle : {
																	color : '#2a8ed3'
																}
															},
															tooltip : {
																trigger : 'axis',
																axisPointer : {
																	animation : true
																}
															},
															legend : {
																x : 300,
																top : '7%',
																textStyle : {
																	color : '#ffd285',
																},
																data : [ '使用率' ]
															},
															grid : {
																left : '1%',
																right : '1%',
																top : '16%',
																bottom : '10%',
																containLabel : true
															},
															toolbox : {
																"show" : true,
																right : '1%',
																top : '3%',
																feature : {
																	saveAsImage : {}
																}
															},
															xAxis : {
																type : 'category',
																splitLine : {
																	show : false
																},
																axisTick : {
																	alignWithLabel : true
																},
																
																data : ddata
															},
															yAxis : [ {
																type : 'value',
																boundaryGap : [
																		0,
																		'100%' ],
																splitline : {
																	show : false
																}
															} ],
															series : {
																name : 'CPU负载率',
																type : 'line',
																showSymbol : true,
																hoverAnimation : true,
																data : systemCpuLoad,
																smooth: true
															}
														});
											}
										});
								$.ajax({
											method : "GET",
											url : "/security/active/sessions",
											success : function(data) {
												memoryInfo.activeUser = data;
											}
										});

								$.ajax({
									url : 'http://192.168.1.3:9200/nginx/_search',
							    	type : "POST",
							    	async : false,
							    	jsonType : "jsonp",
							    	data : JSON.stringify({
							    		"size": 0,
							    		"aggs": {
							    			"visitedIP": {
							    				"terms": {
							    					"field": "clientip.keyword",
							    					"order": {
							    						"_count": "desc"
							    					}
							    				}
							    		    }
							    		 }
							    	}),
							    	success : function(data) {
							    		memoryInfo.mostVisitedIP = data.aggregations.visitedIP.buckets[0].key;
									}
								})

							}, 5000);
				})