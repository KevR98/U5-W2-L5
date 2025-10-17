package U5_W2_L5.Entities;

import U5_W2_L5.Enum.StatoViaggio;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Setter
@Getter
@NoArgsConstructor
@Table(name = "viaggio")
public class Viaggio {

    // LISTA ATTRIBUTI
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter(AccessLevel.NONE) // Serve per non prenderlo in considerazione dal setter
    private long id;

    @Column(name = "destinazione")
    private String destinazione;

    @Column(name = "data")
    private LocalDate data;

    @Column(name = "stato")
    @Enumerated(EnumType.STRING)
    private StatoViaggio stato;

    // LISTA COSTRUTTORI
    public Viaggio(String destinazione, LocalDate data, StatoViaggio stato) {
        this.destinazione = destinazione;
        this.data = data;
        this.stato = stato;
    }

    // LISTA METODI

    // SETTER/GETTER
    // Generati automaticamente dal lombok
}
