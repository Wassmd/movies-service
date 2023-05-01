package com.paxier.moviesservice.downstream

import org.slf4j.LoggerFactory
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.reactive.function.client.WebClientResponseException
import reactor.core.publisher.Mono


class ServiceEnpointDataFetcher(
    private val endpoint: ServiceEndpoint,
    webClient: WebClient.Builder
) {

    private val webClient = webClient
        .baseUrl(endpoint.baseUrl)
        .build()

    fun get(): Mono<Any> {
        Logger.debug("Fetching data from downstream service...")
        return webClient
            .method(endpoint.method)
            .uri(endpoint.path)
            .retrieve()
            .toEntity(Any::class.java)
            .doOnError(WebClientResponseException::class.java) {
                Logger.error("Error while fetching data from downstream service: ${it.message}")
            }
            .mapNotNull { it.body }
    }

    companion object {
        private val Logger = LoggerFactory.getLogger(ServiceEnpointDataFetcher::class.java.name)
    }
}