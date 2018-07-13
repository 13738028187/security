var chart;
var c=[];
var curDate = getDate(0);
var passDate = getDate(-10);
var jsonData={
		query : {
			bool : {					
				filter : [
				     {
							"range" : {
								"@timestamp" : {
									"gte" : passDate,
									"lte" : curDate								
								}
							}
				     }     
					,{ 
						range : {
						severity : {
							gte : 0,
							lte : 7
						}
					}
				}]
			}
		},
		"size" : 0,
		"aggs" : {
			"sales" : {
				"date_histogram" : {
					"field" : "@timestamp",
					"interval" : "day",
					"format" : "yyyy-MM-dd",
					//"time_zone" : "+08:00"
				},
				"aggs" : {
					"uniq_attr" : {
						"cardinality" : {
							"field" : "clientip"
						}
					}
				}
			}
		}}

$(function() {
	// 基于准备好的dom，初始化echarts实例
	var myChart = echarts.init(document.getElementById('main'));
	
	// 指定图表的配置项和数据
	option = {
		    title : {
		        text: '历史告警信息统计图',
		        subtext: '',
		        x:'center'
		    },
		    tooltip : {
		        trigger: 'item',
		        formatter: "{a} <br/>{b} : {c} ({d}%)"
		    },
		    toolbox : {
				"show" : true,
				right : '1%',
				top : '3%',
				feature : {
					saveAsImage : {}
				}
			},
			color : ['#FF0000', '#FF4500', '#FFD700', 'green'],
		    legend: {
		        orient: 'vertical',
		        left: 'left',
		        data: ['重要告警','严重告警','一般告警','信息提示']
		    },
		    series : [
		        {
		            name: '告警信息',
		            type: 'pie',
		            radius : '55%',
		            center: ['50%', '60%'],
		            data:[
		                /*{value:335, name:'重要告警'},
		                {value:310, name:'严重告警'},
		                {value:234, name:'一般告警'},
		                {value:135, name:'信息提示'}*/
		            ],
		            itemStyle: {
		                emphasis: {
		                    shadowBlur: 10,
		                    shadowOffsetX: 0,
		                    shadowColor: 'rgba(0, 0, 0, 0.5)'
		                },
		                normal: {
		                	label: {
		                		show:true,  
		                		formatter:'{b} : {c} ({d}%)'  
		                	}
		                }
		            }
		        }
		    ]
		};
	
	// 使用刚指定的配置项和数据显示图表。
    myChart.setOption(option);
    
    var severity = new Array();
    severity.push({
    	name : "重要告警",
    	value : getAlarmNum(0, 1)
    });
    severity.push({
    	name : "严重告警",
    	value : getAlarmNum(2, 3)
    });
    severity.push({
    	name : "一般告警",
    	value : getAlarmNum(4, 5)
    });
    severity.push({
    	name : "信息提示",
    	value : getAlarmNum(6, 7)
    });
    
    myChart.setOption({
    	series : [{
    		data : severity
    	}]
    })
	
})

function getAlarmNum(a, b) {
	var total;
	$.ajax({
		url : 'http://192.168.1.3:9200/syslog/_search',
    	type : "POST",
    	async : false,
    	jsonType : "jsonp",
    	data : JSON.stringify({
    		"query" : {
				"bool" : {
					"must" : {
						"match_all" : {}
					},
					"filter" : [ {
						"range" : {
							"severity" : {
								"gte" : a,
								"lte" : b
							}
						}
					} ]
				}
			}
    	}),
    	success : function(data) {
    		total = data.hits.total;
		}
	});
	return total;
}

$(function() {
	var myChart = echarts.init(document.getElementById('main2'));
	
	option = {
	    color: ['#3398DB'],
	    title : {
	        text: '告警个数最多设备Top10',
	        subtext: '',
	        x:'center'
	    },
	    tooltip : {
	        trigger: 'axis',
	        axisPointer : {            // 坐标轴指示器，坐标轴触发有效
	            type : 'shadow'        // 默认为直线，可选为：'line' | 'shadow'
	        }
	    },
	    toolbox : {
			"show" : true,
			right : '1%',
			top : '3%',
			feature : {
				saveAsImage : {}
			}
		},
	    grid: {
	        left: '3%',
	        right: '4%',
	        bottom: '3%',
	        containLabel: true
	    },
	    xAxis : [
	        {
	            type : 'category',
	            data : [/*'win7', 'win8', 'win10', 'linux1', 'linux2', '路由器1', '路由器2', '网关设备1', '网关设备2', '网关设备3'*/],
	            axisTick: {
	                alignWithLabel: true
	            }
	        }
	    ],
	    yAxis : [
	        {
	            type : 'value'
	        }
	    ],
	    series : [
	        {
	            name:'告警数量',
	            type:'bar',
	            barWidth: '60%',
	            data:[/*10, 52, 200, 334, 390, 330, 220, 200, 175, 150*/]
	        }
	    ],
	    label: {
	        normal: {
	            show: true,
	            position: 'top',
	            textStyle: {
	              color: 'black'
	            }
	        }
	     }
	};
	
	myChart.setOption(option);
	
	var xData = new Array();
	var yData = new Array();
	$.ajax({
		url : 'http://192.168.1.3:9200/syslog/_search',
    	type : "POST",
    	async : false,
    	jsonType : "jsonp",
    	data : JSON.stringify({
    		"size": 0,
    		"aggs": {
    			"deviceNames": {
    				"terms": {
    					"field": "logsource.keyword",
    					"order": {
    						"_count": "desc"
    					}
    				}
    		    }
    		 }
    	}),
    	success : function(data) {
    		for (var i = 0; i < data.aggregations.deviceNames.buckets.length; i++) {
				xData.push(data.aggregations.deviceNames.buckets[i].key);
				yData.push(data.aggregations.deviceNames.buckets[i].doc_count);
			}
    		myChart.setOption({
    			xAxis : {
    				data : xData
    			},
    			series : [ {
    				data : yData
    			} ]
    		});
		}
	});
	
})

$(function () {
    $("#jqGrid").jqGrid({
        url: '../reportManager/getTableData',
        datatype: "json",
        colModel: [			
        	{ label: '序号', name: 'id', width: 10, key: true },
			{ label: '设备名称', name: 'deviceName', width:20 },
			{ label: '设备IP', name: 'deviceIP', width:20 },
			{ label: '最新统计时间', name: 'timestamp', width:20},
			{ label: '告警数量', name: 'alarmNum', width:20}
        ],
		viewrecords: true,
        height: 200,
        rowNum: 5,
		rowList : [5,10,20],
        rownumbers: true, 
        rownumWidth: 50, 
        autowidth:true,
        multiselect: false,
        pager: "#jqGridPager",
        gridComplete:function(){  //数据加载完成后 
        	//隐藏grid底部滚动条
        	$("#jqGrid").closest(".ui-jqgrid-bdiv").css({ "overflow-x" : "hidden" }); 
        }
    });
})
function getDate(index) {
	var date = new Date(); // 当前日期
	var newDate = new Date();
	newDate.setDate(date.getDate() + index);// 官方文档上虽然说setDate参数是1-31,其实是可以设置负数的
	return newDate;
}
$(function() {
	$.ajax({
		type : "POST",
		async : false,
		jsonType : "jsonp",
		url : "http://192.168.1.3:9200/syslog/_search",
		data : JSON.stringify(jsonData),
		success : function(r) {

			for (var i = 0; i < r.aggregations.sales.buckets.length; i++) {
				var d = {
					"date" : "",
					"distance" : 1,
				// "townSize": 10,
				    "latitude": 1,
				    "duration": 1
				}
				d.date = r.aggregations.sales.buckets[i].key_as_string;
				if (r.aggregations.sales.buckets[i].doc_count != 0) {
					d.distance = r.aggregations.sales.buckets[i].doc_count;
				}				
				c.push(d);
				jsonData.query.bool.filter[1].range.severity.gte=4;
				jsonData.query.bool.filter[1].range.severity.lte=5;
				$.ajax({
					type : "POST",
					async : false,
					jsonType : "jsonp",
					url : "http://192.168.1.3:9200/syslog/_search",
					data : JSON.stringify(jsonData),
					success : function(r) {						
						for (var j = 0; j < c.length; j++) {												
							c[j].latitude=r.aggregations.sales.buckets[j].doc_count;			
						}
						jsonData.query.bool.filter[1].range.severity.gte=6;
						jsonData.query.bool.filter[1].range.severity.lte=7;
						$.ajax({
							type : "POST",
							async : false,
							jsonType : "jsonp",
							url : "http://192.168.1.3:9200/syslog/_search",
							data : JSON.stringify(jsonData),
							success : function(r) {						
								for (var k = 0; k < c.length; k++) {												
									c[k].duration=r.aggregations.sales.buckets[k].doc_count;			
								}
								console.log(JSON.stringify(c));
								
							}
							
						});						
					}
				
				});	
			}
		}
	});	
	/*var date2=new Date().Format("yyyy-MM-dd");
	var e={
			"date" : date2,
			"distance" : 0,
		    "latitude": 0,
		    "duration": 0
		}
	c.push(e);
	console.log(JSON.stringify(c));*/
})
            AmCharts.ready(function () {
                // SERIAL CHART
                chart = new AmCharts.AmSerialChart();
                chart.addClassNames = true;
                chart.dataProvider = c;
                chart.categoryField = "date";
                chart.dataDateFormat = "YYYY-MM-DD";
                chart.startDuration = 1;
                
                chart.color = "black";
                chart.marginLeft = 0;
                chart.export={
	                    "enabled": true
	                };
                // AXES
                // category
                var categoryAxis = chart.categoryAxis;
                categoryAxis.parseDates = true; // as our data is date-based, we set parseDates to true
                categoryAxis.minPeriod = "DD"; // our data is daily, so we set minPeriod to DD
                categoryAxis.autoGridCount = false;
                categoryAxis.gridCount = 50;
                categoryAxis.gridAlpha = 0.1;
                categoryAxis.gridColor = "#555555";
                categoryAxis.axisColor = "#555555";
                // we want custom date formatting, so we change it in next line
                categoryAxis.dateFormats = [{
                    period: 'DD',
                    format: 'DD'
                }, {
                    period: 'WW',
                    format: 'MMM DD'
                }, {
                    period: 'MM',
                    format: 'MMM'
                }, {
                    period: 'YYYY',
                    format: 'YYYY'
                }];

                // as we have data of different units, we create three different value axes
                // Distance value axis
                var distanceAxis = new AmCharts.ValueAxis();
                distanceAxis.title = "日志数量";
                distanceAxis.gridAlpha = 0;
                distanceAxis.axisAlpha = 0;
                chart.addValueAxis(distanceAxis);

                // latitude value axis
                /*var latitudeAxis = new AmCharts.ValueAxis();
                latitudeAxis.gridAlpha = 0;
                latitudeAxis.axisAlpha = 0;
                latitudeAxis.labelsEnabled = false;
                latitudeAxis.position = "right";
                chart.addValueAxis(latitudeAxis);*/

                // duration value axis
               /* var durationAxis = new AmCharts.ValueAxis();
                durationAxis.title = "新增重要日志数量";               
                durationAxis.gridAlpha = 0;
                durationAxis.axisAlpha = 0;
                durationAxis.inside = true;
                durationAxis.position = "right";
                chart.addValueAxis(durationAxis);*/

                // GRAPHS
                

                // latitude graph
                var latitudeGraph = new AmCharts.AmGraph();
                latitudeGraph.valueField = "latitude";
                //latitudeGraph.id = "g1";
                latitudeGraph.classNameField = "bulletClass";
                latitudeGraph.title = "新增严重日志数量";
                latitudeGraph.type = "line";
                latitudeGraph.valueAxis = distanceAxis; // indicate which axis should be used
                latitudeGraph.lineColor = "green";
                latitudeGraph.lineThickness = 1;
                latitudeGraph.legendValueText = "[[description]] [[value]]";
                //latitudeGraph.descriptionField = "townName";
                latitudeGraph.bullet = "round";
                latitudeGraph.bulletSizeField = "townSize"; // indicate which field should be used for bullet size
                latitudeGraph.bulletBorderColor = "#786c56";
                latitudeGraph.bulletBorderAlpha = 1;
                latitudeGraph.bulletBorderThickness = 2;
                latitudeGraph.bulletColor = "#000000";
                //latitudeGraph.labelText = "[[townName2]]"; // not all data points has townName2 specified, that's why labels are displayed only near some of the bullets.
                latitudeGraph.labelPosition = "right";
                latitudeGraph.balloonText = "新增严重日志数量:[[value]]";
                latitudeGraph.showBalloon = true;
                latitudeGraph.animationPlayed = true;
                chart.addGraph(latitudeGraph);

                // duration graph
                var durationGraph = new AmCharts.AmGraph();
                durationGraph.id = "g2";
                durationGraph.title = "新增重要日志数量";
                durationGraph.valueField = "duration";
                durationGraph.type = "line";
                durationGraph.valueAxis = distanceAxis; // indicate which axis should be used
                durationGraph.lineColor = "#ff5755";
                durationGraph.balloonText = "新增重要日志数量:[[value]]";
                durationGraph.lineThickness = 1;
                durationGraph.legendValueText = "[[value]]";
                durationGraph.bullet = "square";
                durationGraph.bulletBorderColor = "#ff5755";
                durationGraph.bulletBorderThickness = 1;
                durationGraph.bulletBorderAlpha = 1;
                durationGraph.dashLengthField = "dashLength";
                durationGraph.animationPlayed = true;
                chart.addGraph(durationGraph);
                
                // distance graph
                var distanceGraph = new AmCharts.AmGraph();
                distanceGraph.valueField = "distance";
                distanceGraph.title = "新增日志总量";
                distanceGraph.type = "column";
                distanceGraph.fillAlphas = 0.9;
                distanceGraph.valueAxis = distanceAxis; // indicate which axis should be used
                distanceGraph.balloonText = "新增日志总量:[[value]]";
                distanceGraph.legendValueText = "[[value]]";
                distanceGraph.legendPeriodValueText = "新增日志总量: [[value.sum]]";
                distanceGraph.lineColor = "#263138";
                distanceGraph.alphaField = "alpha";
                chart.addGraph(distanceGraph);

                // CURSOR
                var chartCursor = new AmCharts.ChartCursor();
                chartCursor.zoomable = false;
                chartCursor.categoryBalloonDateFormat = "DD";
                chartCursor.cursorAlpha = 0;
                chartCursor.valueBalloonsEnabled = false;
                chart.addChartCursor(chartCursor);

                // LEGEND
                var legend = new AmCharts.AmLegend();
                legend.bulletType = "round";
                legend.equalWidths = false;
                legend.valueWidth = 120;
                legend.useGraphSettings = true;
                legend.color = "black";
                chart.addLegend(legend);

                // WRITE
                chart.write("chartdiv");
            });
	

var vm = new Vue({
	el : '#rrapp',
	data : {
		timestamp : [
			{key : 0, value : '任意时间'},
			{key : 1, value : '一月'},
			{key : 2, value : '一周'}
		]
	},
	methods : {
		exported:function(){
			location.href = "../reportManager/exported";			
		},
		changeTimeStamp : function() {
			var key = $("#timestamp").val();
			if(key != 0)
				$("#timeForm").hide();
			else {
				$("#timeForm").show();
			}
		},
		getOneMonthDate : function() {
			var nowdate = new Date();
			nowdate.setMonth(nowdate.getMonth()-1);  
	        var y = nowdate.getFullYear();  
	        var m = nowdate.getMonth()+1;  
	        var d = nowdate.getDate();  
	        if(m < 10)
	        	m = '0' + m;
	        if(d < 10)
	        	d = '0' + d;
	        var formatmdate = y+'-'+m+'-'+d;  
	        return formatmdate;
		},
		getOneWeekDate : function(nowdate) {
			var nowdate = new Date();
			var oneweekdate = new Date(nowdate-7*24*3600*1000);  
	        var y = oneweekdate.getFullYear();  
	        var m = oneweekdate.getMonth()+1;  
	        var d = oneweekdate.getDate();  
	        if(m < 10)
	        	m = '0' + m;
	        if(d < 10)
	        	d = '0' + d;
	        var formatwdate = y+'-'+m+'-'+d;  
	        return formatwdate;
		},
		getPieDataByDate : function(a, b, startDate, endDate) {
			var total;
			$.ajax({
				url : 'http://192.168.1.3:9200/syslog/_search',
		    	type : "POST",
		    	async : false,
		    	jsonType : "jsonp",
		    	data : JSON.stringify({
		    		"query": {
		    		    "bool": {
		    		    	"must": {
		    		    		"match_all": {}
		    		    	},
		    		    	"filter": [
		    		    		{
		    		    			"range": {
		    		    				"@timestamp": {
		    		    					"gte": startDate + "T00:00:00.000Z",
		    		    					"lte": endDate + "T00:00:00.000Z"
		    		    				}
		    		    			}
		    		    		},
		    		    		{
		    		    			"range": {
		    		    				"severity": {
		    		    					"gte": a,
		    		    					"lte": b
		    		    				}
		    		    			}
		    		    		}
		    		    	]
		    		    }
		    		}
		    	}),
		    	success : function(data) {
		    		total = data.hits.total;
				}
			});
			return total;
		},
		search : function() {
			var nowdate = new Date();  
	        var y = nowdate.getFullYear();  
	        var m = nowdate.getMonth()+1;  
	        var d = nowdate.getDate();  
	        if(m < 10)
	        	m = '0' + m;
	        if(d < 10)
	        	d = '0' + d;
	        var formatnowdate = y+'-'+m+'-'+d;  
			
			var key = $("#timestamp").val();
			var startDate;
			var endDate;
			if(key == 0) {
				startDate = $("#startDate").val();
				endDate = $("#endDate").val();
				if(startDate == '' || endDate == '') {
					alert("请选择需要搜索的时间");
					return;
				}
			} else if(key == 1) {
				startDate = vm.getOneMonthDate(formatnowdate);
				endDate = formatnowdate;
			} else if(key == 2) {
				startDate = vm.getOneWeekDate(formatnowdate);
				endDate = formatnowdate;
			}
			
			// 获取饼图数据
			var severity = new Array();
		    severity.push({
		    	name : "重要告警",
		    	value : vm.getPieDataByDate(0, 1, startDate, endDate)
		    });
		    severity.push({
		    	name : "严重告警",
		    	value : vm.getPieDataByDate(2, 3, startDate, endDate)
		    });
		    severity.push({
		    	name : "一般告警",
		    	value : vm.getPieDataByDate(4, 5, startDate, endDate)
		    });
		    severity.push({
		    	name : "信息提示",
		    	value : vm.getPieDataByDate(6, 7, startDate, endDate)
		    });
		    
		    var myChart1 = echarts.init(document.getElementById('main'));
		    
		    myChart1.setOption({
		    	series : [{
		    		data : severity
		    	}]
		    });
		    
		    // 获取柱状图数据
		    var myChart2 = echarts.init(document.getElementById('main2'));
		    
		    var xData = new Array();
			var yData = new Array();
			$.ajax({
				url : 'http://192.168.1.3:9200/syslog/_search',
		    	type : "POST",
		    	async : false,
		    	jsonType : "jsonp",
		    	data : JSON.stringify({
		    		"query": {
		    			"bool": {
		    				"must": {
		    					"match_all": {}
		    				},
		    				"filter": [
		    					{
		    						"range": {
		    							"@timestamp": {
		    								"gte": startDate + "T00:00:00.000Z",
		    								"lte": endDate + "T00:00:00.000Z"
		    							}
		    						}
		    					}
		    				]
		    		    }
		    		},
		    		"size": 0,
		    		"aggs": {
		    			"deviceNames": {
		    				"terms": {
		    					"field": "logsource.keyword",
		    					"order": {
		    						"_count": "desc"
		    					}
		    				}
		    		    }
		    		 }
		    	}),
		    	success : function(data) {
		    		for (var i = 0; i < data.aggregations.deviceNames.buckets.length; i++) {
						xData.push(data.aggregations.deviceNames.buckets[i].key);
						yData.push(data.aggregations.deviceNames.buckets[i].doc_count);
					}
		    		myChart2.setOption({
		    			xAxis : {
		    				data : xData
		    			},
		    			series : [ {
		    				data : yData
		    			} ]
		    		});
				}
			});
			
			// 获取表格数据
			$("#jqGrid").jqGrid('setGridParam',{  // 重新加载数据
			      postData : {
			    	  startDate : startDate,
			    	  endDate : endDate
			      },
			      page : 1
			}).trigger("reloadGrid");
			
			//amcharts
			jsonData.query.bool.filter[1].range.severity.gte=0;
			jsonData.query.bool.filter[1].range.severity.lte=7;
			jsonData.query.bool.filter[0].range=
			{
				"@timestamp" : {
					"gte" : new Date($('#startDate').get(0).realValue).getTime(),
					"lte" : new Date($('#endDate').get(0).realValue).getTime()								
				}
			}		
			$.ajax({
				type : "POST",
				async : false,
				jsonType : "jsonp",
				url : "http://192.168.1.3:9200/syslog/_search",
				data : JSON.stringify(jsonData),
				success : function(r) {
					console.log(JSON.stringify(r));
					console.log($('#startDate').get(0).realValue);
					console.log($('#endDate').get(0).realValue);
					c.length = 0;
					for (var i = 0; i < r.aggregations.sales.buckets.length; i++) {
						var d = {
							"date" : "",
							"distance" : 1,
						// "townSize": 10,
						    "latitude": 1,
						    "duration": 1
						}
						d.date = r.aggregations.sales.buckets[i].key_as_string;
						if (r.aggregations.sales.buckets[i].doc_count != 0) {
							d.distance = r.aggregations.sales.buckets[i].doc_count;
						}				
						c.push(d);
					}
					jsonData.query.bool.filter[1].range.severity.gte=4;
					jsonData.query.bool.filter[1].range.severity.lte=5;
					$.ajax({
						type : "POST",
						async : false,
						jsonType : "jsonp",
						url : "http://192.168.1.3:9200/syslog/_search",
						data : JSON.stringify(jsonData),
						success : function(r) {						
							for (var j = 0; j < c.length; j++) {												
								c[j].latitude=r.aggregations.sales.buckets[j].doc_count;			
							}
							jsonData.query.bool.filter[1].range.severity.gte=6;
							jsonData.query.bool.filter[1].range.severity.lte=7;
							$.ajax({
								type : "POST",
								async : false,
								jsonType : "jsonp",
								url : "http://192.168.1.3:9200/syslog/_search",
								data : JSON.stringify(jsonData),
								success : function(r) {						
									for (var k = 0; k < c.length; k++) {												
										c[k].duration=r.aggregations.sales.buckets[k].doc_count;			
									}									
								}
							});	
						}
					});
					/*var e={							
							"date" : $('#endDate').get(0).realValue,
							"distance" : 0,
						    "latitude": 0,
						    "duration": 0
						}
					c.push(e);*/
					chart = new AmCharts.AmSerialChart();
				    chart.addClassNames = true;
				    chart.dataProvider = c;
				    chart.categoryField = "date";
				    chart.dataDateFormat = "YYYY-MM-DD";
				    chart.startDuration = 1;
				 
				    chart.color = "black";
				    chart.marginLeft = 0;
				    chart.export={
		                    "enabled": true
		                };
				    // AXES
				    // category
				    var categoryAxis = chart.categoryAxis;
				    categoryAxis.parseDates = true; // as our data is date-based, we set parseDates to true
				    categoryAxis.minPeriod = "DD"; // our data is daily, so we set minPeriod to DD
				    categoryAxis.autoGridCount = false;
				    categoryAxis.gridCount = 50;
				    categoryAxis.gridAlpha = 0.1;
				    categoryAxis.gridColor = "#555555";
				    categoryAxis.axisColor = "#555555";
				    // we want custom date formatting, so we change it in next line
				    categoryAxis.dateFormats = [{
				        period: 'DD',
				        format: 'DD'
				    }, {
				        period: 'WW',
				        format: 'MMM DD'
				    }, {
				        period: 'MM',
				        format: 'MMM'
				    }, {
				        period: 'YYYY',
				        format: 'YYYY'
				    }];

				    // as we have data of different units, we create three different value axes
				   
				    // latitude value axis
				   /* var latitudeAxis = new AmCharts.ValueAxis();
				    latitudeAxis.gridAlpha = 0;
				    latitudeAxis.axisAlpha = 0;
				    latitudeAxis.labelsEnabled = false;
				    latitudeAxis.position = "right";
				    chart.addValueAxis(latitudeAxis);*/

				    // duration value axis
				    /*var durationAxis = new AmCharts.ValueAxis();
				    durationAxis.title = "duration";				    
				    durationAxis.gridAlpha = 0;
				    durationAxis.axisAlpha = 0;
				    durationAxis.inside = true;
				    durationAxis.position = "right";
				    chart.addValueAxis(durationAxis);*/
				    
				    // Distance value axis
				    var distanceAxis = new AmCharts.ValueAxis();
				    distanceAxis.title = "日志数量";
				    distanceAxis.gridAlpha = 0;
				    distanceAxis.axisAlpha = 0;
				    chart.addValueAxis(distanceAxis);


				    // GRAPHS
				    

				    // latitude graph
				    var latitudeGraph = new AmCharts.AmGraph();
				    latitudeGraph.valueField = "latitude";
				    //latitudeGraph.id = "g1";
				    latitudeGraph.classNameField = "bulletClass";
				    latitudeGraph.title = "新增严重日志数量";
				    latitudeGraph.type = "line";
				    latitudeGraph.valueAxis = distanceAxis; // indicate which axis should be used
				    latitudeGraph.lineColor = "green";

				    latitudeGraph.lineThickness = 1;
				    latitudeGraph.legendValueText = "[[description]] [[value]]";
				    //latitudeGraph.descriptionField = "townName";
				    latitudeGraph.bullet = "round";
				    latitudeGraph.bulletSizeField = "townSize"; // indicate which field should be used for bullet size
				    latitudeGraph.bulletBorderColor = "#786c56";
				    latitudeGraph.bulletBorderAlpha = 1;
				    latitudeGraph.bulletBorderThickness = 2;
				    latitudeGraph.bulletColor = "#000000";
				    //latitudeGraph.labelText = "[[townName2]]"; // not all data points has townName2 specified, that's why labels are displayed only near some of the bullets.
				    latitudeGraph.labelPosition = "right";
				    latitudeGraph.balloonText = "新增严重日志数量:[[value]]";
				    latitudeGraph.showBalloon = true;
				    latitudeGraph.animationPlayed = true;
				    chart.addGraph(latitudeGraph);

				    // duration graph
				    var durationGraph = new AmCharts.AmGraph();
				    durationGraph.id = "g2";
				    durationGraph.title = "新增重要日志数量";
				    durationGraph.valueField = "duration";
				    durationGraph.type = "line";
				    durationGraph.valueAxis = distanceAxis; // indicate which axis should be used
				    durationGraph.lineColor = "#ff5755";
				    durationGraph.balloonText = "[[value]]";
				    durationGraph.lineThickness = 1;
				    durationGraph.legendValueText = "新增重要日志数量:[[value]]";
				    durationGraph.bullet = "square";
				    durationGraph.bulletBorderColor = "#ff5755";
				    durationGraph.bulletBorderThickness = 1;
				    durationGraph.bulletBorderAlpha = 1;
				    durationGraph.dashLengthField = "dashLength";
				    durationGraph.animationPlayed = true;
				    chart.addGraph(durationGraph);
				    
				    // distance graph
				    var distanceGraph = new AmCharts.AmGraph();
				    distanceGraph.valueField = "distance";
				    distanceGraph.title = "今日新增日志数量";
				    distanceGraph.type = "column";
				    distanceGraph.fillAlphas = 0.9;
				    distanceGraph.valueAxis = distanceAxis; // indicate which axis should be used
				    distanceGraph.balloonText = "[[value]]";
				    distanceGraph.legendValueText = "[[value]]";
				    distanceGraph.legendPeriodValueText = "日志总量: [[value.sum]]";
				    distanceGraph.lineColor = "#263138";
				    distanceGraph.alphaField = "alpha";
				    chart.addGraph(distanceGraph);

				    // CURSOR
				    var chartCursor = new AmCharts.ChartCursor();
				    chartCursor.zoomable = false;
				    chartCursor.categoryBalloonDateFormat = "DD";
				    chartCursor.cursorAlpha = 0;
				    chartCursor.valueBalloonsEnabled = false;
				    chart.addChartCursor(chartCursor);

				    // LEGEND
				    var legend = new AmCharts.AmLegend();
				    legend.bulletType = "round";
				    legend.equalWidths = false;
				    legend.valueWidth = 120;
				    legend.useGraphSettings = true;
				    legend.color = "black";
				    chart.addLegend(legend);
					
					chart.write("chartdiv");						            

				}
			});	
		}
	}
})