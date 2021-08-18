package fernandez.abel.cryptobets.messaging.kafka;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.dataformat.JsonLibrary;
import org.springframework.stereotype.Service;

@Service
public class BetReceptionNotificationProducer extends RouteBuilder {

    @Override
    public void configure() throws Exception {
        from("direct:betReceptionStart").routeId("betReceptionStart")
                .marshal().json(JsonLibrary.Jackson)
                .to("kafka:{{kafka.bet.reception.topic}}?brokers={{kafkaProducerConsumerProperties}}");
    }
}
