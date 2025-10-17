package U5_W2_L5.Excpetions;

public class NotFoundException extends RuntimeException {
    public NotFoundException(long id) {
        super("Il record con id " + id + " non è stato trovato!");
    }
}
