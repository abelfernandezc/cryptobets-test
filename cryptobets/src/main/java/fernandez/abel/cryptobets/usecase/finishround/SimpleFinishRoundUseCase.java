package fernandez.abel.cryptobets.usecase.finishround;

import fernandez.abel.cryptobets.messaging.WinnerNotification;
import fernandez.abel.cryptobets.model.Bet;
import fernandez.abel.cryptobets.model.RoundOfBet;
import fernandez.abel.cryptobets.model.RoundOfBetStatus;
import fernandez.abel.cryptobets.model.Winner;
import fernandez.abel.cryptobets.repository.BetRepository;
import fernandez.abel.cryptobets.repository.RoundOfBetRepository;
import fernandez.abel.cryptobets.repository.WinnerRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.core.MessageSendingOperations;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;

@Service
@AllArgsConstructor
@Slf4j
public class SimpleFinishRoundUseCase implements FinishRoundUseCase {

    private final RoundOfBetRepository roundRepository;
    private final BetRepository betRepository;
    private final WinnerRepository winnerRepository;
    private final MessageSendingOperations<String> messageSendingOperations;
    private final WinnerNotification winnerNotification;

    public void execute(Double price) {
        log.info("CloseRoundUseCase execute. {}, {}", new Date(), price);
        List<RoundOfBet> roundOfBetList = roundRepository.findByStatus(RoundOfBetStatus.OPEN.get());
        roundOfBetList.forEach(round -> this.completeRound(round, price));
    }

    @Transactional
    protected List<Winner> saveFinishRound(RoundOfBet roundOfBet, Double realPrice, List<Bet> winners) {
        betRepository.saveAllAndFlush(winners);
        roundOfBet.completeRound(realPrice);
        roundOfBet.setStatus(RoundOfBetStatus.FINISHED.get());
        roundRepository.saveAndFlush(roundOfBet);
        return winnerRepository.saveAll(Winner.getWinners(winners));
    }

    private void completeRound(RoundOfBet roundOfBet, Double realPrice) {
        log.info("CloseRoundUseCase completeRound. {}, real price: {}", roundOfBet, realPrice);
        List<Bet> betList = betRepository.findByRoundOfBetId(roundOfBet.getId());
        List<Bet> betWinners = Bet.getWinners(betList, realPrice);
        log.debug("betList: {}", betList);
        log.debug("betWinnerList: {}", betWinners);
        List<Winner> winnerList = saveFinishRound(roundOfBet, realPrice, betWinners);
        this.messageSendingOperations.convertAndSend("/topic/finish-round", betWinners);
        winnerList.forEach(x -> winnerNotification.sendWinnerNotification(x));
    }

}
