package fernandez.abel.cryptobets.scheduler;

import fernandez.abel.cryptobets.gateway.CurrencyClient;
import fernandez.abel.cryptobets.usecase.createround.CreateRoundUseCase;
import fernandez.abel.cryptobets.usecase.finishround.FinishRoundUseCase;
import lombok.AllArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class CreateRoundJob {

    private final CreateRoundUseCase createRoundUseCase;
    private final FinishRoundUseCase finishRoundUserCase;
    private final CurrencyClient currencyClient;

    @Scheduled(cron = "0 * * * * *")
    public void createRoundJob() {
        Double bitcointPrice = currencyClient.getBitcoinPriceByMinute();
        finishRoundUserCase.execute(bitcointPrice);
        createRoundUseCase.execute(bitcointPrice);
    }
}
