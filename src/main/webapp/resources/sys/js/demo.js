var demo = (function() {
	// 基于准备好的dom，初始化echarts实例
	var myChart = echarts.init(document.getElementById('main'));

	var loanDataValues = [];
	var repayDataValues = [];

	// 使用刚指定的配置项和数据显示图表。
	var showChartFunc = function() {
		myChart
				.setOption({
					title : {
						show : false,
						text : '图表详情'
					},
					tooltip : {
						trigger : 'item',
						formatter : function(params) {
							var date = new Date(params.value[0]);
							data = date.getFullYear() + '-'
									+ (date.getMonth() + 1) + '-'
									+ date.getDate() + ' ' + date.getHours()
									+ ':' + date.getMinutes() + ':'
									+ date.getSeconds();
							return data + '<br/>' + '金额:' + params.value[1]
									+ '<br/>' + '公司:' + params.value[2];
						}
					},
					legend : {
						data : [ 'Demo1金额', 'Demo2金额' ]
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
							restore : {
								show : true
							},
							saveAsImage : {
								show : true
							}
						}
					},
					xAxis : [ {
						type : 'time',
						splitNumber : 10,
						boundaryGap : [ '20%', '20%' ],
						min : 'dataMin',
						max : 'dataMax'
					} ],
					yAxis : [ {
						type : 'value',
						scale : true,
						name : '金额(元)',
						min : 0,
						boundaryGap : [ '20%', '20%' ]
					} ],
					dataZoom : {
						type : 'inside',
						start : 0,
						end : 100
					},
					series : [ {
						name : 'Demo1金额',
						type : 'line',
						smooth : true,
						symbol : 'circle',
						data : loanDataValues
					}, {
						name : 'Demo2金额',
						type : 'line',
						smooth : true,
						symbol : 'rect',
						data : repayDataValues
					} ]
				});
	};

	/**
	 * 实时接受消息并绘制图标
	 * 
	 * @param message
	 */
	var addPointFunc = function addPoint(message) {
		var dataVo = JSON.parse(message.body);
		addData(dataVo);
		showChartFunc();
	};

	function addData(dataVo) {
		if (dataVo.type == 1) {
			loanDataValues.push([ dataVo.date, dataVo.value, dataVo.name ]);
		} else if (dataVo.type == 2) {
			repayDataValues.push([ dataVo.date, dataVo.value, dataVo.name ]);
		}
	}

	/**
	 * WebSocket连接
	 */
	var connectFunc = function connect() {
		websocket.createConnect("getLoanPoints", "/topic/addLoanPoint",
				addPointFunc);
	};

	/**
	 * 发送数据到服务器(暂时不用)
	 */
	var sendValueFunc = function sendValue() {
		var value = document.getElementById('name').value;
		websocket.sendData("/getLoanPoints", value);
	};

	/**
	 * 获取当日借贷信息
	 */
	var getLoanFunc = function() {
		$.getJSON('getLoanInfo').done(function(data) {
			if (data.success) {
				loanDataValues = data.loanInfos.datas;
				repayDataValues = data.repayInfos.datas;
				showChartFunc();
			} else {
				alert(data.message);
			}
		});
	};

	return {
		getLoan : getLoanFunc,
		connect : connectFunc
	}
})();