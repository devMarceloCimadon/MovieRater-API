package proj.devMarceloCimadon.MovieRater.Dto.Review;

import proj.devMarceloCimadon.MovieRater.Dto.Movie.ResponseMovieDto;
import proj.devMarceloCimadon.MovieRater.Dto.User.ResponseUserForReviewDto;
import proj.devMarceloCimadon.MovieRater.Models.Review;

import java.time.Instant;

public record ResponseReviewDto (ResponseUserForReviewDto user, ResponseMovieDto movie, Double grade, String content, Instant creationTimestamp){
    public static ResponseReviewDto fromEntity(Review review){
        return new ResponseReviewDto(ResponseUserForReviewDto.fromEntity(review.getUser()), ResponseMovieDto.fromEntity(review.getMovie()),
                review.getGrade(), review.getContent(), review.getCreationTimestamp());
    }
}
