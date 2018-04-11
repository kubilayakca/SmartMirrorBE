package com.mirror.controller;


import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

import com.mirror.controller.dto.Greeting;
import com.mirror.controller.dto.HelloMessage;

@Controller
public class WebSocketController {
	
	@MessageMapping("/hello")
    @SendTo("/topic/greetings")
    public String greeting(String message) throws Exception {
        Thread.sleep(1000); // simulated delay
        return "Hello, " + message + "!";
    }
}
