package proj.devMarceloCimadon.MovieRater.Dto.Movie;

import java.util.List;

public record CreateMovieDto(String name, String description, String studio, List<String> cast) {
}
