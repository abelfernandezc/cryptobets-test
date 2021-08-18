package fernandez.abel.cryptobets.usecase.createround;

import fernandez.abel.cryptobets.exception.NotBetValidException;
import fernandez.abel.cryptobets.model.RoundOfBet;
import fernandez.abel.cryptobets.model.RoundOfBetStatus;
import fernandez.abel.cryptobets.repository.RoundOfBetRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.messaging.core.MessageSendingOperations;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
@Slf4j
public class SimpleCreateRoundUseCase implements CreateRoundUseCase {

    @Autowired
    private RoundOfBetRepository repository;
    @Autowired
    private MessageSendingOperations<String> messageSendingOperations;
    @Value("${round.durationInSeconds}")
    private int roundDuration;
    @Value("${round.durationForBetInSeconds}")
    private int betDuration;

    public void execute(Double price) {
        log.info("CreateRoundUseCase execute, {}, {}", new Date(), price);
        List<RoundOfBet> roundOfBetList = repository.findByStatus(RoundOfBetStatus.OPEN.get());
        if (!roundOfBetList.isEmpty()) {
            throw new NotBetValidException("Exist a open round in this moment");
        }
        RoundOfBet roundOfBet = new RoundOfBet(roundDuration, betDuration, price);
        roundOfBet = repository.save(roundOfBet);
        log.info("CreateRoundUseCase executed, {}", roundOfBet);
        this.messageSendingOperations.convertAndSend("/topic/new-round", roundOfBet);
    }
}
