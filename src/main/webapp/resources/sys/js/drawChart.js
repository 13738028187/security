var c=[];

function getDate(index) {
	var date = new Date(); // 当前日期
	var newDate = new Date();
	newDate.setDate(date.getDate() + index);// 官方文档上虽然说setDate参数是1-31,其实是可以设置负数的
	return newDate;
}
var curDate = getDate(0);
var passDate = getDate(-10);
var jsonData={
		query : {
			bool : {					
				filter : [
				     {
							"range" : {
								"@timestamp" : {
									"gte" : passDate.getTime(),
									"lte" : curDate.getTime()
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
	//控制搜索条件0-7  搜索全部数据
	var self = this;
	jsonData.query.bool.filter[1].range.severity.gte=0;
	jsonData.query.bool.filter[1].range.severity.lte=7;
	$.ajax({
		type : "POST",
		async : false,
		jsonType : "jsonp",
		url : "http://192.168.1.3:9200/syslog/_search",
		data : JSON.stringify(jsonData),
		success : function(r) {
			//获取每天的数据总数量
			for (var i = 0; i < r.aggregations.sales.buckets.length; i++) {
				var d = {
					"date" : "",
					"distance" : 1,
				    "latitude": 1,
				    "duration": 1
				}
				d.date = r.aggregations.sales.buckets[i].key_as_string;
				if (r.aggregations.sales.buckets[i].doc_count != 0) {
					d.distance = r.aggregations.sales.buckets[i].doc_count;
				}				
				c.push(d);
			}
			
			//控制搜索条件4-5  搜索严重告警数据
			jsonData.query.bool.filter[1].range.severity.gte=4;
			jsonData.query.bool.filter[1].range.severity.lte=5;
			$.ajax({
				type : "POST",
				async : false,
				jsonType : "jsonp",
				url : "http://192.168.1.3:9200/syslog/_search",
				data : JSON.stringify(jsonData),
				success : function(r) {	
					//获取每天的数据总数量
					for (var j = 0; j < c.length; j++) {												
						c[j].latitude=r.aggregations.sales.buckets[j].doc_count;			
					}
					//控制搜索条件4-5  搜索重要告警数据
					jsonData.query.bool.filter[1].range.severity.gte=6;
					jsonData.query.bool.filter[1].range.severity.lte=7;
					$.ajax({
						type : "POST",
						async : false,
						jsonType : "jsonp",
						url : "http://192.168.1.3:9200/syslog/_search",
						data : JSON.stringify(jsonData),
						success : function(r) {	
							//获取每天的数据总数量
							for (var k = 0; k < c.length; k++) {												
								c[k].duration=r.aggregations.sales.buckets[k].doc_count;			
							}
							/*var date=new Date().Format("yyyy-MM-dd");
							var e={
									"date" : date,
									"distance" : 0,
								    "latitude": 0,
								    "duration": 0
								}
							c.push(e);*/
						}
					});	
				}
			});	
		}
	});	
})
			

            var chart;
			//生成柱状折线图
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
                //导出工具
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
                /*var latitudeAxis = new AmCharts.ValueAxis();
                latitudeAxis.gridAlpha = 0;
                latitudeAxis.axisAlpha = 0;
                latitudeAxis.labelsEnabled = false;
                latitudeAxis.position = "right";
                chart.addValueAxis(latitudeAxis);*/

                // duration value axis
                /*var durationAxis = new AmCharts.ValueAxis();
                durationAxis.title = "新增重要日志数量";
                
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