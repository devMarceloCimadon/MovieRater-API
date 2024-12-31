package proj.devMarceloCimadon.MovieRater.Dto.User;

import proj.devMarceloCimadon.MovieRater.Dto.Movie.ResponseMovieDto;
import proj.devMarceloCimadon.MovieRater.Dto.Review.ResponseReviewDto;
import proj.devMarceloCimadon.MovieRater.Models.User;

import java.util.List;
import java.util.stream.Collectors;

public record ResponseUserDto(String username, String name, String email, List<ResponseMovieDto> watchedMovies, List<ResponseMovieDto> watchList, List<ResponseReviewDto> reviews) {
    public static ResponseUserDto fromEntity(User user){
        return new ResponseUserDto(user.getUsername(), user.getName(), user.getEmail(), user.getWatchedMovies().stream().map(ResponseMovieDto :: fromEntity).collect(Collectors.toList()),
                user.getWatchList().stream().map(ResponseMovieDto :: fromEntity).collect(Collectors.toList()), user.getReviews().stream().map(ResponseReviewDto :: fromEntity).collect(Collectors.toList()));
    }
}
