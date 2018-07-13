//右击不出现浏览器菜单
document.oncontextmenu = function() {
    return false;
}
var timer1 = window.setInterval(function() {});
var oldtopo;
var vm = new Vue({
	el:'#rrapp',
	data:{
		node:{
			ip:"",
			label:"",
		},
		link:{
			source:"",
			target:"",
			cost:1,
		},
		nodes:[
		],
		loginName:"",
		type:0,
		title:''

	},
	//进入页面自动调用
	created: function() {//从数据库获取

		$.ajax({
            type: "POST",
            url: "../topoManage/testTopo",
            success: function(r){
               if(r.code==0||r.code==1){
            	  //0是从用户表获取  1是从主表获取 2是从路由获取
            	   d3.fixNetJsonGraph(r.networkGraph);
            	   oldtopo=r.networkGraph;
            	   console.log(JSON.stringify(r.networkGraph));
               }
               if(r.code==2){
            	 	 //路由里的对象没有坐标 数据结构也不同
            	   d3.netJsonGraph(r.networkGraph);
            	   oldtopo=r.networkGraph;         	   
               }
            }
        });		
		
		
	},
	methods: {
		
		//打印
		printSvg:function(){  	
        	var svg = d3.select("svg");
        	var serializer = new XMLSerializer();  
            var source = serializer.serializeToString(svg.node());  
            source = '<?xml version="1.0" standalone="no"?>\r\n' + source;         
            var file = new File([source],"网络自发现拓扑.svg", {type: "text/plain;charset=utf-8"});
            saveAs(file);
           
       },
       //保存
       save :function(){
    	  $.ajax({
	            type: "POST",
	            url: "../topoManage/saveTopo?table=factoryTopo",
	            data:JSON.stringify(oldtopo),
	            success: function(r){
	               if(r.code==0){
	            	   alert("保存成功");
	               }
	            }
	        });
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
       //展示新增框
       	showInsert:function(){
        	vm.reset();
        	
       		$('#editModal').modal('show');
			             
       },
     //展示删除节点框
       showRemoveNode:function(){  	
    	   	vm.reset();
    	   	
    	   	//删除克隆的行
    	   	$('.clone').remove();
    	   	vm.insert("removeNode");
      		$('#removeModal').modal('show');
          
      },
    //展示删除链路框
      showRemoveLink:function(){  	
  	   		vm.reset();
  	   		vm.title="删除链路";
    		$('#removeLink').modal('show');
        
      },
    //展示新增链路框
      showInsertLink:function(){  	
	   		vm.reset();
	   		vm.title="新增链路";
	   		$('#removeLink').modal('show');
      
    },
    //新增节点
      insertNode:function(){  
    	  if(vm.link.source.trim()==""){
			  alert("设备ip不能为空");
			  return;
		  } 
    	  if(vm.type==0){
			  alert("请选择类型");
			  return;
		  }
    	  //获取已有的节点
    	  var nodes=oldtopo.nodes;
    	  //判断节点是否已存在,并且获取目标节点
    	  var flag=0;
 
    	  var targetNode=null;
    	  for(i=0;i<nodes.length;i++){
    		  if(nodes[i].label==vm.link.source){
    			  flag=1;    			  
    		  }
    		  if(vm.link.target.trim()!=""&&nodes[i].label==vm.link.target){
    			  targetNode=nodes[i];
    		  }
    	  }
    	  if(flag==1){
    		  alert("已存在该设备");
    		  return;
    	  }
    	  //如果节点不存在就新生成一个节点
    	  if(flag==0){
    		//生成一个节点
    		  var copyNode;
    		  console.log(JSON.stringify(nodes[0]));
    		  if(nodes[0]!=null){
    			  console.log(111);
    			  copyNode=JSON.parse(JSON.stringify(nodes[0]));    		  
        		  copyNode.ip="";
        		  copyNode.label=vm.link.source;
        		  copyNode.type=vm.type;
        		  copyNode.linkCount=0;
        		  console.log(JSON.stringify(copyNode));
    		  }else{
    			  copyNode = {
    						"ip" : "",
    						"label" :vm.link.source,
    						"type" : vm.type,
    						"linkCount" : 0
    						};
    		  }							   
    	  }
    	  //如果填写了设备就生成一个设备
    	  if(vm.link.target.trim()!=""){
    		  if(targetNode==null){
    			  alert("没有该目标设备");
    			  return;
    		  }
    		  //生成一个链路
    		  var copyLink={
    					"source" : {
    						
    					},
    					"target" : {
    						
    					}
    				};
    		  //记得更改节点linkCount
    		  copyNode.linkCount=1;
    		  copyLink.source= copyNode;
    		  copyLink.target= targetNode;  		  
    		  oldtopo.links[oldtopo.links.length]= copyLink;
    	  }
    	  //因为要先判断节点linkCount是否为1，所以这句写最后
    	  oldtopo.nodes[oldtopo.nodes.length]=copyNode;
    	  vm.redraw();
  		  $('#editModal').modal('hide');
       },
       //删除节点
       removeNode:function(){
    	vm.nodes=[];
    	var nodes=document.getElementsByName("nodeName")
       	for(i=0;i<nodes.length;i++){
       		vm.nodes[vm.nodes.length]=nodes[i].value;
       	}
    	//删除第一个空字符串
       	vm.nodes.splice(0,1);
       	
       	var noexitstNodes = new Array();
       	
       	//遍历用户输入的所有要删除的节点
       	for(i=0;i<vm.nodes.length;i++){
       		var flag=0;
       		//遍历目前topo图里的所有节点
       		for(j=0;j<oldtopo.nodes.length;j++){
       			//确保要删除的节点最多只有一条设备
       			if(oldtopo.nodes[j].label==vm.nodes[i]){
       				flag=1;
       				if(oldtopo.nodes[j].linkCount>1){
       					alert("请先删除该ip下的子设备");
       					return;
       				}else if(oldtopo.nodes[j].linkCount==1){
       					//1条设备的话删除设备和节点
       					for(var k=0;k<oldtopo.links.length;k++){
       						if(oldtopo.links[k].source.label==oldtopo.nodes[j].label){
       							oldtopo.links.splice(k,1);
       							oldtopo.nodes.splice(j,1);
       							continue;
       						}
       					}
       				}else{
       					//无设备只删除节点
       					oldtopo.nodes.splice(j,1);
       					
       				}
       				
       			}	
       		}
       		if(flag==0){
       			/*confirm("不存在该ip"+vm.nodes[i]);*/
       			noexitstNodes.push(vm.nodes[i]);
       		}
       			
       		
       		
       	}
       	if(noexitstNodes.length > 0)
       		alert("设备</br>" + noexitstNodes.join("</br>") + "</br>不存在");
       	
       	vm.redraw();
		$('#removeModal').modal('hide');
             
      },
    //编辑链路
      editLink:function(){   	 
    	  if(vm.title=='新增链路'){
    		  vm.insertLink();
    	  }
    	  if(vm.title=='删除链路'){
    		  vm.removeLink();
    	  }
      },
    //新增链路
      insertLink:function(){
    	  var links=document.getElementsByName("linkIp");
    	  if(links[0].value==links[1].value){
    		  alert("设备填写相同");
			  return;
    	  }
    	  var sourceNode;
    	  var targetNode;
    	  var modleLink={
				"source" : {					
				},
				"target" : {					
				}
			} ;
    		  for(var i=0;i<oldtopo.nodes.length;i++){
        		  if(oldtopo.nodes[i].label==links[0].value){
        			  var sourceNode=oldtopo.nodes[i];
        		  }
        		  if(oldtopo.nodes[i].label==links[1].value){
        			  var targetNode=oldtopo.nodes[i];
        		  }
        		  modleLink.source=sourceNode;
        		  modleLink.target=targetNode;
        		  
        	  }
    		  if(sourceNode==null||targetNode==null){
    			  alert("含有不存在的设备");
    			  return;
    		  }
    		  oldtopo.links.push(modleLink);
    		  vm.redraw();
    		  alert("新增链路成功");
    	  $('#removeLink').modal('hide');
      },
      //删除链路
      removeLink:function(){
    	  var links=document.getElementsByName("linkIp");
    	  if(links[0].value==links[1].value){
    		  alert("设备填写相同");
			  return;
    	  }
    	  var flag=0;
    	  for(var i=0;i<oldtopo.links.length;i++){
    		  
    		  if(oldtopo.links[i].source.label==links[0].value){
    			 if(oldtopo.links[i].target.label==links[1].value){
    				 flag=1;
    				 oldtopo.links.splice(i,1) 
    				 vm.redraw();
    				 alert("删除链路成功");
    				 $('#removeLink').modal('hide');
    				 return;
    			 }
    		  }else if(oldtopo.links[i].source.label==links[1].value){
    			  if(oldtopo.links[i].target.label==links[0].value){
    				 flag=1;
     				 oldtopo.links.splice(i,1);
     				 vm.redraw();
     				 alert("删除链路成功");
     		  		 $('#removeLink').modal('hide');
     				 return;
     			 }
    		  }
    	  }
    	  if(flag==0){
    		  alert("无该ip链路");
    	  }
    		 

    	  $('#removeLink').modal('hide');
      },
      //模态框里新增
      insert:function(id,event){//插入一条空的
			var cloneItem = $('#'+id).find("li:last").clone();
			$(cloneItem).css("display","inline-block");	
			$(cloneItem).attr("class","clone");	
			$(cloneItem).find(".control-label").css("margin-left","14px");
			$(cloneItem).find("input").val("");			
			$($(cloneItem).find(".glyphicon-minus-sign")).bind("click",function(event){
				vm.del(event);
			});			
			$(".list-group").append(cloneItem);
		},
		 //模态框里删除
		del:function(event){//删除一条
			event = event || window.event;
			var $currentItem = $(event.currentTarget); //获取当前 事件的 节点元素
			$currentItem.closest("li").remove();
		},
		//重置所有数据
		reset:function(){
			 var links=document.getElementsByName("linkIp");
			 for(var i=0;i<links.length;i++){
				 links[i].value="";
			 }
			vm.link.source="";
			vm.link.target="";
			vm.type=0;
			
		},
		//重新加载拓扑图
		redraw:function(){
			window.clearInterval(timer1);
			var metadata=$(".njg-metadata");
			metadata.remove();			
			var overlay=$(".njg-overlay");
			overlay.remove();
			$("svg").remove();    	  
			d3.fixNetJsonGraph(oldtopo);
		}
	}
});
