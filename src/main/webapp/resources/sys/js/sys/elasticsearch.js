/*function getDate(index) {
	var date = new Date(); // 当前日期
	var newDate = new Date();
	newDate.setDate(date.getDate() + index);// 官方文档上虽然说setDate参数是1-31,其实是可以设置负数的
	var time = newDate.getFullYear() + "." + (newDate.getMonth() + 1) + "."
			+ newDate.getDate();
	return time;
}*/
/*{
 "properties": {
 "clientip": {
 "type": "text",
 "fielddata": true
 }
 }
 }*/
var userNumberGraph = echarts.init(document.getElementById('userNumberGraph'));
option = {
	color : [ '#3398DB' ],
	title : {
		text : '历史访问人数',
		left : '1%',
		top : '2%',
		textStyle : {
			color : '#2a8ed3'
		}
	},
	tooltip : {
		trigger : 'axis',
		axisPointer : { // 坐标轴指示器，坐标轴触发有效
			type : 'shadow' // 默认为直线，可选为：'line' | 'shadow'
		}
	},
	grid : {
		left : '3%',
		right : '4%',
		bottom : '3%',
		containLabel : true
	},
	/*xAxis : [ {
		type : 'category',
		data : ["1"],
		axisTick : {
			alignWithLabel : true
		}
	} ],
	yAxis : [ {
		type : 'value'
	} ],
	series : [ {
		name : '访问过网站的用户数',
		type : 'bar',
		barWidth : '60%',
		data : [1]
	} ]*/
	xAxis: {
		type: 'category',
	    data: []
	},
	yAxis: {
		type: 'value'
	},
	series: [{
		name : '访问过网站的用户数',
		barWidth : '60%',
		data : [1],
	    type: 'line',
	    smooth: true
	}]
};
userNumberGraph.setOption(option);
function getDate(index) {
	var date = new Date(); // 当前日期
	var newDate = new Date();
	newDate.setDate(date.getDate() + index);// 官方文档上虽然说setDate参数是1-31,其实是可以设置负数的
	return newDate;
}
var curDate = getDate(0);
var passDate = getDate(-7);
var self = this;
$.ajax({
	type : "POST",
	async : false,
	jsonType : "jsonp",
	url : "http://192.168.1.3:9200/nginx/_search",
	data : JSON.stringify({
		"query" : {
			"constant_score" : {
				"filter" : {
					"range" : {
						"@timestamp" : {
							"lte" : curDate.getTime(),
							"gte" : passDate.getTime()
						}
					}
				}
			}
		},
		"size" : 0,
		"aggs" : {
			"sales" : {
				"date_histogram" : {
					"field" : "@timestamp",
					"interval" : "day",
					"format" : "yyyy-MM-dd",
					"time_zone" : "+08:00"
				},
				"aggs" : {
					"uniq_attr" : {
						"cardinality" : {
							"field" : "clientip"
						}
					}
				}
			}
		}
	}),
	success : function(r) {
		console.log("asdfasfdadsfasdfaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaasd");
		var lists = r.aggregations.sales.buckets;
		var date1=new Array();
		var date2=new Array();
		for (var i = 0; i < lists.length; i++) {
			date1.push(lists[i].key_as_string);
			date2.push(lists[i].uniq_attr.value);
		}
		if(lists.length < 7) {
			for (var i = 0; i < 7 - lists.length; i++) {
				var isdate;
				if(lists.length == 0) {
					var nowdate = new Date();
					var oneweekdate = new Date(nowdate-8*24*3600*1000);  
			        var y = oneweekdate.getFullYear();  
			        var m = oneweekdate.getMonth()+1;  
			        var d = oneweekdate.getDate();  
			        if(m < 10)
			        	m = '0' + m;
			        if(d < 10)
			        	d = '0' + d;
			        var formatwdate = y+'-'+m+'-'+d;
			        isdate = new Date(formatwdate.replace(/-/g,"/"));
				} else
					isdate = new Date(lists[lists.length-1].key_as_string.replace(/-/g,"/"));  //把日期字符串转换成日期格式
				
			    isdate = new Date((isdate/1000+(86400*(i+1)))*1000);  //日期加1天  
			    var y = isdate.getFullYear();  
		        var m = isdate.getMonth()+1;  
		        var d = isdate.getDate();  
		        if(m < 10)
		        	m = '0' + m;
		        if(d < 10)
		        	d = '0' + d;
			    var pdate = y+"-"+m+"-"+d;   //把日期格式转换成字符串  
				date1.push(pdate);
				date2.push(0);
			}
		}
		userNumberGraph.setOption({
			xAxis : {
				data : date1
			},
			series : [ {
				data : date2
			} ]
		});
	}
});
