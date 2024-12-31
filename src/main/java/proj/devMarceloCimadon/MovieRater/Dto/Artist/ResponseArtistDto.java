package proj.devMarceloCimadon.MovieRater.Dto.Artist;

import proj.devMarceloCimadon.MovieRater.Dto.Movie.ResponseMovieDto;
import proj.devMarceloCimadon.MovieRater.Models.Artist;

import java.util.List;

public record ResponseArtistDto(String name, List<ResponseMovieDto> movies) {
    public static ResponseArtistDto fromEntity(Artist artist){
        return new ResponseArtistDto(artist.getName(), artist.getMovies().stream().map(ResponseMovieDto :: fromEntity).toList());
    }
}
