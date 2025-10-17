package U5_W2_L5.Payload;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

// DTO per la creazione di un nuovo Dipendente
public record NewDipendenteRequest(@NotBlank(message = "username non può essere vuoto")
                                   String username,

                                   @NotBlank(message = "nome non può essere vuoto")
                                   String nome,

                                   @NotBlank(message = "cognome non può essere vuoto")
                                   String cognome,

                                   @Email(message = "deve essere in formato email")
                                   @NotBlank(message = "email non può essere vuota")
                                   String email) {

}
