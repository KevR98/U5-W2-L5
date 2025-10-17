package U5_W2_L5.Repository;

import U5_W2_L5.Entities.Dipendente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DipendenteRepository extends JpaRepository<Dipendente, Long> {
    Optional<Dipendente> findByUsername(String username);

    Optional<Dipendente> findByEmail(String email);
}
