package com.paxier.moviesservice.client

import com.paxier.moviesservice.domain.MovieInfo
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.io.Resource
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.reactive.function.client.bodyToMono
import reactor.core.publisher.Mono
import java.io.BufferedReader

@Component
class MovieInfoRestClient(
    @Value("\${restClient.moviesInfoUrl}") val moviesInfoURL: String,
    val webClient: WebClient
) {

    fun retrieveMovie(movieId: String): Mono<MovieInfo> {
        val url = "$moviesInfoURL/$movieId"

       return webClient
            .get()
            .uri(url)
            .retrieve()
            .bodyToMono(MovieInfo::class.java)
            .log()

    }
}

