package U5_W2_L5.Controllers;

import U5_W2_L5.Entities.Dipendente;
import U5_W2_L5.Excpetions.ValidationException;
import U5_W2_L5.Payload.NewDipendenteRequest;
import U5_W2_L5.Services.DipendenteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/dipendenti")
public class DipendenteController {

    @Autowired
    private DipendenteService dipendenteService;

    @GetMapping
    public Page<Dipendente> findAllDipendenti(@RequestParam(defaultValue = "0") int page,
                                              @RequestParam(defaultValue = "10") int size,
                                              @RequestParam(defaultValue = "dipendenteId") String sortBy) {
        return this.dipendenteService.findAll(page, size, sortBy);
    }

    @GetMapping("/{dipendenteId}")
    public Dipendente findDipendenteById(@PathVariable long dipendenteId) {
        return this.dipendenteService.findById(dipendenteId);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Dipendente createDipendente(@RequestBody @Validated NewDipendenteRequest payload, BindingResult validationResult) {

        if (validationResult.hasErrors()) {

            throw new ValidationException(validationResult.getFieldErrors()
                    .stream().map(fieldError -> fieldError.getDefaultMessage()).toList());
        }
        return this.dipendenteService.save(payload);
    }

    @PutMapping("/{dipendenteId}")
    public Dipendente findByIdAndUpdate(@PathVariable long dipendenteId, @RequestBody @Validated NewDipendenteRequest dipendentePayload, BindingResult validationResult) {
        if (validationResult.hasErrors()) {
            throw new ValidationException(validationResult.getFieldErrors()
                    .stream().map(fieldError -> fieldError.getDefaultMessage()).toList());
        }

        return this.dipendenteService.findByIdAndUpdate(dipendenteId, dipendentePayload);
    }

    @DeleteMapping("/{dipendenteId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void findByIdAndDelete(@PathVariable long dipendenteId) {
        this.dipendenteService.findByIdAndDelete(dipendenteId);
    }


    @PostMapping("/{dipendenteId}/upload")
    public Dipendente uploadImmagine(@PathVariable long dipendenteId, @RequestParam("immagine") MultipartFile file) throws IOException {


        return this.dipendenteService.uploadImmagine(dipendenteId, String.valueOf(file));
    }


}