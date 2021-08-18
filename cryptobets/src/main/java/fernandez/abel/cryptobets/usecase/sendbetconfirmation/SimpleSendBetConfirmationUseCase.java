package fernandez.abel.cryptobets.usecase.sendbetconfirmation;

import fernandez.abel.cryptobets.model.Bet;
import fernandez.abel.cryptobets.notification.SendNotification;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@AllArgsConstructor
public class SimpleSendBetConfirmationUseCase implements SendBetConfirmationUseCase {

    private final SendNotification sendNotification;

    public void execute(Bet bet) {
        log.info("SendBetConfirmationUseCase.execute {}", bet);
        sendNotification.sendNotification(bet);
    }
}
