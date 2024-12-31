package proj.devMarceloCimadon.MovieRater.Dto.Movie;

import proj.devMarceloCimadon.MovieRater.Dto.Artist.ResponseArtistForMovieDto;
import proj.devMarceloCimadon.MovieRater.Dto.Studio.ResponseStudioForMovieDto;
import proj.devMarceloCimadon.MovieRater.Models.Movie;

import java.util.List;
import java.util.stream.Collectors;

public record ResponseMovieDto(String name, String description, ResponseStudioForMovieDto studio, List<ResponseArtistForMovieDto> cast, Float finalGrade) {
    public static ResponseMovieDto fromEntity(Movie movie){
        return new ResponseMovieDto(movie.getName(), movie.getDescription(), ResponseStudioForMovieDto.fromEntity(movie.getStudio()),
                movie.getArtists().stream().map(ResponseArtistForMovieDto:: fromEntity).collect(Collectors.toList()), movie.getFinalGrade());
    }
}
