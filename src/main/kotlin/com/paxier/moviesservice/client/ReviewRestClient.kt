package com.paxier.moviesservice.client

import com.paxier.moviesservice.domain.MovieInfo
import com.paxier.moviesservice.domain.Review
import com.paxier.moviesservice.downstream.ServiceEndpoint
import com.paxier.moviesservice.downstream.ServiceEnpointDataFetcher
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.HttpMethod
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.util.UriComponentsBuilder
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@Component
class ReviewRestClient(
    @Value("\${restClient.reviewsUrl}") val reviewURL: String
) {
    fun retrieveReviews(movieId: String): Flux<Review> {
        val url = UriComponentsBuilder.fromHttpUrl(reviewURL)
            .queryParam("movieInfoId", movieId)
            .buildAndExpand()
            .toString()

        val endpoint = ServiceEndpoint(HttpMethod.GET, reviewURL, "/$movieId", null)
        return ServiceEnpointDataFetcher(endpoint, WebClient.builder().build()).get() as Flux<Review>
    }
}