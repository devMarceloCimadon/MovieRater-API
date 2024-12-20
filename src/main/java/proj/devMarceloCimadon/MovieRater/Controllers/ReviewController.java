package proj.devMarceloCimadon.MovieRater.Controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import proj.devMarceloCimadon.MovieRater.Dto.Review.CreateReviewDto;
import proj.devMarceloCimadon.MovieRater.Dto.Review.UpdateReviewDto;
import proj.devMarceloCimadon.MovieRater.Models.Review;
import proj.devMarceloCimadon.MovieRater.Services.ReviewService;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/v1/review")
public class ReviewController {
    private final ReviewService reviewService;

    public ReviewController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    @PostMapping
    public ResponseEntity<Review> createReview(@RequestBody CreateReviewDto createReviewDto) {
        var reviewId = reviewService.createReview(createReviewDto);
        return ResponseEntity.created(URI.create("v1/review/" + reviewId.toString())).build();
    }

    @GetMapping("/{userId}")
    public ResponseEntity<List<Review>> listReviewsByUser(@PathVariable("userId") String userId) {
        var userReviews = reviewService.findReviewsByUserId(userId);
        return ResponseEntity.ok(userReviews.get());
    }

    @PutMapping("/{reviewId}")
    public ResponseEntity<Void> updateReviewById(@PathVariable("reviewId") String reviewId, @RequestBody UpdateReviewDto updateReviewDto) {
        reviewService.updateReviewById(reviewId, updateReviewDto);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{reviewId}")
    public ResponseEntity<Void> deleteReviewByID(@PathVariable("reviewId") String reviewId){
        reviewService.deleteReviewById(reviewId);
        return ResponseEntity.noContent().build();
    }
}