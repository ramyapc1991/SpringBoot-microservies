package io.javabrains.moviecatalogservice.resources;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.ribbon.proxy.annotation.Hystrix;
import io.javabrains.moviecatalogservice.models.CatalogItem;
import io.javabrains.moviecatalogservice.models.Movie;
import io.javabrains.moviecatalogservice.models.Rating;
import io.javabrains.moviecatalogservice.models.UserRating;
import io.javabrains.moviecatalogservice.services.MovieInfo;
import io.javabrains.moviecatalogservice.services.UserRatingInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.discovery.DiscoveryClient;
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

    // not recommended to use this just leave to rest template to pick up the instance.
    @Autowired
    private WebClient.Builder webClientBuilder;

    @Autowired
    private DiscoveryClient discoveryClient;

    @Autowired
    private MovieInfo movieInfo;

    @Autowired
    private UserRatingInfo userRatingInfo;

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

        UserRating ratings = userRatingInfo.getUserRating(userId);
        return ratings.getUserRatingList().stream().map(rating -> {
            //for all movie ID, call movie info service get details
            Movie movie = movieInfo.getCatalogItem(rating);

            //put them all together
            return new CatalogItem(movie.getName(), "Movie desc", rating.getRating());
        }).collect(Collectors.toList());

//            Movie movie = webClientBuilder.build()
//                    .get()
//                    .uri("http://localhost:8082/movies/" + rating.getMovieId())
//                    .retrieve()
//                    .bodyToMono(Movie.class)
//                    .block();

//        return Collections.singletonList(
//                new CatalogItem("ABCD", "Ant body can dance", 4)
//        );
    }


    public List<CatalogItem> getFallbackCatalog(@PathVariable("userId") String userId){
        return Arrays.asList(new CatalogItem("no movie", "", 0));
    }

}
