package fernandez.abel.cryptobets.messaging.kafka;

import fernandez.abel.cryptobets.messaging.BetReceptionNotification;
import fernandez.abel.cryptobets.model.Bet;
import lombok.extern.slf4j.Slf4j;
import org.apache.camel.ProducerTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class KafkaBetReceptionNotification implements BetReceptionNotification {

    @Autowired
    private ProducerTemplate producerTemplate;


    @Override
    public void sendBetReceptionNotification(Bet bet) {
        log.info("KafkaBetReceptionNotification.sendBetReceptionNotification: {}", bet);
        producerTemplate.sendBody("direct:betReceptionStart", bet);
    }
}
