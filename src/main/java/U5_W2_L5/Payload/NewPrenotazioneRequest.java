package U5_W2_L5.Payload;

import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

// DTO per creare una nuova prenotazione
public record NewPrenotazioneRequest(@NotNull(message = "viaggioId richiesto") long viaggio_id,
                                     @NotNull(message = "dipendenteId richiesto") long dipendente_id,
                                     LocalDate dataViaggio,
                                     LocalDate dataRichiesta,
                                     String note, String preferenze) {
}
