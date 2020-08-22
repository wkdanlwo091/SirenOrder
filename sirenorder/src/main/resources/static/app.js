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

function resetOwnerOrderCss(){
	var currentNode = document.getElementById("form1").cloneNode(true);//owner의 화면에 있는 
	var nodeLength = document.getElementById("form1").children.length;//owner의 화면에 있는 
    var children = document.getElementById("form1").childNodes;

    var tempArr = [];
    var newNode = children[0].cloneNode(true) ;
    
	document.getElementById("form1").remove();

	
	//새로 들어갈 것 작성
	
	if(nodeLength <= 5){//1개면 2개로 n -> n + 1
		//한개 새로 만들고 0부터 n까지 붙인다. 
	    for(var i=0; i<children.length; i++){
	    	if(children[i].nodeName == 'DIV'){//div를 복사한다. 
	    		tempArr.push(children[i].cloneNode(true));
	    	}
	    }
	    for(var i=0; i<children.length; i++){
	    	if(children[i].nodeName == 'DIV'){//div를 복사한다. 
	    		document.getElementById("form1").appendChild(tempArr[i]);
	    	}
	    }
	}else if(nodeLength == 6){//화면에 6개가 있을 때 1 2 3 4 5 6 에서  0 1 2 3 4 5 로 변환 
	    for(var i=0; i<children.length-1; i++){
	    	if(children[i].nodeName == 'DIV'){
	    		tempArr.push(children[i].cloneNode(true));
	    	}
	    }
	    //한개 새로 만들고 1부터 5까지 붙인다 .
	}
	
	
	<div style="height: 40%; width: 33%;" class="col-lg-4 col-sm-6">
	<!-- from이 시간을 포함할 때 -->
	<div class="single_product_item">
		<img src="https://www.nespresso.com/ncp/res/uploads/recipes/nespresso-recipes-Cafe-Latte-Do-Brazil.jpg" alt="" width="100%" height="40%">
		<!-- 이미지 사이지를 정해줘야지 안깨진다.  -->
		<div class="single_product_text">
			<h4 id="orders_detail_id223">주문번호 : orders_detail_id223 </h4>
			<h4>상품명: greentea114 </h4>
			<h4>가격: 3000 </h4>
			<h4>날짜: 2020-08-18 </h4>
			<h4>가게이름: banapresso_sinchon </h4>
			<h4 hidden="">orders_id144</h4><!-- 이 것은 -->
			<!-- not_done을 데이터베이스로 가서 done으로 바꾼다.  -->
			<input hidden="" name="store_name[0]" value="banapresso_sinchon">
			<input hidden="" name="orders_id[0]" value="orders_id144">
			<input type="checkbox" id="checkBoxIdorders_detail_id223" name="orders_detail_id[0]" value="orders_detail_id223"> 
			<label> 주문 완료 체크</label><br>
		</div>
		<!-- th:attr="onclick=|upload('${gallery}')|"  -->
	</div>
</div>

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
        //여기서 /topic/store_name 으로 설정한다. --> subscription
        var topic_store_name = "/topic/"  + store_name;
        stompClient.subscribe(topic_store_name, function (greeting) {// subscription 한 메시지로 데이터를 받는다. 
        	
        	//여기서 ownerOrderStatus의 주문품목 위치를 재배열 한다. 123456 에서 012345로  
        	resetOwnerOrderCss();
        	alert(greeting);
        	document.getElementById("customConnect").remove();
           //이거 왜 안되는건지 몰라  showGreeting(JSON.parse(greeting.body).content); 
            //alert(JSON.parse(greeting.body).content);
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