package proj.devMarceloCimadon.MovieRater.Dto.Artist;

import proj.devMarceloCimadon.MovieRater.Models.Artist;

public record ResponseArtistForMovieDto(String name){
    public static ResponseArtistForMovieDto fromEntity(Artist artist){
        return new ResponseArtistForMovieDto(artist.getName());
    }
}
