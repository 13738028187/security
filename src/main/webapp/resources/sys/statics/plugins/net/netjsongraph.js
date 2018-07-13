var timer1=window.setInterval(function() {},30000);
// version 0.1
(function () {
    /**
     * vanilla JS 实现了 jQuery.extend()
     */
    d3._extend = function(defaults, options) {
        var extended = {},
            prop;
        for(prop in defaults) {
            if(Object.prototype.hasOwnProperty.call(defaults, prop)) {
                extended[prop] = defaults[prop];
            }
        }
        for(prop in options) {
            if(Object.prototype.hasOwnProperty.call(options, prop)) {
                extended[prop] = options[prop];
            }
        }
        return extended;
    };

    /**
      * @function
      *   @name d3._pxToNumber
      * 把string转换成int,去掉多余的px
      *
      * @param  {string}       val         The value to convert
      * @return {int}              The converted integer
      */
    d3._pxToNumber = function(val) {
        return parseFloat(val.replace('px'));
    };

    /**
      * @function
      *   @name d3._windowHeight
      *
      * 获取窗口大小
      *
      * @return  {int}            The window innerHeight
      */
    d3._windowHeight = function() {
        return window.innerHeight || document.documentElement.clientHeight || 600;
    };

    /**
      * @function
      *   @name d3._getPosition
      *
      * 获取元素相对于容器的位置
      *
      * @param  {object}      element
      * @param  {object}      container
      */
     d3._getPosition = function(element, container) {
         var n = element.node(),
             nPos = n.getBoundingClientRect();
             cPos = container.node().getBoundingClientRect();
         return {
            top: nPos.top - cPos.top,
            left: nPos.left - cPos.left,
            width: nPos.width,
            bottom: nPos.bottom - cPos.top,
            height: nPos.height,
            right: nPos.right - cPos.left
        };
     };
     /**
      * netjsongraph.js main function
      *
      * @constructor
      * @param  {string}      url             The NetJSON file url
      * @param  {object}      opts            The object with parameters to override {@link d3.netJsonGraph.opts}
      */
  // TODO: probably change defaultStyle
     d3.netJsonGraph = function(url, opts) {
         /**
          * Default options
          *
          * @param  {string}     el                  "body"      The container element                                  el: "body" [description]
          * @param  {bool}       metadata            true        Display NetJSON metadata at startup?
          * @param  {bool}       defaultStyle        true        Use css style?
          * @param  {bool}       animationAtStart    false       Animate nodes or not on load
          * @param  {array}      scaleExtent         [0.25, 5]   The zoom scale's allowed range. @see {@link https://github.com/mbostock/d3/wiki/Zoom-Behavior#scaleExtent}
          * @param  {int}        charge              -130        The charge strength to the specified value. @see {@link https://github.com/mbostock/d3/wiki/Force-Layout#charge}
          * @param  {int}        linkDistance        50          The target distance between linked nodes to the specified value. @see {@link https://github.com/mbostock/d3/wiki/Force-Layout#linkDistance}
          * @param  {float}      linkStrength        0.2         The strength (rigidity) of links to the specified value in the range. @see {@link https://github.com/mbostock/d3/wiki/Force-Layout#linkStrength}
          * @param  {float}      friction            0.9         The friction coefficient to the specified value. @see {@link https://github.com/mbostock/d3/wiki/Force-Layout#friction}
          * @param  {string}     chargeDistance      Infinity    The maximum distance over which charge forces are applied. @see {@link https://github.com/mbostock/d3/wiki/Force-Layout#chargeDistance}
          * @param  {float}      theta               0.8         The Barnes–Hut approximation criterion to the specified value. @see {@link https://github.com/mbostock/d3/wiki/Force-Layout#theta}
          * @param  {float}      gravity             0.1         The gravitational strength to the specified numerical value. @see {@link https://github.com/mbostock/d3/wiki/Force-Layout#gravity}
          * @param  {int}        circleRadius        8           The radius of circles (nodes) in pixel
          * @param  {string}     labelDx             "0"         SVG dx (distance on x axis) attribute of node labels in graph
          * @param  {string}     labelDy             "-1.3em"    SVG dy (distance on y axis) attribute of node labels in graph
          * @param  {function}   onInit                          Callback function executed on initialization
          * @param  {function}   onLoad                          Callback function executed after data has been loaded
          * @param  {function}   onEnd                           Callback function executed when initial animation is complete
          * @param  {function}   linkDistanceFunc                By default high density areas have longer links
          * @param  {function}   redraw                          Called when panning and zooming
          * @param  {function}   prepareData                     Used to convert NetJSON NetworkGraph to the javascript data
          * @param  {function}   onClickNode                     Called when a node is clicked
          * @param  {function}   onClickLink                     Called when a link is clicked
          */
         opts = d3._extend({
             el: "body",
             metadata: false,//启动时显示元数据
             defaultStyle: true,
             animationAtStart: false,//是否有初始动画，false的话 就可以让加载数据的时候，节点在可视范围内。
             scaleExtent: [0.25, 5],//缩放范围
             charge: -130,//电荷强度达到指定值
             linkDistance: 150,//连线的长度
             linkStrength: 0.2,//[0,1]值越大，越坚硬，0的时候拖动节点，顶点不会运动
             friction: 0.9,  // d3 default 摩擦系数指数。[0,1]
             chargeDistance: Infinity,  // d3 default 引力的作用距离。默认无穷大
             theta: 0.8,  // d3 default 顶点如果很多，计算的时间会加大，这个值越小，就能把计算限制得越紧
             gravity: 0.1, //重力指数，只要大于0，所有节点都会向中间聚。
             circleRadius: 8,//半径
             labelDx: "0",//图中节点标号的x（x轴距离）属性
             labelDy: "-1.3em",//图中节点标号的y（y轴距离）属性
             nodeClassProperty: null,
             linkClassProperty: null,
             /**
              * @function
              * @name onInit
              *
              * Callback function executed on initialization
              * @param  {string|object}  url     The netJson remote url or object
              * @param  {object}         opts    The object of passed arguments
              * @return {function}
              */
             onInit: function(url, opts) {},
             /**
              * @function
              * @name onLoad
              *
              * Callback function executed after data has been loaded
              * @param  {string|object}  url     The netJson remote url or object
              * @param  {object}         opts    The object of passed arguments
              * @return {function}
              */
             onLoad: function(url, opts) {},
             /**
              * @function
              * @name onEnd
              *
              * Callback function executed when initial animation is complete
              * @param  {string|object}  url     The netJson remote url or object
              * @param  {object}         opts    The object of passed arguments
              * @return {function}
              */
             onEnd: function(url, opts) {},
             /**
              * @function
              * @name linkDistanceFunc
              *
              * 默认情况下，高密度区域有更长的链接
              */
             linkDistanceFunc: function(d){
                 var val = opts.linkDistance;
                 if(d.source.linkCount >= 4 && d.target.linkCount >= 4) {
                     return val * 2;
                 }
                 return val;
             },
             /**
              * @function
              * @name redraw
              *
              * 调用缩放和平移
              */
             redraw: function() {
                 panner.attr("transform",
                     "translate(" + d3.event.translate + ") " +
                     "scale(" + d3.event.scale + ")"
                 );
             },
             /**
              * @function
              * @name prepareData
              *
              * Convert NetJSON NetworkGraph to the data structure consumed by d3
              * 运用d3将netjson网络图转换为数据结构
              * @param graph {object}
              */
             prepareData: function(graph) {
                 var nodesMap = {},
                     nodes = graph.nodes.slice(), // copy
                     links = graph.links.slice(), // copy
                     nodes_length = graph.nodes.length,
                     links_length = graph.links.length;

                 for(var i = 0; i < nodes_length; i++) {
                     // 计算节点有多少连接
                     nodes[i].linkCount = 0;
                     nodesMap[nodes[i].ip] = i;
                 }
                 for(var c = 0; c < links_length; c++) {
                     var sourceIndex = nodesMap[links[c].source],
                     targetIndex = nodesMap[links[c].target];
                     // 确保源和目标存在
                     if(!nodes[sourceIndex]) { throw("source '" + links[c].source + "' not found"); }
                     if(!nodes[targetIndex]) { throw("target '" + links[c].target + "' not found"); }
                     //把node防入source和target中
                     links[c].source = nodesMap[links[c].source];
                     links[c].target = nodesMap[links[c].target];
                     // 将链接计数添加到两端
                     nodes[sourceIndex].linkCount++;
                     nodes[targetIndex].linkCount++;
                 }
                 return { "nodes": nodes, "links": links };
             },
             /**
              * @function
              * @name 
              *
              * 单击某个节点时调用
              */
             onClickNode: function(n) {
          		
                 var overlay = d3.select(".njg-overlay"),
                     overlayInner = d3.select(".njg-overlay > .njg-inner"),
                     html = "<p><b>ip</b>: " + n.ip + "</p>";
                     if(n.label) { html += "<p><b>label</b>: " + n.label + "</p>"; }
                     if(n.properties) {
                         for(var key in n.properties) {
                             if(!n.properties.hasOwnProperty(key)) { continue; }
                             html += "<p><b>"+key.replace(/_/g, " ")+"</b>: " + n.properties[key] + "</p>";
                     }
                 }
                 if(n.linkCount) { html += "<p><b>links</b>: " + n.linkCount + "</p><br>"; }
                 if(n.local_addresses) {
                     html += "<p><b>local addresses</b>:<br>" + n.local_addresses.join('<br>') + "</p>";
                 }
                 $.ajax({
     				type : "GET",
     				async : false,
     				url : "../equipment/getInterfacesEntry",
     				data:{
     		        	ipAddress : n.ip,
     					community : 'public'
     		        },
     		        error:function(){
     		        	alert("获取失败");
     		        	return;
     		        },
     				success : function(r) {
     					var rlist=r.page.list;
     					if(rlist==null){
     						return;
     					}
     						for(var i=0;i<rlist.length;i++){
     							 html += "<p><b>接口名</b>: " + rlist[i].interfaceName + "</p>";
     							 if(rlist[i].ifType==1){
     								 html += "<p><b>接口类型</b>: " + 'other' + "</p>"; 
     							 }
     							 if(rlist[i].ifType==2){
     								 html += "<p><b>接口类型</b>: " + 'regular1822' + "</p>"; 
     							 }
     							 if(rlist[i].ifType==3){
     								 html += "<p><b>接口类型</b>: " + 'hdh1822' + "</p>"; 
     							 }
     							 if(rlist[i].ifType==4){
     								 html += "<p><b>接口类型</b>: " + 'ddn-x25' + "</p>"; 
     							 }
     							 if(rlist[i].ifType==5){
     								 html += "<p><b>接口类型</b>: " + 'rfc877-x25' + "</p>"; 
     							 }						 
     							 if(rlist[i].ifType==6){
     								 html += "<p><b>接口类型</b>: " + 'ethernet-csmacd' + "</p>"; 
     							 }
     							 if(rlist[i].ifType==7){
     								 html += "<p><b>接口类型</b>: " + 'iso88023-csmacd' + "</p>"; 
     							 }
     							 if(rlist[i].ifType==8){
     								 html += "<p><b>接口类型</b>: " + 'iso88024-tokenBus' + "</p>"; 
     							 }
     							 if(rlist[i].ifType==9){
     								 html += "<p><b>接口类型</b>: " + 'iso88025-tokenRing' + "</p>"; 
     							 }
     							 if(rlist[i].ifType==10){
     								 html += "<p><b>接口类型</b>: " + 'iso88026-man' + "</p>"; 
     							 }
     							 if(rlist[i].ifType==11){
     								 html += "<p><b>接口类型</b>: " + ' starLan' + "</p>"; 
     							 }
     							 if(rlist[i].ifType==12){
     								 html += "<p><b>接口类型</b>: " + 'proteon-10Mbit' + "</p>"; 
     							 }
     							 if(rlist[i].ifType==13){
     								 html += "<p><b>接口类型</b>: " + 'proteon-80Mbit' + "</p>"; 
     							 }
     							 if(rlist[i].ifType==14){
     								 html += "<p><b>接口类型</b>: " + 'hyperchannel' + "</p>"; 
     							 }
     							 if(rlist[i].ifType==15){
     								 html += "<p><b>接口类型</b>: " + 'fddi' + "</p>"; 
     							 }
     							 if(rlist[i].ifType==16){
     								 html += "<p><b>接口类型</b>: " + 'lapb' + "</p>"; 
     							 }
     							 if(rlist[i].ifType==17){
     								 html += "<p><b>接口类型</b>: " + 'sdlc' + "</p>"; 
     							 }
     							 if(rlist[i].ifType==18){
     								 html += "<p><b>接口类型</b>: " + 'ds1' + "</p>"; 
     							 }
     							 if(rlist[i].ifType==19){
     								 html += "<p><b>接口类型</b>: " + 'e1' + "</p>"; 
     							 }
     							 if(rlist[i].ifType==20){
     								 html += "<p><b>接口类型</b>: " + 'basicISDN' + "</p>"; 
     							 }
     							 if(rlist[i].ifType==21){
     								 html += "<p><b>接口类型</b>: " + 'primaryISDN' + "</p>"; 
     							 }
     							 if(rlist[i].ifType==22){
     								 html += "<p><b>接口类型</b>: " + 'propPointToPointSerial' + "</p>"; 
     							 }
     							 if(rlist[i].ifType==23){
     								 html += "<p><b>接口类型</b>: " + 'ppp' + "</p>"; 
     							 }
     							 if(rlist[i].ifType==24){
     								 html += "<p><b>接口类型</b>: " + 'softwareLoopback' + "</p>"; 
     							 }
     							 if(rlist[i].ifType==25){
     								 html += "<p><b>接口类型</b>: " + 'eon' + "</p>"; 
     							 }
     							 if(rlist[i].ifType==26){
     								 html += "<p><b>接口类型</b>: " + 'ethernet-3Mbit' + "</p>"; 
     							 }
     							 if(rlist[i].ifType==27){
     								 html += "<p><b>接口类型</b>: " + 'nsip' + "</p>"; 
     							 }
     							 if(rlist[i].ifType==28){
     								 html += "<p><b>接口类型</b>: " + 'slip' + "</p>"; 
     							 }
     							 if(rlist[i].ifType==29){
     								 html += "<p><b>接口类型</b>: " + 'ultra' + "</p>"; 
     							 }
     							 if(rlist[i].ifType==30){
     								 html += "<p><b>接口类型</b>: " + 'ds3' + "</p>"; 
     							 }
     							 if(rlist[i].ifType==31){
     								 html += "<p><b>接口类型</b>: " + 'sip' + "</p>"; 
     							 }
     							 if(rlist[i].ifType==32){
     								 html += "<p><b>接口类型</b>: " + 'frame-relay' + "</p>"; 
     							 }
     							 html += "<p><b>最大数据包</b>: " + rlist[i].ifMtu + "</p>"; 
     							 html += "<p><b>带宽</b>: " + rlist[i].ifLastChange + "</p>"; 
     							 html += "<p><b>接收字节数</b>: " + rlist[i].ifInOctets + "</p>"; 
     							 html += "<p><b>接收数据包数</b>: " + rlist[i].ifInUcastPkts + "</p>";   							
     							 html += "<p><b>丢包数</b>: " + rlist[i].ifInUnknownProtos + "</p>"; 
     							 html += "<p><b>发出字节数</b>: " + rlist[i].ifOutOctets + "</p>"; 
     							 html += "<p><b>发出数据包数</b>: " + rlist[i].ifOutUcastPkts + "</p><br>"; 

     					}
     				}
     			});
                 overlayInner.html(html);
                 
                 overlay.classed("njg-hidden", false);
                 overlay.style("display", "block");
                 overlay.style("height", "450px");
                 overlay.style("overflow", "scroll");
                 
                 
                 // 将当前节点设置为“open”样式
                 removeOpenClass();
                 $('.njg-overlay').find('.njg-close').bind('click',function(){
                 	 html = "";
                 	window.clearInterval(timer1);
                 })
                 d3.select(this).classed("njg-open", true);
                 
             	window.clearInterval(timer1);
             	timer1 = window.setInterval(function() {           		
                 var overlay = d3.select(".njg-overlay"),
                     overlayInner = d3.select(".njg-overlay > .njg-inner"),
                     html = "<p><b>ip</b>: " + n.ip + "</p>";
                     if(n.label) { html += "<p><b>label</b>: " + n.label + "</p>"; }
                     if(n.properties) {
                         for(var key in n.properties) {
                             if(!n.properties.hasOwnProperty(key)) { continue; }
                             html += "<p><b>"+key.replace(/_/g, " ")+"</b>: " + n.properties[key] + "</p>";
                     }
                 }
                 if(n.linkCount) { html += "<p><b>links</b>: " + n.linkCount + "</p><br>"; }
                 if(n.local_addresses) {
                     html += "<p><b>local addresses</b>:<br>" + n.local_addresses.join('<br>') + "</p>";
                 }
                 $.ajax({
     				type : "GET",
     				async : false,
     				url : "../equipment/getInterfacesEntry",
     				data:{
     		        	ipAddress : n.ip,
     					community : 'public'
     		        },
     		       error:function(){
    		        	alert("获取失败");
    		        	window.clearInterval(timer1);
    		        	return;
    		        },
     				success : function(r) {
     					var rlist=r.page.list;
     					if(rlist==null){
     						return;
     					}
     						for(var i=0;i<rlist.length;i++){
     							 html += "<p><b>接口名</b>: " + rlist[i].interfaceName + "</p>";
     							 if(rlist[i].ifType==1){
     								 html += "<p><b>接口类型</b>: " + 'other' + "</p>"; 
     							 }
     							 if(rlist[i].ifType==2){
     								 html += "<p><b>接口类型</b>: " + 'regular1822' + "</p>"; 
     							 }
     							 if(rlist[i].ifType==3){
     								 html += "<p><b>接口类型</b>: " + 'hdh1822' + "</p>"; 
     							 }
     							 if(rlist[i].ifType==4){
     								 html += "<p><b>接口类型</b>: " + 'ddn-x25' + "</p>"; 
     							 }
     							 if(rlist[i].ifType==5){
     								 html += "<p><b>接口类型</b>: " + 'rfc877-x25' + "</p>"; 
     							 }						 
     							 if(rlist[i].ifType==6){
     								 html += "<p><b>接口类型</b>: " + 'ethernet-csmacd' + "</p>"; 
     							 }
     							 if(rlist[i].ifType==7){
     								 html += "<p><b>接口类型</b>: " + 'iso88023-csmacd' + "</p>"; 
     							 }
     							 if(rlist[i].ifType==8){
     								 html += "<p><b>接口类型</b>: " + 'iso88024-tokenBus' + "</p>"; 
     							 }
     							 if(rlist[i].ifType==9){
     								 html += "<p><b>接口类型</b>: " + 'iso88025-tokenRing' + "</p>"; 
     							 }
     							 if(rlist[i].ifType==10){
     								 html += "<p><b>接口类型</b>: " + 'iso88026-man' + "</p>"; 
     							 }
     							 if(rlist[i].ifType==11){
     								 html += "<p><b>接口类型</b>: " + ' starLan' + "</p>"; 
     							 }
     							 if(rlist[i].ifType==12){
     								 html += "<p><b>接口类型</b>: " + 'proteon-10Mbit' + "</p>"; 
     							 }
     							 if(rlist[i].ifType==13){
     								 html += "<p><b>接口类型</b>: " + 'proteon-80Mbit' + "</p>"; 
     							 }
     							 if(rlist[i].ifType==14){
     								 html += "<p><b>接口类型</b>: " + 'hyperchannel' + "</p>"; 
     							 }
     							 if(rlist[i].ifType==15){
     								 html += "<p><b>接口类型</b>: " + 'fddi' + "</p>"; 
     							 }
     							 if(rlist[i].ifType==16){
     								 html += "<p><b>接口类型</b>: " + 'lapb' + "</p>"; 
     							 }
     							 if(rlist[i].ifType==17){
     								 html += "<p><b>接口类型</b>: " + 'sdlc' + "</p>"; 
     							 }
     							 if(rlist[i].ifType==18){
     								 html += "<p><b>接口类型</b>: " + 'ds1' + "</p>"; 
     							 }
     							 if(rlist[i].ifType==19){
     								 html += "<p><b>接口类型</b>: " + 'e1' + "</p>"; 
     							 }
     							 if(rlist[i].ifType==20){
     								 html += "<p><b>接口类型</b>: " + 'basicISDN' + "</p>"; 
     							 }
     							 if(rlist[i].ifType==21){
     								 html += "<p><b>接口类型</b>: " + 'primaryISDN' + "</p>"; 
     							 }
     							 if(rlist[i].ifType==22){
     								 html += "<p><b>接口类型</b>: " + 'propPointToPointSerial' + "</p>"; 
     							 }
     							 if(rlist[i].ifType==23){
     								 html += "<p><b>接口类型</b>: " + 'ppp' + "</p>"; 
     							 }
     							 if(rlist[i].ifType==24){
     								 html += "<p><b>接口类型</b>: " + 'softwareLoopback' + "</p>"; 
     							 }
     							 if(rlist[i].ifType==25){
     								 html += "<p><b>接口类型</b>: " + 'eon' + "</p>"; 
     							 }
     							 if(rlist[i].ifType==26){
     								 html += "<p><b>接口类型</b>: " + 'ethernet-3Mbit' + "</p>"; 
     							 }
     							 if(rlist[i].ifType==27){
     								 html += "<p><b>接口类型</b>: " + 'nsip' + "</p>"; 
     							 }
     							 if(rlist[i].ifType==28){
     								 html += "<p><b>接口类型</b>: " + 'slip' + "</p>"; 
     							 }
     							 if(rlist[i].ifType==29){
     								 html += "<p><b>接口类型</b>: " + 'ultra' + "</p>"; 
     							 }
     							 if(rlist[i].ifType==30){
     								 html += "<p><b>接口类型</b>: " + 'ds3' + "</p>"; 
     							 }
     							 if(rlist[i].ifType==31){
     								 html += "<p><b>接口类型</b>: " + 'sip' + "</p>"; 
     							 }
     							 if(rlist[i].ifType==32){
     								 html += "<p><b>接口类型</b>: " + 'frame-relay' + "</p>"; 
     							 }
     							
     							 html += "<p><b>最大数据包</b>: " + rlist[i].ifMtu + "</p>"; 
     							 html += "<p><b>带宽</b>: " + rlist[i].ifLastChange + "</p>"; 
     							 html += "<p><b>接收字节数</b>: " + rlist[i].ifInOctets + "</p>"; 
     							 html += "<p><b>接收数据包数</b>: " + rlist[i].ifInUcastPkts + "</p>";   							
     							 html += "<p><b>丢包数</b>: " + rlist[i].ifInUnknownProtos + "</p>"; 
     							 html += "<p><b>发出字节数</b>: " + rlist[i].ifOutOctets + "</p>"; 
     							 html += "<p><b>发出数据包数</b>: " + rlist[i].ifOutUcastPkts + "</p><br>"; 

     					}
     				}
     			});
                 overlayInner.html(html);
             	}, 3000);
                 overlay.classed("njg-hidden", false);
                 overlay.style("display", "block");
                 overlay.style("height", "450px");
                 overlay.style("overflow", "scroll");
                 // 将当前节点设置为“open”样式
                 removeOpenClass();
                 $('.njg-overlay').find('.njg-close').bind('click',function(){
                 	 html = "";
                 	window.clearInterval(timer1);
                 })
                 d3.select(this).classed("njg-open", true);
             	
             },
             /**
              * @function
              * @name onClickLink
              *
              * 单击某个链路时调用
              */
             onClickLink: function(l) {
        
                 var overlay = d3.select(".njg-overlay"),
                     overlayInner = d3.select(".njg-overlay > .njg-inner"),
                     html = "<p><b>source</b>: " + (l.source.label || l.source.ip) + "</p>";
                     
                 if(l.properties) {
                     for(var key in l.properties) {
                         if(!l.properties.hasOwnProperty(key)) { continue; }
                         html += "<p><b>"+ key.replace(/_/g, " ") +"</b>: " + l.properties[key] + "</p>";
                     }
                 }
                 $.ajax({
  				type : "GET",
  				async : false,
  				url : "../equipment/getLinkInterfacesEntry",
  				data:{
  		        	ipAddress1 : l.source.ip,
  		        	ipAddress2 : l.target.ip,
  					community : 'public'
  		        },
  		      error:function(){
		        	alert("获取失败");
		        	return;
		        },
  				success : function(r) {	
  					var rlist=r.result;
  					for(var i=0;i<rlist.length;i++){
  						if(i==1){
 							html += "<p><b>target</b>: " + (l.target.label || l.target.ip) + "</p>";
 						}
 						 html += "<p><b>接口名</b>: " + rlist[i].interfaceName + "</p>";
 						 if(rlist[i].ifType==1){
 							 html += "<p><b>接口类型</b>: " + 'other' + "</p>"; 
 						 }
 						 if(rlist[i].ifType==2){
 							 html += "<p><b>接口类型</b>: " + 'regular1822' + "</p>"; 
 						 }
 						 if(rlist[i].ifType==3){
 							 html += "<p><b>接口类型</b>: " + 'hdh1822' + "</p>"; 
 						 }
 						 if(rlist[i].ifType==4){
 							 html += "<p><b>接口类型</b>: " + 'ddn-x25' + "</p>"; 
 						 }
 						 if(rlist[i].ifType==5){
 							 html += "<p><b>接口类型</b>: " + 'rfc877-x25' + "</p>"; 
 						 }						 
 						 if(rlist[i].ifType==6){
 							 html += "<p><b>接口类型</b>: " + 'ethernet-csmacd' + "</p>"; 
 						 }
 						 if(rlist[i].ifType==7){
 							 html += "<p><b>接口类型</b>: " + 'iso88023-csmacd' + "</p>"; 
 						 }
 						 if(rlist[i].ifType==8){
 							 html += "<p><b>接口类型</b>: " + 'iso88024-tokenBus' + "</p>"; 
 						 }
 						 if(rlist[i].ifType==9){
 							 html += "<p><b>接口类型</b>: " + 'iso88025-tokenRing' + "</p>"; 
 						 }
 						 if(rlist[i].ifType==10){
 							 html += "<p><b>接口类型</b>: " + 'iso88026-man' + "</p>"; 
 						 }
 						 if(rlist[i].ifType==11){
 							 html += "<p><b>接口类型</b>: " + ' starLan' + "</p>"; 
 						 }
 						 if(rlist[i].ifType==12){
 							 html += "<p><b>接口类型</b>: " + 'proteon-10Mbit' + "</p>"; 
 						 }
 						 if(rlist[i].ifType==13){
 							 html += "<p><b>接口类型</b>: " + 'proteon-80Mbit' + "</p>"; 
 						 }
 						 if(rlist[i].ifType==14){
 							 html += "<p><b>接口类型</b>: " + 'hyperchannel' + "</p>"; 
 						 }
 						 if(rlist[i].ifType==15){
 							 html += "<p><b>接口类型</b>: " + 'fddi' + "</p>"; 
 						 }
 						 if(rlist[i].ifType==16){
 							 html += "<p><b>接口类型</b>: " + 'lapb' + "</p>"; 
 						 }
 						 if(rlist[i].ifType==17){
 							 html += "<p><b>接口类型</b>: " + 'sdlc' + "</p>"; 
 						 }
 						 if(rlist[i].ifType==18){
 							 html += "<p><b>接口类型</b>: " + 'ds1' + "</p>"; 
 						 }
 						 if(rlist[i].ifType==19){
 							 html += "<p><b>接口类型</b>: " + 'e1' + "</p>"; 
 						 }
 						 if(rlist[i].ifType==20){
 							 html += "<p><b>接口类型</b>: " + 'basicISDN' + "</p>"; 
 						 }
 						 if(rlist[i].ifType==21){
 							 html += "<p><b>接口类型</b>: " + 'primaryISDN' + "</p>"; 
 						 }
 						 if(rlist[i].ifType==22){
 							 html += "<p><b>接口类型</b>: " + 'propPointToPointSerial' + "</p>"; 
 						 }
 						 if(rlist[i].ifType==23){
 							 html += "<p><b>接口类型</b>: " + 'ppp' + "</p>"; 
 						 }
 						 if(rlist[i].ifType==24){
 							 html += "<p><b>接口类型</b>: " + 'softwareLoopback' + "</p>"; 
 						 }
 						 if(rlist[i].ifType==25){
 							 html += "<p><b>接口类型</b>: " + 'eon' + "</p>"; 
 						 }
 						 if(rlist[i].ifType==26){
 							 html += "<p><b>接口类型</b>: " + 'ethernet-3Mbit' + "</p>"; 
 						 }
 						 if(rlist[i].ifType==27){
 							 html += "<p><b>接口类型</b>: " + 'nsip' + "</p>"; 
 						 }
 						 if(rlist[i].ifType==28){
 							 html += "<p><b>接口类型</b>: " + 'slip' + "</p>"; 
 						 }
 						 if(rlist[i].ifType==29){
 							 html += "<p><b>接口类型</b>: " + 'ultra' + "</p>"; 
 						 }
 						 if(rlist[i].ifType==30){
 							 html += "<p><b>接口类型</b>: " + 'ds3' + "</p>"; 
 						 }
 						 if(rlist[i].ifType==31){
 							 html += "<p><b>接口类型</b>: " + 'sip' + "</p>"; 
 						 }
 						 if(rlist[i].ifType==32){
 							 html += "<p><b>接口类型</b>: " + 'frame-relay' + "</p>"; 
 						 }
 						 html += "<p><b>最大数据包</b>: " + rlist[i].ifMtu + "</p>"; 
 						 html += "<p><b>带宽</b>: " + rlist[i].ifLastChange + "</p>"; 
 						 html += "<p><b>接收字节数</b>: " + rlist[i].ifInOctets + "</p>"; 
 						 html += "<p><b>接收数据包数</b>: " + rlist[i].ifInUcastPkts + "</p>";   							
 						 html += "<p><b>丢包数</b>: " + rlist[i].ifInUnknownProtos + "</p>"; 
 						 html += "<p><b>发出字节数</b>: " + rlist[i].ifOutOctets + "</p>"; 
 						 html += "<p><b>发出数据包数</b>: " + rlist[i].ifOutUcastPkts + "</p><br>"; 

  						}
  					}
  		        });
                 overlayInner.html(html);
                 overlay.classed("njg-hidden", false);
                 overlay.style("display", "block");
                 overlay.style("height", "450px");
                 overlay.style("overflow", "scroll");
                 // 将当前连接设置为“open”样式
                 removeOpenClass(); 
                 $('.njg-overlay').find('.njg-close').bind('click',function(){
                 	 html = "";
                 	window.clearInterval(timer1);
                 })
                 d3.select(this).classed("njg-open", true);
                 window.clearInterval(timer1);
              	timer1 = window.setInterval(function() {           		
              		var overlay = d3.select(".njg-overlay"),
                     overlayInner = d3.select(".njg-overlay > .njg-inner"),
                     html = "<p><b>source</b>: " + (l.source.label || l.source.ip) + "</p>";
                     
                     if(l.properties) {
                     	for(var key in l.properties) {
                     		if(!l.properties.hasOwnProperty(key)) { continue; }
                     		html += "<p><b>"+ key.replace(/_/g, " ") +"</b>: " + l.properties[key] + "</p>";
                     	}
                     }
                     $.ajax({
          				type : "GET",
          				async : false,
          				url : "../equipment/getLinkInterfacesEntry",
          				data:{
          		        	ipAddress1 : l.source.ip,
          		        	ipAddress2 : l.target.ip,
          					community : 'public'
          		        },
          		      error:function(){
       		        	alert("获取失败");
       		        	window.clearInterval(timer1);
       		        	return;
       		        },
          				success : function(r) {	
          					var rlist=r.result;
          					for(var i=0;i<rlist.length;i++){
          						if(i==1){
         							html += "<p><b>target</b>: " + (l.target.label || l.target.ip) + "</p>";
         						}
         						 html += "<p><b>接口名</b>: " + rlist[i].interfaceName + "</p>";
         						 if(rlist[i].ifType==1){
         							 html += "<p><b>接口类型</b>: " + 'other' + "</p>"; 
         						 }
         						 if(rlist[i].ifType==2){
         							 html += "<p><b>接口类型</b>: " + 'regular1822' + "</p>"; 
         						 }
         						 if(rlist[i].ifType==3){
         							 html += "<p><b>接口类型</b>: " + 'hdh1822' + "</p>"; 
         						 }
         						 if(rlist[i].ifType==4){
         							 html += "<p><b>接口类型</b>: " + 'ddn-x25' + "</p>"; 
         						 }
         						 if(rlist[i].ifType==5){
         							 html += "<p><b>接口类型</b>: " + 'rfc877-x25' + "</p>"; 
         						 }						 
         						 if(rlist[i].ifType==6){
         							 html += "<p><b>接口类型</b>: " + 'ethernet-csmacd' + "</p>"; 
         						 }
         						 if(rlist[i].ifType==7){
         							 html += "<p><b>接口类型</b>: " + 'iso88023-csmacd' + "</p>"; 
         						 }
         						 if(rlist[i].ifType==8){
         							 html += "<p><b>接口类型</b>: " + 'iso88024-tokenBus' + "</p>"; 
         						 }
         						 if(rlist[i].ifType==9){
         							 html += "<p><b>接口类型</b>: " + 'iso88025-tokenRing' + "</p>"; 
         						 }
         						 if(rlist[i].ifType==10){
         							 html += "<p><b>接口类型</b>: " + 'iso88026-man' + "</p>"; 
         						 }
         						 if(rlist[i].ifType==11){
         							 html += "<p><b>接口类型</b>: " + ' starLan' + "</p>"; 
         						 }
         						 if(rlist[i].ifType==12){
         							 html += "<p><b>接口类型</b>: " + 'proteon-10Mbit' + "</p>"; 
         						 }
         						 if(rlist[i].ifType==13){
         							 html += "<p><b>接口类型</b>: " + 'proteon-80Mbit' + "</p>"; 
         						 }
         						 if(rlist[i].ifType==14){
         							 html += "<p><b>接口类型</b>: " + 'hyperchannel' + "</p>"; 
         						 }
         						 if(rlist[i].ifType==15){
         							 html += "<p><b>接口类型</b>: " + 'fddi' + "</p>"; 
         						 }
         						 if(rlist[i].ifType==16){
         							 html += "<p><b>接口类型</b>: " + 'lapb' + "</p>"; 
         						 }
         						 if(rlist[i].ifType==17){
         							 html += "<p><b>接口类型</b>: " + 'sdlc' + "</p>"; 
         						 }
         						 if(rlist[i].ifType==18){
         							 html += "<p><b>接口类型</b>: " + 'ds1' + "</p>"; 
         						 }
         						 if(rlist[i].ifType==19){
         							 html += "<p><b>接口类型</b>: " + 'e1' + "</p>"; 
         						 }
         						 if(rlist[i].ifType==20){
         							 html += "<p><b>接口类型</b>: " + 'basicISDN' + "</p>"; 
         						 }
         						 if(rlist[i].ifType==21){
         							 html += "<p><b>接口类型</b>: " + 'primaryISDN' + "</p>"; 
         						 }
         						 if(rlist[i].ifType==22){
         							 html += "<p><b>接口类型</b>: " + 'propPointToPointSerial' + "</p>"; 
         						 }
         						 if(rlist[i].ifType==23){
         							 html += "<p><b>接口类型</b>: " + 'ppp' + "</p>"; 
         						 }
         						 if(rlist[i].ifType==24){
         							 html += "<p><b>接口类型</b>: " + 'softwareLoopback' + "</p>"; 
         						 }
         						 if(rlist[i].ifType==25){
         							 html += "<p><b>接口类型</b>: " + 'eon' + "</p>"; 
         						 }
         						 if(rlist[i].ifType==26){
         							 html += "<p><b>接口类型</b>: " + 'ethernet-3Mbit' + "</p>"; 
         						 }
         						 if(rlist[i].ifType==27){
         							 html += "<p><b>接口类型</b>: " + 'nsip' + "</p>"; 
         						 }
         						 if(rlist[i].ifType==28){
         							 html += "<p><b>接口类型</b>: " + 'slip' + "</p>"; 
         						 }
         						 if(rlist[i].ifType==29){
         							 html += "<p><b>接口类型</b>: " + 'ultra' + "</p>"; 
         						 }
         						 if(rlist[i].ifType==30){
         							 html += "<p><b>接口类型</b>: " + 'ds3' + "</p>"; 
         						 }
         						 if(rlist[i].ifType==31){
         							 html += "<p><b>接口类型</b>: " + 'sip' + "</p>"; 
         						 }
         						 if(rlist[i].ifType==32){
         							 html += "<p><b>接口类型</b>: " + 'frame-relay' + "</p>"; 
         						 }
         						 html += "<p><b>最大数据包</b>: " + rlist[i].ifMtu + "</p>"; 
         						 html += "<p><b>带宽</b>: " + rlist[i].ifLastChange + "</p>"; 
         						 html += "<p><b>接收字节数</b>: " + rlist[i].ifInOctets + "</p>"; 
         						 html += "<p><b>接收数据包数</b>: " + rlist[i].ifInUcastPkts + "</p>";   							
         						 html += "<p><b>丢包数</b>: " + rlist[i].ifInUnknownProtos + "</p>"; 
         						 html += "<p><b>发出字节数</b>: " + rlist[i].ifOutOctets + "</p>"; 
         						 html += "<p><b>发出数据包数</b>: " + rlist[i].ifOutUcastPkts + "</p><br>"; 

          						}
          					}
          		        });
                     	overlayInner.html(html);
              			}, 3000);
              	 overlay.classed("njg-hidden", false);
                  overlay.style("display", "block");
                  overlay.style("height", "450px");
                  overlay.style("overflow", "scroll");
                  // 将当前节点设置为“open”样式
                  removeOpenClass();
                  $('.njg-overlay').find('.njg-close').bind('click',function(){
                  	 html = "";
                  	window.clearInterval(timer1);
                  })
                  d3.select(this).classed("njg-open", true);
              	
              },
         }, opts);

         // init callback
         opts.onInit(url, opts);

         if(!opts.animationAtStart) {//判断是否加载动画，我调节成false了，所以直接在这边修改属性
             opts.linkStrength = 0;//强度指数
             opts.friction = 0.3;//摩擦系数
             opts.gravity = 0;//重力系数
         }
         if(opts.el == "body") {//设置body大小
             var body = d3.select(opts.el),
                 rect = body.node().getBoundingClientRect();
             if (d3._pxToNumber(d3.select("body").style("height")) < 60) {
                 body.style("height", d3._windowHeight() - rect.top - rect.bottom + "px");
             }
         }
         var el = d3.select(opts.el).style("position", "relative"),
             width = d3._pxToNumber(el.style('width')),
             height = d3._pxToNumber(el.style('height')),//调节画布的大小
             force = d3.layout.force()
                       .charge(opts.charge)
                       .linkStrength(opts.linkStrength)
                       .linkDistance(opts.linkDistanceFunc)
                       .friction(opts.friction)
                       .chargeDistance(opts.chargeDistance)
                       .theta(opts.theta)
                       .gravity(opts.gravity)
                       // 宽度很容易获得，如果高度是0，取body的高度。
                       .size([width, height]),
             zoom = d3.behavior.zoom().scaleExtent(opts.scaleExtent),
             // panner is 元素允许缩放和平移   要加样式的话，直接在下面html后面额外加入，不然导出的图片是没有你加入的样式的
             panner = el.append("svg")
                        .attr("width", width)
                        .attr("height", height)
                        .call(zoom.on("zoom", opts.redraw))
                        .html("<style type='text/css'>.njg-overlay{background: #fbfbfb;border-radius: 2px;border: 1px solid #ccc;color: #6d6357;font-family: Arial, sans-serif;font-family: sans-serif;font-size: 14px;line-height: 20px;height: auto;max-width: 400px;min-width: 200px;padding: 0 15px;right: 10px;top: 10px;width: auto;}.njg-metadata{background: #fbfbfb;border-radius: 2px;border: 1px solid #ccc;color: #6d6357;display: none;font-family: Arial, sans-serif;font-family: sans-serif;font-size: 14px;height: auto;left: 10px;max-width: 500px;min-width: 200px;padding: 0 15px;top: 10px;width: auto;}.njg-node{stroke-opacity: 0.5;stroke-width: 7px;stroke: #fff;}.njg-node:hover,.njg-node.njg-open {stroke: rgba(0, 0, 0, 0.2);}.njg-link{cursor: pointer;stroke: #999;stroke-width: 2;stroke-opacity: 0.25;}.njg-link:hover,.njg-link.njg-open{stroke-width: 4 !important;stroke-opacity: 0.5;}.njg-hidden {    display: none !important;    visibility: hidden !important;}.njg-tooltip{    font-family: sans-serif;    font-size: 10px;    fill: #000;    opacity: 0.5;    text-anchor: middle;}.njg-overlay{    display: none;    position: absolute;    z-index: 11;}.njg-close{    cursor: pointer;    position: absolute;    right: 10px;    top: 10px;}.njg-close:before { content: '\\2716'; }.njg-metadata{    display: none;    position: absolute;    z-index: 12;}.njg-node{ cursor: pointer }.njg-link{ cursor: pointer }#njg-select-group {    text-align: center;    box-shadow: 0 0 10px #ccc;    position: fixed;    left: 50%;    top: 50%;    width: 50%;    margin-top: -7.5em;    margin-left: -25%;    padding: 5em 2em;}#njg-select-group select {    font-size: 2em;    padding: 10px 15px;    width: 50%;    cursor: pointer;}#njg-select-group option {    padding: 0.5em;}#njg-select-group option[value=''] {    color: #aaa;}</style>")
                        .append("g")
                        .style("position", "absolute"),
             svg = d3.select(opts.el + " svg"),
             drag = force.drag(),
             //overlay节点和线的框（右）   metadata网络框（左）
             overlay = d3.select(opts.el).append("div").attr("class", "njg-overlay"),
             closeOverlay = overlay.append("a").attr("class", "njg-close"),
             overlayInner = overlay.append("div").attr("class", "njg-inner"),
             metadata = d3.select(opts.el).append("div").attr("class", "njg-metadata"),
             metadataInner = metadata.append("div").attr("class", "njg-inner"),
             closeMetadata = metadata.append("a").attr("class", "njg-close"),
             // 未分组网络的容器
             str = [],
             selected = [],
             /**
              * @function
              * @name removeOpenClass
              *
              * 从节点和连接中删除“open”样式
              */
             removeOpenClass = function () {
                 d3.selectAll("svg .njg-open").classed("njg-open", false);
             };
             processJson = function(graph) {
                 /**
                  * Init netJsonGraph
                  */
                 init = function(url, opts) {
                     d3.netJsonGraph(url, opts);
                 };
                 /**
                  * 删除所有实例
                  */
                 destroy = function() {
                     force.stop();
                     d3.select("#selectGroup").remove();
                     d3.select(".njg-overlay").remove();
                     d3.select(".njg-metadata").remove();
                     overlay.remove();
                     overlayInner.remove();
                     metadata.remove();
                     svg.remove();
                     node.remove();
                     link.remove();
                     nodes = [];
                     links = [];
                 };
                 /**
                  * Destroy and e-init all instances
                  * @return {[type]} [description]
                  */
                 reInit = function() {
                     destroy();
                     init(url, opts);
                 };

                 var data = opts.prepareData(graph),
                     links = data.links,
                     nodes = data.nodes;

                 // 在拖动时禁用一些转换
                 drag.on('dragstart', function(n){
                     d3.event.sourceEvent.stopPropagation();
                     zoom.on('zoom', null);
                 })
                 // 停止拖动时重新启用转换
                 .on('dragend', function(n){
                     zoom.on('zoom', opts.redraw);
                 })
                 .on("drag", function(d) {
                     // 避免平移冲突
                     d3.select(this).attr("x", d.x = d3.event.x).attr("y", d.y = d3.event.y);
                 });

                 force.nodes(nodes).links(links).start();

                 var link = panner.selectAll(".link")
                                  .data(links)
                                  .enter().append("line")
                                  .attr("class", function (link) {
                                      var baseClass = "njg-link",
                                          addClass = null;
                                          value = link.properties && link.properties[opts.linkClassProperty];
                                      if (opts.linkClassProperty && value) {
                                          // if value is string use that as class
                                          if (typeof(value) === "string") {
                                              addClass = value;
                                          }
                                          else if (typeof(value) === "number") {
                                              addClass = opts.linkClassProperty + value;
                                          }
                                          else if (value === true) {
                                              addClass = opts.linkClassProperty;
                                          }
                                          return baseClass + " " + addClass;
                                      }
                                      return baseClass;
                                  })
                                  .on("click", opts.onClickLink),
                     groups = panner.selectAll(".node")
                                    .data(nodes)
                                    .enter()
                                    .append("g");
                 node = groups.append("image")
                 .attr('xlink:href',function(node){                    			  
      			 if(node.type==2){
                		 return "data:image/jpg;base64,/9j/4AAQSkZJRgABAQEAHAAcAAD/2wBDAAIBAQIBAQICAgICAgICAwUDAwMDAwYEBAMFBwYHBwcGBwcICQsJCAgKCAcHCg0KCgsMDAwMBwkODw0MDgsMDAz/2wBDAQICAgMDAwYDAwYMCAcIDAwMDAwMDAwMDAwMDAwMDAwMDAwMDAwMDAwMDAwMDAwMDAwMDAwMDAwMDAwMDAwMDAz/wAARCAAgACADASIAAhEBAxEB/8QAHwAAAQUBAQEBAQEAAAAAAAAAAAECAwQFBgcICQoL/8QAtRAAAgEDAwIEAwUFBAQAAAF9AQIDAAQRBRIhMUEGE1FhByJxFDKBkaEII0KxwRVS0fAkM2JyggkKFhcYGRolJicoKSo0NTY3ODk6Q0RFRkdISUpTVFVWV1hZWmNkZWZnaGlqc3R1dnd4eXqDhIWGh4iJipKTlJWWl5iZmqKjpKWmp6ipqrKztLW2t7i5usLDxMXGx8jJytLT1NXW19jZ2uHi4+Tl5ufo6erx8vP09fb3+Pn6/8QAHwEAAwEBAQEBAQEBAQAAAAAAAAECAwQFBgcICQoL/8QAtREAAgECBAQDBAcFBAQAAQJ3AAECAxEEBSExBhJBUQdhcRMiMoEIFEKRobHBCSMzUvAVYnLRChYkNOEl8RcYGRomJygpKjU2Nzg5OkNERUZHSElKU1RVVldYWVpjZGVmZ2hpanN0dXZ3eHl6goOEhYaHiImKkpOUlZaXmJmaoqOkpaanqKmqsrO0tba3uLm6wsPExcbHyMnK0tPU1dbX2Nna4uPk5ebn6Onq8vP09fb3+Pn6/9oADAMBAAIRAxEAPwD9sP2tv2yPhl+wn8G7n4gfFnxdp/g3wnbXMVn9suY5Z3nnlJCQwwwq800hAZtkaMwRHcgKjMPkz/iKN/YT/wCi5f8AlmeIP/kGj/gvp/zZX/2dV4G/9vq+/wCgD4A/4ijf2E/+i5f+WZ4g/wDkGvrP9kn9sj4Z/t2fBu2+IHwm8Xaf4y8J3VzLZ/bLaOWF4J4iA8M0MypNDIAVbZIisUdHAKurH0yvgD/ggX/zep/2dV45/wDbGgDgv+C7n7THw6+JfgT4Y/8ACB/F34Aa98R/2e/jRofjzUPBOr/FLRvD99qR0qS5iuNO825m2W1wJJRu87bsEcnDOFjbz3/iJw+Iv/RAP2f/APxL7wR/8VX1V8cv+Dd39jf9pD4v+IvHnjD4L2moeKvFl9JqerXdt4j1jT0vLmQ5kmMNvdxxK7tlmKoNzMzHLMScPwt/wbOfsT+B/ENpq2i/B/UNH1awkEtre2PjvxHb3Fs46MkiX4ZT7gg0AfOP/ETh8Rf+iAfs/wD/AIl94I/+Kr0L/ghH+0x8Ovhn4E+J3/CefF74AaF8R/2hPjRrnjzT/BOj/FLRvEF9pp1WS2it9O822m2XNwZIjt8ndvEkfCuWjX0zxT/wbOfsT+OPEN3q2tfB/UNY1a/kMt1e33jvxHcXFy56s8j35Zj7kk1ufA3/AIN3f2N/2b/i/wCHfHng/wCC9pp/irwnfR6npN3c+I9Y1BLO5jOY5hDcXckTOjYZSyHayqwwyggA/9k="; 
                	 }else if(node.type==1){
                		 return "data:image/jpg;base64,/9j/4AAQSkZJRgABAQEASABIAAD/2wBDAAIBAQIBAQICAgICAgICAwUDAwMDAwYEBAMFBwYHBwcGBwcICQsJCAgKCAcHCg0KCgsMDAwMBwkODw0MDgsMDAz/2wBDAQICAgMDAwYDAwYMCAcIDAwMDAwMDAwMDAwMDAwMDAwMDAwMDAwMDAwMDAwMDAwMDAwMDAwMDAwMDAwMDAwMDAz/wAARCAAgACADASIAAhEBAxEB/8QAHwAAAQUBAQEBAQEAAAAAAAAAAAECAwQFBgcICQoL/8QAtRAAAgEDAwIEAwUFBAQAAAF9AQIDAAQRBRIhMUEGE1FhByJxFDKBkaEII0KxwRVS0fAkM2JyggkKFhcYGRolJicoKSo0NTY3ODk6Q0RFRkdISUpTVFVWV1hZWmNkZWZnaGlqc3R1dnd4eXqDhIWGh4iJipKTlJWWl5iZmqKjpKWmp6ipqrKztLW2t7i5usLDxMXGx8jJytLT1NXW19jZ2uHi4+Tl5ufo6erx8vP09fb3+Pn6/8QAHwEAAwEBAQEBAQEBAQAAAAAAAAECAwQFBgcICQoL/8QAtREAAgECBAQDBAcFBAQAAQJ3AAECAxEEBSExBhJBUQdhcRMiMoEIFEKRobHBCSMzUvAVYnLRChYkNOEl8RcYGRomJygpKjU2Nzg5OkNERUZHSElKU1RVVldYWVpjZGVmZ2hpanN0dXZ3eHl6goOEhYaHiImKkpOUlZaXmJmaoqOkpaanqKmqsrO0tba3uLm6wsPExcbHyMnK0tPU1dbX2Nna4uPk5ebn6Onq8vP09fb3+Pn6/9oADAMBAAIRAxEAPwD9Uv8Agod+0n458J/F/wCBfwV+FurWvhrx98bNeu5H8QXmnrfW+h6HpMKXmpyrGwZGuJFaC3iVxtJumO5CoYc7+zb/AMFf9E/aQ+GHhHUrfw7DoPiz42XXin/hS/hzUL+6DePbHRY2f7VdXC2Rj0rzgu4pL5uyNkZGmYlB03/BQr9lzxx8RPin8EfjJ8KrfS9U+JXwR1+5aDR9Wv2srDXdF1SFbPVbVpVB8qURrDPFKVcK1tjy3LAV8OfET9hvWP2IP+Ci/wCwX8I/CPxBlmsbW8+MD+A7++0gXEng2zu9BjltLSRWmP24Ws0jsGdo/MTbHhAoNAHv/wCx7/wVP8VfHX9p74KapqiyQfC79qbwtqaeGtAmsNuoeAvFPh/zBq+nzziNDcW8ojuik0gDF7PCxxq3zfoVX5//ALIf/BKPxB8BP2nPg1b6ldQzfCv9l3wpqMXg7UTqEk2reMvE2u7jrOq3sbEiCNQ9wscGZDuuy4lIHlr+gFAHyR+1r/wXM/ZX/YZ+NF98O/id8WLPw74z023hubzTYdE1PUns1mQSRCV7S2lRHaMq+xmD7HRsYdSfjf8AaH/4LR/sK/Hn9uv9nX41f8NN/wBk/wDCgv8AhJf+JN/wrvxBP/bv9safHZf6/wCzL5Hk7N/+rk352/JjdXhn/Bbf/glT+3J8av23v2ipvgh4Vs/EXwX/AGiLfwvNr8UOr6Jbvfto1pbpbxS/bpI7iF4rmCSTMBCOkiBmb5kX89P+IXL9uv8A6Ib/AOXn4f8A/k6gD9/v+Io39hT/AKLl/wCWZ4g/+Qa9I/ZJ/wCC5n7K37c/xosvh38MfizZ+IvGepW81zZ6bNomp6a92sKGSURPd20SO6xhn2KxfYjtjCMR+XH7IP7IH7dX7Kf/AAx5/wAYef29/wAMnf8ACaf81X8P2v8AwlX/AAkXm/8ATR/sv2fzf+m3m7f+WeePSv8AgkX/AME7v2qfhx8av2MdF+J/wRs/hz4N/ZRt/Hc134lm8a6Zqj+I28QJN5UUVnaO7wvFJKoyzMjortuQhUYA/9k="; 
                	 }else{
                		 return "data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAABoAAAAbCAIAAADtdAg8AAABeUlEQVRIDe2VwaqCQBSGb3o3CiEYIVLQMlxkix4geofeoyfzGeI+gG4lVwqpgRZFQavo/tyB8TSO3oiWuRj+Oec7P6dJz3Tu9/vX+55vwep0Ol2vVyEo3WqaZhiGkHqwS5LE9/39fi9A0m2v15vNZqPRiGYru9vt5nneZrOhabQwHA5ZZLvdCo3HcbxarVRV5SWV3fF4FLwA6bruOA6jD4eDYAceVWhTYleWJYuapkmJMAx5HCmmcSBwh0YVhavuGId1MpnM53O+lYqfv6eekth1u93BYFBHaQQM3XKtcPUW8bF7/Rg/Z/f62Um+isvlstvt2i3BSAGJXRRFTTS3yLKMayokdkApjZHX7/eLohCmE3Xh+v8XZTqdLpdLrLymRVR26ELKua47Ho+xSrNCVfVjLcvCdGRDkVYGQaAoClYaZBo8qmi8Qy/G9XqNq+d8PlOiSWPk4epZLBYUeLBDIk3TPM8p0aRt265PWdGuqfjJePVXPFnQjv0CkymKbVbpYEUAAAAASUVORK5CYII="; 
                	 }})
                 .on("dblclick",opts.onClickNode)
                 .call(drag);

                     var labels = groups.append('text')//node的名字
                                        .text(function(n){ return n.label || n.id })
                                        .attr('dx', opts.labelDx)
                                        .attr('dy', opts.labelDy)
                                        .attr('class', 'njg-tooltip');

                 // 关闭覆盖
                 closeOverlay.on("click", function() {
                     removeOpenClass();
                     overlay.classed("njg-hidden", true);
                 });
                 // 关闭元数据面板
                 closeMetadata.on("click", function() {
                     // 重新初始化页面
                     if(graph.type === "NetworkCollection") {
                         reInit();
                     }
                     else {
                         removeOpenClass();
                         metadata.classed("njg-hidden", true);
                     }
                 });
                 // default style
                 
                 // into something else
                 if(opts.defaultStyle) {
                     var colors = d3.scale.category20c();
                     node.style({
                         "fill": function(d){ return colors(d.linkCount); },
                         "cursor": "pointer"
                     });
                 }
                 // Metadata style
                 if(opts.metadata) {
                     metadata.attr("class", "njg-metadata").style("display", "block");
                 }

                 var attrs = ["protocol",
                              "version",
                              "revision",
                              "metric",
                              "router_ip",
                              "topology_ip"],
                     html = "";
                 if(graph.label) {
                     html += "<h3>" + graph.label + "</h3>";
                 }
                 for(var i in attrs) {
                     var attr = attrs[i];
                     if(graph[attr]) {
                         html += "<p><b>" + attr + "</b>: <span>" + graph[attr] + "</span></p>";
                     }
                 }
                 // Add nodes and links count
                 html += "<p><b>nodes</b>: <span>" + graph.nodes.length + "</span></p>";
                 html += "<p><b>links</b>: <span>" + graph.links.length + "</span></p>";
                 metadataInner.html(html);
                 metadata.classed("njg-hidden", false);

                 // onLoad callback
                 opts.onLoad(url, opts);

                 force.on("tick", function() {
                     link.attr("x1", function(d) {
                         return d.source.x;
                     })
                     .attr("y1", function(d) {
                         return d.source.y;
                     })
                     .attr("x2", function(d) {
                         return d.target.x;
                     })
                     .attr("y2", function(d) {
                         return d.target.y;
                     });

                     node.attr("x", function(d) {
                         return d.x-15;
                     })
                     .attr("y", function(d) {
                         return d.y-10;
                     });

                     labels.attr("transform", function(d) {
                         return "translate(" + d.x + "," + d.y + ")";
                     });
                 })
                 .on("end", function(){
                     force.stop();
                     // onEnd callback
                     opts.onEnd(url, opts);
                 });

                 return force;
             };

         if(typeof(url) === "object") {
             processJson(url);
         }
         else {
             /**
             * 解析提供的JSON文件
             * 并且调用 processJson() function
             *
             * @param  {string}     url         The provided json file
             * @param  {function}   error
             */
             d3.json(url, function(error, graph) {
                 if(error) { throw error; }
                 /**
                 * 检查如果JSON包含 NetworkCollection
                 */
                 if(graph.type === "NetworkCollection") {
                     var selectGroup = body.append("div").attr("id", "njg-select-group"),
                         select = selectGroup.append("select")
                                             .attr("id", "select");
                         str = graph;
                     select.append("option")
                           .attr({
                               "value": "",
                               "selected": "selected",
                               "name": "default",
                               "disabled": "disabled"
                           })
                           .html("Choose the network to display");
                     graph.collection.forEach(function(structure) {
                         select.append("option").attr("value", structure.type).html(structure.type);
                         // 收集每个网络JSON结构
                         selected[structure.type] = structure;
                     });
                     select.on("change", function() {
                         selectGroup.attr("class", "njg-hidden");
                         // 调用选定的JSON结构
                         processJson(selected[this.options[this.selectedIndex].value]);
                     });
                 }
                 else {
                     processJson(graph);
                 }
             });
         }
      };
   // TODO: probably change defaultStyle
    d3.fixNetJsonGraph = function(url, opts) {
        /**
         * Default options
         *
         * @param  {string}     el                  "body"      The container element                                  el: "body" [description]
         * @param  {bool}       metadata            true        Display NetJSON metadata at startup?
         * @param  {bool}       defaultStyle        true        Use css style?
         * @param  {bool}       animationAtStart    false       Animate nodes or not on load
         * @param  {array}      scaleExtent         [0.25, 5]   The zoom scale's allowed range. @see {@link https://github.com/mbostock/d3/wiki/Zoom-Behavior#scaleExtent}
         * @param  {int}        charge              -130        The charge strength to the specified value. @see {@link https://github.com/mbostock/d3/wiki/Force-Layout#charge}
         * @param  {int}        linkDistance        50          The target distance between linked nodes to the specified value. @see {@link https://github.com/mbostock/d3/wiki/Force-Layout#linkDistance}
         * @param  {float}      linkStrength        0.2         The strength (rigidity) of links to the specified value in the range. @see {@link https://github.com/mbostock/d3/wiki/Force-Layout#linkStrength}
         * @param  {float}      friction            0.9         The friction coefficient to the specified value. @see {@link https://github.com/mbostock/d3/wiki/Force-Layout#friction}
         * @param  {string}     chargeDistance      Infinity    The maximum distance over which charge forces are applied. @see {@link https://github.com/mbostock/d3/wiki/Force-Layout#chargeDistance}
         * @param  {float}      theta               0.8         The Barnes–Hut approximation criterion to the specified value. @see {@link https://github.com/mbostock/d3/wiki/Force-Layout#theta}
         * @param  {float}      gravity             0.1         The gravitational strength to the specified numerical value. @see {@link https://github.com/mbostock/d3/wiki/Force-Layout#gravity}
         * @param  {int}        circleRadius        8           The radius of circles (nodes) in pixel
         * @param  {string}     labelDx             "0"         SVG dx (distance on x axis) attribute of node labels in graph
         * @param  {string}     labelDy             "-1.3em"    SVG dy (distance on y axis) attribute of node labels in graph
         * @param  {function}   onInit                          Callback function executed on initialization
         * @param  {function}   onLoad                          Callback function executed after data has been loaded
         * @param  {function}   onEnd                           Callback function executed when initial animation is complete
         * @param  {function}   linkDistanceFunc                By default high density areas have longer links
         * @param  {function}   redraw                          Called when panning and zooming
         * @param  {function}   prepareData                     Used to convert NetJSON NetworkGraph to the javascript data
         * @param  {function}   onClickNode                     Called when a node is clicked
         * @param  {function}   onClickLink                     Called when a link is clicked
         */
        opts = d3._extend({
            el: "body",
            metadata: false,//启动时显示元数据
            defaultStyle: false,
            animationAtStart: false,//动画节点是否加载
            scaleExtent: [0.25, 5],//缩放范围
            charge: 0,//电荷强度达到指定值
            linkDistance: 150,//两个节点间的距离
            linkStrength: 1,//链接到范围内的强度（刚度）指数。
            friction: 0.9,  // d3 default 摩擦系数指数。
            chargeDistance: Infinity,  // d3 default 施加电荷力的最大距离。
            theta: 0.8,  // d3 default 巴尼斯-小屋近似准则的指数。
            gravity: 0.1, //重力指数
            circleRadius: 8,//半径
            labelDx: "0",//图中节点标号的x（x轴距离）属性
            labelDy: "-1.3em",//图中节点标号的y（y轴距离）属性
            nodeClassProperty: null,
            linkClassProperty: null,
            /**
             * @function
             * @name onInit
             *
             * Callback function executed on initialization
             * @param  {string|object}  url     The netJson remote url or object
             * @param  {object}         opts    The object of passed arguments
             * @return {function}
             */
            onInit: function(url, opts) {},
            /**
             * @function
             * @name onLoad
             *
             * Callback function executed after data has been loaded
             * @param  {string|object}  url     The netJson remote url or object
             * @param  {object}         opts    The object of passed arguments
             * @return {function}
             */
            onLoad: function(url, opts) {},
            /**
             * @function
             * @name onEnd
             *
             * Callback function executed when initial animation is complete
             * @param  {string|object}  url     The netJson remote url or object
             * @param  {object}         opts    The object of passed arguments
             * @return {function}
             */
            onEnd: function(url, opts) {},
            /**
             * @function
             * @name linkDistanceFunc
             *
             * 默认情况下，高密度区域有更长的链接
             */
            linkDistanceFunc: function(d){
                var val = opts.linkDistance;
                if(d.source.linkCount >= 4 && d.target.linkCount >= 4) {
                    return val * 2;
                }
                return val;
            },
            /**
             * @function
             * @name redraw
             *
             * 调用缩放和平移
             */
            redraw: function() {
                panner.attr("transform",
                    "translate(" + d3.event.translate + ") " +
                    "scale(" + d3.event.scale + ")"
                );
            },
            /**
             * @function
             * @name prepareData
             *
             * Convert NetJSON NetworkGraph to the data structure consumed by d3
             * 运用d3将netjson网络图转换为数据结构
             * @param graph {object}
             */
            prepareData: function(graph) {
                var nodesMap = {},
                    nodes = graph.nodes.slice(), // copy
                    links = graph.links.slice(), // copy
                    nodes_length = graph.nodes.length,
                    links_length = graph.links.length;

                for(var i = 0; i < nodes_length; i++) {
                    // 计算节点有多少连接
                    nodes[i].linkCount = 0;
                    nodesMap[nodes[i].ip] = i;
                }
                for(var c = 0; c < links_length; c++) {
                    var sourceIndex = nodesMap[links[c].source.ip],
                    targetIndex = nodesMap[links[c].target.ip];
                    // 确保源和目标存在
                    if(!nodes[sourceIndex]) { throw("source '" + links[c].source.ip + "' not found"); }
                    if(!nodes[targetIndex]) { throw("target '" + links[c].target.ip + "' not found"); }
                    links[c].source = nodesMap[links[c].source.ip];
                    links[c].target = nodesMap[links[c].target.ip];
                    // 将链接计数添加到两端
                    nodes[sourceIndex].linkCount++;
                    nodes[targetIndex].linkCount++;
                }
                return { "nodes": nodes, "links": links };
            },
            /**
             * @function
             * @name onClickNode
             *
             * 单击某个节点时调用
             */
            onClickNode: function(n) {
            	 
                var overlay = d3.select(".njg-overlay"),
                    overlayInner = d3.select(".njg-overlay > .njg-inner"),
                    html = "<p><b>ip</b>: " + n.ip + "</p>";
                    if(n.label) { html += "<p><b>label</b>: " + n.label + "</p>"; }
                    if(n.properties) {
                        for(var key in n.properties) {
                            if(!n.properties.hasOwnProperty(key)) { continue; }
                            html += "<p><b>"+key.replace(/_/g, " ")+"</b>: " + n.properties[key] + "</p>";
                    }
                }
                if(n.linkCount) { html += "<p><b>links</b>: " + n.linkCount + "</p><br>"; }
                if(n.local_addresses) {
                    html += "<p><b>local addresses</b>:<br>" + n.local_addresses.join('<br>') + "</p>";
                }
                $.ajax({
    				type : "GET",
    				async : false,
    				url : "../equipment/getInterfacesEntry",
    				data:{
    		        	ipAddress : n.ip,
    					community : 'public'
    		        },
    		        error:function(){
     		        	alert("获取失败");
     		        	return;
     		        },
    				success : function(r) {
    					var rlist=r.page.list;
    					if(rlist==null){
    						return;
    					}
    						for(var i=0;i<rlist.length;i++){
    							 html += "<p><b>接口名</b>: " + rlist[i].interfaceName + "</p>";
    							 if(rlist[i].ifType==1){
    								 html += "<p><b>接口类型</b>: " + 'other' + "</p>"; 
    							 }
    							 if(rlist[i].ifType==2){
    								 html += "<p><b>接口类型</b>: " + 'regular1822' + "</p>"; 
    							 }
    							 if(rlist[i].ifType==3){
    								 html += "<p><b>接口类型</b>: " + 'hdh1822' + "</p>"; 
    							 }
    							 if(rlist[i].ifType==4){
    								 html += "<p><b>接口类型</b>: " + 'ddn-x25' + "</p>"; 
    							 }
    							 if(rlist[i].ifType==5){
    								 html += "<p><b>接口类型</b>: " + 'rfc877-x25' + "</p>"; 
    							 }						 
    							 if(rlist[i].ifType==6){
    								 html += "<p><b>接口类型</b>: " + 'ethernet-csmacd' + "</p>"; 
    							 }
    							 if(rlist[i].ifType==7){
    								 html += "<p><b>接口类型</b>: " + 'iso88023-csmacd' + "</p>"; 
    							 }
    							 if(rlist[i].ifType==8){
    								 html += "<p><b>接口类型</b>: " + 'iso88024-tokenBus' + "</p>"; 
    							 }
    							 if(rlist[i].ifType==9){
    								 html += "<p><b>接口类型</b>: " + 'iso88025-tokenRing' + "</p>"; 
    							 }
    							 if(rlist[i].ifType==10){
    								 html += "<p><b>接口类型</b>: " + 'iso88026-man' + "</p>"; 
    							 }
    							 if(rlist[i].ifType==11){
    								 html += "<p><b>接口类型</b>: " + ' starLan' + "</p>"; 
    							 }
    							 if(rlist[i].ifType==12){
    								 html += "<p><b>接口类型</b>: " + 'proteon-10Mbit' + "</p>"; 
    							 }
    							 if(rlist[i].ifType==13){
    								 html += "<p><b>接口类型</b>: " + 'proteon-80Mbit' + "</p>"; 
    							 }
    							 if(rlist[i].ifType==14){
    								 html += "<p><b>接口类型</b>: " + 'hyperchannel' + "</p>"; 
    							 }
    							 if(rlist[i].ifType==15){
    								 html += "<p><b>接口类型</b>: " + 'fddi' + "</p>"; 
    							 }
    							 if(rlist[i].ifType==16){
    								 html += "<p><b>接口类型</b>: " + 'lapb' + "</p>"; 
    							 }
    							 if(rlist[i].ifType==17){
    								 html += "<p><b>接口类型</b>: " + 'sdlc' + "</p>"; 
    							 }
    							 if(rlist[i].ifType==18){
    								 html += "<p><b>接口类型</b>: " + 'ds1' + "</p>"; 
    							 }
    							 if(rlist[i].ifType==19){
    								 html += "<p><b>接口类型</b>: " + 'e1' + "</p>"; 
    							 }
    							 if(rlist[i].ifType==20){
    								 html += "<p><b>接口类型</b>: " + 'basicISDN' + "</p>"; 
    							 }
    							 if(rlist[i].ifType==21){
    								 html += "<p><b>接口类型</b>: " + 'primaryISDN' + "</p>"; 
    							 }
    							 if(rlist[i].ifType==22){
    								 html += "<p><b>接口类型</b>: " + 'propPointToPointSerial' + "</p>"; 
    							 }
    							 if(rlist[i].ifType==23){
    								 html += "<p><b>接口类型</b>: " + 'ppp' + "</p>"; 
    							 }
    							 if(rlist[i].ifType==24){
    								 html += "<p><b>接口类型</b>: " + 'softwareLoopback' + "</p>"; 
    							 }
    							 if(rlist[i].ifType==25){
    								 html += "<p><b>接口类型</b>: " + 'eon' + "</p>"; 
    							 }
    							 if(rlist[i].ifType==26){
    								 html += "<p><b>接口类型</b>: " + 'ethernet-3Mbit' + "</p>"; 
    							 }
    							 if(rlist[i].ifType==27){
    								 html += "<p><b>接口类型</b>: " + 'nsip' + "</p>"; 
    							 }
    							 if(rlist[i].ifType==28){
    								 html += "<p><b>接口类型</b>: " + 'slip' + "</p>"; 
    							 }
    							 if(rlist[i].ifType==29){
    								 html += "<p><b>接口类型</b>: " + 'ultra' + "</p>"; 
    							 }
    							 if(rlist[i].ifType==30){
    								 html += "<p><b>接口类型</b>: " + 'ds3' + "</p>"; 
    							 }
    							 if(rlist[i].ifType==31){
    								 html += "<p><b>接口类型</b>: " + 'sip' + "</p>"; 
    							 }
    							 if(rlist[i].ifType==32){
    								 html += "<p><b>接口类型</b>: " + 'frame-relay' + "</p>"; 
    							 }
    							 html += "<p><b>最大数据包</b>: " + rlist[i].ifMtu + "</p>"; 
    							 html += "<p><b>带宽</b>: " + rlist[i].ifLastChange + "</p>"; 
    							 html += "<p><b>接收字节数</b>: " + rlist[i].ifInOctets + "</p>"; 
    							 html += "<p><b>接收数据包数</b>: " + rlist[i].ifInUcastPkts + "</p>";   							
    							 html += "<p><b>丢包数</b>: " + rlist[i].ifInUnknownProtos + "</p>"; 
    							 html += "<p><b>发出字节数</b>: " + rlist[i].ifOutOctets + "</p>"; 
    							 html += "<p><b>发出数据包数</b>: " + rlist[i].ifOutUcastPkts + "</p><br>"; 

    					}
    				}
    			});
                overlayInner.html(html);

                overlay.classed("njg-hidden", false);
                overlay.style("display", "block");
                overlay.style("height", "450px");
                overlay.style("overflow", "scroll");
                // 将当前节点设置为“open”样式
                removeOpenClass();
                $('.njg-overlay').find('.njg-close').bind('click',function(){
                	 html = "";
                	window.clearInterval(timer1);
                })
                d3.select(this).classed("njg-open", true);
                
            	window.clearInterval(timer1);
            	timer1 = window.setInterval(function() {           		
                var overlay = d3.select(".njg-overlay"),
                    overlayInner = d3.select(".njg-overlay > .njg-inner"),
                    html = "<p><b>ip</b>: " + n.ip + "</p>";
                    if(n.label) { html += "<p><b>label</b>: " + n.label + "</p>"; }
                    if(n.properties) {
                        for(var key in n.properties) {
                            if(!n.properties.hasOwnProperty(key)) { continue; }
                            html += "<p><b>"+key.replace(/_/g, " ")+"</b>: " + n.properties[key] + "</p>";
                    }
                }
                if(n.linkCount) { html += "<p><b>links</b>: " + n.linkCount + "</p><br>"; }
                if(n.local_addresses) {
                    html += "<p><b>local addresses</b>:<br>" + n.local_addresses.join('<br>') + "</p>";
                }
                $.ajax({
    				type : "GET",
    				async : false,
    				url : "../equipment/getInterfacesEntry",
    				data:{
    		        	ipAddress : n.ip,
    					community : 'public'
    		        },
    		        error:function(){
     		        	alert("获取失败");
     		        	window.clearInterval(timer1);
     		        	return;
     		        },
    				success : function(r) {
    					var rlist=r.page.list;
    					if(rlist==null){
    						return;
    					}
    						for(var i=0;i<rlist.length;i++){
    							 html += "<p><b>接口名</b>: " + rlist[i].interfaceName + "</p>";
    							 if(rlist[i].ifType==1){
    								 html += "<p><b>接口类型</b>: " + 'other' + "</p>"; 
    							 }
    							 if(rlist[i].ifType==2){
    								 html += "<p><b>接口类型</b>: " + 'regular1822' + "</p>"; 
    							 }
    							 if(rlist[i].ifType==3){
    								 html += "<p><b>接口类型</b>: " + 'hdh1822' + "</p>"; 
    							 }
    							 if(rlist[i].ifType==4){
    								 html += "<p><b>接口类型</b>: " + 'ddn-x25' + "</p>"; 
    							 }
    							 if(rlist[i].ifType==5){
    								 html += "<p><b>接口类型</b>: " + 'rfc877-x25' + "</p>"; 
    							 }						 
    							 if(rlist[i].ifType==6){
    								 html += "<p><b>接口类型</b>: " + 'ethernet-csmacd' + "</p>"; 
    							 }
    							 if(rlist[i].ifType==7){
    								 html += "<p><b>接口类型</b>: " + 'iso88023-csmacd' + "</p>"; 
    							 }
    							 if(rlist[i].ifType==8){
    								 html += "<p><b>接口类型</b>: " + 'iso88024-tokenBus' + "</p>"; 
    							 }
    							 if(rlist[i].ifType==9){
    								 html += "<p><b>接口类型</b>: " + 'iso88025-tokenRing' + "</p>"; 
    							 }
    							 if(rlist[i].ifType==10){
    								 html += "<p><b>接口类型</b>: " + 'iso88026-man' + "</p>"; 
    							 }
    							 if(rlist[i].ifType==11){
    								 html += "<p><b>接口类型</b>: " + ' starLan' + "</p>"; 
    							 }
    							 if(rlist[i].ifType==12){
    								 html += "<p><b>接口类型</b>: " + 'proteon-10Mbit' + "</p>"; 
    							 }
    							 if(rlist[i].ifType==13){
    								 html += "<p><b>接口类型</b>: " + 'proteon-80Mbit' + "</p>"; 
    							 }
    							 if(rlist[i].ifType==14){
    								 html += "<p><b>接口类型</b>: " + 'hyperchannel' + "</p>"; 
    							 }
    							 if(rlist[i].ifType==15){
    								 html += "<p><b>接口类型</b>: " + 'fddi' + "</p>"; 
    							 }
    							 if(rlist[i].ifType==16){
    								 html += "<p><b>接口类型</b>: " + 'lapb' + "</p>"; 
    							 }
    							 if(rlist[i].ifType==17){
    								 html += "<p><b>接口类型</b>: " + 'sdlc' + "</p>"; 
    							 }
    							 if(rlist[i].ifType==18){
    								 html += "<p><b>接口类型</b>: " + 'ds1' + "</p>"; 
    							 }
    							 if(rlist[i].ifType==19){
    								 html += "<p><b>接口类型</b>: " + 'e1' + "</p>"; 
    							 }
    							 if(rlist[i].ifType==20){
    								 html += "<p><b>接口类型</b>: " + 'basicISDN' + "</p>"; 
    							 }
    							 if(rlist[i].ifType==21){
    								 html += "<p><b>接口类型</b>: " + 'primaryISDN' + "</p>"; 
    							 }
    							 if(rlist[i].ifType==22){
    								 html += "<p><b>接口类型</b>: " + 'propPointToPointSerial' + "</p>"; 
    							 }
    							 if(rlist[i].ifType==23){
    								 html += "<p><b>接口类型</b>: " + 'ppp' + "</p>"; 
    							 }
    							 if(rlist[i].ifType==24){
    								 html += "<p><b>接口类型</b>: " + 'softwareLoopback' + "</p>"; 
    							 }
    							 if(rlist[i].ifType==25){
    								 html += "<p><b>接口类型</b>: " + 'eon' + "</p>"; 
    							 }
    							 if(rlist[i].ifType==26){
    								 html += "<p><b>接口类型</b>: " + 'ethernet-3Mbit' + "</p>"; 
    							 }
    							 if(rlist[i].ifType==27){
    								 html += "<p><b>接口类型</b>: " + 'nsip' + "</p>"; 
    							 }
    							 if(rlist[i].ifType==28){
    								 html += "<p><b>接口类型</b>: " + 'slip' + "</p>"; 
    							 }
    							 if(rlist[i].ifType==29){
    								 html += "<p><b>接口类型</b>: " + 'ultra' + "</p>"; 
    							 }
    							 if(rlist[i].ifType==30){
    								 html += "<p><b>接口类型</b>: " + 'ds3' + "</p>"; 
    							 }
    							 if(rlist[i].ifType==31){
    								 html += "<p><b>接口类型</b>: " + 'sip' + "</p>"; 
    							 }
    							 if(rlist[i].ifType==32){
    								 html += "<p><b>接口类型</b>: " + 'frame-relay' + "</p>"; 
    							 }
    							
    							 html += "<p><b>最大数据包</b>: " + rlist[i].ifMtu + "</p>"; 
    							 html += "<p><b>带宽</b>: " + rlist[i].ifLastChange + "</p>"; 
    							 html += "<p><b>接收字节数</b>: " + rlist[i].ifInOctets + "</p>"; 
    							 html += "<p><b>接收数据包数</b>: " + rlist[i].ifInUcastPkts + "</p>";   							
    							 html += "<p><b>丢包数</b>: " + rlist[i].ifInUnknownProtos + "</p>"; 
    							 html += "<p><b>发出字节数</b>: " + rlist[i].ifOutOctets + "</p>"; 
    							 html += "<p><b>发出数据包数</b>: " + rlist[i].ifOutUcastPkts + "</p><br>"; 

    					}
    				}
    			});
                overlayInner.html(html);
            	}, 3000);
                overlay.classed("njg-hidden", false);
                overlay.style("display", "block");
                overlay.style("height", "450px");
                overlay.style("overflow", "scroll");
                // 将当前节点设置为“open”样式
                removeOpenClass();
                $('.njg-overlay').find('.njg-close').bind('click',function(){
                	 html = "";
                	window.clearInterval(timer1);
                })
                d3.select(this).classed("njg-open", true);
            	
            },
            /**
             * @function
             * @name onClickLink
             *
             * 单击某个链路时调用
             */
            onClickLink: function(l) {
       
                var overlay = d3.select(".njg-overlay"),
                    overlayInner = d3.select(".njg-overlay > .njg-inner"),
                    html = "<p><b>source</b>: " + (l.source.label || l.source.ip) + "</p>";
                    
                if(l.properties) {
                    for(var key in l.properties) {
                        if(!l.properties.hasOwnProperty(key)) { continue; }
                        html += "<p><b>"+ key.replace(/_/g, " ") +"</b>: " + l.properties[key] + "</p>";
                    }
                }
                $.ajax({
 				type : "GET",
 				async : false,
 				url : "../equipment/getLinkInterfacesEntry",
 				data:{
 		        	ipAddress1 : l.source.ip,
 		        	ipAddress2 : l.target.ip,
 					community : 'public'
 		        },
 		       error:function(){
		        	alert("获取失败");
		        	return;
		        },
 				success : function(r) {	
 					var rlist=r.result;
 					for(var i=0;i<rlist.length;i++){
 						if(i==1){
 							html += "<p><b>target</b>: " + (l.target.label || l.target.ip) + "</p>";
 						}
						 html += "<p><b>接口名</b>: " + rlist[i].interfaceName + "</p>";
						 if(rlist[i].ifType==1){
							 html += "<p><b>接口类型</b>: " + 'other' + "</p>"; 
						 }
						 if(rlist[i].ifType==2){
							 html += "<p><b>接口类型</b>: " + 'regular1822' + "</p>"; 
						 }
						 if(rlist[i].ifType==3){
							 html += "<p><b>接口类型</b>: " + 'hdh1822' + "</p>"; 
						 }
						 if(rlist[i].ifType==4){
							 html += "<p><b>接口类型</b>: " + 'ddn-x25' + "</p>"; 
						 }
						 if(rlist[i].ifType==5){
							 html += "<p><b>接口类型</b>: " + 'rfc877-x25' + "</p>"; 
						 }						 
						 if(rlist[i].ifType==6){
							 html += "<p><b>接口类型</b>: " + 'ethernet-csmacd' + "</p>"; 
						 }
						 if(rlist[i].ifType==7){
							 html += "<p><b>接口类型</b>: " + 'iso88023-csmacd' + "</p>"; 
						 }
						 if(rlist[i].ifType==8){
							 html += "<p><b>接口类型</b>: " + 'iso88024-tokenBus' + "</p>"; 
						 }
						 if(rlist[i].ifType==9){
							 html += "<p><b>接口类型</b>: " + 'iso88025-tokenRing' + "</p>"; 
						 }
						 if(rlist[i].ifType==10){
							 html += "<p><b>接口类型</b>: " + 'iso88026-man' + "</p>"; 
						 }
						 if(rlist[i].ifType==11){
							 html += "<p><b>接口类型</b>: " + ' starLan' + "</p>"; 
						 }
						 if(rlist[i].ifType==12){
							 html += "<p><b>接口类型</b>: " + 'proteon-10Mbit' + "</p>"; 
						 }
						 if(rlist[i].ifType==13){
							 html += "<p><b>接口类型</b>: " + 'proteon-80Mbit' + "</p>"; 
						 }
						 if(rlist[i].ifType==14){
							 html += "<p><b>接口类型</b>: " + 'hyperchannel' + "</p>"; 
						 }
						 if(rlist[i].ifType==15){
							 html += "<p><b>接口类型</b>: " + 'fddi' + "</p>"; 
						 }
						 if(rlist[i].ifType==16){
							 html += "<p><b>接口类型</b>: " + 'lapb' + "</p>"; 
						 }
						 if(rlist[i].ifType==17){
							 html += "<p><b>接口类型</b>: " + 'sdlc' + "</p>"; 
						 }
						 if(rlist[i].ifType==18){
							 html += "<p><b>接口类型</b>: " + 'ds1' + "</p>"; 
						 }
						 if(rlist[i].ifType==19){
							 html += "<p><b>接口类型</b>: " + 'e1' + "</p>"; 
						 }
						 if(rlist[i].ifType==20){
							 html += "<p><b>接口类型</b>: " + 'basicISDN' + "</p>"; 
						 }
						 if(rlist[i].ifType==21){
							 html += "<p><b>接口类型</b>: " + 'primaryISDN' + "</p>"; 
						 }
						 if(rlist[i].ifType==22){
							 html += "<p><b>接口类型</b>: " + 'propPointToPointSerial' + "</p>"; 
						 }
						 if(rlist[i].ifType==23){
							 html += "<p><b>接口类型</b>: " + 'ppp' + "</p>"; 
						 }
						 if(rlist[i].ifType==24){
							 html += "<p><b>接口类型</b>: " + 'softwareLoopback' + "</p>"; 
						 }
						 if(rlist[i].ifType==25){
							 html += "<p><b>接口类型</b>: " + 'eon' + "</p>"; 
						 }
						 if(rlist[i].ifType==26){
							 html += "<p><b>接口类型</b>: " + 'ethernet-3Mbit' + "</p>"; 
						 }
						 if(rlist[i].ifType==27){
							 html += "<p><b>接口类型</b>: " + 'nsip' + "</p>"; 
						 }
						 if(rlist[i].ifType==28){
							 html += "<p><b>接口类型</b>: " + 'slip' + "</p>"; 
						 }
						 if(rlist[i].ifType==29){
							 html += "<p><b>接口类型</b>: " + 'ultra' + "</p>"; 
						 }
						 if(rlist[i].ifType==30){
							 html += "<p><b>接口类型</b>: " + 'ds3' + "</p>"; 
						 }
						 if(rlist[i].ifType==31){
							 html += "<p><b>接口类型</b>: " + 'sip' + "</p>"; 
						 }
						 if(rlist[i].ifType==32){
							 html += "<p><b>接口类型</b>: " + 'frame-relay' + "</p>"; 
						 }
						 html += "<p><b>最大数据包</b>: " + rlist[i].ifMtu + "</p>"; 
						 html += "<p><b>带宽</b>: " + rlist[i].ifLastChange + "</p>"; 
						 html += "<p><b>接收字节数</b>: " + rlist[i].ifInOctets + "</p>"; 
						 html += "<p><b>接收数据包数</b>: " + rlist[i].ifInUcastPkts + "</p>";   							
						 html += "<p><b>丢包数</b>: " + rlist[i].ifInUnknownProtos + "</p>"; 
						 html += "<p><b>发出字节数</b>: " + rlist[i].ifOutOctets + "</p>"; 
						 html += "<p><b>发出数据包数</b>: " + rlist[i].ifOutUcastPkts + "</p><br>"; 

 						}
 					}
 		        });
                overlayInner.html(html);
                overlay.classed("njg-hidden", false);
                overlay.style("display", "block");
                overlay.style("height", "450px");
                overlay.style("overflow", "scroll");
                // 将当前连接设置为“open”样式
                removeOpenClass(); 
                $('.njg-overlay').find('.njg-close').bind('click',function(){
                	 html = "";
                	window.clearInterval(timer1);
                })
                d3.select(this).classed("njg-open", true);
                window.clearInterval(timer1);
             	timer1 = window.setInterval(function() {           		
             		var overlay = d3.select(".njg-overlay"),
                    overlayInner = d3.select(".njg-overlay > .njg-inner"),
                    html = "<p><b>source</b>: " + (l.source.label || l.source.ip) + "</p>";
                    
                    if(l.properties) {
                    	for(var key in l.properties) {
                    		if(!l.properties.hasOwnProperty(key)) { continue; }
                    		html += "<p><b>"+ key.replace(/_/g, " ") +"</b>: " + l.properties[key] + "</p>";
                    	}
                    }
                    $.ajax({
         				type : "GET",
         				async : false,
         				url : "../equipment/getLinkInterfacesEntry",
         				data:{
         		        	ipAddress1 : l.source.ip,
         		        	ipAddress2 : l.target.ip,
         					community : 'public'
         		        },
         		       error:function(){
        		        	alert("获取失败");
        		        	window.clearInterval(timer1);
        		        	return;
        		        },
         				success : function(r) {	
         					var rlist=r.result;
         					for(var i=0;i<rlist.length;i++){
         						if(i==1){
         							html += "<p><b>target</b>: " + (l.target.label || l.target.ip) + "</p>";
         						}
        						 html += "<p><b>接口名</b>: " + rlist[i].interfaceName + "</p>";
        						 if(rlist[i].ifType==1){
        							 html += "<p><b>接口类型</b>: " + 'other' + "</p>"; 
        						 }
        						 if(rlist[i].ifType==2){
        							 html += "<p><b>接口类型</b>: " + 'regular1822' + "</p>"; 
        						 }
        						 if(rlist[i].ifType==3){
        							 html += "<p><b>接口类型</b>: " + 'hdh1822' + "</p>"; 
        						 }
        						 if(rlist[i].ifType==4){
        							 html += "<p><b>接口类型</b>: " + 'ddn-x25' + "</p>"; 
        						 }
        						 if(rlist[i].ifType==5){
        							 html += "<p><b>接口类型</b>: " + 'rfc877-x25' + "</p>"; 
        						 }						 
        						 if(rlist[i].ifType==6){
        							 html += "<p><b>接口类型</b>: " + 'ethernet-csmacd' + "</p>"; 
        						 }
        						 if(rlist[i].ifType==7){
        							 html += "<p><b>接口类型</b>: " + 'iso88023-csmacd' + "</p>"; 
        						 }
        						 if(rlist[i].ifType==8){
        							 html += "<p><b>接口类型</b>: " + 'iso88024-tokenBus' + "</p>"; 
        						 }
        						 if(rlist[i].ifType==9){
        							 html += "<p><b>接口类型</b>: " + 'iso88025-tokenRing' + "</p>"; 
        						 }
        						 if(rlist[i].ifType==10){
        							 html += "<p><b>接口类型</b>: " + 'iso88026-man' + "</p>"; 
        						 }
        						 if(rlist[i].ifType==11){
        							 html += "<p><b>接口类型</b>: " + ' starLan' + "</p>"; 
        						 }
        						 if(rlist[i].ifType==12){
        							 html += "<p><b>接口类型</b>: " + 'proteon-10Mbit' + "</p>"; 
        						 }
        						 if(rlist[i].ifType==13){
        							 html += "<p><b>接口类型</b>: " + 'proteon-80Mbit' + "</p>"; 
        						 }
        						 if(rlist[i].ifType==14){
        							 html += "<p><b>接口类型</b>: " + 'hyperchannel' + "</p>"; 
        						 }
        						 if(rlist[i].ifType==15){
        							 html += "<p><b>接口类型</b>: " + 'fddi' + "</p>"; 
        						 }
        						 if(rlist[i].ifType==16){
        							 html += "<p><b>接口类型</b>: " + 'lapb' + "</p>"; 
        						 }
        						 if(rlist[i].ifType==17){
        							 html += "<p><b>接口类型</b>: " + 'sdlc' + "</p>"; 
        						 }
        						 if(rlist[i].ifType==18){
        							 html += "<p><b>接口类型</b>: " + 'ds1' + "</p>"; 
        						 }
        						 if(rlist[i].ifType==19){
        							 html += "<p><b>接口类型</b>: " + 'e1' + "</p>"; 
        						 }
        						 if(rlist[i].ifType==20){
        							 html += "<p><b>接口类型</b>: " + 'basicISDN' + "</p>"; 
        						 }
        						 if(rlist[i].ifType==21){
        							 html += "<p><b>接口类型</b>: " + 'primaryISDN' + "</p>"; 
        						 }
        						 if(rlist[i].ifType==22){
        							 html += "<p><b>接口类型</b>: " + 'propPointToPointSerial' + "</p>"; 
        						 }
        						 if(rlist[i].ifType==23){
        							 html += "<p><b>接口类型</b>: " + 'ppp' + "</p>"; 
        						 }
        						 if(rlist[i].ifType==24){
        							 html += "<p><b>接口类型</b>: " + 'softwareLoopback' + "</p>"; 
        						 }
        						 if(rlist[i].ifType==25){
        							 html += "<p><b>接口类型</b>: " + 'eon' + "</p>"; 
        						 }
        						 if(rlist[i].ifType==26){
        							 html += "<p><b>接口类型</b>: " + 'ethernet-3Mbit' + "</p>"; 
        						 }
        						 if(rlist[i].ifType==27){
        							 html += "<p><b>接口类型</b>: " + 'nsip' + "</p>"; 
        						 }
        						 if(rlist[i].ifType==28){
        							 html += "<p><b>接口类型</b>: " + 'slip' + "</p>"; 
        						 }
        						 if(rlist[i].ifType==29){
        							 html += "<p><b>接口类型</b>: " + 'ultra' + "</p>"; 
        						 }
        						 if(rlist[i].ifType==30){
        							 html += "<p><b>接口类型</b>: " + 'ds3' + "</p>"; 
        						 }
        						 if(rlist[i].ifType==31){
        							 html += "<p><b>接口类型</b>: " + 'sip' + "</p>"; 
        						 }
        						 if(rlist[i].ifType==32){
        							 html += "<p><b>接口类型</b>: " + 'frame-relay' + "</p>"; 
        						 }
        						 html += "<p><b>最大数据包</b>: " + rlist[i].ifMtu + "</p>"; 
        						 html += "<p><b>带宽</b>: " + rlist[i].ifLastChange + "</p>"; 
        						 html += "<p><b>接收字节数</b>: " + rlist[i].ifInOctets + "</p>"; 
        						 html += "<p><b>接收数据包数</b>: " + rlist[i].ifInUcastPkts + "</p>";   							
        						 html += "<p><b>丢包数</b>: " + rlist[i].ifInUnknownProtos + "</p>"; 
        						 html += "<p><b>发出字节数</b>: " + rlist[i].ifOutOctets + "</p>"; 
        						 html += "<p><b>发出数据包数</b>: " + rlist[i].ifOutUcastPkts + "</p><br>"; 

         						}
         					}
         		        });
                    	overlayInner.html(html);
             			}, 3000);
             	 overlay.classed("njg-hidden", false);
                 overlay.style("display", "block");
                 overlay.style("height", "450px");
                 overlay.style("overflow", "scroll");
                 // 将当前节点设置为“open”样式
                 removeOpenClass();
                 $('.njg-overlay').find('.njg-close').bind('click',function(){
                 	 html = "";
                 	window.clearInterval(timer1);
                 })
                 d3.select(this).classed("njg-open", true);
             	
             },
              
        }, opts);

        // init callback
        opts.onInit(url, opts);

        if(!opts.animationAtStart) {
            opts.linkStrength = 0     ;//强度指数
            opts.friction = 0.3;//摩擦系数
            opts.gravity = 0;//重力系数
        }
        if(opts.el == "body") {//设置body大小
            var body = d3.select(opts.el),
                rect = body.node().getBoundingClientRect();
            if (d3._pxToNumber(d3.select("body").style("height")) < 60) {
                body.style("height", d3._windowHeight() - rect.top - rect.bottom + "px");
            }
        }
        var el = d3.select(opts.el).style("position", "relative"),
            width = d3._pxToNumber(el.style('width')),
            //height = d3._pxToNumber(el.style('height')),
            height = 500;
            force = d3.layout.force()
                      .charge(opts.charge)
                      .linkStrength(opts.linkStrength)
                      .linkDistance(opts.linkDistanceFunc)
                      .friction(opts.friction)
                      .chargeDistance(opts.chargeDistance)
                      .theta(opts.theta)
                      .gravity(opts.gravity)
                      // 宽度很容易获得，如果高度是0，取body的高度。
                      .size([width, height]),
            zoom = d3.behavior.zoom().scaleExtent(opts.scaleExtent),
            // panner is 元素允许缩放和平移
            panner = el.append("svg")
                       .attr("width", width)
                       .attr("height", height)
                       .call(zoom.on("zoom", opts.redraw))
                       .html("<style type='text/css'>.njg-overlay{background: #fbfbfb;border-radius: 2px;border: 1px solid #ccc;color: #6d6357;font-family: Arial, sans-serif;font-family: sans-serif;font-size: 14px;line-height: 20px;height: auto;max-width: 400px;min-width: 200px;padding: 0 15px;right: 10px;top: 10px;width: auto;}.njg-metadata{background: #fbfbfb;border-radius: 2px;border: 1px solid #ccc;color: #6d6357;display: none;font-family: Arial, sans-serif;font-family: sans-serif;font-size: 14px;height: auto;left: 10px;max-width: 500px;min-width: 200px;padding: 0 15px;top: 10px;width: auto;}.njg-node{stroke-opacity: 0.5;stroke-width: 7px;stroke: #fff;}.njg-node:hover,.njg-node.njg-open {stroke: rgba(0, 0, 0, 0.2);}.njg-link{cursor: pointer;stroke: #999;stroke-width: 2;stroke-opacity: 0.25;}.njg-link:hover,.njg-link.njg-open{stroke-width: 4 !important;stroke-opacity: 0.5;}.njg-hidden {    display: none !important;    visibility: hidden !important;}.njg-tooltip{    font-family: sans-serif;    font-size: 10px;    fill: #000;    opacity: 0.5;    text-anchor: middle;}.njg-overlay{    display: none;    position: absolute;    z-index: 11;}.njg-close{    cursor: pointer;    position: absolute;    right: 10px;    top: 10px;}.njg-close:before { content: '\\2716'; }.njg-metadata{    display: none;    position: absolute;    z-index: 12;}.njg-node{ cursor: pointer }.njg-link{ cursor: pointer }#njg-select-group {    text-align: center;    box-shadow: 0 0 10px #ccc;    position: fixed;    left: 50%;    top: 50%;    width: 50%;    margin-top: -7.5em;    margin-left: -25%;    padding: 5em 2em;}#njg-select-group select {    font-size: 2em;    padding: 10px 15px;    width: 50%;    cursor: pointer;}#njg-select-group option {    padding: 0.5em;}#njg-select-group option[value=''] {    color: #aaa;}</style>")
                       .append("g")
                       .style("position", "absolute"),
            svg = d3.select(opts.el + " svg"),
            drag = force.drag(),
            //overlay节点和线的框（右）   metadata网络框（左）
            overlay = d3.select(opts.el).append("div").attr("class", "njg-overlay"),
            closeOverlay = overlay.append("a").attr("class", "njg-close"),
            overlayInner = overlay.append("div").attr("class", "njg-inner"),
            metadata = d3.select(opts.el).append("div").attr("class", "njg-metadata"),
            metadataInner = metadata.append("div").attr("class", "njg-inner"),
            closeMetadata = metadata.append("a").attr("class", "njg-close"),
            // 未分组网络的容器
            str = [],
            selected = [],
            /**
             * @function
             * @name removeOpenClass
             *
             * 从节点和连接中删除“open”样式
             */
            removeOpenClass = function () {
                d3.selectAll("svg .njg-open").classed("njg-open", false);
            };
            processJson = function(graph) {
                /**
                 * Init netJsonGraph
                 */
                init = function(url, opts) {
                    d3.netJsonGraph(url, opts);
                };
                /**
                 * 删除所有实例
                 */
                destroy = function() {
                    force.stop();
                    d3.select("#selectGroup").remove();
                    d3.select(".njg-overlay").remove();
                    d3.select(".njg-metadata").remove();
                    overlay.remove();
                    overlayInner.remove();
                    metadata.remove();
                    svg.remove();
                    node.remove();
                    link.remove();
                    nodes = [];
                    links = [];
                };
                /**
                 * Destroy and e-init all instances
                 * @return {[type]} [description]
                 */
                reInit = function() {
                    destroy();
                    init(url, opts);
                };

                var data = opts.prepareData(graph),
                    links = data.links,
                    nodes = data.nodes;

                // 在拖动时禁用一些转换
                drag.on('dragstart', function(n){
                    d3.event.sourceEvent.stopPropagation();
                    zoom.on('zoom', null);
                })
                // 停止拖动时重新启用转换
                .on('dragend', function(n){
                    zoom.on('zoom', opts.redraw);
                })
                .on("drag", function(d) {
                    // 避免平移冲突
                    d3.select(this).attr("x", d.x = d3.event.x).attr("y", d.y = d3.event.y);
                });

                force.nodes(nodes).links(links).start();

                var link = panner.selectAll(".link")
                                 .data(links)
                                 .enter().append("line")
                                 .attr("class", function (link) {
                                     var baseClass = "njg-link",
                                         addClass = null;
                                         value = link.properties && link.properties[opts.linkClassProperty];
                                     if (opts.linkClassProperty && value) {
                                         // if value is string use that as class
                                         if (typeof(value) === "string") {
                                             addClass = value;
                                         }
                                         else if (typeof(value) === "number") {
                                             addClass = opts.linkClassProperty + value;
                                         }
                                         else if (value === true) {
                                             addClass = opts.linkClassProperty;
                                         }
                                         return baseClass + " " + addClass;
                                     }
                                     return baseClass;
                                 })
                                 .on("click", opts.onClickLink),
                    groups = panner.selectAll(".node")
                                   .data(nodes)
                                   .enter()
                                   .append("g");
                    node = groups.append("image")
                    .attr('xlink:href',function(node){                    			  
         			 if(node.type==2){
                   		 return "data:image/jpg;base64,/9j/4AAQSkZJRgABAQEAHAAcAAD/2wBDAAIBAQIBAQICAgICAgICAwUDAwMDAwYEBAMFBwYHBwcGBwcICQsJCAgKCAcHCg0KCgsMDAwMBwkODw0MDgsMDAz/2wBDAQICAgMDAwYDAwYMCAcIDAwMDAwMDAwMDAwMDAwMDAwMDAwMDAwMDAwMDAwMDAwMDAwMDAwMDAwMDAwMDAwMDAz/wAARCAAgACADASIAAhEBAxEB/8QAHwAAAQUBAQEBAQEAAAAAAAAAAAECAwQFBgcICQoL/8QAtRAAAgEDAwIEAwUFBAQAAAF9AQIDAAQRBRIhMUEGE1FhByJxFDKBkaEII0KxwRVS0fAkM2JyggkKFhcYGRolJicoKSo0NTY3ODk6Q0RFRkdISUpTVFVWV1hZWmNkZWZnaGlqc3R1dnd4eXqDhIWGh4iJipKTlJWWl5iZmqKjpKWmp6ipqrKztLW2t7i5usLDxMXGx8jJytLT1NXW19jZ2uHi4+Tl5ufo6erx8vP09fb3+Pn6/8QAHwEAAwEBAQEBAQEBAQAAAAAAAAECAwQFBgcICQoL/8QAtREAAgECBAQDBAcFBAQAAQJ3AAECAxEEBSExBhJBUQdhcRMiMoEIFEKRobHBCSMzUvAVYnLRChYkNOEl8RcYGRomJygpKjU2Nzg5OkNERUZHSElKU1RVVldYWVpjZGVmZ2hpanN0dXZ3eHl6goOEhYaHiImKkpOUlZaXmJmaoqOkpaanqKmqsrO0tba3uLm6wsPExcbHyMnK0tPU1dbX2Nna4uPk5ebn6Onq8vP09fb3+Pn6/9oADAMBAAIRAxEAPwD9sP2tv2yPhl+wn8G7n4gfFnxdp/g3wnbXMVn9suY5Z3nnlJCQwwwq800hAZtkaMwRHcgKjMPkz/iKN/YT/wCi5f8AlmeIP/kGj/gvp/zZX/2dV4G/9vq+/wCgD4A/4ijf2E/+i5f+WZ4g/wDkGvrP9kn9sj4Z/t2fBu2+IHwm8Xaf4y8J3VzLZ/bLaOWF4J4iA8M0MypNDIAVbZIisUdHAKurH0yvgD/ggX/zep/2dV45/wDbGgDgv+C7n7THw6+JfgT4Y/8ACB/F34Aa98R/2e/jRofjzUPBOr/FLRvD99qR0qS5iuNO825m2W1wJJRu87bsEcnDOFjbz3/iJw+Iv/RAP2f/APxL7wR/8VX1V8cv+Dd39jf9pD4v+IvHnjD4L2moeKvFl9JqerXdt4j1jT0vLmQ5kmMNvdxxK7tlmKoNzMzHLMScPwt/wbOfsT+B/ENpq2i/B/UNH1awkEtre2PjvxHb3Fs46MkiX4ZT7gg0AfOP/ETh8Rf+iAfs/wD/AIl94I/+Kr0L/ghH+0x8Ovhn4E+J3/CefF74AaF8R/2hPjRrnjzT/BOj/FLRvEF9pp1WS2it9O822m2XNwZIjt8ndvEkfCuWjX0zxT/wbOfsT+OPEN3q2tfB/UNY1a/kMt1e33jvxHcXFy56s8j35Zj7kk1ufA3/AIN3f2N/2b/i/wCHfHng/wCC9pp/irwnfR6npN3c+I9Y1BLO5jOY5hDcXckTOjYZSyHayqwwyggA/9k="; 
                   	 }else if(node.type==1){
                   		 return "data:image/jpg;base64,/9j/4AAQSkZJRgABAQEASABIAAD/2wBDAAIBAQIBAQICAgICAgICAwUDAwMDAwYEBAMFBwYHBwcGBwcICQsJCAgKCAcHCg0KCgsMDAwMBwkODw0MDgsMDAz/2wBDAQICAgMDAwYDAwYMCAcIDAwMDAwMDAwMDAwMDAwMDAwMDAwMDAwMDAwMDAwMDAwMDAwMDAwMDAwMDAwMDAwMDAz/wAARCAAgACADASIAAhEBAxEB/8QAHwAAAQUBAQEBAQEAAAAAAAAAAAECAwQFBgcICQoL/8QAtRAAAgEDAwIEAwUFBAQAAAF9AQIDAAQRBRIhMUEGE1FhByJxFDKBkaEII0KxwRVS0fAkM2JyggkKFhcYGRolJicoKSo0NTY3ODk6Q0RFRkdISUpTVFVWV1hZWmNkZWZnaGlqc3R1dnd4eXqDhIWGh4iJipKTlJWWl5iZmqKjpKWmp6ipqrKztLW2t7i5usLDxMXGx8jJytLT1NXW19jZ2uHi4+Tl5ufo6erx8vP09fb3+Pn6/8QAHwEAAwEBAQEBAQEBAQAAAAAAAAECAwQFBgcICQoL/8QAtREAAgECBAQDBAcFBAQAAQJ3AAECAxEEBSExBhJBUQdhcRMiMoEIFEKRobHBCSMzUvAVYnLRChYkNOEl8RcYGRomJygpKjU2Nzg5OkNERUZHSElKU1RVVldYWVpjZGVmZ2hpanN0dXZ3eHl6goOEhYaHiImKkpOUlZaXmJmaoqOkpaanqKmqsrO0tba3uLm6wsPExcbHyMnK0tPU1dbX2Nna4uPk5ebn6Onq8vP09fb3+Pn6/9oADAMBAAIRAxEAPwD9Uv8Agod+0n458J/F/wCBfwV+FurWvhrx98bNeu5H8QXmnrfW+h6HpMKXmpyrGwZGuJFaC3iVxtJumO5CoYc7+zb/AMFf9E/aQ+GHhHUrfw7DoPiz42XXin/hS/hzUL+6DePbHRY2f7VdXC2Rj0rzgu4pL5uyNkZGmYlB03/BQr9lzxx8RPin8EfjJ8KrfS9U+JXwR1+5aDR9Wv2srDXdF1SFbPVbVpVB8qURrDPFKVcK1tjy3LAV8OfET9hvWP2IP+Ci/wCwX8I/CPxBlmsbW8+MD+A7++0gXEng2zu9BjltLSRWmP24Ws0jsGdo/MTbHhAoNAHv/wCx7/wVP8VfHX9p74KapqiyQfC79qbwtqaeGtAmsNuoeAvFPh/zBq+nzziNDcW8ojuik0gDF7PCxxq3zfoVX5//ALIf/BKPxB8BP2nPg1b6ldQzfCv9l3wpqMXg7UTqEk2reMvE2u7jrOq3sbEiCNQ9wscGZDuuy4lIHlr+gFAHyR+1r/wXM/ZX/YZ+NF98O/id8WLPw74z023hubzTYdE1PUns1mQSRCV7S2lRHaMq+xmD7HRsYdSfjf8AaH/4LR/sK/Hn9uv9nX41f8NN/wBk/wDCgv8AhJf+JN/wrvxBP/bv9safHZf6/wCzL5Hk7N/+rk352/JjdXhn/Bbf/glT+3J8av23v2ipvgh4Vs/EXwX/AGiLfwvNr8UOr6Jbvfto1pbpbxS/bpI7iF4rmCSTMBCOkiBmb5kX89P+IXL9uv8A6Ib/AOXn4f8A/k6gD9/v+Io39hT/AKLl/wCWZ4g/+Qa9I/ZJ/wCC5n7K37c/xosvh38MfizZ+IvGepW81zZ6bNomp6a92sKGSURPd20SO6xhn2KxfYjtjCMR+XH7IP7IH7dX7Kf/AAx5/wAYef29/wAMnf8ACaf81X8P2v8AwlX/AAkXm/8ATR/sv2fzf+m3m7f+WeePSv8AgkX/AME7v2qfhx8av2MdF+J/wRs/hz4N/ZRt/Hc134lm8a6Zqj+I28QJN5UUVnaO7wvFJKoyzMjortuQhUYA/9k="; 
                   	 }else{
                   		 return "data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAABoAAAAbCAIAAADtdAg8AAABeUlEQVRIDe2VwaqCQBSGb3o3CiEYIVLQMlxkix4geofeoyfzGeI+gG4lVwqpgRZFQavo/tyB8TSO3oiWuRj+Oec7P6dJz3Tu9/vX+55vwep0Ol2vVyEo3WqaZhiGkHqwS5LE9/39fi9A0m2v15vNZqPRiGYru9vt5nneZrOhabQwHA5ZZLvdCo3HcbxarVRV5SWV3fF4FLwA6bruOA6jD4eDYAceVWhTYleWJYuapkmJMAx5HCmmcSBwh0YVhavuGId1MpnM53O+lYqfv6eekth1u93BYFBHaQQM3XKtcPUW8bF7/Rg/Z/f62Um+isvlstvt2i3BSAGJXRRFTTS3yLKMayokdkApjZHX7/eLohCmE3Xh+v8XZTqdLpdLrLymRVR26ELKua47Ho+xSrNCVfVjLcvCdGRDkVYGQaAoClYaZBo8qmi8Qy/G9XqNq+d8PlOiSWPk4epZLBYUeLBDIk3TPM8p0aRt265PWdGuqfjJePVXPFnQjv0CkymKbVbpYEUAAAAASUVORK5CYII="; 
                   	 }})
                    .on("dblclick",opts.onClickNode)
                    .call(drag);
                                 
                                                    
                  
                    var labels = groups.append('text')//node的名字
                                       .text(function(n){ return n.label || n.id })
                                       .attr('dx', opts.labelDx)
                                       .attr('dy', opts.labelDy)
                                       .attr('class', 'njg-tooltip');
                    
                // 关闭覆盖
                closeOverlay.on("click", function() {
                    removeOpenClass();
                    overlay.classed("njg-hidden", true);
                });
                // 关闭元数据面板
                closeMetadata.on("click", function() {
                    // 重新初始化页面
                    if(graph.type === "NetworkCollection") {
                        reInit();
                    }
                    else {
                        removeOpenClass();
                        metadata.classed("njg-hidden", true);
                    }
                });
                // default style
                
                // into something else
                if(opts.defaultStyle) {
                    var colors = d3.scale.category20c();
                    node.style({
                        "fill": function(d){ return colors(d.linkCount); },
                        "cursor": "pointer"
                    });
                }
                // Metadata style
                if(opts.metadata) {
                    metadata.attr("class", "njg-metadata").style("display", "block");
                }

                var attrs = ["protocol",
                             "version",
                             "revision",
                             "metric",
                             "router_ip",
                             "topology_ip"],
                    html = "";
                if(graph.label) {
                    html += "<h3>" + graph.label + "</h3>";
                }
                for(var i in attrs) {
                    var attr = attrs[i];
                    if(graph[attr]) {
                        html += "<p><b>" + attr + "</b>: <span>" + graph[attr] + "</span></p>";
                    }
                }
                // Add nodes and links count
                html += "<p><b>nodes</b>: <span>" + graph.nodes.length + "</span></p>";
                html += "<p><b>links</b>: <span>" + graph.links.length + "</span></p>";
                metadataInner.html(html);
                metadata.classed("njg-hidden", false);

                // onLoad callback
                opts.onLoad(url, opts);

                force.on("tick", function() {
                    link.attr("x1", function(d) {
                        return d.source.x;
                    })
                    .attr("y1", function(d) {
                        return d.source.y;
                    })
                    .attr("x2", function(d) {
                        return d.target.x;
                    })
                    .attr("y2", function(d) {
                        return d.target.y;
                    });

                    node.attr("x", function(d) {
                        return d.x-15;
                    })
                    .attr("y", function(d) {
                        return d.y-10;
                    });

                    labels.attr("transform", function(d) {
                        return "translate(" + d.x + "," + d.y + ")";
                    });
                    
                   /* img.attr("transform", function(d) {
                    	var ix=d.x-15;
                    	var iy=d.y+30;
                        return "translate(" + ix + "," + iy + ")";
                    });*/
                    
                })
                .on("end", function(){
                    force.stop();
                    // onEnd callback
                    opts.onEnd(url, opts);
                });

                return force;
            };

        if(typeof(url) === "object") {
            processJson(url);
        }
        else {
            /**
            * 解析提供的JSON文件
            * 并且调用 processJson() function
            *
            * @param  {string}     url         The provided json file
            * @param  {function}   error
            */
            d3.json(url, function(error, graph) {
                if(error) { throw error; }
                /**
                * 检查如果JSON包含 NetworkCollection
                */
                if(graph.type === "NetworkCollection") {
                    var selectGroup = body.append("div").attr("id", "njg-select-group"),
                        select = selectGroup.append("select")
                                            .attr("id", "select");
                        str = graph;
                    select.append("option")
                          .attr({
                              "value": "",
                              "selected": "selected",
                              "name": "default",
                              "disabled": "disabled"
                          })
                          .html("Choose the network to display");
                    graph.collection.forEach(function(structure) {
                        select.append("option").attr("value", structure.type).html(structure.type);
                        // 收集每个网络JSON结构
                        selected[structure.type] = structure;
                    });
                    select.on("change", function() {
                        selectGroup.attr("class", "njg-hidden");
                        // 调用选定的JSON结构
                        processJson(selected[this.options[this.selectedIndex].value]);
                    });
                }
                else {
                    processJson(graph);
                }
            });
        }
     };
     
     // TODO: probably change defaultStyle
     
     d3.focusNetJsonGraph = function(url, opts) {
         /**
          * Default options
          *
          * @param  {string}     el                  "body"      The container element                                  el: "body" [description]
          * @param  {bool}       metadata            true        Display NetJSON metadata at startup?
          * @param  {bool}       defaultStyle        true        Use css style?
          * @param  {bool}       animationAtStart    false       Animate nodes or not on load
          * @param  {array}      scaleExtent         [0.25, 5]   The zoom scale's allowed range. @see {@link https://github.com/mbostock/d3/wiki/Zoom-Behavior#scaleExtent}
          * @param  {int}        charge              -130        The charge strength to the specified value. @see {@link https://github.com/mbostock/d3/wiki/Force-Layout#charge}
          * @param  {int}        linkDistance        50          The target distance between linked nodes to the specified value. @see {@link https://github.com/mbostock/d3/wiki/Force-Layout#linkDistance}
          * @param  {float}      linkStrength        0.2         The strength (rigidity) of links to the specified value in the range. @see {@link https://github.com/mbostock/d3/wiki/Force-Layout#linkStrength}
          * @param  {float}      friction            0.9         The friction coefficient to the specified value. @see {@link https://github.com/mbostock/d3/wiki/Force-Layout#friction}
          * @param  {string}     chargeDistance      Infinity    The maximum distance over which charge forces are applied. @see {@link https://github.com/mbostock/d3/wiki/Force-Layout#chargeDistance}
          * @param  {float}      theta               0.8         The Barnes–Hut approximation criterion to the specified value. @see {@link https://github.com/mbostock/d3/wiki/Force-Layout#theta}
          * @param  {float}      gravity             0.1         The gravitational strength to the specified numerical value. @see {@link https://github.com/mbostock/d3/wiki/Force-Layout#gravity}
          * @param  {int}        circleRadius        8           The radius of circles (nodes) in pixel
          * @param  {string}     labelDx             "0"         SVG dx (distance on x axis) attribute of node labels in graph
          * @param  {string}     labelDy             "-1.3em"    SVG dy (distance on y axis) attribute of node labels in graph
          * @param  {function}   onInit                          Callback function executed on initialization
          * @param  {function}   onLoad                          Callback function executed after data has been loaded
          * @param  {function}   onEnd                           Callback function executed when initial animation is complete
          * @param  {function}   linkDistanceFunc                By default high density areas have longer links
          * @param  {function}   redraw                          Called when panning and zooming
          * @param  {function}   prepareData                     Used to convert NetJSON NetworkGraph to the javascript data
          * @param  {function}   onClickNode                     Called when a node is clicked
          * @param  {function}   onClickLink                     Called when a link is clicked
          */
         opts = d3._extend({
             el: "body",
             metadata: false,//启动时显示元数据
             defaultStyle: false,
             animationAtStart: false,//动画节点是否加载
             scaleExtent: [0.25, 5],//缩放范围
             charge: -130,//电荷强度达到指定值
             linkDistance: 150,//两个节点间的距离
             linkStrength: 0.5,//链接到范围内的强度（刚度）指数。
             friction: 0.9,  // d3 default 摩擦系数指数。
             chargeDistance: Infinity,  // d3 default 施加电荷力的最大距离。
             theta: 0.8,  // d3 default 巴尼斯-小屋近似准则的指数。
             gravity: 0.3, //重力指数
             circleRadius: 8,//半径
             labelDx: "0",//图中节点标号的x（x轴距离）属性
             labelDy: "-1.3em",//图中节点标号的y（y轴距离）属性
             nodeClassProperty: null,
             linkClassProperty: null,
             /**
              * @function
              * @name onInit
              *
              * Callback function executed on initialization
              * @param  {string|object}  url     The netJson remote url or object
              * @param  {object}         opts    The object of passed arguments
              * @return {function}
              */
             onInit: function(url, opts) {},
             /**
              * @function
              * @name onLoad
              *
              * Callback function executed after data has been loaded
              * @param  {string|object}  url     The netJson remote url or object
              * @param  {object}         opts    The object of passed arguments
              * @return {function}
              */
             onLoad: function(url, opts) {},
             /**
              * @function
              * @name onEnd
              *
              * Callback function executed when initial animation is complete
              * @param  {string|object}  url     The netJson remote url or object
              * @param  {object}         opts    The object of passed arguments
              * @return {function}
              */
             onEnd: function(url, opts) {},
             /**
              * @function
              * @name linkDistanceFunc
              *
              * 默认情况下，高密度区域有更长的链接
              */
             linkDistanceFunc: function(d){
                 var val = opts.linkDistance;
                 if(d.source.linkCount >= 4 && d.target.linkCount >= 4) {
                     return val * 2;
                 }
                 return val;
             },
             /**
              * @function
              * @name redraw
              *
              * 调用缩放和平移
              */
             redraw: function() {
                 panner.attr("transform",
                     "translate(" + d3.event.translate + ") " +
                     "scale(" + d3.event.scale + ")"
                 );
             },
             /**
              * @function
              * @name prepareData
              *
              * Convert NetJSON NetworkGraph to the data structure consumed by d3
              * 运用d3将netjson网络图转换为数据结构
              * @param graph {object}
              */
             prepareData: function(graph) {
                 var nodesMap = {},
                     nodes = graph.nodes.slice(), // copy
                     links = graph.links.slice(), // copy
                     nodes_length = graph.nodes.length,
                     links_length = graph.links.length;

                 for(var i = 0; i < nodes_length; i++) {
                     // 计算节点有多少连接
                     nodes[i].linkCount = 0;
                     nodesMap[nodes[i].ip] = i;
                 }
                 for(var c = 0; c < links_length; c++) {
                     var sourceIndex = nodesMap[links[c].source.ip],
                     targetIndex = nodesMap[links[c].target.ip];
                     // 确保源和目标存在
                     if(!nodes[sourceIndex]) { throw("source '" + links[c].source.ip + "' not found"); }
                     if(!nodes[targetIndex]) { throw("target '" + links[c].target.ip + "' not found"); }
                     links[c].source = nodesMap[links[c].source.ip];
                     links[c].target = nodesMap[links[c].target.ip];
                     // 将链接计数添加到两端
                     nodes[sourceIndex].linkCount++;
                     nodes[targetIndex].linkCount++;
                 }
                 return { "nodes": nodes, "links": links };
             },
             /**
              * @function
              * @name onClickNode
              *
              * 单击某个节点时调用
              */
             onClickNode: function(n) {
          		
                 var overlay = d3.select(".njg-overlay"),
                     overlayInner = d3.select(".njg-overlay > .njg-inner"),
                     html = "<p><b>ip</b>: " + n.ip + "</p>";
                     if(n.label) { html += "<p><b>label</b>: " + n.label + "</p>"; }
                     if(n.properties) {
                         for(var key in n.properties) {
                             if(!n.properties.hasOwnProperty(key)) { continue; }
                             html += "<p><b>"+key.replace(/_/g, " ")+"</b>: " + n.properties[key] + "</p>";
                     }
                 }
                 if(n.linkCount) { html += "<p><b>links</b>: " + n.linkCount + "</p><br>"; }
                 if(n.local_addresses) {
                     html += "<p><b>local addresses</b>:<br>" + n.local_addresses.join('<br>') + "</p>";
                 }
                 $.ajax({
     				type : "GET",
     				async : false,
     				url : "../equipment/getInterfacesEntry",
     				data:{
     		        	ipAddress : n.ip,
     					community : 'public'
     		        },
     		       error:function(){
    		        	alert("获取失败");
    		        	return;
    		        },
     				success : function(r) {
     					var rlist=r.page.list;
     					if(rlist==null){
     						return;
     					}
     						for(var i=0;i<rlist.length;i++){
     							 html += "<p><b>接口名</b>: " + rlist[i].interfaceName + "</p>";
     							 if(rlist[i].ifType==1){
     								 html += "<p><b>接口类型</b>: " + 'other' + "</p>"; 
     							 }
     							 if(rlist[i].ifType==2){
     								 html += "<p><b>接口类型</b>: " + 'regular1822' + "</p>"; 
     							 }
     							 if(rlist[i].ifType==3){
     								 html += "<p><b>接口类型</b>: " + 'hdh1822' + "</p>"; 
     							 }
     							 if(rlist[i].ifType==4){
     								 html += "<p><b>接口类型</b>: " + 'ddn-x25' + "</p>"; 
     							 }
     							 if(rlist[i].ifType==5){
     								 html += "<p><b>接口类型</b>: " + 'rfc877-x25' + "</p>"; 
     							 }						 
     							 if(rlist[i].ifType==6){
     								 html += "<p><b>接口类型</b>: " + 'ethernet-csmacd' + "</p>"; 
     							 }
     							 if(rlist[i].ifType==7){
     								 html += "<p><b>接口类型</b>: " + 'iso88023-csmacd' + "</p>"; 
     							 }
     							 if(rlist[i].ifType==8){
     								 html += "<p><b>接口类型</b>: " + 'iso88024-tokenBus' + "</p>"; 
     							 }
     							 if(rlist[i].ifType==9){
     								 html += "<p><b>接口类型</b>: " + 'iso88025-tokenRing' + "</p>"; 
     							 }
     							 if(rlist[i].ifType==10){
     								 html += "<p><b>接口类型</b>: " + 'iso88026-man' + "</p>"; 
     							 }
     							 if(rlist[i].ifType==11){
     								 html += "<p><b>接口类型</b>: " + ' starLan' + "</p>"; 
     							 }
     							 if(rlist[i].ifType==12){
     								 html += "<p><b>接口类型</b>: " + 'proteon-10Mbit' + "</p>"; 
     							 }
     							 if(rlist[i].ifType==13){
     								 html += "<p><b>接口类型</b>: " + 'proteon-80Mbit' + "</p>"; 
     							 }
     							 if(rlist[i].ifType==14){
     								 html += "<p><b>接口类型</b>: " + 'hyperchannel' + "</p>"; 
     							 }
     							 if(rlist[i].ifType==15){
     								 html += "<p><b>接口类型</b>: " + 'fddi' + "</p>"; 
     							 }
     							 if(rlist[i].ifType==16){
     								 html += "<p><b>接口类型</b>: " + 'lapb' + "</p>"; 
     							 }
     							 if(rlist[i].ifType==17){
     								 html += "<p><b>接口类型</b>: " + 'sdlc' + "</p>"; 
     							 }
     							 if(rlist[i].ifType==18){
     								 html += "<p><b>接口类型</b>: " + 'ds1' + "</p>"; 
     							 }
     							 if(rlist[i].ifType==19){
     								 html += "<p><b>接口类型</b>: " + 'e1' + "</p>"; 
     							 }
     							 if(rlist[i].ifType==20){
     								 html += "<p><b>接口类型</b>: " + 'basicISDN' + "</p>"; 
     							 }
     							 if(rlist[i].ifType==21){
     								 html += "<p><b>接口类型</b>: " + 'primaryISDN' + "</p>"; 
     							 }
     							 if(rlist[i].ifType==22){
     								 html += "<p><b>接口类型</b>: " + 'propPointToPointSerial' + "</p>"; 
     							 }
     							 if(rlist[i].ifType==23){
     								 html += "<p><b>接口类型</b>: " + 'ppp' + "</p>"; 
     							 }
     							 if(rlist[i].ifType==24){
     								 html += "<p><b>接口类型</b>: " + 'softwareLoopback' + "</p>"; 
     							 }
     							 if(rlist[i].ifType==25){
     								 html += "<p><b>接口类型</b>: " + 'eon' + "</p>"; 
     							 }
     							 if(rlist[i].ifType==26){
     								 html += "<p><b>接口类型</b>: " + 'ethernet-3Mbit' + "</p>"; 
     							 }
     							 if(rlist[i].ifType==27){
     								 html += "<p><b>接口类型</b>: " + 'nsip' + "</p>"; 
     							 }
     							 if(rlist[i].ifType==28){
     								 html += "<p><b>接口类型</b>: " + 'slip' + "</p>"; 
     							 }
     							 if(rlist[i].ifType==29){
     								 html += "<p><b>接口类型</b>: " + 'ultra' + "</p>"; 
     							 }
     							 if(rlist[i].ifType==30){
     								 html += "<p><b>接口类型</b>: " + 'ds3' + "</p>"; 
     							 }
     							 if(rlist[i].ifType==31){
     								 html += "<p><b>接口类型</b>: " + 'sip' + "</p>"; 
     							 }
     							 if(rlist[i].ifType==32){
     								 html += "<p><b>接口类型</b>: " + 'frame-relay' + "</p>"; 
     							 }
     							 html += "<p><b>最大数据包</b>: " + rlist[i].ifMtu + "</p>"; 
     							 html += "<p><b>带宽</b>: " + rlist[i].ifLastChange + "</p>"; 
     							 html += "<p><b>接收字节数</b>: " + rlist[i].ifInOctets + "</p>"; 
     							 html += "<p><b>接收数据包数</b>: " + rlist[i].ifInUcastPkts + "</p>";   							
     							 html += "<p><b>丢包数</b>: " + rlist[i].ifInUnknownProtos + "</p>"; 
     							 html += "<p><b>发出字节数</b>: " + rlist[i].ifOutOctets + "</p>"; 
     							 html += "<p><b>发出数据包数</b>: " + rlist[i].ifOutUcastPkts + "</p><br>"; 

     					}
     				}
     			});
                 overlayInner.html(html);

                 overlay.classed("njg-hidden", false);
                 overlay.style("display", "block");
                 overlay.style("height", "450px");
                 overlay.style("overflow", "scroll");
                 // 将当前节点设置为“open”样式
                 removeOpenClass();
                 $('.njg-overlay').find('.njg-close').bind('click',function(){
                 	 html = "";
                 	window.clearInterval(timer1);
                 })
                 d3.select(this).classed("njg-open", true);
                 
             	window.clearInterval(timer1);
             	timer1 = window.setInterval(function() {           		
                 var overlay = d3.select(".njg-overlay"),
                     overlayInner = d3.select(".njg-overlay > .njg-inner"),
                     html = "<p><b>ip</b>: " + n.ip + "</p>";
                     if(n.label) { html += "<p><b>label</b>: " + n.label + "</p>"; }
                     if(n.properties) {
                         for(var key in n.properties) {
                             if(!n.properties.hasOwnProperty(key)) { continue; }
                             html += "<p><b>"+key.replace(/_/g, " ")+"</b>: " + n.properties[key] + "</p>";
                     }
                 }
                 if(n.linkCount) { html += "<p><b>links</b>: " + n.linkCount + "</p><br>"; }
                 if(n.local_addresses) {
                     html += "<p><b>local addresses</b>:<br>" + n.local_addresses.join('<br>') + "</p>";
                 }
                 $.ajax({
     				type : "GET",
     				async : false,
     				url : "../equipment/getInterfacesEntry",
     				data:{
     		        	ipAddress : n.ip,
     					community : 'public'
     		        },
     		       error:function(){
    		        	alert("获取失败");
    		        	window.clearInterval(timer1);
    		        	return;
    		        },
     				success : function(r) {
     					var rlist=r.page.list;
     					if(rlist==null){
     						return;
     					}
     						for(var i=0;i<rlist.length;i++){
     							 html += "<p><b>接口名</b>: " + rlist[i].interfaceName + "</p>";
     							 if(rlist[i].ifType==1){
     								 html += "<p><b>接口类型</b>: " + 'other' + "</p>"; 
     							 }
     							 if(rlist[i].ifType==2){
     								 html += "<p><b>接口类型</b>: " + 'regular1822' + "</p>"; 
     							 }
     							 if(rlist[i].ifType==3){
     								 html += "<p><b>接口类型</b>: " + 'hdh1822' + "</p>"; 
     							 }
     							 if(rlist[i].ifType==4){
     								 html += "<p><b>接口类型</b>: " + 'ddn-x25' + "</p>"; 
     							 }
     							 if(rlist[i].ifType==5){
     								 html += "<p><b>接口类型</b>: " + 'rfc877-x25' + "</p>"; 
     							 }						 
     							 if(rlist[i].ifType==6){
     								 html += "<p><b>接口类型</b>: " + 'ethernet-csmacd' + "</p>"; 
     							 }
     							 if(rlist[i].ifType==7){
     								 html += "<p><b>接口类型</b>: " + 'iso88023-csmacd' + "</p>"; 
     							 }
     							 if(rlist[i].ifType==8){
     								 html += "<p><b>接口类型</b>: " + 'iso88024-tokenBus' + "</p>"; 
     							 }
     							 if(rlist[i].ifType==9){
     								 html += "<p><b>接口类型</b>: " + 'iso88025-tokenRing' + "</p>"; 
     							 }
     							 if(rlist[i].ifType==10){
     								 html += "<p><b>接口类型</b>: " + 'iso88026-man' + "</p>"; 
     							 }
     							 if(rlist[i].ifType==11){
     								 html += "<p><b>接口类型</b>: " + ' starLan' + "</p>"; 
     							 }
     							 if(rlist[i].ifType==12){
     								 html += "<p><b>接口类型</b>: " + 'proteon-10Mbit' + "</p>"; 
     							 }
     							 if(rlist[i].ifType==13){
     								 html += "<p><b>接口类型</b>: " + 'proteon-80Mbit' + "</p>"; 
     							 }
     							 if(rlist[i].ifType==14){
     								 html += "<p><b>接口类型</b>: " + 'hyperchannel' + "</p>"; 
     							 }
     							 if(rlist[i].ifType==15){
     								 html += "<p><b>接口类型</b>: " + 'fddi' + "</p>"; 
     							 }
     							 if(rlist[i].ifType==16){
     								 html += "<p><b>接口类型</b>: " + 'lapb' + "</p>"; 
     							 }
     							 if(rlist[i].ifType==17){
     								 html += "<p><b>接口类型</b>: " + 'sdlc' + "</p>"; 
     							 }
     							 if(rlist[i].ifType==18){
     								 html += "<p><b>接口类型</b>: " + 'ds1' + "</p>"; 
     							 }
     							 if(rlist[i].ifType==19){
     								 html += "<p><b>接口类型</b>: " + 'e1' + "</p>"; 
     							 }
     							 if(rlist[i].ifType==20){
     								 html += "<p><b>接口类型</b>: " + 'basicISDN' + "</p>"; 
     							 }
     							 if(rlist[i].ifType==21){
     								 html += "<p><b>接口类型</b>: " + 'primaryISDN' + "</p>"; 
     							 }
     							 if(rlist[i].ifType==22){
     								 html += "<p><b>接口类型</b>: " + 'propPointToPointSerial' + "</p>"; 
     							 }
     							 if(rlist[i].ifType==23){
     								 html += "<p><b>接口类型</b>: " + 'ppp' + "</p>"; 
     							 }
     							 if(rlist[i].ifType==24){
     								 html += "<p><b>接口类型</b>: " + 'softwareLoopback' + "</p>"; 
     							 }
     							 if(rlist[i].ifType==25){
     								 html += "<p><b>接口类型</b>: " + 'eon' + "</p>"; 
     							 }
     							 if(rlist[i].ifType==26){
     								 html += "<p><b>接口类型</b>: " + 'ethernet-3Mbit' + "</p>"; 
     							 }
     							 if(rlist[i].ifType==27){
     								 html += "<p><b>接口类型</b>: " + 'nsip' + "</p>"; 
     							 }
     							 if(rlist[i].ifType==28){
     								 html += "<p><b>接口类型</b>: " + 'slip' + "</p>"; 
     							 }
     							 if(rlist[i].ifType==29){
     								 html += "<p><b>接口类型</b>: " + 'ultra' + "</p>"; 
     							 }
     							 if(rlist[i].ifType==30){
     								 html += "<p><b>接口类型</b>: " + 'ds3' + "</p>"; 
     							 }
     							 if(rlist[i].ifType==31){
     								 html += "<p><b>接口类型</b>: " + 'sip' + "</p>"; 
     							 }
     							 if(rlist[i].ifType==32){
     								 html += "<p><b>接口类型</b>: " + 'frame-relay' + "</p>"; 
     							 }
     							
     							 html += "<p><b>最大数据包</b>: " + rlist[i].ifMtu + "</p>"; 
     							 html += "<p><b>带宽</b>: " + rlist[i].ifLastChange + "</p>"; 
     							 html += "<p><b>接收字节数</b>: " + rlist[i].ifInOctets + "</p>"; 
     							 html += "<p><b>接收数据包数</b>: " + rlist[i].ifInUcastPkts + "</p>";   							
     							 html += "<p><b>丢包数</b>: " + rlist[i].ifInUnknownProtos + "</p>"; 
     							 html += "<p><b>发出字节数</b>: " + rlist[i].ifOutOctets + "</p>"; 
     							 html += "<p><b>发出数据包数</b>: " + rlist[i].ifOutUcastPkts + "</p><br>"; 

     					}
     				}
     			});
                 overlayInner.html(html);
             	}, 3000);
                 overlay.classed("njg-hidden", false);
                 overlay.style("display", "block");
                 overlay.style("height", "450px");
                 overlay.style("overflow", "scroll");
                 // 将当前节点设置为“open”样式
                 removeOpenClass();
                 $('.njg-overlay').find('.njg-close').bind('click',function(){
                 	 html = "";
                 	window.clearInterval(timer1);
                 })
                 d3.select(this).classed("njg-open", true);
             	
             },
             /**
              * @function
              * @name onClickLink
              *
              * 单击某个链路时调用
              */
             onClickLink: function(l) {
        
                 var overlay = d3.select(".njg-overlay"),
                     overlayInner = d3.select(".njg-overlay > .njg-inner"),
                     html = "<p><b>source</b>: " + (l.source.label || l.source.ip) + "</p>";
                     
                 if(l.properties) {
                     for(var key in l.properties) {
                         if(!l.properties.hasOwnProperty(key)) { continue; }
                         html += "<p><b>"+ key.replace(/_/g, " ") +"</b>: " + l.properties[key] + "</p>";
                     }
                 }
                 $.ajax({
  				type : "GET",
  				async : false,
  				url : "../equipment/getLinkInterfacesEntry",
  				data:{
  		        	ipAddress1 : l.source.ip,
  		        	ipAddress2 : l.target.ip,
  					community : 'public'
  		        },
  		      error:function(){
		        	alert("获取失败");
		        	return;
		        },
  				success : function(r) {	
  					var rlist=r.result;
  					for(var i=0;i<rlist.length;i++){
  						if(i==1){
 							html += "<p><b>target</b>: " + (l.target.label || l.target.ip) + "</p>";
 						}
 						 html += "<p><b>接口名</b>: " + rlist[i].interfaceName + "</p>";
 						 if(rlist[i].ifType==1){
 							 html += "<p><b>接口类型</b>: " + 'other' + "</p>"; 
 						 }
 						 if(rlist[i].ifType==2){
 							 html += "<p><b>接口类型</b>: " + 'regular1822' + "</p>"; 
 						 }
 						 if(rlist[i].ifType==3){
 							 html += "<p><b>接口类型</b>: " + 'hdh1822' + "</p>"; 
 						 }
 						 if(rlist[i].ifType==4){
 							 html += "<p><b>接口类型</b>: " + 'ddn-x25' + "</p>"; 
 						 }
 						 if(rlist[i].ifType==5){
 							 html += "<p><b>接口类型</b>: " + 'rfc877-x25' + "</p>"; 
 						 }						 
 						 if(rlist[i].ifType==6){
 							 html += "<p><b>接口类型</b>: " + 'ethernet-csmacd' + "</p>"; 
 						 }
 						 if(rlist[i].ifType==7){
 							 html += "<p><b>接口类型</b>: " + 'iso88023-csmacd' + "</p>"; 
 						 }
 						 if(rlist[i].ifType==8){
 							 html += "<p><b>接口类型</b>: " + 'iso88024-tokenBus' + "</p>"; 
 						 }
 						 if(rlist[i].ifType==9){
 							 html += "<p><b>接口类型</b>: " + 'iso88025-tokenRing' + "</p>"; 
 						 }
 						 if(rlist[i].ifType==10){
 							 html += "<p><b>接口类型</b>: " + 'iso88026-man' + "</p>"; 
 						 }
 						 if(rlist[i].ifType==11){
 							 html += "<p><b>接口类型</b>: " + ' starLan' + "</p>"; 
 						 }
 						 if(rlist[i].ifType==12){
 							 html += "<p><b>接口类型</b>: " + 'proteon-10Mbit' + "</p>"; 
 						 }
 						 if(rlist[i].ifType==13){
 							 html += "<p><b>接口类型</b>: " + 'proteon-80Mbit' + "</p>"; 
 						 }
 						 if(rlist[i].ifType==14){
 							 html += "<p><b>接口类型</b>: " + 'hyperchannel' + "</p>"; 
 						 }
 						 if(rlist[i].ifType==15){
 							 html += "<p><b>接口类型</b>: " + 'fddi' + "</p>"; 
 						 }
 						 if(rlist[i].ifType==16){
 							 html += "<p><b>接口类型</b>: " + 'lapb' + "</p>"; 
 						 }
 						 if(rlist[i].ifType==17){
 							 html += "<p><b>接口类型</b>: " + 'sdlc' + "</p>"; 
 						 }
 						 if(rlist[i].ifType==18){
 							 html += "<p><b>接口类型</b>: " + 'ds1' + "</p>"; 
 						 }
 						 if(rlist[i].ifType==19){
 							 html += "<p><b>接口类型</b>: " + 'e1' + "</p>"; 
 						 }
 						 if(rlist[i].ifType==20){
 							 html += "<p><b>接口类型</b>: " + 'basicISDN' + "</p>"; 
 						 }
 						 if(rlist[i].ifType==21){
 							 html += "<p><b>接口类型</b>: " + 'primaryISDN' + "</p>"; 
 						 }
 						 if(rlist[i].ifType==22){
 							 html += "<p><b>接口类型</b>: " + 'propPointToPointSerial' + "</p>"; 
 						 }
 						 if(rlist[i].ifType==23){
 							 html += "<p><b>接口类型</b>: " + 'ppp' + "</p>"; 
 						 }
 						 if(rlist[i].ifType==24){
 							 html += "<p><b>接口类型</b>: " + 'softwareLoopback' + "</p>"; 
 						 }
 						 if(rlist[i].ifType==25){
 							 html += "<p><b>接口类型</b>: " + 'eon' + "</p>"; 
 						 }
 						 if(rlist[i].ifType==26){
 							 html += "<p><b>接口类型</b>: " + 'ethernet-3Mbit' + "</p>"; 
 						 }
 						 if(rlist[i].ifType==27){
 							 html += "<p><b>接口类型</b>: " + 'nsip' + "</p>"; 
 						 }
 						 if(rlist[i].ifType==28){
 							 html += "<p><b>接口类型</b>: " + 'slip' + "</p>"; 
 						 }
 						 if(rlist[i].ifType==29){
 							 html += "<p><b>接口类型</b>: " + 'ultra' + "</p>"; 
 						 }
 						 if(rlist[i].ifType==30){
 							 html += "<p><b>接口类型</b>: " + 'ds3' + "</p>"; 
 						 }
 						 if(rlist[i].ifType==31){
 							 html += "<p><b>接口类型</b>: " + 'sip' + "</p>"; 
 						 }
 						 if(rlist[i].ifType==32){
 							 html += "<p><b>接口类型</b>: " + 'frame-relay' + "</p>"; 
 						 }
 						 html += "<p><b>最大数据包</b>: " + rlist[i].ifMtu + "</p>"; 
 						 html += "<p><b>带宽</b>: " + rlist[i].ifLastChange + "</p>"; 
 						 html += "<p><b>接收字节数</b>: " + rlist[i].ifInOctets + "</p>"; 
 						 html += "<p><b>接收数据包数</b>: " + rlist[i].ifInUcastPkts + "</p>";   							
 						 html += "<p><b>丢包数</b>: " + rlist[i].ifInUnknownProtos + "</p>"; 
 						 html += "<p><b>发出字节数</b>: " + rlist[i].ifOutOctets + "</p>"; 
 						 html += "<p><b>发出数据包数</b>: " + rlist[i].ifOutUcastPkts + "</p><br>"; 

  						}
  					}
  		        });
                 overlayInner.html(html);
                 overlay.classed("njg-hidden", false);
                 overlay.style("display", "block");
                 overlay.style("height", "450px");
                 overlay.style("overflow", "scroll");
                 // 将当前连接设置为“open”样式
                 removeOpenClass(); 
                 $('.njg-overlay').find('.njg-close').bind('click',function(){
                 	 html = "";
                 	window.clearInterval(timer1);
                 })
                 d3.select(this).classed("njg-open", true);
                 window.clearInterval(timer1);
              	timer1 = window.setInterval(function() {           		
              		var overlay = d3.select(".njg-overlay"),
                     overlayInner = d3.select(".njg-overlay > .njg-inner"),
                     html = "<p><b>source</b>: " + (l.source.label || l.source.ip) + "</p>";
                     
                     if(l.properties) {
                     	for(var key in l.properties) {
                     		if(!l.properties.hasOwnProperty(key)) { continue; }
                     		html += "<p><b>"+ key.replace(/_/g, " ") +"</b>: " + l.properties[key] + "</p>";
                     	}
                     }
                     $.ajax({
          				type : "GET",
          				async : false,
          				url : "../equipment/getLinkInterfacesEntry",
          				data:{
          		        	ipAddress1 : l.source.ip,
          		        	ipAddress2 : l.target.ip,
          					community : 'public'
          		        },
          		      error:function(){
       		        	alert("获取失败");
       		        	window.clearInterval(timer1);
       		        	return;
       		        },
          				success : function(r) {	
          					var rlist=r.result;
          					for(var i=0;i<rlist.length;i++){
          						if(i==1){
         							html += "<p><b>target</b>: " + (l.target.label || l.target.ip) + "</p>";
         						}
         						 html += "<p><b>接口名</b>: " + rlist[i].interfaceName + "</p>";
         						 if(rlist[i].ifType==1){
         							 html += "<p><b>接口类型</b>: " + 'other' + "</p>"; 
         						 }
         						 if(rlist[i].ifType==2){
         							 html += "<p><b>接口类型</b>: " + 'regular1822' + "</p>"; 
         						 }
         						 if(rlist[i].ifType==3){
         							 html += "<p><b>接口类型</b>: " + 'hdh1822' + "</p>"; 
         						 }
         						 if(rlist[i].ifType==4){
         							 html += "<p><b>接口类型</b>: " + 'ddn-x25' + "</p>"; 
         						 }
         						 if(rlist[i].ifType==5){
         							 html += "<p><b>接口类型</b>: " + 'rfc877-x25' + "</p>"; 
         						 }						 
         						 if(rlist[i].ifType==6){
         							 html += "<p><b>接口类型</b>: " + 'ethernet-csmacd' + "</p>"; 
         						 }
         						 if(rlist[i].ifType==7){
         							 html += "<p><b>接口类型</b>: " + 'iso88023-csmacd' + "</p>"; 
         						 }
         						 if(rlist[i].ifType==8){
         							 html += "<p><b>接口类型</b>: " + 'iso88024-tokenBus' + "</p>"; 
         						 }
         						 if(rlist[i].ifType==9){
         							 html += "<p><b>接口类型</b>: " + 'iso88025-tokenRing' + "</p>"; 
         						 }
         						 if(rlist[i].ifType==10){
         							 html += "<p><b>接口类型</b>: " + 'iso88026-man' + "</p>"; 
         						 }
         						 if(rlist[i].ifType==11){
         							 html += "<p><b>接口类型</b>: " + ' starLan' + "</p>"; 
         						 }
         						 if(rlist[i].ifType==12){
         							 html += "<p><b>接口类型</b>: " + 'proteon-10Mbit' + "</p>"; 
         						 }
         						 if(rlist[i].ifType==13){
         							 html += "<p><b>接口类型</b>: " + 'proteon-80Mbit' + "</p>"; 
         						 }
         						 if(rlist[i].ifType==14){
         							 html += "<p><b>接口类型</b>: " + 'hyperchannel' + "</p>"; 
         						 }
         						 if(rlist[i].ifType==15){
         							 html += "<p><b>接口类型</b>: " + 'fddi' + "</p>"; 
         						 }
         						 if(rlist[i].ifType==16){
         							 html += "<p><b>接口类型</b>: " + 'lapb' + "</p>"; 
         						 }
         						 if(rlist[i].ifType==17){
         							 html += "<p><b>接口类型</b>: " + 'sdlc' + "</p>"; 
         						 }
         						 if(rlist[i].ifType==18){
         							 html += "<p><b>接口类型</b>: " + 'ds1' + "</p>"; 
         						 }
         						 if(rlist[i].ifType==19){
         							 html += "<p><b>接口类型</b>: " + 'e1' + "</p>"; 
         						 }
         						 if(rlist[i].ifType==20){
         							 html += "<p><b>接口类型</b>: " + 'basicISDN' + "</p>"; 
         						 }
         						 if(rlist[i].ifType==21){
         							 html += "<p><b>接口类型</b>: " + 'primaryISDN' + "</p>"; 
         						 }
         						 if(rlist[i].ifType==22){
         							 html += "<p><b>接口类型</b>: " + 'propPointToPointSerial' + "</p>"; 
         						 }
         						 if(rlist[i].ifType==23){
         							 html += "<p><b>接口类型</b>: " + 'ppp' + "</p>"; 
         						 }
         						 if(rlist[i].ifType==24){
         							 html += "<p><b>接口类型</b>: " + 'softwareLoopback' + "</p>"; 
         						 }
         						 if(rlist[i].ifType==25){
         							 html += "<p><b>接口类型</b>: " + 'eon' + "</p>"; 
         						 }
         						 if(rlist[i].ifType==26){
         							 html += "<p><b>接口类型</b>: " + 'ethernet-3Mbit' + "</p>"; 
         						 }
         						 if(rlist[i].ifType==27){
         							 html += "<p><b>接口类型</b>: " + 'nsip' + "</p>"; 
         						 }
         						 if(rlist[i].ifType==28){
         							 html += "<p><b>接口类型</b>: " + 'slip' + "</p>"; 
         						 }
         						 if(rlist[i].ifType==29){
         							 html += "<p><b>接口类型</b>: " + 'ultra' + "</p>"; 
         						 }
         						 if(rlist[i].ifType==30){
         							 html += "<p><b>接口类型</b>: " + 'ds3' + "</p>"; 
         						 }
         						 if(rlist[i].ifType==31){
         							 html += "<p><b>接口类型</b>: " + 'sip' + "</p>"; 
         						 }
         						 if(rlist[i].ifType==32){
         							 html += "<p><b>接口类型</b>: " + 'frame-relay' + "</p>"; 
         						 }
         						 html += "<p><b>最大数据包</b>: " + rlist[i].ifMtu + "</p>"; 
         						 html += "<p><b>带宽</b>: " + rlist[i].ifLastChange + "</p>"; 
         						 html += "<p><b>接收字节数</b>: " + rlist[i].ifInOctets + "</p>"; 
         						 html += "<p><b>接收数据包数</b>: " + rlist[i].ifInUcastPkts + "</p>";   							
         						 html += "<p><b>丢包数</b>: " + rlist[i].ifInUnknownProtos + "</p>"; 
         						 html += "<p><b>发出字节数</b>: " + rlist[i].ifOutOctets + "</p>"; 
         						 html += "<p><b>发出数据包数</b>: " + rlist[i].ifOutUcastPkts + "</p><br>"; 

          						}
          					}
          		        });
                     	overlayInner.html(html);
              			}, 3000);
              	 overlay.classed("njg-hidden", false);
                  overlay.style("display", "block");
                  overlay.style("height", "450px");
                  overlay.style("overflow", "scroll");
                  // 将当前节点设置为“open”样式
                  removeOpenClass();
                  $('.njg-overlay').find('.njg-close').bind('click',function(){
                  	 html = "";
                  	window.clearInterval(timer1);
                  })
                  d3.select(this).classed("njg-open", true);
              	
              },
         }, opts);

         // init callback
         opts.onInit(url, opts);

         if(!opts.animationAtStart) {
             opts.linkStrength = 1     ;//强度指数
             opts.friction = 0.3;//摩擦系数
             opts.gravity = 0.1;//重力系数
         }
         if(opts.el == "body") {//设置body大小
             var body = d3.select(opts.el),
                 rect = body.node().getBoundingClientRect();
             if (d3._pxToNumber(d3.select("body").style("height")) < 60) {
                 body.style("height", d3._windowHeight() - rect.top - rect.bottom + "px");
             }
         }
         var el = d3.select(opts.el).style("position", "relative"),
             width = d3._pxToNumber(el.style('width')),
             //height = d3._pxToNumber(el.style('height')),
             height = 500;
             force = d3.layout.force()
                       .charge(opts.charge)
                       .linkStrength(opts.linkStrength)
                       .linkDistance(opts.linkDistanceFunc)
                       .friction(opts.friction)
                       .chargeDistance(opts.chargeDistance)
                       .theta(opts.theta)
                       .gravity(opts.gravity)
                       // 宽度很容易获得，如果高度是0，取body的高度。
                       .size([width, height]),
             zoom = d3.behavior.zoom().scaleExtent(opts.scaleExtent),
             // panner is 元素允许缩放和平移
             panner = el.append("svg")
                        .attr("width", width)
                        .attr("height", height)
                        .call(zoom.on("zoom", opts.redraw))
                        .html("<style type='text/css'>.njg-overlay{background: #fbfbfb;border-radius: 2px;border: 1px solid #ccc;color: #6d6357;font-family: Arial, sans-serif;font-family: sans-serif;font-size: 14px;line-height: 20px;height: auto;max-width: 400px;min-width: 200px;padding: 0 15px;right: 10px;top: 10px;width: auto;}.njg-metadata{background: #fbfbfb;border-radius: 2px;border: 1px solid #ccc;color: #6d6357;display: none;font-family: Arial, sans-serif;font-family: sans-serif;font-size: 14px;height: auto;left: 10px;max-width: 500px;min-width: 200px;padding: 0 15px;top: 10px;width: auto;}.njg-node{stroke-opacity: 0.5;stroke-width: 7px;stroke: #fff;}.njg-node:hover,.njg-node.njg-open {stroke: rgba(0, 0, 0, 0.2);}.njg-link{cursor: pointer;stroke: #999;stroke-width: 2;stroke-opacity: 0.25;}.njg-link:hover,.njg-link.njg-open{stroke-width: 4 !important;stroke-opacity: 0.5;}.njg-hidden {    display: none !important;    visibility: hidden !important;}.njg-tooltip{    font-family: sans-serif;    font-size: 10px;    fill: #000;    opacity: 0.5;    text-anchor: middle;}.njg-overlay{    display: none;    position: absolute;    z-index: 11;}.njg-close{    cursor: pointer;    position: absolute;    right: 10px;    top: 10px;}.njg-close:before { content: '\\2716'; }.njg-metadata{    display: none;    position: absolute;    z-index: 12;}.njg-node{ cursor: pointer }.njg-link{ cursor: pointer }#njg-select-group {    text-align: center;    box-shadow: 0 0 10px #ccc;    position: fixed;    left: 50%;    top: 50%;    width: 50%;    margin-top: -7.5em;    margin-left: -25%;    padding: 5em 2em;}#njg-select-group select {    font-size: 2em;    padding: 10px 15px;    width: 50%;    cursor: pointer;}#njg-select-group option {    padding: 0.5em;}#njg-select-group option[value=''] {    color: #aaa;}</style>")
                        .append("g")
                        .style("position", "absolute"),
             svg = d3.select(opts.el + " svg"),
             drag = force.drag(),
             //overlay节点和线的框（右）   metadata网络框（左）
             overlay = d3.select(opts.el).append("div").attr("class", "njg-overlay"),
             closeOverlay = overlay.append("a").attr("class", "njg-close"),
             overlayInner = overlay.append("div").attr("class", "njg-inner"),
             metadata = d3.select(opts.el).append("div").attr("class", "njg-metadata"),
             metadataInner = metadata.append("div").attr("class", "njg-inner"),
             closeMetadata = metadata.append("a").attr("class", "njg-close"),
             // 未分组网络的容器
             str = [],
             selected = [],
             /**
              * @function
              * @name removeOpenClass
              *
              * 从节点和连接中删除“open”样式
              */
             removeOpenClass = function () {
                 d3.selectAll("svg .njg-open").classed("njg-open", false);
             };
             processJson = function(graph) {
                 /**
                  * Init netJsonGraph
                  */
                 init = function(url, opts) {
                     d3.netJsonGraph(url, opts);
                 };
                 /**
                  * 删除所有实例
                  */
                 destroy = function() {
                     force.stop();
                     d3.select("#selectGroup").remove();
                     d3.select(".njg-overlay").remove();
                     d3.select(".njg-metadata").remove();
                     overlay.remove();
                     overlayInner.remove();
                     metadata.remove();
                     svg.remove();
                     node.remove();
                     link.remove();
                     nodes = [];
                     links = [];
                 };
                 /**
                  * Destroy and e-init all instances
                  * @return {[type]} [description]
                  */
                 reInit = function() {
                     destroy();
                     init(url, opts);
                 };

                 var data = opts.prepareData(graph),
                     links = data.links,
                     nodes = data.nodes;

                 // 在拖动时禁用一些转换
                 drag.on('dragstart', function(n){
                     d3.event.sourceEvent.stopPropagation();
                     zoom.on('zoom', null);
                 })
                 // 停止拖动时重新启用转换
                 .on('dragend', function(n){
                     zoom.on('zoom', opts.redraw);
                 })
                 .on("drag", function(d) {
                     // 避免平移冲突
                     d3.select(this).attr("x", d.x = d3.event.x).attr("y", d.y = d3.event.y);
                 });

                 force.nodes(nodes).links(links).start();

                 var link = panner.selectAll(".link")
                                  .data(links)
                                  .enter().append("line")
                                  .attr("class", function (link) {
                                      var baseClass = "njg-link",
                                          addClass = null;
                                          value = link.properties && link.properties[opts.linkClassProperty];
                                      if (opts.linkClassProperty && value) {
                                          // if value is string use that as class
                                          if (typeof(value) === "string") {
                                              addClass = value;
                                          }
                                          else if (typeof(value) === "number") {
                                              addClass = opts.linkClassProperty + value;
                                          }
                                          else if (value === true) {
                                              addClass = opts.linkClassProperty;
                                          }
                                          return baseClass + " " + addClass;
                                      }
                                      return baseClass;
                                  })
                                  .on("click", opts.onClickLink),
                     groups = panner.selectAll(".node")
                                    .data(nodes)
                                    .enter()
                                    .append("g");
                     node = groups.append("image")
                     .attr('xlink:href',function(node){                    			  
          			 if(node.type==2){
                    		 return "data:image/jpg;base64,/9j/4AAQSkZJRgABAQEAHAAcAAD/2wBDAAIBAQIBAQICAgICAgICAwUDAwMDAwYEBAMFBwYHBwcGBwcICQsJCAgKCAcHCg0KCgsMDAwMBwkODw0MDgsMDAz/2wBDAQICAgMDAwYDAwYMCAcIDAwMDAwMDAwMDAwMDAwMDAwMDAwMDAwMDAwMDAwMDAwMDAwMDAwMDAwMDAwMDAwMDAz/wAARCAAgACADASIAAhEBAxEB/8QAHwAAAQUBAQEBAQEAAAAAAAAAAAECAwQFBgcICQoL/8QAtRAAAgEDAwIEAwUFBAQAAAF9AQIDAAQRBRIhMUEGE1FhByJxFDKBkaEII0KxwRVS0fAkM2JyggkKFhcYGRolJicoKSo0NTY3ODk6Q0RFRkdISUpTVFVWV1hZWmNkZWZnaGlqc3R1dnd4eXqDhIWGh4iJipKTlJWWl5iZmqKjpKWmp6ipqrKztLW2t7i5usLDxMXGx8jJytLT1NXW19jZ2uHi4+Tl5ufo6erx8vP09fb3+Pn6/8QAHwEAAwEBAQEBAQEBAQAAAAAAAAECAwQFBgcICQoL/8QAtREAAgECBAQDBAcFBAQAAQJ3AAECAxEEBSExBhJBUQdhcRMiMoEIFEKRobHBCSMzUvAVYnLRChYkNOEl8RcYGRomJygpKjU2Nzg5OkNERUZHSElKU1RVVldYWVpjZGVmZ2hpanN0dXZ3eHl6goOEhYaHiImKkpOUlZaXmJmaoqOkpaanqKmqsrO0tba3uLm6wsPExcbHyMnK0tPU1dbX2Nna4uPk5ebn6Onq8vP09fb3+Pn6/9oADAMBAAIRAxEAPwD9sP2tv2yPhl+wn8G7n4gfFnxdp/g3wnbXMVn9suY5Z3nnlJCQwwwq800hAZtkaMwRHcgKjMPkz/iKN/YT/wCi5f8AlmeIP/kGj/gvp/zZX/2dV4G/9vq+/wCgD4A/4ijf2E/+i5f+WZ4g/wDkGvrP9kn9sj4Z/t2fBu2+IHwm8Xaf4y8J3VzLZ/bLaOWF4J4iA8M0MypNDIAVbZIisUdHAKurH0yvgD/ggX/zep/2dV45/wDbGgDgv+C7n7THw6+JfgT4Y/8ACB/F34Aa98R/2e/jRofjzUPBOr/FLRvD99qR0qS5iuNO825m2W1wJJRu87bsEcnDOFjbz3/iJw+Iv/RAP2f/APxL7wR/8VX1V8cv+Dd39jf9pD4v+IvHnjD4L2moeKvFl9JqerXdt4j1jT0vLmQ5kmMNvdxxK7tlmKoNzMzHLMScPwt/wbOfsT+B/ENpq2i/B/UNH1awkEtre2PjvxHb3Fs46MkiX4ZT7gg0AfOP/ETh8Rf+iAfs/wD/AIl94I/+Kr0L/ghH+0x8Ovhn4E+J3/CefF74AaF8R/2hPjRrnjzT/BOj/FLRvEF9pp1WS2it9O822m2XNwZIjt8ndvEkfCuWjX0zxT/wbOfsT+OPEN3q2tfB/UNY1a/kMt1e33jvxHcXFy56s8j35Zj7kk1ufA3/AIN3f2N/2b/i/wCHfHng/wCC9pp/irwnfR6npN3c+I9Y1BLO5jOY5hDcXckTOjYZSyHayqwwyggA/9k="; 
                    	 }else if(node.type==1){
                    		 return "data:image/jpg;base64,/9j/4AAQSkZJRgABAQEASABIAAD/2wBDAAIBAQIBAQICAgICAgICAwUDAwMDAwYEBAMFBwYHBwcGBwcICQsJCAgKCAcHCg0KCgsMDAwMBwkODw0MDgsMDAz/2wBDAQICAgMDAwYDAwYMCAcIDAwMDAwMDAwMDAwMDAwMDAwMDAwMDAwMDAwMDAwMDAwMDAwMDAwMDAwMDAwMDAwMDAz/wAARCAAgACADASIAAhEBAxEB/8QAHwAAAQUBAQEBAQEAAAAAAAAAAAECAwQFBgcICQoL/8QAtRAAAgEDAwIEAwUFBAQAAAF9AQIDAAQRBRIhMUEGE1FhByJxFDKBkaEII0KxwRVS0fAkM2JyggkKFhcYGRolJicoKSo0NTY3ODk6Q0RFRkdISUpTVFVWV1hZWmNkZWZnaGlqc3R1dnd4eXqDhIWGh4iJipKTlJWWl5iZmqKjpKWmp6ipqrKztLW2t7i5usLDxMXGx8jJytLT1NXW19jZ2uHi4+Tl5ufo6erx8vP09fb3+Pn6/8QAHwEAAwEBAQEBAQEBAQAAAAAAAAECAwQFBgcICQoL/8QAtREAAgECBAQDBAcFBAQAAQJ3AAECAxEEBSExBhJBUQdhcRMiMoEIFEKRobHBCSMzUvAVYnLRChYkNOEl8RcYGRomJygpKjU2Nzg5OkNERUZHSElKU1RVVldYWVpjZGVmZ2hpanN0dXZ3eHl6goOEhYaHiImKkpOUlZaXmJmaoqOkpaanqKmqsrO0tba3uLm6wsPExcbHyMnK0tPU1dbX2Nna4uPk5ebn6Onq8vP09fb3+Pn6/9oADAMBAAIRAxEAPwD9Uv8Agod+0n458J/F/wCBfwV+FurWvhrx98bNeu5H8QXmnrfW+h6HpMKXmpyrGwZGuJFaC3iVxtJumO5CoYc7+zb/AMFf9E/aQ+GHhHUrfw7DoPiz42XXin/hS/hzUL+6DePbHRY2f7VdXC2Rj0rzgu4pL5uyNkZGmYlB03/BQr9lzxx8RPin8EfjJ8KrfS9U+JXwR1+5aDR9Wv2srDXdF1SFbPVbVpVB8qURrDPFKVcK1tjy3LAV8OfET9hvWP2IP+Ci/wCwX8I/CPxBlmsbW8+MD+A7++0gXEng2zu9BjltLSRWmP24Ws0jsGdo/MTbHhAoNAHv/wCx7/wVP8VfHX9p74KapqiyQfC79qbwtqaeGtAmsNuoeAvFPh/zBq+nzziNDcW8ojuik0gDF7PCxxq3zfoVX5//ALIf/BKPxB8BP2nPg1b6ldQzfCv9l3wpqMXg7UTqEk2reMvE2u7jrOq3sbEiCNQ9wscGZDuuy4lIHlr+gFAHyR+1r/wXM/ZX/YZ+NF98O/id8WLPw74z023hubzTYdE1PUns1mQSRCV7S2lRHaMq+xmD7HRsYdSfjf8AaH/4LR/sK/Hn9uv9nX41f8NN/wBk/wDCgv8AhJf+JN/wrvxBP/bv9safHZf6/wCzL5Hk7N/+rk352/JjdXhn/Bbf/glT+3J8av23v2ipvgh4Vs/EXwX/AGiLfwvNr8UOr6Jbvfto1pbpbxS/bpI7iF4rmCSTMBCOkiBmb5kX89P+IXL9uv8A6Ib/AOXn4f8A/k6gD9/v+Io39hT/AKLl/wCWZ4g/+Qa9I/ZJ/wCC5n7K37c/xosvh38MfizZ+IvGepW81zZ6bNomp6a92sKGSURPd20SO6xhn2KxfYjtjCMR+XH7IP7IH7dX7Kf/AAx5/wAYef29/wAMnf8ACaf81X8P2v8AwlX/AAkXm/8ATR/sv2fzf+m3m7f+WeePSv8AgkX/AME7v2qfhx8av2MdF+J/wRs/hz4N/ZRt/Hc134lm8a6Zqj+I28QJN5UUVnaO7wvFJKoyzMjortuQhUYA/9k="; 
                    	 }else{
                    		 return "data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAABoAAAAbCAIAAADtdAg8AAABeUlEQVRIDe2VwaqCQBSGb3o3CiEYIVLQMlxkix4geofeoyfzGeI+gG4lVwqpgRZFQavo/tyB8TSO3oiWuRj+Oec7P6dJz3Tu9/vX+55vwep0Ol2vVyEo3WqaZhiGkHqwS5LE9/39fi9A0m2v15vNZqPRiGYru9vt5nneZrOhabQwHA5ZZLvdCo3HcbxarVRV5SWV3fF4FLwA6bruOA6jD4eDYAceVWhTYleWJYuapkmJMAx5HCmmcSBwh0YVhavuGId1MpnM53O+lYqfv6eekth1u93BYFBHaQQM3XKtcPUW8bF7/Rg/Z/f62Um+isvlstvt2i3BSAGJXRRFTTS3yLKMayokdkApjZHX7/eLohCmE3Xh+v8XZTqdLpdLrLymRVR26ELKua47Ho+xSrNCVfVjLcvCdGRDkVYGQaAoClYaZBo8qmi8Qy/G9XqNq+d8PlOiSWPk4epZLBYUeLBDIk3TPM8p0aRt265PWdGuqfjJePVXPFnQjv0CkymKbVbpYEUAAAAASUVORK5CYII="; 
                    	 }})
                     .on("dblclick",opts.onClickNode)
                     .call(drag);
                                  
                                                     
                   
                     var labels = groups.append('text')//node的名字
                                        .text(function(n){ return n.label || n.id })
                                        .attr('dx', opts.labelDx)
                                        .attr('dy', opts.labelDy)
                                        .attr('class', 'njg-tooltip');
                     
                 // 关闭覆盖
                 closeOverlay.on("click", function() {
                     removeOpenClass();
                     overlay.classed("njg-hidden", true);
                 });
                 // 关闭元数据面板
                 closeMetadata.on("click", function() {
                     // 重新初始化页面
                     if(graph.type === "NetworkCollection") {
                         reInit();
                     }
                     else {
                         removeOpenClass();
                         metadata.classed("njg-hidden", true);
                     }
                 });
                 // default style
                 
                 // into something else
                 if(opts.defaultStyle) {
                     var colors = d3.scale.category20c();
                     node.style({
                         "fill": function(d){ return colors(d.linkCount); },
                         "cursor": "pointer"
                     });
                 }
                 // Metadata style
                 if(opts.metadata) {
                     metadata.attr("class", "njg-metadata").style("display", "block");
                 }

                 var attrs = ["protocol",
                              "version",
                              "revision",
                              "metric",
                              "router_ip",
                              "topology_ip"],
                     html = "";
                 if(graph.label) {
                     html += "<h3>" + graph.label + "</h3>";
                 }
                 for(var i in attrs) {
                     var attr = attrs[i];
                     if(graph[attr]) {
                         html += "<p><b>" + attr + "</b>: <span>" + graph[attr] + "</span></p>";
                     }
                 }
                 // Add nodes and links count
                 html += "<p><b>nodes</b>: <span>" + graph.nodes.length + "</span></p>";
                 html += "<p><b>links</b>: <span>" + graph.links.length + "</span></p>";
                 metadataInner.html(html);
                 metadata.classed("njg-hidden", false);

                 // onLoad callback
                 opts.onLoad(url, opts);

                 force.on("tick", function() {
                     link.attr("x1", function(d) {
                         return d.source.x;
                     })
                     .attr("y1", function(d) {
                         return d.source.y;
                     })
                     .attr("x2", function(d) {
                         return d.target.x;
                     })
                     .attr("y2", function(d) {
                         return d.target.y;
                     });

                     node.attr("x", function(d) {
                         return d.x-15;
                     })
                     .attr("y", function(d) {
                         return d.y-10;
                     });

                     labels.attr("transform", function(d) {
                         return "translate(" + d.x + "," + d.y + ")";
                     });
                     
                    /* img.attr("transform", function(d) {
                     	var ix=d.x-15;
                     	var iy=d.y+30;
                         return "translate(" + ix + "," + iy + ")";
                     });*/
                     
                 })
                 .on("end", function(){
                     force.stop();
                     // onEnd callback
                     opts.onEnd(url, opts);
                 });

                 return force;
             };

         if(typeof(url) === "object") {
             processJson(url);
         }
         else {
             /**
             * 解析提供的JSON文件
             * 并且调用 processJson() function
             *
             * @param  {string}     url         The provided json file
             * @param  {function}   error
             */
             d3.json(url, function(error, graph) {
                 if(error) { throw error; }
                 /**
                 * 检查如果JSON包含 NetworkCollection
                 */
                 if(graph.type === "NetworkCollection") {
                     var selectGroup = body.append("div").attr("id", "njg-select-group"),
                         select = selectGroup.append("select")
                                             .attr("id", "select");
                         str = graph;
                     select.append("option")
                           .attr({
                               "value": "",
                               "selected": "selected",
                               "name": "default",
                               "disabled": "disabled"
                           })
                           .html("Choose the network to display");
                     graph.collection.forEach(function(structure) {
                         select.append("option").attr("value", structure.type).html(structure.type);
                         // 收集每个网络JSON结构
                         selected[structure.type] = structure;
                     });
                     select.on("change", function() {
                         selectGroup.attr("class", "njg-hidden");
                         // 调用选定的JSON结构
                         processJson(selected[this.options[this.selectedIndex].value]);
                     });
                 }
                 else {
                     processJson(graph);
                 }
             });
         }
      };
})();
