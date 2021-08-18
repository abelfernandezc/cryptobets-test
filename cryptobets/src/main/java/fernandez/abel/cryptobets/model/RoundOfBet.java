package fernandez.abel.cryptobets.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import fernandez.abel.cryptobets.exception.BadRequestException;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Date;

@Entity
@JsonInclude(JsonInclude.Include.NON_NULL)
@Getter
@Setter
@ToString
public class RoundOfBet {
    @Id
    @GeneratedValue(
            strategy = GenerationType.AUTO
    )
    private Long id;
    private String status;
    private Double initialBtcPrice;
    private Double finalBtcPrice;
    private Date initialDate;
    private Date finishDate;
    private Integer durationTimeInSeconds;
    private Integer durationForBetsInSeconds;

    public boolean isAvailableForBet() {
        if (!this.getStatus().equals(RoundOfBetStatus.OPEN.get())) {
            return false;
        }
        Date actualDate = new Date();
        return (initialDate.getTime() / 1000 + this.getDurationForBetsInSeconds()) > actualDate.getTime() / 1000;
    }

    public RoundOfBet(){}

    public RoundOfBet(Integer durationOfRound, Integer durationForBet, Double price) {
        if (durationForBet > durationOfRound) {
            throw new BadRequestException("The duration of round must be greater than duration for bet");
        }
        this.setInitialDate(new Date());
        this.setDurationTimeInSeconds(durationOfRound);
        this.setDurationForBetsInSeconds(durationForBet);
        this.setStatus(RoundOfBetStatus.OPEN.get());
        this.setInitialBtcPrice(price);
    }

    public void completeRound(Double price) {
        this.setFinishDate(new Date());
        this.setStatus(RoundOfBetStatus.FINISHED.get());
        this.setFinalBtcPrice(price);
    }

}
