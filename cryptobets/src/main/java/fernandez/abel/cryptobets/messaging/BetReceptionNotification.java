package fernandez.abel.cryptobets.messaging;

import fernandez.abel.cryptobets.model.Bet;

public interface BetReceptionNotification {
    void sendBetReceptionNotification(Bet bet);
}
