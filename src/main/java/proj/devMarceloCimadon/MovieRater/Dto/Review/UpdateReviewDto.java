package proj.devMarceloCimadon.MovieRater.Dto.Review;

import proj.devMarceloCimadon.MovieRater.Models.Review;

import java.util.Optional;

public record UpdateReviewDto(Double grade, String content) {
}
