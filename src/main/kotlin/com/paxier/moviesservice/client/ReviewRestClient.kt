package com.paxier.moviesservice.client

import com.paxier.moviesservice.domain.Review
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.util.UriComponentsBuilder
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@Component
class ReviewRestClient(
    val webClient: WebClient,
    @Value("\${restClient.reviewsUrl}") val reviewURL: String
) {
    fun retrieveReviews(movieId: String): Flux<Review> {
        val url = UriComponentsBuilder.fromHttpUrl(reviewURL)
            .queryParam("movieInfoId", movieId)
            .buildAndExpand()
            .toString()

        return webClient
            .get()
            .uri(url)
            .retrieve()
            .bodyToFlux(Review:: class.java)
            .log()
    }
}