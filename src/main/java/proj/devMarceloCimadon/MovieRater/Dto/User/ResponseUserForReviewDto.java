package proj.devMarceloCimadon.MovieRater.Dto.User;

import proj.devMarceloCimadon.MovieRater.Models.User;

public record ResponseUserForReviewDto(String username, String name) {
    public static ResponseUserForReviewDto fromEntity(User user){
        return new ResponseUserForReviewDto(user.getUsername(), user.getName());
    }
}
