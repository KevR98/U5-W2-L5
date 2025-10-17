package U5_W2_L5.Services;

import U5_W2_L5.Entities.Viaggio;
import U5_W2_L5.Enum.StatoViaggio;
import U5_W2_L5.Excpetions.NotFoundException;
import U5_W2_L5.Payload.NewViaggioRequest;
import U5_W2_L5.Repository.ViaggioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class ViaggioService {

    @Autowired
    private ViaggioRepository viaggioRepository;

    public Viaggio save(NewViaggioRequest payload) {


        Viaggio nuovoViaggio = new Viaggio(
                payload.destinazione(),
                payload.data(),
                payload.stato()
        );
        return this.viaggioRepository.save(nuovoViaggio);
    }

    public Page<Viaggio> findAll(Integer pageNumber, Integer pageSize, String sortBy) {
        if (pageSize > 20) pageSize = 20;
        Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.by(sortBy).ascending());
        return this.viaggioRepository.findAll(pageable);
    }

    public Viaggio findById(long viaggioId) {
        return this.viaggioRepository.findById(viaggioId).orElseThrow(() -> new NotFoundException(viaggioId));
    }

    public Viaggio findByIdAndUpdate(long viaggioId, NewViaggioRequest payload) {
        Viaggio found = this.findById(viaggioId);

        found.setDestinazione(payload.destinazione());
        found.setData(payload.data());
        found.setStato(payload.stato());

        return this.viaggioRepository.save(found);
    }

    public void findByIdAndDelete(long viaggioId) {
        Viaggio found = this.findById(viaggioId);
        this.viaggioRepository.delete(found);
    }

    //modifica solo lo stato
    public Viaggio updateStato(long viaggioId, StatoViaggio nuovoStato) {
        Viaggio found = this.findById(viaggioId);

        found.setStato(nuovoStato);

        return this.viaggioRepository.save(found);
    }
}
