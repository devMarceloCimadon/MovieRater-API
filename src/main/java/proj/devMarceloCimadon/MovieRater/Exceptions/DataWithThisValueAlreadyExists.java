package proj.devMarceloCimadon.MovieRater.Exceptions;

public class DataWithThisValueAlreadyExists extends RuntimeException{
    public DataWithThisValueAlreadyExists(String field, String value){
        super("Data where the field '" + field + "' equals '" + value + "' already exists");
    }
}
