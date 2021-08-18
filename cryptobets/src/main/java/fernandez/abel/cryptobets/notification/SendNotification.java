package fernandez.abel.cryptobets.notification;

import fernandez.abel.cryptobets.model.Bet;
import fernandez.abel.cryptobets.model.Winner;

public interface SendNotification {

    void sendNotification(Bet bet);

    void sendNotificationToWinner(Winner winner);
}
