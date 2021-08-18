package fernandez.abel.cryptobets.usecase.createBet;

import fernandez.abel.cryptobets.exception.BadRequestException;
import fernandez.abel.cryptobets.exception.NotBetValidException;
import fernandez.abel.cryptobets.messaging.BetReceptionNotification;
import fernandez.abel.cryptobets.model.Bet;
import fernandez.abel.cryptobets.model.RoundOfBet;
import fernandez.abel.cryptobets.model.RoundOfBetStatus;
import fernandez.abel.cryptobets.repository.BetRepository;
import fernandez.abel.cryptobets.repository.RoundOfBetRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.core.MessageSendingOperations;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@AllArgsConstructor
@Slf4j
public class SimpleCreateBetUseCase implements CreateBetUseCase{

    private final BetRepository betRepository;
    private final RoundOfBetRepository roundRepository;
    private final MessageSendingOperations<String> messageSendingOperations;
    private final BetReceptionNotification betReceptionNotification;

    public Bet execute(Bet bet) {
        log.info("CreateBetUserCase.execute {}", bet);
        validateBet(bet);
        bet = betRepository.saveAndFlush(bet);

        List<Bet> bets = betRepository.findByRoundOfBetId(bet.getRoundOfBetId());
        String broadcast = String.format("Quantity of bets: %s", bets.size());
        this.messageSendingOperations.convertAndSend("/topic/bet-quantity", broadcast);

        betReceptionNotification.sendBetReceptionNotification(bet);
        return bet;
    }

    private void validateBet(Bet bet) {
        if (bet == null) {
            throw new BadRequestException("The bet is required");
        }
        if (bet.getUser() == null || bet.getUser().equals("")) {
            throw new BadRequestException("The user is required");
        }
        if (bet.getBetPrice() == null || bet.getBetPrice().isNaN()) {
            throw new BadRequestException("The bet price is required");
        }
        List<RoundOfBet> roundList = roundRepository.findByStatus(RoundOfBetStatus.OPEN.get());
        if (roundList.isEmpty()) {
            throw new NotBetValidException("In this time not exist a round of bets open");
        }

        if (roundList.size() > 1) {
            throw new NotBetValidException("In this time exist more than permitted round of bets opens");
        }

        if (!roundList.get(0).isAvailableForBet()) {
            throw new NotBetValidException("At this time bets are no longer accepted for this round, please wait for the new round available");
        }

        bet.setRoundOfBetId(roundList.get(0).getId());
    }
}
