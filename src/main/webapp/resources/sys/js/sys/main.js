var vm = new Vue({
	el:'#rrapp',
	data:{
		status:{
			online:0,
			unknown:0,
			offline:0
		},
	},
	created: function() {
		this.getstatus();
    },
	methods: {
		getstatus: function () {
			$.ajax({
				type : "get",
				async : true,
				jsonType : "json",
				url : "../equipment/getTotalByIp",
				success : function(r) {
					vm.status.online = r.result.total;
					$.ajax({
						type : "get",
						async : true,
						jsonType : "json",
						url : "../equipment/getTotalByEquipment",
						success : function(r) {
							console.log(r);
							vm.status.unknown=vm.status.online-r.result.total;
						}
					});
				}
			});
		}
	}
});
