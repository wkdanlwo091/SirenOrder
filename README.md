# SirenOrder

모바일,웹으로 소규모 가게나 체인점의 물건 혹은 음식 주문하는 서비스



안드로이드 (웹뷰) APK 파일 url : 

웹 url: 54.193.173.207( AWS 서버입니다.  미완입니다 )



Admin 페이지, Owner 페이지, User 페이지로 나누어진 서비스

Admin 페이지는 안드로이드 apk 파일을 배포하고 Owner에게 chain과 store를 할당한다. 

Owner 페이지가 Websocket으로 spring boot 서버와 실시간으로 통신하면서 들어온 주문을 확인하고 

주문이 완료되면 주문 완료 status를 websocket으로 전달한다.  스프링 서버에서는 주문이 완료되면 이를 

파이어베이스 notification을 이용해서 user의 안드로이드 어플에게 알려준다. 

User 페이지는 특정 store에 들어가서 주문을 한다. 



작업중입니다. 





  자세한 description notion: 

 --> https://www.notion.so/SirenOrder-3584efe365a9419baa2ea68318dddc1a





***

이미지 예시

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
<img width="584" alt="develope ERD" src="https://user-images.githubusercontent.com/12855243/90954306-2b4b0280-e4ae-11ea-9ebc-36c4d582c856.PNG">
<img width="576" alt="erd_pic" src="https://user-images.githubusercontent.com/12855243/90954307-2be39900-e4ae-11ea-84f6-7507dd45e619.PNG">
<img width="960" alt="login" src="https://user-images.githubusercontent.com/12855243/90954308-2be39900-e4ae-11ea-9c71-12fb0916f136.png">

<img width="960" alt="main" src="https://user-images.githubusercontent.com/12855243/90954310-2d14c600-e4ae-11ea-9971-2b95707e0bd5.PNG">
<img width="960" alt="orderpage" src="https://user-images.githubusercontent.com/12855243/90954311-2d14c600-e4ae-11ea-8082-776a5df4222d.PNG">



---



description

정규화된 발표용 ERD

<img width="576" alt="erd_pic" src="https://user-images.githubusercontent.com/12855243/82558589-f4acd580-9ba8-11ea-9b29-6ad8ce9d7e63.PNG">

역정규화된 ERD

<img width="584" alt="develope ERD" src="https://user-images.githubusercontent.com/12855243/87220360-fec7a500-c39d-11ea-8d1b-3610152088d2.PNG">

##  개발환경 및 기술

react, spring boot , android (web view) ,  aws , oracle db , web socket, Thymeleaf , Firebase notification



## 시스템 구성도 

<img width="442" alt="system architecture" src="https://user-images.githubusercontent.com/12855243/82747453-d840b100-9dd3-11ea-8b36-b7174e016f94.PNG">