package fernandez.abel.cryptobets.repository;

import fernandez.abel.cryptobets.model.RoundOfBet;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RoundOfBetRepository extends JpaRepository<RoundOfBet, Long> {

    List<RoundOfBet> findByStatus(String status);
}
