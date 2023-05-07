package com.paxier.moviesservice.client

import com.paxier.moviesservice.domain.MovieInfo
import com.paxier.moviesservice.downstream.ServiceEndpoint
import com.paxier.moviesservice.downstream.ServiceEnpointDataFetcher
import com.paxier.moviesservice.exception.MovieInfoClientException
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.io.Resource
import org.springframework.http.HttpMethod
import org.springframework.http.HttpStatus
import org.springframework.http.HttpStatusCode
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.reactive.function.client.bodyToMono
import reactor.core.publisher.Mono
import java.io.BufferedReader
import java.net.http.HttpClient

@Component
class MovieInfoRestClient(
    @Value("\${restClient.moviesInfoUrl}") val moviesInfoURL: String,
) {

    fun retrieveMovie(movieId: String): Mono<MovieInfo> {
        val url = "$moviesInfoURL/$movieId"
        val endpoint = ServiceEndpoint(HttpMethod.GET, moviesInfoURL, "/$movieId", null)
        return ServiceEnpointDataFetcher(endpoint, WebClient.builder().build()).get() as Mono<MovieInfo>

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

