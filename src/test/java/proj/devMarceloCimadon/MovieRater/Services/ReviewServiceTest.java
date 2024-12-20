package proj.devMarceloCimadon.MovieRater.Services;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import proj.devMarceloCimadon.MovieRater.Dto.Review.CreateReviewDto;
import proj.devMarceloCimadon.MovieRater.Dto.Review.UpdateReviewDto;
import proj.devMarceloCimadon.MovieRater.Exceptions.RecordNotCreatedException;
import proj.devMarceloCimadon.MovieRater.Exceptions.RecordNotFoundException;
import proj.devMarceloCimadon.MovieRater.Models.Movie;
import proj.devMarceloCimadon.MovieRater.Models.Review;
import proj.devMarceloCimadon.MovieRater.Models.Studio;
import proj.devMarceloCimadon.MovieRater.Models.User;
import proj.devMarceloCimadon.MovieRater.Repositories.ReviewRepository;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

@ExtendWith(MockitoExtension.class)
class ReviewServiceTest {
    @Mock
    private ReviewRepository reviewRepository;

    @InjectMocks
    private ReviewService reviewService;

    @Mock
    private MovieService movieService;

    @Mock
    private UserService userService;

    @Captor
    private ArgumentCaptor<Review> reviewArgumentCaptor;

    @Captor
    private ArgumentCaptor<Long> idArgumentCaptor;

    @Captor
    private ArgumentCaptor<UUID> uuidArgumentCaptor;

    @Nested
    class createReview{
        @Test
        @DisplayName("Should create a review with success")
        void shouldCreateAReviewWithSuccess(){
            //Arrange
            var studio = new Studio(1L, "studio", null);

            var user = new User(UUID.randomUUID(), "username", "Name", "email@email.com", "password", null, null, null, Instant.now(), null);
            when(userService.findUserByUsername(user.getUsername())).thenReturn(Optional.of(user));
            var movie = new Movie(1L, "movie", "description", studio, null, null, null, null, 7.0F);
            when(movieService.findMovieByName(movie.getName())).thenReturn(Optional.of(movie));

            var review = new Review(1L, user, movie, 7.8F, "content", Instant.now(), null);
            doReturn(review).when(reviewRepository).save(reviewArgumentCaptor.capture());

            var input = new CreateReviewDto("username","movie", 7.0, "content");
            //Act
            var output = reviewService.createReview(input);
            //Assert
            assertNotNull(output);
            var reviewCaptured = reviewArgumentCaptor.getValue();
            assertEquals(user, reviewCaptured.getUser());
            assertEquals(movie, reviewCaptured.getMovie());
            assertEquals(input.grade(), reviewCaptured.getGrade());
            assertEquals(input.content(), reviewCaptured.getContent());
        }

        @Test
        @DisplayName("Should throw a record not created exception")
        void shouldThrowARecordNotCreatedException(){
            //Arrange
            var input = new CreateReviewDto("username","movie", null, "content");
            //Act & Assert
            assertThrows(RecordNotCreatedException.class, () -> reviewService.createReview(input));
            verify(reviewRepository, times(0)).save(any());
        }

        @Test
        @DisplayName("Should throw a record not found exception when movie was not found")
        void shouldThrowARecordNotFoundExceptionWhenMovieWasNotFound() {
            var movieName = "name";
            when(movieService.findMovieByName(movieName)).thenReturn(Optional.empty());

            var input = new CreateReviewDto("username", movieName, 7.0, "content");

            assertThrows(RecordNotFoundException.class, () -> reviewService.createReview(input));
            verify(reviewRepository, times(0)).save(any());
        }
    }

    @Nested
    class findReviewByUserId{
        @Test
        @DisplayName("Should get a list of reviews by user id with success")
        void shouldGetAListOfReviewsByUserIdWithSuccess(){
            //Arrange
            var reviews = new ArrayList<Review>();
            var user = new User(UUID.randomUUID(), "name", "username", "email@email.com", "password", null, null, reviews, Instant.now(), null);
            reviews.add(new Review(1L, user, null, 7.8, "content", Instant.now(), null));
            doReturn(Optional.of(reviews)).when(reviewRepository).findReviewsByUserId(uuidArgumentCaptor.capture());
            //Act
            var output = reviewService.findReviewsByUserId(user.getUserId().toString());
            //Assert
            assertTrue(output.isPresent());
        }

        @Test
        @DisplayName("Should throw a record not found exception when optional is empty")
        void shouldThrowARecordNotFoundExceptionWhenOptionalIsEmpty() {
            //Arrange
            var userId = UUID.randomUUID();
            doReturn(Optional.empty()).when(reviewRepository).findReviewsByUserId(uuidArgumentCaptor.capture());
            //Act & Assert
            assertThrows(RecordNotFoundException.class, () -> reviewService.findReviewsByUserId(userId.toString()));
        }
    }

    @Nested
    class updateReviewById{
        @Test
        @DisplayName("Should update a review by id with success when review exists")
        void shouldUpdateAReviewByIdWithSuccessWhenReviewExists(){
            //Arrange
            var updateReviewDto = new UpdateReviewDto(7.0,"content");

            var review = new Review(1L, null, null, 6.0, "content", Instant.now(), null);

            doReturn(Optional.of(review)).when(reviewRepository).findById(idArgumentCaptor.capture());
            doReturn(review).when(reviewRepository).save(reviewArgumentCaptor.capture());
            //Act
            reviewService.updateReviewById(review.getReviewId().toString(), updateReviewDto);
            //Assert
            assertEquals(review.getReviewId(), idArgumentCaptor.getValue());

            var reviewCaptured = reviewArgumentCaptor.getValue();

            assertEquals(updateReviewDto.grade(), reviewCaptured.getGrade());
            assertEquals(updateReviewDto.content(), reviewCaptured.getContent());

            verify(reviewRepository, times(1)).findById(idArgumentCaptor.getValue());
            verify(reviewRepository, times(1)).save(review);
        }

        @Test
        @DisplayName("Should throw a record not found exception and not update when review does not exist")
        void shouldThrowARecordNotFoundExceptionAndNotUpdateWhenReviewDoesNotExist(){
            //Arrange
            doReturn(Optional.empty()).when(reviewRepository).findById(idArgumentCaptor.capture());
            var updateReviewDto = new UpdateReviewDto(7.0,"content");
            var reviewId = 1L;
            //Act & Assert
            assertThrows(RecordNotFoundException.class, () -> reviewService.updateReviewById(Long.toString(reviewId), updateReviewDto));
            verify(reviewRepository, times(0)).save(any());
        }
    }

    @Nested
    class deleteReviewById{
        @Test
        @DisplayName("Should delete review with success when review exists")
        void shouldDeleteReviewWithSuccessWhenReviewExists(){
            //Arrange
            doReturn(true).when(reviewRepository).existsById(idArgumentCaptor.capture());
            doNothing().when(reviewRepository).deleteById(idArgumentCaptor.capture());
            var reviewId = 1L;
            //Act
            reviewService.deleteReviewById(Long.toString(reviewId));
            //Assert
            var idList = idArgumentCaptor.getAllValues();
            assertEquals(reviewId, idList.get(0));
            assertEquals(reviewId, idList.get(1));

            verify(reviewRepository, times(1)).existsById(idList.get(0));
            verify(reviewRepository, times(1)).deleteById(idList.get(1));
        }

        @Test
        @DisplayName("Should throw a record not found exception when review was not found")
        void shouldThrowARecordNotFoundExceptionWhenReviewWasNotFound(){
            //Arrange
            doReturn(false).when(reviewRepository).existsById(idArgumentCaptor.capture());
            var reviewId = 1L;
            //Act & Assert
            assertThrows(RecordNotFoundException.class, () -> reviewService.deleteReviewById(Long.toString(reviewId)));
            verify(reviewRepository, times(0)).deleteById(any());
        }
    }
}