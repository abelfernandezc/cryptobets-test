package fernandez.abel.cryptobets.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@JsonInclude(JsonInclude.Include.NON_NULL)
@Getter
@Setter
@ToString
public class Winner {
    @Id
    @GeneratedValue(
            strategy = GenerationType.AUTO
    )
    private Long id;
    private Long betId;
    private String user;
    private String mail;
    private String telegram;
    private Date winnerDate;

    public Winner() {
    }

    public Winner(Long betId, String user, String mail, String telegram) {
        this.user = user;
        this.mail = mail;
        this.winnerDate = new Date();
        this.telegram = telegram;
        this.betId = betId;
    }

    public static List<Winner> getWinners(List<Bet> bets) {
        List<Winner> winners = new ArrayList<>();
        bets.forEach(x -> winners.add(new Winner(x.getId(), x.getUser(), x.getMail(), x.getTelegram())));
        return winners;
    }

}
