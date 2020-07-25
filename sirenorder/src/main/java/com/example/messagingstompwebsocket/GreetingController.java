package com.example.messagingstompwebsocket;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.util.HtmlUtils;

@Controller
public class GreetingController {
  
  @MessageMapping("/hello")//자바스크립트 front에서 /app/hello로 송신하면 여기로 들어온다. 
  @SendTo("/topic/greetings")// 
  public Greeting greeting(HelloMessage message) throws Exception {
    Thread.sleep(1000); // simulated delay
    return new Greeting("Hello, " + HtmlUtils.htmlEscape(message.getName()) + "!");
    //주문 전달되었습니다. 코드를 owner client한테 전달 
  }
  ///아래 작동 한다. 
  
  @Autowired
  private SimpMessagingTemplate template;//서버에서 메시지를 보내는 변수 
  @RequestMapping(value="requestObject2", method=RequestMethod.POST)
  public void greeting1( ) throws Exception {
	  System.out.println("hahahaahah");
    Thread.sleep(1000); // simulated delay
    this.template.convertAndSend("/topic/greetings", "SentFromServer");
  }
}