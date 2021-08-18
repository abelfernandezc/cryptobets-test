package fernandez.abel.cryptobets.usecase.sendwinnernotification;

import fernandez.abel.cryptobets.model.Winner;

public interface SendWinnerNotificationUseCase {

    void execute(Winner winner);
}
