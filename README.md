# SirenOrder

모바일,웹으로 소규모 가게나 체인점의 물건 혹은 음식 주문하는 서비스



안드로이드 (웹뷰) APK 파일 url : 

웹 url: 54.193.173.207( AWS 서버입니다. 계속 업데이트 중입니다. )

test 할 수 있는 계정 : admin : id : 01027994207  pwd: 1111

​									 owner : id : 01012341234  pwd: 1111 

​									 user: 계정을 한개 만들면 됩니다.  



Admin 페이지, Owner 페이지, User 페이지로 나누어진 서비스

Admin 페이지는 안드로이드 apk 파일을 배포하고 Owner에게 chain과 store를 할당한다. 

Owner 페이지가 Websocket으로 spring boot 서버와 실시간으로 통신하면서 들어온 주문을 확인하고 

주문이 완료되면 주문 완료 status를 websocket으로 전달한다.  스프링 서버에서는 주문이 완료되면 이를 

파이어베이스 notification을 이용해서 user의 안드로이드 어플에게 알려준다. 

User 페이지는 특정 store에 들어가서 주문을 한다. 







  자세한 description notion: 

 --> https://www.notion.so/SirenOrder-3584efe365a9419baa2ea68318dddc1a


















## 로그인 페이지 :

 

![Screenshot_20200821-000047_SirenOrder](https://user-images.githubusercontent.com/12855243/90955371-08255080-e4b8-11ea-863b-b2753fcf8685.jpg)

<img width="960" alt="login" src="https://user-images.githubusercontent.com/12855243/90954536-fd66bd80-e4af-11ea-9360-1461515a360c.png">

apk 배포 페이지 : 

<img width="960" alt="배포페이지" src="https://user-images.githubusercontent.com/12855243/90954723-e3c67580-e4b1-11ea-9e4b-8ff62ff99fc7.PNG">



## 유저페이지 

* gps 설정하기 ( pc의 웹 브라우져 )

<img width="960" alt="kakaomap" src="https://user-images.githubusercontent.com/12855243/91044792-d2679f80-e650-11ea-9c77-54415d71cfb5.PNG">





* gps 설정하기 (안드로이드 )

![Screenshot_20200819-183049_SirenOrder](https://user-images.githubusercontent.com/12855243/90955368-06f42380-e4b8-11ea-9668-30c9edba700e.jpg)





* 검색창 페이지 

![Screenshot_20200821-153126_SirenOrder](https://user-images.githubusercontent.com/12855243/90955374-09567d80-e4b8-11ea-864f-70de72970c44.jpg)





* 주문 페이지 

![Screenshot_20200821-153140_SirenOrder](https://user-images.githubusercontent.com/12855243/90955376-0a87aa80-e4b8-11ea-8af7-369cf6df99ae.jpg)





* 장바구니 페이지 

![Screenshot_20200821-153152_SirenOrder](https://user-images.githubusercontent.com/12855243/90955377-0a87aa80-e4b8-11ea-9f1d-355b7fae6e33.jpg)



* 메인페이지에서 장바구니 보기 

![Screenshot_20200821-153200_SirenOrder](https://user-images.githubusercontent.com/12855243/90955378-0b204100-e4b8-11ea-8a6f-b0788316aec1.jpg)



* 파이어베이스 notification 

  ![Screenshot_20200821-174901_SirenOrder](https://user-images.githubusercontent.com/12855243/90955383-0bb8d780-e4b8-11ea-8afb-8bfe87741d43.jpg)

![Screenshot_20200821-154254_SirenOrder](https://user-images.githubusercontent.com/12855243/90955380-0b204100-e4b8-11ea-8902-699c7717ce2e.jpg)







<img width="960" alt="main" src="https://user-images.githubusercontent.com/12855243/90954310-2d14c600-e4ae-11ea-9971-2b95707e0bd5.PNG">





<img width="960" alt="orderpage" src="https://user-images.githubusercontent.com/12855243/90954311-2d14c600-e4ae-11ea-8082-776a5df4222d.PNG">

<img width="960" alt="orders_status" src="https://user-images.githubusercontent.com/12855243/90954291-25edb800-e4ae-11ea-9c7f-377e757668fe.PNG">
<img width="960" alt="pagination" src="https://user-images.githubusercontent.com/12855243/90954292-271ee500-e4ae-11ea-988c-1b01bc548b29.PNG">
<img width="960" alt="profile" src="https://user-images.githubusercontent.com/12855243/90954293-27b77b80-e4ae-11ea-8ccb-8ce96f09e676.PNG">
<img width="960" alt="profile_change" src="https://user-images.githubusercontent.com/12855243/90954294-28501200-e4ae-11ea-8285-62535b6fdd87.PNG">
<img width="960" alt="store_search" src="https://user-images.githubusercontent.com/12855243/90954296-28501200-e4ae-11ea-85ba-c2e14e17bdeb.PNG">

<img width="960" alt="usage_history" src="https://user-images.githubusercontent.com/12855243/90954299-29813f00-e4ae-11ea-940f-35ee8aa68da3.PNG">
<img width="960" alt="cart" src="https://user-images.githubusercontent.com/12855243/90954300-29813f00-e4ae-11ea-9f78-3e5f3ab59d50.png">
<img width="960" alt="cart_and_page" src="https://user-images.githubusercontent.com/12855243/90954302-2a19d580-e4ae-11ea-8827-52159a0f6f8b.png">
<img width="960" alt="cart2" src="https://user-images.githubusercontent.com/12855243/90954303-2ab26c00-e4ae-11ea-81ad-728bc3b39753.png">
<img width="960" alt="checkout" src="https://user-images.githubusercontent.com/12855243/90954304-2ab26c00-e4ae-11ea-91d3-c64dcfa116fb.PNG">



## 점주 페이지



* 웹소켓 커넥션 페이지

![Screenshot_20200821-170902_SirenOrder](https://user-images.githubusercontent.com/12855243/90955381-0bb8d780-e4b8-11ea-9561-a3fb7eaef21e.jpg) 





description

정규화된 발표용 ERD

<img width="576" alt="erd_pic" src="https://user-images.githubusercontent.com/12855243/82558589-f4acd580-9ba8-11ea-9b29-6ad8ce9d7e63.PNG">

역정규화된 ERD

<img width="584" alt="develope ERD" src="https://user-images.githubusercontent.com/12855243/87220360-fec7a500-c39d-11ea-8d1b-3610152088d2.PNG">

##  개발환경 및 기술

react, spring boot , android (web view) ,  aws , oracle db , web socket, Thymeleaf , Firebase notification



## 시스템 구성도 

