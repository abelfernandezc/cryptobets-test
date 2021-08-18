package fernandez.abel.cryptobets.usecase.sendwinnernotification;

import fernandez.abel.cryptobets.model.Winner;
import fernandez.abel.cryptobets.notification.SendNotification;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@AllArgsConstructor
public class SimpleSendWinnerNotificationUseCase implements SendWinnerNotificationUseCase {

    private final SendNotification sendNotification;

    public void execute(Winner winner) {
        log.info("SendWinnerNotificationUseCase.execute {}", winner);
        sendNotification.sendNotificationToWinner(winner);
    }
}
