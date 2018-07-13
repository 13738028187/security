//生成菜单
var menuItem = Vue.extend({
    name: 'menu-item',
    props: {item: {}},
    template: [
        '<li>',
        '<a v-if="item.nodeType === 0" href="javascript:;" @clink="changeIcon(item.url)">',
        '<i v-if="item.icon != null" :class="item.icon"></i>',
        '<span>{{item.name}}</span>',
        '<i class="fa fa-angle-down pull-right"></i>',
        '</a>',
        '<ul v-if="item.nodeType === 0" class="treeview-menu">',
        '<menu-item :item="item" v-for="item in item.list"></menu-item>',
        '</ul>',
        '<a style="font-size:small;" v-if="item.nodeType === 1" :href="\'#\'+item.url" ><i v-if="item.icon != null" :class="item.icon"></i><i v-else class="fa fa-circle-o"></i> {{item.name}}</a>',
        '</li>'
    ].join('')
});

//注册菜单组件
Vue.component('menuItem', menuItem);

var vm = new Vue({
    el: '#rrapp',
    data: {
        user: {},
        menuList: {},
        main: "sys/main.html",
        password: '',
        newPassword: '',
        navTitle: "",
        messageNumber: 0,
        myEmail: [{
            receiveEmail: ''
        }],
        email: []
    },
    methods: {
        getMenuList: function (event) { //获取权限列表
            $.getJSON("sys/menu/user?_" + $.now(), function (r) {
                vm.menuList = r.menuList;
            });
        },
        getUser: function () { //获取用户信息
            $.getJSON("info?_" + $.now(), function (r) {
                vm.user = r.user;
            });
        },
        /*changeIcon: function(url){
            if($("a[href='"+url+"']").parents("li").className=='active'){
                $("a[href='"+url+"']").parents("li").removeClass("active");
                $("a[href='"+url+"']").parents("li").find("i").className="fa fa-angle-down pull-right";
            }else{
                $("a[href='"+url+"']").parents("li").addClass("active");
                $("a[href='"+url+"']").parents("li").find("i").className="fa fa-angle-up pull-right";
            }
        },*/
        updatePassword: function () {  //修改密码
            layer.open({
                type: 1,
                skin: 'layui-layer-molv',
                title: "修改密码",
                area: ['550px', '270px'],
                shadeClose: false,
                content: jQuery("#passwordLayer"),
                btn: ['修改', '取消'],
                btn1: function (index) {
                    var data = "password=" + vm.password + "&newPassword=" + vm.newPassword;
                    $.ajax({
                        type: "POST",
                        url: "sys/user/password",
                        data: data,
                        dataType: "json",
                        success: function (result) {
                            if (result.code == 0) {
                                layer.close(index);
                                layer.alert('修改成功', function (index) {
                                    location.reload();
                                });
                            } else {
                                layer.alert(result.msg);
                            }
                        }
                    });
                }
            });
        },
        logout: function () { //退出 系统
            layer.confirm('确定要退出系统吗?', {icon: 3, title: '提示'}, function (index) {
                window.location.href = "logout";
                layer.close(index);
            });
        },
        getEemal: function () { //获取用户信息
            $.getJSON("getMyEmail?_" + $.now(), function (r) {
                /*	vm.myEmail=r;*/
                vm.myEmail = r;
                console.log(vm.myEmail[0]);
                /*				Array.prototype.push.apply(vm.myEmail, r);
                                console.log(vm.myEmail[0]);*/
            });
        },
        addEmail: function () {
            layer.open({
                type: 1,
                skin: 'layui-layer-molv',
                title: "添加一个邮件",
                area: ['550px', '270px'],
                shadeClose: false,
                content: jQuery("#addEmailLayer"),
                btn: ['确定', '取消'],
                btn1: function (index) {
                    var data = vm.myEmail[0].receiveEmail;
                    $.ajax({
                        type: "POST",
                        url: "addEmail?_" + $.now() + "&receiveEmail=" + data,
                        success: function (result) {
                            if (result == 0) {
                                layer.close(index);
                                layer.alert('操作成功', function (index) {
                                    location.reload();
                                });
                            } else {
                                layer.alert(result.msg);
                            }
                        }
                    });
                }
            });
        },
        subscribeEmail: function () {
            layer.open({
                type: 1,
                skin: 'layui-layer-molv',
                title: "订阅警告时发送邮件事件",
                area: ['550px', '270px'],
                shadeClose: false,
                content: jQuery("#subscribeEmailLayer"),
                btn: ['确定', '取消'],
                btn1: function (index) {
                    $.ajax({
                        type: "POST",
                        url: "subscribeLoggerEmail",
                        data: $.now(),
                        success: function (result) {
                            if (result == 0) {
                                layer.close(index);
                                layer.alert('订阅成功', function (index) {
                                    parent.layer.close(index);
                                });

                            } else {
                                layer.alert(result.msg);
                            }
                        }
                    });
                }
            });
        }
    },
    created: function () {
        this.getMenuList();
        this.getUser();
    },

    updated: function () {
        //路由
        var router = new Router();
        routerList(router, vm.menuList);
        router.start();
    }
});

function routerList(router, menuList) {
    for (var key in menuList) {
        var menu = menuList[key];
        if (menu.nodeType == 0) {
            routerList(router, menu.list);
        } else if (menu.nodeType == 1) {
            router.add('#' + menu.url, function () {
                var url = window.location.hash;

                //替换iframe的url
                vm.main = url.replace('#', '');

                //导航菜单展开
                $(".treeview-menu li").removeClass("active");
                $("a[href='" + url + "']").parents("li").addClass("active");

                vm.navTitle = $("a[href='" + url + "']").text();
            });
        }
    }
}

var showMessage = function getMessage(message) {
    // var email = JSON.parse(message.body);
    vm.email.push({
        author: 'Security系统',
        subject: '无流量告警',
        content: message.body,
        mins: '5'
    });
    vm.messageNumber = vm.messageNumber + 1;
};
/**
 * WebSocket连接
 */
var connectFunc = function connect() {
    websocket.createConnect("ws://192.168.2.9:61614/stomp", "/topic/snmp", showMessage);
    websocket = new SockJS("/security/sockjs/socketServer.do");
};

connectFunc();