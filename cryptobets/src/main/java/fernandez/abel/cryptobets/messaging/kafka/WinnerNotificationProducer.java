package fernandez.abel.cryptobets.messaging.kafka;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.dataformat.JsonLibrary;
import org.springframework.stereotype.Service;

@Service
public class WinnerNotificationProducer extends RouteBuilder {

    @Override
    public void configure() throws Exception {
        from("direct:winnerNotificationStart").routeId("winnerNotificationStart")
                .marshal().json(JsonLibrary.Jackson)
                .to("kafka:{{kafka.bet.winner.topic}}?brokers={{kafkaProducerConsumerProperties}}");
    }
}
