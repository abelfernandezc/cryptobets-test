package fernandez.abel.cryptobets.messaging.kafka;

import fernandez.abel.cryptobets.model.Bet;
import fernandez.abel.cryptobets.usecase.sendbetconfirmation.SendBetConfirmationUseCase;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.dataformat.JsonLibrary;
import org.springframework.stereotype.Service;

@Service
public class BetReceptionNotificationConsumer extends RouteBuilder {

    @Override
    public void configure() {
        from("kafka:{{kafka.bet.reception.topic}}?brokers={{kafkaProducerConsumerProperties}}").routeId("betReceptionNotificationConsumer")
                .unmarshal().json(JsonLibrary.Jackson, Bet.class)
                .bean(SendBetConfirmationUseCase.class, "execute");
    }

}
