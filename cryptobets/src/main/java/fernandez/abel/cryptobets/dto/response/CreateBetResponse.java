package fernandez.abel.cryptobets.dto.response;

import fernandez.abel.cryptobets.model.Bet;

public class CreateBetResponse extends Response {

    private Long id;

    public CreateBetResponse(Bet bet) {
        super("success", "Your bet was create successfully (" + bet.getId() + ")");
        this.id = bet.getId();
    }
}
