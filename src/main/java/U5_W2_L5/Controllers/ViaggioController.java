package U5_W2_L5.Controllers;

import U5_W2_L5.Entities.Viaggio;
import U5_W2_L5.Enum.StatoViaggio;
import U5_W2_L5.Excpetions.ValidationException;
import U5_W2_L5.Payload.NewViaggioRequest;
import U5_W2_L5.Services.ViaggioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/viaggi")
public class ViaggioController {

    @Autowired
    private ViaggioService viaggioService;

    @GetMapping
    public Page<Viaggio> findAllViaggi(@RequestParam(defaultValue = "0") int page,
                                       @RequestParam(defaultValue = "10") int size,
                                       @RequestParam(defaultValue = "viaggioId") String sortBy) {
        return this.viaggioService.findAll(page, size, sortBy);
    }

    @GetMapping("/{viaggioId}")
    public Viaggio findViaggioById(@PathVariable long viaggioId) {
        return this.viaggioService.findById(viaggioId);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Viaggio createViaggio(@RequestBody @Validated NewViaggioRequest payload, BindingResult validationResult) {

        if (validationResult.hasErrors()) {

            throw new ValidationException(validationResult.getFieldErrors()
                    .stream().map(fieldError -> fieldError.getDefaultMessage()).toList());
        }
        return this.viaggioService.save(payload);
    }

    @PutMapping("/{viaggioId}")
    public Viaggio findByIdAndUpdate(@PathVariable long viaggioId, @RequestBody @Validated NewViaggioRequest payload) {
        return this.viaggioService.findByIdAndUpdate(viaggioId, payload);
    }

    @DeleteMapping("/{viaggioId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void findByIdAndDelete(@PathVariable long viaggioId) {
        this.viaggioService.findByIdAndDelete(viaggioId);
    }

    @PatchMapping("/{viaggioId}/stato")
    public Viaggio updateStatoViaggio(@PathVariable long viaggioId, @RequestParam StatoViaggio nuovoStato) {
        return this.viaggioService.updateStato(viaggioId, nuovoStato);
    }


}
