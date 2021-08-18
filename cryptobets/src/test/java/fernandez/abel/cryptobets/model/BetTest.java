package fernandez.abel.cryptobets.model;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class BetTest {

    @Test
    public void getBet() {
        Bet bet = new Bet("user", 1.25, "fernandez.abel@gmail.com", null);
        assertNotNull(bet.getBetDate(), "The bet date is null ");
        assertFalse(bet.isWinner(), "The isWinner attribute must be false");
    }

    @Test
    public void onlyOneWinner() {
        Bet betWinner = new Bet("user1", 1.23, "fernandez.abel@gmail.com", null);
        List<Bet> betList = Arrays.asList(
                new Bet("user0", 1.0, "fernandez.abel@gmail.com", null),
                new Bet("user9", 1.1, "fernandez.abel@gmail.com", null),
                new Bet("user8", 2.0, "fernandez.abel@gmail.com", null),
                betWinner,
                new Bet("user7", 1.28, "fernandez.abel@gmail.com", null),
                new Bet("user6", 1.22, "fernandez.abel@gmail.com", null)
                );
        List<Bet> winners = Bet.getWinners(betList, 1.25);
        assertTrue(winners.size() == 1, "The quantity of winner bed must be 1");
        assertEquals(betWinner, winners.get(0), "The winner and returned winner are not equals");
        assertTrue(betWinner.isWinner(), "The isWinner attribute must be true");
    }

    @Test
    public void moreThanOneWinner() {
        List<Bet> betList = Arrays.asList(
                new Bet("user21", 1.0, "fernandez.abel@gmail.com", null),
                new Bet("user", 1.27, "fernandez.abel@gmail.com", null),
                new Bet("user12", 2.0, "fernandez.abel@gmail.com", null),
                new Bet("user3", 1.1, "fernandez.abel@gmail.com", null),
                new Bet("user1", 1.23, "fernandez.abel@gmail.com", null),
                new Bet("user4", 1.23, "fernandez.abel@gmail.com", null)

        );
        List<Bet> winners = Bet.getWinners(betList, 1.25);
        assertEquals(winners.size(), 3, "The quantity of winner beds must be 3");
    }


}