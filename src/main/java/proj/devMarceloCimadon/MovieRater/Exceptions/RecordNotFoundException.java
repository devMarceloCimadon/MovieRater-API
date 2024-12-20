package proj.devMarceloCimadon.MovieRater.Exceptions;

public class RecordNotFoundException extends RuntimeException {
    public RecordNotFoundException(String searchField, String searchValue) {
        super("No record found where " + searchField + " is " + searchValue);
    }
}
