package U5_W2_L5.Entities;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Setter
@Getter
@NoArgsConstructor
@Table(name = "dipendente")
public class Dipendente {
    // LISTA ATTRIBUTI
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter(AccessLevel.NONE)
    private long id;

    @Column(name = "username")
    private String username;

    @Column(name = "nome")
    private String nome;

    @Column(name = "cognome")
    private String cognome;

    @Column(name = "email")
    private String email;

    @Column(name = "profile_image_url")
    private String profileImageUrl;

    // LISTA COSTRUTTORI
    public Dipendente(String username, String nome, String cognome, String email) {
        this.username = username;
        this.nome = nome;
        this.cognome = cognome;
        this.email = email;
        this.profileImageUrl = profileImageUrl;
    }

    // LISTA METODI

    // SETTER/GETTER
    // Generati automaticamente dal lombok
}
