package fernandez.abel.cryptobets.messaging;

import fernandez.abel.cryptobets.model.Winner;

public interface WinnerNotification {
    void sendWinnerNotification(Winner winner);
}
