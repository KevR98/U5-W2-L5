package U5_W2_L5.Repository;

import U5_W2_L5.Entities.Prenotazione;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;

@Repository
public interface PrenotazioneRepository extends JpaRepository<Prenotazione, Long> {
    boolean existsByDipendenteIdAndData(long dipendenteId, LocalDate dataViaggio);

    Prenotazione findByDipendenteIdAndViaggioDate(long id, LocalDate nuovaDataViaggio);
}
