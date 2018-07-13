var timer1 = window.setInterval(function() {});
var oldtopo;
var vm = new Vue({
	el:'#rrapp',
	data:{

	},
	//进入页面自动调用
	created: function() {	
		$.ajax({
            type: "POST",
            url: "../topoManage/getTopo",
            success: function(r){
               if(r.code==0){
            	   console.log(JSON.stringify(r.networkGraph));
            	   d3.fixNetJsonGraph(r.networkGraph);//返回的数据不带有坐标      
            	   oldtopo=r.networkGraph;
               }
               if(r.code==1){
            	   alert("请联系管理员应用拓扑");
               }
            }
        });
		
	},
	methods: {
		//获取拓扑
		getTopo : function() {
			$.ajax({
	            type: "POST",
	            url: "../topoManage/flushTopo",
	            success: function(r){
	               if(r.code==2){
	            	   oldtopo=r.networkGraph;	
	            	   d3.netJsonGraph(r.networkGraph);
	            	   $("svg").remove();
	            	   d3.fixNetJsonGraph(oldtopo);
	               }
	            }
	        });
		},
		//刷新
		flushTopo : function() {
			window.clearInterval(timer1);
			var metadata=$(".njg-metadata");
			metadata.remove();			
			var overlay=$(".njg-overlay");
			overlay.remove();
			$("svg").remove();
			vm.getTopo();
			
		},	
		//导出拓扑
		printSvg:function(){  	
        	var svg = d3.select("svg");
        	var serializer = new XMLSerializer();  
            var source = serializer.serializeToString(svg.node());  
            source = '<?xml version="1.0" standalone="no"?>\r\n' + source;         
            var file = new File([source], "网络自发现拓扑.svg", {type: "text/plain;charset=utf-8"});
            saveAs(file);
           
       },
       //集中展示
       showFocus:function(){
    	   window.clearInterval(timer1);
			var metadata=$(".njg-metadata");
			metadata.remove();			
			var overlay=$(".njg-overlay");
			overlay.remove();
			$("svg").remove();    	  
			d3.focusNetJsonGraph(oldtopo);
       },
	}
});
/*var a=setInterval(function() {
	var nodes= $(".njg-node");
	var node;
	//console.log(JSON.stringify(nodes));
	for(var i=0;i<nodes.length;i++){
		if(nodes[i].ip=="10.0.0.1"){
			node=nodes[i];		
		}		
	}
	if(node!=null){
		if(node.style.fill!="white"){
			 node.style.fill="white";
	     }else{
	    	 node.style.fill="red";
	     }
		
	}
	
}, 500)*/