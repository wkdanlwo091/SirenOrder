package com.example.sirenorder.controller;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.util.HtmlUtils;
import com.example.sirenorder.vo.Greeting;
import com.example.sirenorder.vo.HelloMessage;
@Controller
public class GreetingController {
  @MessageMapping("/hello")//자바스크립트 front에서 /app/hello로 송신하면 여기로 들어온다. 
  @SendTo("/topic/greetings")// 
  public Greeting greeting(HelloMessage message) throws Exception {
    Thread.sleep(1000); // simulated delay
    return new Greeting("Hello, " + HtmlUtils.htmlEscape(message.getName()) + "!");
  }
}