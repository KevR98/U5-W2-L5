package U5_W2_L5.Entities;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@NoArgsConstructor
@Setter
@Getter
@Table(name = "prenotazione")
public class Prenotazione {

    // LISTA ATTRIBUTI
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter(AccessLevel.NONE)
    private long id;

    @ManyToOne
    @JoinColumn(name = "viaggio_id")
    private Viaggio viaggio;

    @ManyToOne
    @JoinColumn(name = "dipendente_id")
    private Dipendente dipendente;

    @Column(name = "data_viaggio")
    private LocalDate dataViaggio;

    @Column(name = "data_richiesta")
    private LocalDate dataRichiesta;

    @Column(name = "note")
    private String note;

    @Column(name = "preferenze")
    private String preferenze;

    // LISTA COSTRUTTORI
    public Prenotazione(Viaggio viaggio, Dipendente dipendente, LocalDate dataViaggio, LocalDate dataRichiesta, String note, String preferenze) {
        this.viaggio = viaggio;
        this.dipendente = dipendente;
        this.dataViaggio = dataViaggio;
        this.dataRichiesta = dataRichiesta;
        this.note = note;
        this.preferenze = preferenze;
    }

    public Prenotazione(LocalDate now, String preferenze, Viaggio viaggio, Dipendente dipendente) {
    }

    // LISTA METODI

    // SETTER/GETTER
    // Generati automaticamente dal lombok
}
