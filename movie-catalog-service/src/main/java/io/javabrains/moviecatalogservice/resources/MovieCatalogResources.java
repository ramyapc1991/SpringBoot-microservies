package io.javabrains.moviecatalogservice.resources;

import io.javabrains.moviecatalogservice.models.CatalogItem;
import io.javabrains.moviecatalogservice.models.Movie;
import io.javabrains.moviecatalogservice.models.Rating;
import io.javabrains.moviecatalogservice.models.UserRating;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/catalog")
public class MovieCatalogResources {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private WebClient.Builder webClientBuilder;

    @RequestMapping("/{userId}")
    public List<CatalogItem> getCatalog(@PathVariable("userId") String userId){

       //RestTemplate restTemplate= new RestTemplate();

      //  WebClient.Builder builder = WebClient.builder();

        //get all rated movie ID's
//        List<Rating> ratings = Arrays.asList(
//                new Rating("Oh", 5),
//                new Rating("A", 4),
//                new Rating("OMO", 2),
//                new Rating("OMG", 3)
//        );

        UserRating ratings = restTemplate.getForObject("http://localhost:8083/ratingsData/user/"+userId,
                UserRating.class);
        return ratings.getUserRatingList().stream().map(rating -> {
            //for all movie ID, call movie info service get details
            Movie movie = restTemplate.getForObject("http://localhost:8082/movies/" + rating.getMovieId(), Movie.class);

//            Movie movie = webClientBuilder.build()
//                    .get()
//                    .uri("http://localhost:8082/movies/" + rating.getMovieId())
//                    .retrieve()
//                    .bodyToMono(Movie.class)
//                    .block();

            //put them all together
            return new CatalogItem(movie.getName(), "Movie desc", rating.getRating());
        }).collect(Collectors.toList());

//        return Collections.singletonList(
//                new CatalogItem("ABCD", "Ant body can dance", 4)
//        );
    }

}
