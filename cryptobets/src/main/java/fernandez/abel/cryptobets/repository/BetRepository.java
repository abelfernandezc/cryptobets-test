package fernandez.abel.cryptobets.repository;

import fernandez.abel.cryptobets.model.Bet;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BetRepository extends JpaRepository<Bet, Long> {

    List<Bet> findByRoundOfBetId(Long roundId);

}
