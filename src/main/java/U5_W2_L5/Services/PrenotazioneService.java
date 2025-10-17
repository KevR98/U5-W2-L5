package U5_W2_L5.Services;

import U5_W2_L5.Entities.Dipendente;
import U5_W2_L5.Entities.Prenotazione;
import U5_W2_L5.Entities.Viaggio;
import U5_W2_L5.Excpetions.BadRequestException;
import U5_W2_L5.Excpetions.NotFoundException;
import U5_W2_L5.Payload.NewPrenotazioneRequest;
import U5_W2_L5.Repository.DipendenteRepository;
import U5_W2_L5.Repository.PrenotazioneRepository;
import U5_W2_L5.Repository.ViaggioRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class PrenotazioneService {

    private final PrenotazioneRepository prenotazioneRepository;
    private final ViaggioRepository viaggioRepository;
    private final DipendenteRepository dipendenteRepository;

    @Transactional
    public Prenotazione save(NewPrenotazioneRequest payload) {

        // Assumo che il record abbia metodi: dipendenteId(), viaggioId(), preferenze(), note()
        Long dipId = payload.dipendente_id();
        Long viagId = payload.viaggio_id();

        Dipendente dipendente = dipendenteRepository.findById(dipId)
                .orElseThrow(() -> new NotFoundException(dipId));

        Viaggio viaggio = viaggioRepository.findById(viagId)
                .orElseThrow(() -> new NotFoundException(viagId));

        LocalDate dataViaggio = viaggio.getData();
        if (dataViaggio == null) {
            throw new BadRequestException("Data del viaggio non presente");
        }

        // usa il metodo repository corretto (vedi PrenotazioneRepository sotto)
        boolean exists = prenotazioneRepository.existsByDipendenteIdAndData(dipendente.getId(), dataViaggio);
        if (exists) {
            throw new BadRequestException("Il dipendente con ID " + dipendente.getId() +
                    " ha già una prenotazione per un viaggio in data " + dataViaggio + ". " +
                    "Non può avere più prenotazioni per lo stesso giorno.");
        }

        Prenotazione nuovaPrenotazione = new Prenotazione();
        // setta i campi adeguatamente (dipende dal costruttore della tua entity)
        nuovaPrenotazione.setDataRichiesta(LocalDate.now());
        nuovaPrenotazione.setPreferenze(payload.preferenze());
        nuovaPrenotazione.setViaggio(viaggio);
        nuovaPrenotazione.setDipendente(dipendente);
        nuovaPrenotazione.setDataViaggio(dataViaggio); // se hai questo campo

        return this.prenotazioneRepository.save(nuovaPrenotazione);
    }

    public Page<Prenotazione> findAll(Integer pageNumber, Integer pageSize, String sortBy) {
        int pn = (pageNumber == null || pageNumber < 0) ? 0 : pageNumber;
        int ps = (pageSize == null || pageSize <= 0) ? 10 : Math.min(pageSize, 20);
        String sortField = (sortBy == null || sortBy.isBlank()) ? "id" : sortBy;

        Pageable pageable = PageRequest.of(pn, ps, Sort.by(sortField).ascending());
        return this.prenotazioneRepository.findAll(pageable);
    }

    public Prenotazione findById(long id) {
        return this.prenotazioneRepository.findById(id).orElseThrow(() -> new NotFoundException(id));
    }

    @Transactional
    public Prenotazione findByIdAndUpdate(long prenotazioneId, NewPrenotazioneRequest payload) {

        Prenotazione found = this.findById(prenotazioneId);

        Long nuovoDipId = payload.dipendente_id();
        Long nuovoViagId = payload.viaggio_id();

        Dipendente nuovoDipendente = dipendenteRepository.findById(nuovoDipId)
                .orElseThrow(() -> new NotFoundException(nuovoDipId));

        Viaggio nuovoViaggio = viaggioRepository.findById(nuovoViagId)
                .orElseThrow(() -> new NotFoundException(nuovoViagId));

        LocalDate nuovaDataViaggio = nuovoViaggio.getData();
        if (nuovaDataViaggio == null) {
            throw new BadRequestException("Data del nuovo viaggio non presente");
        }

        // Se esiste già una prenotazione (diversa da quella che sto aggiornando) per lo stesso dipendente+data => errore
        boolean exists = prenotazioneRepository.existsByDipendenteIdAndData(nuovoDipendente.getId(), nuovaDataViaggio);
        if (exists) {
            // se l'esistente è proprio la stessa prenotazione che sto aggiornando, OK; altrimenti errore
            // controlla tramite repository (opzionale) o recupera la prenotazione di quel dip+data
            Prenotazione existingSame = prenotazioneRepository.findByDipendenteIdAndViaggioDate(nuovoDipendente.getId(), nuovaDataViaggio);
            if (existingSame != null && !Objects.equals(existingSame.getId(), prenotazioneId)) {
                throw new BadRequestException("Il dipendente con ID " + nuovoDipendente.getId() +
                        " ha già un'altra prenotazione per un viaggio in data " + nuovaDataViaggio + ".");
            }
        }

        found.setViaggio(nuovoViaggio);
        found.setDipendente(nuovoDipendente);
        // usa il campo corretto del payload:
        found.setPreferenze(payload.preferenze());

        return prenotazioneRepository.save(found);
    }

    public void findByIdAndDelete(long id) {
        Prenotazione found = this.findById(id);
        this.prenotazioneRepository.delete(found);
    }
}