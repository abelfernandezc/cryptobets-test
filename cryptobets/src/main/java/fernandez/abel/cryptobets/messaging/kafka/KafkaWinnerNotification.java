package fernandez.abel.cryptobets.messaging.kafka;

import fernandez.abel.cryptobets.messaging.WinnerNotification;
import fernandez.abel.cryptobets.model.Bet;
import fernandez.abel.cryptobets.model.Winner;
import lombok.extern.slf4j.Slf4j;
import org.apache.camel.ProducerTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class KafkaWinnerNotification implements WinnerNotification {

    @Autowired
    private ProducerTemplate producerTemplate;

    @Override
    public void sendWinnerNotification(Winner winner) {
        log.info("KafkaWinnerNotification.sendWinnerNotification: {}", winner);
        producerTemplate.sendBody("direct:winnerNotificationStart", winner);
    }
}
