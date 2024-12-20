package proj.devMarceloCimadon.MovieRater.ExceptionHandler;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import proj.devMarceloCimadon.MovieRater.Exceptions.DataWithThisValueAlreadyExists;
import proj.devMarceloCimadon.MovieRater.Exceptions.RecordNotCreatedException;
import proj.devMarceloCimadon.MovieRater.Exceptions.RecordNotFoundException;

@RestControllerAdvice
public class ApplicationExceptionHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler(RecordNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    private String userNotFoundHandler(RecordNotFoundException exception){
        return exception.getMessage();
    }

    @ExceptionHandler(RecordNotCreatedException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    private String userNotCreatedHandler(RecordNotCreatedException exception){
        return exception.getMessage();
    }

    @ExceptionHandler(DataWithThisValueAlreadyExists.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    private String dataWithThisValueAlreadyExists(DataWithThisValueAlreadyExists exception){
        return exception.getMessage();
    }
}
