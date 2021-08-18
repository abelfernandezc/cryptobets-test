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
public class Bet {
    @Id
    @GeneratedValue(
            strategy = GenerationType.AUTO
    )
    private Long id;
    private Long roundOfBetId;
    private String user;
    private String mail;
    private String telegram;
    private Double betPrice;
    private boolean isWinner;
    private Date betDate;

    public Bet(){}

    public Bet(String user, Double betPrice, String mail, String telegram) {
        this.user = user;
        this.betPrice = betPrice;
        this.betDate = new Date();
        this.isWinner = false;
        this.mail = mail;
        this.telegram = telegram;
    }

    public static List<Bet> getWinners(List<Bet> betList, Double realPrice) {
        Double bestDifference = Double.NaN;
        List<Bet> winners = new ArrayList<>();
        for (Bet actualBet : betList) {
            Double differenceActual = Math.abs(actualBet.betPrice - realPrice);
            if (bestDifference.equals(Double.NaN)) {
                bestDifference = differenceActual;
                winners.add(actualBet);
            } else if (differenceActual.equals(bestDifference)) {
                winners.add(actualBet);
            } else if (actualBet.isBetterBet(realPrice, bestDifference)) {
                winners.clear();
                bestDifference = differenceActual;
                winners.add(actualBet);
            }
        }
        winners.forEach(bet -> bet.setWinner(true));
        return winners;
    }

    private boolean isBetterBet(Double realPrice, Double bestDifference) {
        return Math.abs(this.betPrice - realPrice) < bestDifference;
    }

}
