var websocket = (function() {
	var stompClient = null;

	/**
	 * 创建WebSocket链接
	 * 
	 * @param url
	 * @param databackurl
	 * @param callback
	 */
	var createConnectFunc = function connect(url, databackurl, callback) {
		stompClient=null;
		if(url.substring(0,1)=='h'){
			var socket = new SockJS(url);
			stompClient = Stomp.over(socket);
		}else{
			stompClient = Stomp.client(url);
		}
		stompClient.debug = function(str) {
		    // append the debug log to a #debug div somewhere in the page using JQuery:
		  console.log(str);
		};
		//var socket = new SockJS(url);
        //it is possible to use other type of WebSockets by using the Stomp.over(ws) method
		//stompClient = Stomp.over(socket);
         //The connect() method also accepts two other variants if you need to pass additional headers:
		stompClient.connect({}, function(frame) {
			console.log('Connected: ' + frame);
                        //订阅,To receive messages in the browser, the STOMP client must first subscribe to a destination.
			stompClient.subscribe(databackurl, function(response) {
				if (typeof callback === "function") {
					callback(response);
				} else {
					console.log("Not Function!");
				}
			});
		});
	};

	/**
	 * 断开WebSocket链接
	 */
	var disconnectFunc = function disconnect() {
		if (stompClient != null) {
			stompClient.disconnect();
		}
		console.log("WebSocket has Disconnected!");
	};

	/**
	 * 发送数据到服务端
	 * 
	 * @param url
	 * @param data
	 */
	var sendDataFunc = function sendDate(url, data) {
                //If you have no headers to pass, use an empty JavaScript literal {}:
		stompClient.send("/app/topic", {}, JSON.stringify({name: 'zyd' }));
	};

	/**
	 * 判断是否已经链接
	 * 
	 * @returns {boolean}
	 */
	var hasConnectedFunc = function hasConnected() {
		if (stompClient != null) {
			return true;
		}
		return false;
	};

	return {
		createConnect : createConnectFunc,
		sendData : sendDataFunc,
		disconnect : disconnectFunc,
		hasConnected : hasConnectedFunc
	}
})();
