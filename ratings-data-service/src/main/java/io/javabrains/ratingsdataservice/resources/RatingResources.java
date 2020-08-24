package io.javabrains.ratingsdataservice.resources;

import io.javabrains.ratingsdataservice.models.Rating;
import io.javabrains.ratingsdataservice.models.UserRating;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("ratingsData")
public class RatingResources {

    @RequestMapping("/{movieId}")
    public Rating getRating(@PathVariable("movieId") String movieId){
        return new Rating(movieId, 4);
    }
    @RequestMapping("/user/{UserId}")
    public UserRating getRatingList(@PathVariable("UserId") String UserId){
        List<Rating> ratings = Arrays.asList(
                new Rating("Oh", 5),
                new Rating("A", 4),
                new Rating("OMO", 2),
                new Rating("OMG", 3)
        );
        UserRating userRating = new UserRating();
        userRating.setUserRatingList(ratings);
        return userRating;
    }
}
