package fernandez.abel.cryptobets.usecase.createBet;

import fernandez.abel.cryptobets.model.Bet;

public interface CreateBetUseCase {

    Bet execute(Bet bet);
}
