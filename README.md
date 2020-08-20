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

## description

정규화된 발표용 ERD

<img width="576" alt="erd_pic" src="https://user-images.githubusercontent.com/12855243/82558589-f4acd580-9ba8-11ea-9b29-6ad8ce9d7e63.PNG">

역정규화된 ERD

<img width="584" alt="develope ERD" src="https://user-images.githubusercontent.com/12855243/87220360-fec7a500-c39d-11ea-8d1b-3610152088d2.PNG">

##  개발환경 및 기술

react, spring boot , android (web view) ,  aws , oracle db , web socket, Thymeleaf , Firebase notification



## 시스템 구성도 

<img width="442" alt="system architecture" src="https://user-images.githubusercontent.com/12855243/82747453-d840b100-9dd3-11ea-8b36-b7174e016f94.PNG">