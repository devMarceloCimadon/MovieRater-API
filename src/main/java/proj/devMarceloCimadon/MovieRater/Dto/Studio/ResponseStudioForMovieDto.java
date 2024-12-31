package proj.devMarceloCimadon.MovieRater.Dto.Studio;

import proj.devMarceloCimadon.MovieRater.Models.Studio;

public record ResponseStudioForMovieDto(String name) {
    public static ResponseStudioForMovieDto fromEntity(Studio studio){
        return new ResponseStudioForMovieDto(studio.getName());
    }
}
