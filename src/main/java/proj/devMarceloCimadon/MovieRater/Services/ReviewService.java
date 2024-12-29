package proj.devMarceloCimadon.MovieRater.Services;

import org.springframework.stereotype.Service;
import proj.devMarceloCimadon.MovieRater.Dto.Review.CreateReviewDto;
import proj.devMarceloCimadon.MovieRater.Dto.Review.UpdateReviewDto;
import proj.devMarceloCimadon.MovieRater.Exceptions.RecordNotCreatedException;
import proj.devMarceloCimadon.MovieRater.Exceptions.RecordNotFoundException;
import proj.devMarceloCimadon.MovieRater.Models.Review;
import proj.devMarceloCimadon.MovieRater.Repositories.ReviewRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ReviewService {
    private final ReviewRepository reviewRepository;
    private final MovieService movieService;
    private final UserService userService;

    public ReviewService(ReviewRepository reviewRepository, MovieService movieService, UserService userService) {
        this.reviewRepository = reviewRepository;
        this.movieService = movieService;
        this.userService = userService;
    }

    public Long createReview(CreateReviewDto createReviewDto) {
        if (createReviewDto.userUsername() == null || createReviewDto.movieName() == null ||createReviewDto.grade() == null) {
            throw new RecordNotCreatedException();
        }
        var review = new Review();
        review.setUser(userService.findUserByUsername(createReviewDto.userUsername()).orElseThrow(() -> new RecordNotFoundException("Username", createReviewDto.userUsername())));
        review.setMovie(movieService.findMovieByName(createReviewDto.movieName()).orElseThrow(() -> new RecordNotFoundException("Movie", createReviewDto.movieName())));
        review.setGrade(createReviewDto.grade());
        review.setContent(createReviewDto.content());

        var reviewSaved = reviewRepository.save(review);

        var reviews = findReviewsByMovieName(createReviewDto.movieName()).orElseThrow(() -> new RecordNotFoundException("Review movie name", createReviewDto.movieName()));
        movieService.updateMovieGrade(createReviewDto.movieName(), reviews);

        return reviewSaved.getReviewId();
    }

    public Optional<List<Review>> findReviewsByUserId(String userId) {
        var id = UUID.fromString(userId);
        var listReviews = reviewRepository.findReviewsByUserId(id);
        if (listReviews.isEmpty()) {
            throw new RecordNotFoundException("ID", userId);
        }
        return listReviews;
    }

    public Optional<List<Review>> findReviewsByMovieName(String movieName) {
        var listReviews = reviewRepository.findReviewsByMovieName(movieName);
        if (listReviews.isEmpty()){
            throw new RecordNotFoundException("Movie", movieName);
        }
        return listReviews;
    }

    public void updateReviewById(String reviewId, UpdateReviewDto updateReviewDto) {
        var id = Long.parseLong(reviewId);
        var reviewEntity = reviewRepository.findById(id);
        if (reviewEntity.isEmpty()) {
            throw new RecordNotFoundException("ID", reviewId);
        }
        var review = reviewEntity.get();
        if (updateReviewDto.grade() != null) {
            review.setGrade(updateReviewDto.grade());
        }
        if (updateReviewDto.content() != null) {
            review.setContent(updateReviewDto.content());
        }
        reviewRepository.save(review);
    }

    public void deleteReviewById(String reviewId) {
        var id = Long.parseLong(reviewId);
        var reviewExists = reviewRepository.existsById(id);
        if (!reviewExists){
            throw new RecordNotFoundException("ID", reviewId);
        }
        reviewRepository.deleteById(id);
    }
}