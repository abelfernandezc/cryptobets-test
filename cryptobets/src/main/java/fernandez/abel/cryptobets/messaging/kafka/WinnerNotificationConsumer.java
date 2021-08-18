package fernandez.abel.cryptobets.messaging.kafka;

import fernandez.abel.cryptobets.model.Winner;
import fernandez.abel.cryptobets.usecase.sendwinnernotification.SendWinnerNotificationUseCase;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.dataformat.JsonLibrary;
import org.springframework.stereotype.Service;

@Service
public class WinnerNotificationConsumer extends RouteBuilder {

    @Override
    public void configure() {
        from("kafka:{{kafka.bet.winner.topic}}?brokers={{kafkaProducerConsumerProperties}}").routeId("winnerNotificationConsumer")
                .unmarshal().json(JsonLibrary.Jackson, Winner.class)
                .bean(SendWinnerNotificationUseCase.class, "execute");
    }

}
