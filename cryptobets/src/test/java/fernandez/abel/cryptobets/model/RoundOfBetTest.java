package fernandez.abel.cryptobets.model;

import fernandez.abel.cryptobets.exception.BadRequestException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RoundOfBetTest {

    @Test
    public void createRound() {
        RoundOfBet roundOfBet = new RoundOfBet(60, 55, 1.5);
        assertTrue(roundOfBet.isAvailableForBet());
        roundOfBet.completeRound(1.7);
        assertTrue(!roundOfBet.isAvailableForBet());
    }

    @Test
    public void createRoundWithIncorrectParameters() {
        BadRequestException thrown = assertThrows(
                BadRequestException.class,
                () -> new RoundOfBet(60, 65, 1.5),
                "Expected getNewRoundOfBet to throw, but it didn't"
        );
    }

    @Test
    public void IsNotAvailableForBet() {
        RoundOfBet roundOfBet = new RoundOfBet(60, 0, 1.5);
        assertFalse(roundOfBet.isAvailableForBet());
    }
}