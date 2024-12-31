package proj.devMarceloCimadon.MovieRater.Dto.Studio;

import proj.devMarceloCimadon.MovieRater.Dto.Movie.ResponseMovieDto;
import proj.devMarceloCimadon.MovieRater.Models.Studio;

import java.util.List;

public record ResponseStudioDto(String name, List<ResponseMovieDto> movies) {
    public static ResponseStudioDto fromEntity(Studio studio){
        return new ResponseStudioDto(studio.getName(), studio.getMovies().stream().map(ResponseMovieDto :: fromEntity).toList());
    }
}
