package com.paxier.moviesservice.controller

import com.paxier.moviesservice.client.MovieInfoRestClient
import com.paxier.moviesservice.client.ReviewRestClient
import com.paxier.moviesservice.domain.Movie
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Mono

@RestController
@RequestMapping("/movies")
class MoviesController(
    val moviesInfoRestClient: MovieInfoRestClient,
    val reviewRestClient: ReviewRestClient
) {

    @GetMapping("/{id}")
    fun retrieveMovieById(@PathVariable("id") movieId: String): Mono<Movie> {
            return moviesInfoRestClient.retrieveMovie(movieId)
                .flatMap { moviesInfo ->
                    reviewRestClient.retrieveReviews(movieId)
                        .collectList()
                        .map { Movie(moviesInfo, it) }
                }

    }
}