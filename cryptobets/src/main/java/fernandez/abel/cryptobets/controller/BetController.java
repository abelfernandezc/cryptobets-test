package fernandez.abel.cryptobets.controller;

import fernandez.abel.cryptobets.dto.request.CreateBetRequest;
import fernandez.abel.cryptobets.dto.response.CreateBetResponse;
import fernandez.abel.cryptobets.model.Bet;
import fernandez.abel.cryptobets.usecase.createBet.CreateBetUseCase;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@Slf4j
@AllArgsConstructor
@RequestMapping("bet")
public class BetController {

    private final CreateBetUseCase createBetUseCase;

    @ApiOperation(notes = "Bet creation", value = "Create bet", response = CreateBetRequest.class)
    @PostMapping(value = "/create", produces = MediaType.APPLICATION_JSON_VALUE)
    public CreateBetResponse CreateBet(
            @Valid @RequestBody CreateBetRequest request
    ) {
        log.info("/bet/create. request:{} ", request);
        Bet bet = new Bet(request.getUser(), request.getBetPrice(), request.getMail(), request.getTelegram());
        bet = createBetUseCase.execute(bet);
        return new CreateBetResponse(bet);
    }

}
