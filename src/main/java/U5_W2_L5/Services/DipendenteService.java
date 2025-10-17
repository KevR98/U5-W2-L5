package U5_W2_L5.Services;

import U5_W2_L5.Entities.Dipendente;
import U5_W2_L5.Excpetions.BadRequestException;
import U5_W2_L5.Excpetions.NotFoundException;
import U5_W2_L5.Payload.NewDipendenteRequest;
import U5_W2_L5.Repository.DipendenteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class DipendenteService {

    @Autowired
    private DipendenteRepository dipendenteRepository;

    public Dipendente save(NewDipendenteRequest payload) {
        dipendenteRepository.findByUsername(payload.username()).ifPresent(dipendente -> {
            throw new BadRequestException("L'username " + payload.username() + " è già in uso!");
        });

        dipendenteRepository.findByEmail(payload.email()).ifPresent(dipendente -> {
            throw new BadRequestException("La email " + payload.email() + " è già in uso!");
        });

        Dipendente nuovoDipendente = new Dipendente(
                payload.username(),
                payload.nome(),
                payload.cognome(),
                payload.email()
        );
        return this.dipendenteRepository.save(nuovoDipendente);
    }

//    public Page<Dipendente> findAll(Integer pageNumber, Integer pageSize, String sortBy) {
//        int pn = (pageNumber == null || pageNumber < 0) ? 0 : pageNumber;           // 0-based
//        int ps = (pageSize == null || pageSize <= 0) ? 10 : Math.min(pageSize, 20);
//
//        String sortField = (sortBy == null || sortBy.isBlank()) ? "id" : sortBy;
//
//        Pageable pageable = PageRequest.of(pn, ps, Sort.by(sortField).ascending());
//        return dipendenteRepository.findAll(pageable);
//    }

    public Dipendente findById(long dipendenteId) {
        return this.dipendenteRepository.findById(dipendenteId).orElseThrow(() -> new NotFoundException(dipendenteId));
    }

    public Dipendente findByIdAndUpdate(long dipendeteId, NewDipendenteRequest payload) {
        Dipendente found = this.findById(dipendeteId);

        if (!Objects.equals(found.getEmail(), payload.email())) {
            this.dipendenteRepository.findByEmail(payload.email()).ifPresent(dipendente -> {
                        throw new BadRequestException("L'email " + dipendente.getEmail() + " è già in uso!");
                    }
            );
        }

        if (!Objects.equals(found.getUsername(), payload.username())) {
            this.dipendenteRepository.findByUsername(payload.username()).ifPresent(dipendente -> {
                        throw new BadRequestException("L'username " + dipendente.getUsername() + " è già in uso!");
                    }
            );
        }
        found.setUsername(payload.username());
        found.setNome(payload.nome());
        found.setCognome(payload.cognome());
        found.setEmail(payload.email());

        return this.dipendenteRepository.save(found);

    }

    public void findByIdAndDelete(long id) {
        Dipendente found = this.findById(id);
        this.dipendenteRepository.delete(found);
    }

    // upload immagine
    public Dipendente uploadImmagine(long dipendenteId, String imageUrl) {
        Dipendente found = this.findById(dipendenteId);
        found.setProfileImageUrl(imageUrl);
        return this.dipendenteRepository.save(found);
    }
}