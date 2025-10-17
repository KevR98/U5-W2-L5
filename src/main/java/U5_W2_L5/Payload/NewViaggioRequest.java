package U5_W2_L5.Payload;

import U5_W2_L5.Enum.StatoViaggio;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

// DTO per creare un nuovo viaggio
public record NewViaggioRequest(
        @NotBlank(message = "La destinazione è obbligatorio")
        @Size(min = 2, max = 40, message = "La destinazione deve avere una lunghezza compresa tra 2 e 40 caratteri")
        String destinazione,
        @NotNull(message = "La data  è obbligatoria")
        @FutureOrPresent(message = "La data del viaggio deve essere oggi o successiva")
        @JsonFormat(pattern = "yyyy-MM-dd")
        LocalDate data,
        @NotNull(message = "Stato iniziale è obbligatorio")
        StatoViaggio stato
) {
}
