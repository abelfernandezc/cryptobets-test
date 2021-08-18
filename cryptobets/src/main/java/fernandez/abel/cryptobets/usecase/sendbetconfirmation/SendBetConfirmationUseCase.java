package fernandez.abel.cryptobets.usecase.sendbetconfirmation;

import fernandez.abel.cryptobets.model.Bet;

public interface SendBetConfirmationUseCase {

    void execute(Bet bet);
}
