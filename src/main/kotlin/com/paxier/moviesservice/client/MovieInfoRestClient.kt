package com.paxier.moviesservice.client

import com.paxier.moviesservice.domain.MovieInfo
import com.paxier.moviesservice.downstream.MicroServiceEndpoint
import com.paxier.moviesservice.downstream.DownStreamRequester
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.HttpMethod
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.client.WebClient
import reactor.core.publisher.Mono

@Component
class MovieInfoRestClient(
    @Value("\${restClient.moviesInfoUrl}") val moviesInfoURL: String,
) {

    fun retrieveMovie(movieId: String): Mono<MovieInfo> {
        val url = "$moviesInfoURL/$movieId"
        val endpoint = MicroServiceEndpoint(HttpMethod.GET, moviesInfoURL, "/$movieId", null)
        return DownStreamRequester(endpoint, WebClient.builder().build()).get() as Mono<MovieInfo>

//        return webClient
//            .get()
//            .uri(url)
//            .retrieve()
//            .onStatus(HttpStatus.NOT_FOUND::equals) {
//                Mono.error(MovieInfoClientException("Movie is not found for movieId: $movieId"))
//            }
//            .onStatus(HttpStatus.INTERNAL_SERVER_ERROR::equals) {
//                Mono.error(MovieInfoClientException("Looks like µ-service is down..."))
//            }
//            .bodyToMono(MovieInfo::class.java)
//            .log()
    }
}

