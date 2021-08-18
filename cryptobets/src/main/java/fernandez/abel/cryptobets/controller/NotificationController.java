package fernandez.abel.cryptobets.controller;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.MessageExceptionHandler;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.util.HtmlUtils;

@Controller
@AllArgsConstructor
@Slf4j
public class NotificationController {

    @MessageMapping("/chat.sendMessage")
    @SendTo("/topic/public")
    public String sendMessage(@Payload String chatMessage) {
        return chatMessage;
    }

    @MessageMapping("/request")
    @SendTo("/queue/responses")
    public String newBet(String message) {
        log.info("NotificationController.newBet: {}", message);
        if (message.equals("zero")) {
            throw new RuntimeException(String.format("'%s' is rejected", message));
        }
        return "response to " + HtmlUtils.htmlEscape(message);
    }

    @MessageExceptionHandler
    @SendTo("/queue/errors")
    public String handleException(Throwable exception) {
        log.error("Server exception", exception);
        return "server exception: " + exception.getMessage();
    }
}
