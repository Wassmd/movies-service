package com.paxier.moviesservice.client

import com.paxier.moviesservice.domain.Review
import com.paxier.moviesservice.downstream.MicroServiceEndpoint
import com.paxier.moviesservice.downstream.DownStreamRequester
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.HttpMethod
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.util.UriComponentsBuilder
import reactor.core.publisher.Flux

@Component
class ReviewRestClient(
    @Value("\${restClient.reviewsUrl}") val reviewURL: String
) {
    fun retrieveReviews(movieId: String): Flux<Review> {
        val url = UriComponentsBuilder.fromHttpUrl(reviewURL)
            .queryParam("movieInfoId", movieId)
            .buildAndExpand()
            .toString()

        val endpoint = MicroServiceEndpoint(HttpMethod.GET, reviewURL, "/$movieId", null)
        return DownStreamRequester(endpoint, WebClient.builder().build()).get() as Flux<Review>
    }
}