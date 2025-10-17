package U5_W2_L5.Controllers;

import U5_W2_L5.Entities.Prenotazione;
import U5_W2_L5.Excpetions.ValidationException;
import U5_W2_L5.Payload.NewPrenotazioneRequest;
import U5_W2_L5.Services.PrenotazioneService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/prenotazioni")
public class PrenotazioneController {

    @Autowired
    private PrenotazioneService prenotazioneService;


    @GetMapping
    public Page<Prenotazione> findAllPrenotazioni(@RequestParam(defaultValue = "0") int page,
                                                  @RequestParam(defaultValue = "10") int size,
                                                  @RequestParam(defaultValue = "prenotazioneId") String sortBy) {
        return this.prenotazioneService.findAll(page, size, sortBy);
    }


    @GetMapping("/{prenotazioneId}")
    public Prenotazione findPrenotazioneById(@PathVariable long prenotazioneId) {
        return this.prenotazioneService.findById(prenotazioneId);
    }

    @PutMapping("/{prenotazioneId}")
    public Prenotazione findByIdAndUpdate(@PathVariable long prenotazioneId, @RequestBody @Validated NewPrenotazioneRequest prenotazionePayload, BindingResult validationResult) {
        if (validationResult.hasErrors()) {
            throw new ValidationException(validationResult.getFieldErrors()
                    .stream().map(fieldError -> fieldError.getDefaultMessage()).toList());
        }
        return this.prenotazioneService.findByIdAndUpdate(prenotazioneId, prenotazionePayload);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Prenotazione createPrenotazione(@RequestBody @Validated NewPrenotazioneRequest newPrenotazionePayload, BindingResult validationResult) {
        if (validationResult.hasErrors()) {
            throw new ValidationException(validationResult.getFieldErrors()
                    .stream().map(fieldError -> fieldError.getDefaultMessage()).toList());
        }

        return this.prenotazioneService.save(newPrenotazionePayload);
    }

    @DeleteMapping("/{prenotazioneId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void findByIdAndDelete(@PathVariable long prenotazioneId) {
        this.prenotazioneService.findByIdAndDelete(prenotazioneId);
    }
}
