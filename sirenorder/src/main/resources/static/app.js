//자바스크립트 웹페이지와 스프링 컨트롤러 연결의 로직이 담긴 javascript 파일 
var stompClient = null;

function setConnected(connected) {
    $("#customConnect").prop("disabled", connected);// 원래 connect 였는데 customConnect로 바꿈 
    $("#disconnect").prop("disabled", !connected);
    if (connected) {
        $("#conversation").show();
    }
    else {
        $("#conversation").hide();
    }
    $("#greetings").html("");
}

function connect() {
	
	//connect 누르면 ajax로 session 만들어 달라고 요청 
	$.ajax({ 
		type : "GET",
		url : "makeConnectSession",
		data : {
			data1 : "connect"
		}
	});
    var socket = new SockJS('/gs-guide-websocket');
    stompClient = Stomp.over(socket);
    stompClient.connect({}, function (frame) {
        setConnected(true);
        console.log('Connected: ' + frame);
        stompClient.subscribe('/topic/greetings', function (greeting) {// subscription 한 메시지로 데이터를 받는다. 
        	alert(greeting);
        	console.log(greeting);
            showGreeting(JSON.parse(greeting.body).content);
            alert(JSON.parse(greeting.body).content);
        });
    });
}

function customConnect(store_name) {// topic/banapresso_sinchon 이런식으로 subscribe 한다. 

	$.ajax({ 
		type : "GET",
		url : "makeConnectSession",
		data : {
			data1 : "connect"
		}
	});
	
	var socket = new SockJS('/gs-guide-websocket');
    stompClient = Stomp.over(socket);
    stompClient.connect({}, function (frame) {
        setConnected(true);
        //여기서 /topic/store_name 으로 설정한다. 
        var topic_store_name = "/topic/"  + store_name;
        stompClient.subscribe(topic_store_name, function (greeting) {// subscription 한 메시지로 데이터를 받는다. 
        	alert(greeting);
        	console.log(greeting);
            showGreeting(JSON.parse(greeting.body).content);
            alert(JSON.parse(greeting.body).content);
        });
    });
}

function disconnect() {
	$.ajax({ 
		type : "GET",
		url : "makeConnectSession",
		data : {
			data1 : "disconnect"
		}
	});

	
    if (stompClient !== null) {
        stompClient.disconnect();
    }
    setConnected(false);
    console.log("Disconnected");
}

function sendName() {//app/hello로 보낸다. 
	stompClient.send("/app/hello", {}, JSON.stringify({'name': 'dd'}));
}

function sendProduct(orders_detail_array) {//app/orders_detailId 로 보낸다. 
	var jsonObject = {};
	
	jsonObject["orders_detail_id"] = orders_detail_array;
	console.log(orders_detail_array);
    stompClient.send("/app/orders_detailId", {}, JSON.stringify(jsonObject));
}

function showGreeting(message) {
    $("#greetings").append("<tr><td>" + message + "</td></tr>");
}

$(function () {
	
	//websocket 연결하는 form을 stop한다. 
    $(".form-inline").on('submit', function (e) {
        e.preventDefault();
    });
    
    //$( "#connect" ).click(function() { connect();});
    $( "#customConnect" ).click(function() {
    	var store_name = $("#customConnect").val();
    	customConnect(store_name);});
    $( "#disconnect" ).click(function() { disconnect(); });
    $( "#send" ).click(function() { sendName(); });//이름 보내기
    $( "#sendDoneProduct_name" ).click(function() { 
    	//주문 완료 체크된 상품들의 orders_detail_id를 보낸다. 
    	var orders_detail_array = [];
        $('*[id*="checkBoxId"]:visible:checked').each(function (i, el) {//체크 된 것만 보내겠다. 
        	orders_detail_array.push(this.parentNode.firstChild.nextSibling.id);
        });//'*[id*=mytext]:visible'
    	sendProduct(orders_detail_array); 
    });//완료된 product 보내기 
});