package proj.devMarceloCimadon.MovieRater.Dto.Review;

public record CreateReviewDto(String userUsername, String movieName, Double grade, String content) {
}