package fernandez.abel.cryptobets.notification.mail;

import fernandez.abel.cryptobets.model.Bet;
import fernandez.abel.cryptobets.model.Winner;
import fernandez.abel.cryptobets.notification.SendNotification;
import lombok.extern.slf4j.Slf4j;
import org.apache.camel.ProducerTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class MailSendNotification implements SendNotification {

    @Autowired
    private ProducerTemplate producerTemplate;
    @Value("${notification.bet.reception.message}")
    private String betReceptionMessage;
    @Value("${notification.bet.winner.message}")
    private String betWinnerMessage;

    @Override
    public void sendNotification(Bet bet) {
        log.info("MailSendNotification.sendNotification: {}", bet);
        producerTemplate.sendBodyAndHeader("direct:sendMail", getBetAcceptedMessage(bet), "to", bet.getMail());
    }

    @Override
    public void sendNotificationToWinner(Winner winner) {
        log.info("MailSendNotification.sendNotificationToWinner: {}", winner);
        producerTemplate.sendBodyAndHeader("direct:sendMailWithAttachment", getWinnerMessage(winner), "to", winner.getMail());
    }

    private String getWinnerMessage(Winner winner){
        return winner.getUser() + betWinnerMessage + winner.getBetId();
    }

    private String getBetAcceptedMessage(Bet bet){
        return betReceptionMessage + " " + bet.getId();
    }
}
