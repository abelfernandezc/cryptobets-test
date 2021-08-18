package fernandez.abel.cryptobets.notification.mail;

import org.apache.camel.attachment.AttachmentMessage;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Service;

import javax.activation.DataHandler;
import javax.mail.util.ByteArrayDataSource;
import java.io.ByteArrayOutputStream;

@Service
public class SmptMailNotificator extends RouteBuilder {

    @Override
    public void configure() {

        onException(Exception.class)
                .continued(true)
                .log("Error reported: ${exception.message} - cannot process this message.");

        from("direct:sendMail").routeId("sendMailNotification")
                .setHeader("subject", simple("{{notification.bet.reception.subject}}"))
                .to("{{email.uri}}");

        from("direct:sendMailWithAttachment").routeId("sendMailNotificationWithAttachment")
                .setProperty("messageOriginal", body())
                .to("pdf:create")
                .setProperty("pdf", body())
                .process(exchange ->
                {
                    AttachmentMessage attMsg = exchange.getIn(AttachmentMessage.class);
                    attMsg.addAttachment("winner.pdf",
                            new DataHandler(new ByteArrayDataSource(((ByteArrayOutputStream) exchange.getIn().getBody()).toByteArray(), "application/pdf")
                            ));
                    System.out.println("messagOriginal:::" + exchange.getAllProperties().get("messageOriginal"));
                    exchange.getIn().setBody(exchange.getAllProperties().get("messageOriginal"));
                })
                .setBody(exchangeProperty("messageOriginal"))
                .setHeader("subject", simple("{{notification.bet.winner.subject}}"))
                .to("{{email.uri}}");
    }
}
