package fernandez.abel.cryptobets.repository;

import fernandez.abel.cryptobets.model.Winner;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WinnerRepository extends JpaRepository<Winner, Long> {

}
