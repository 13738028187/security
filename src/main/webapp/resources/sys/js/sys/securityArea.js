//jqGrid 文档 http://www.helloweba.com/view-blog-162.html
$(function () {
    $("#jqGrid").jqGrid({
        url: '../securityArea/list',
        datatype: "json",
        colModel: [			
        	{ label: '序号', name: 'id', width: 45, key: true },
			{ label: '安全域名称', name: 'name', width: 50 },
			{ label: '安全域优先级', name: 'level', width:50 },
			/*{ label: '接口数量', name: 'size', width:50 },*/
			/*{ label: '包含的接口', name: 'include_interface', width: 50 },*/
			{ label: '操作', name: '', width: 50, sortable:false, formatter: function(value, options, row){
				return '<a class="btn btn-primary btn-xs" href="securityAreaConfig.html?securityAreaId=' + row.id + '">配置</a> <a class="btn btn-primary btn-xs" onclick="vm.showUpdate(' + row.id + ')">编辑</a>';
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
});

var vm = new Vue({
	el : '#rrapp',
	data : {
		securityArea : {
			id:0,
			name : '',
			level : 1,
			/*include_interface : '',*/
		},
		 title:'',
		 interfaces:[],
	},
	methods : {
		showInsert : function(value){
            vm.title='新增安全域';
            vm.securityArea.id=0;
            vm.reset();
            $('#editModal').modal('show');

        },
        showUpdate : function(value){
            vm.reset();
            vm.title='编辑安全域';
            var id=value;
            $.ajax({
                type: "POST",
                url: "../securityArea/getSecurityArea?id="+id,
                success: function(r){
                   if(r.code==0){
                	   vm.securityArea=r.data;
                   }
                }
            });
            $('#editModal').modal('show');

        },
        showInterface : function(id){ 
        	$(".clone").remove();
        	$.ajax({
				url : "../securityArea/getInterface?id=" + id,
				type : 'POST',
				success : function(r) {
					if (r.code === 0) {					
						vm.interfaces=r.list;						
						/*console.log(JSON.stringify(vm.interfaces));*/
						for(i=0;i<vm.interfaces.length;i++){
							vm.insertWithData(vm.interfaces[i].interfaceName);
						}
						
						
					}

				}
			});
            $('#editModal2').modal('show');

        },
		deleted : function() {
			var rowId = $('#jqGrid').jqGrid('getGridParam', 'selarrrow');
			if(rowId == null || rowId.length <= 0) {
				alert("请选中需要删除的数据");
				return;
			} else {
				confirm('确定要删除这些记录？', function() {
					var securityAreaIds = new Array();
					for (var i = 0; i < rowId.length; i++) {
						var rowData = $("#jqGrid").jqGrid('getRowData', rowId[i]);
						securityAreaIds.push(rowData.id);
					}
					$.ajax({
						url : "../securityArea/delete?ids=" + securityAreaIds.join(";"),
						type : 'GET',
						success : function(r) {
							if (r.code === 0) {
								alert('删除成功');
								$("#jqGrid").jqGrid("setGridParam", {
									search : true
								}).trigger("reloadGrid");
							} else {
								alert(r.msg);
							}

						}
					});
				});
			}
		},
		vertify : function() {
			// 判断安全域名称是否为空
			if(vm.securityArea.name == '' || $.trim(vm.securityArea.name).length == 0) {
				alert("请输入安全域名称");
				return false;
			}
			return true;
		},
		insertOrUpdate : function () {

			if(!vm.vertify())
				return;
            var url = vm.securityArea.id == 0 ? "../securityArea/insertSecurityArea" : "../securityArea/updateSecurityArea";

            $.ajax({
                type: "POST",
                url: url,
                data: {
                	id : vm.securityArea.id,
                	name : vm.securityArea.name,
                	level : vm.securityArea.level
                },
                dataType: "json",
                contentType: "application/x-www-form-urlencoded",
                cache: false,
                success: function(r){
                    if(r.code === 0){
                        alert('操作成功');
                        $("#editModal").modal('hide');
                      //重载JQGrid
         		       $("#jqGrid").jqGrid("setGridParam", { search: true }).trigger("reloadGrid", [{ page: 1}]);  
         				
                    }else{
                        alert(r.msg);
                    }	
                }
            });
        },
        insertWithData:function(data){//插入一条有值的
        var d=data.replace(/\s+/g,"");//去除所有空格
			var cloneItem = $(".list-group").find("li:last").clone();
			$(cloneItem).css("display","inline-block");
			$(cloneItem).attr("class","clone");	
			$(cloneItem).find(".control-label").css("margin-left","14px");
			if(d==""){
				$(cloneItem).find("input").val("");
			}else{
				$(cloneItem).find("input").val(d);
			}			
			$($(cloneItem).find(".glyphicon-minus-sign")).bind("click",function(event){
				vm.del(event);
			});			
			$(".list-group").append(cloneItem);
		},
		insert:function(event){//插入一条空的
			var cloneItem = $(".list-group").find("li:last").clone();
			$(cloneItem).css("display","inline-block");	
			$(cloneItem).attr("class","clone");	
			$(cloneItem).find(".control-label").css("margin-left","14px");
			$(cloneItem).find("input").val("");			
			$($(cloneItem).find(".glyphicon-minus-sign")).bind("click",function(event){
				vm.del(event);
			});			
			$(".list-group").append(cloneItem);
		},
		del:function(event){//删除一条
			event = event || window.event;
			var $currentItem = $(event.currentTarget); //获取当前 事件的 节点元素
			$currentItem.closest("li").remove();
		},
		
		 /* savePesticide:function(){//新增药物
			$('#myConfirmModal').modal('show');
			
		},
		
		savePesticides: function(){//保存药物
			var pesticideName = jQuery.trim($("#pesticideName").val());
			if(pesticideName){
				pesticideName = pesticideName;
			}
			
			
			var pesticideStatus = jQuery.trim($("#pesticideStatuS").val());
			if(pesticideStatus && pesticideStatus >-1)
				pesticideStatus = pesticideStatus;
		   else	
			   pesticideStatus = null;
			
			
			var pesticideUnit = jQuery.trim($("#pesticideUnit").val());
			if(pesticideUnit && pesticideUnit >-1)
				pesticideUnit = pesticideUnit;
		   else	
			   pesticideStatus = null;

			
			var pesticideUnitPrice = jQuery.trim($("#pesticideUnitPrice").val());
			if(pesticideUnitPrice){
				pesticideUnitPrice = pesticideUnitPrice;
			}
			
			var pesticideDescription = jQuery.trim($("#pesticideDescription").val());
			if(pesticideDescription){
				pesticideDescription = pesticideDescription;
			}
			
			var dosagePerMu = jQuery.trim($("#dosagePerMu").val());
			if(dosagePerMu){
				dosagePerMu = dosagePerMu;
			}
			$.ajax({
				url: '../rb/pesticideSolution/savePesticide',
				 type:'POST',
				 data:
					 JSON.stringify({
					 pesticideUnit:pesticideUnit,//药物单位
					 pesticideName:pesticideName,//药物名称
					 pesticideStatus:pesticideStatus,//药物状态
					 pesticideUnitPrice:pesticideUnitPrice,//每单位价格
					 pesticideDescription:pesticideDescription,//药物描述
					 
				 }),
				 success:function(){
					 
				 }
			});
			
			
		}*/
		searcher: function(){
			var param={};
			var name=$('#name').val().trim()			
			param.name=name;
			var postData = $("#jqGrid").jqGrid("getGridParam", "postData");
			   //发送 数据
		       $.extend(postData,param);
		       //重载JQGrid
		       $("#jqGrid").jqGrid("setGridParam", { search: true }).trigger("reloadGrid", [{ page: 1}]);  
				
        },
        reset: function(value){
        	vm.securityArea.name='';
        	vm.securityArea.level=1;

        }
	}
});