package com.paxier.moviesservice.downstream

import org.slf4j.LoggerFactory
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.reactive.function.client.WebClientResponseException
import reactor.core.publisher.Mono


class DownStreamRequester(
    private val endpoint: MicroServiceEndpoint,
    webClient: WebClient
) {

    private val webClient = webClient
        .mutate()
        .baseUrl(endpoint.baseUrl)
        .build()

    fun get(): Mono<Any> {
        Logger.info("Fetching data from downstream service...")
        return webClient
            .method(endpoint.method)
            .uri(endpoint.path) {
                endpoint.query?.let { query ->
                    it.query(query)
                        .build()
                }?: it.build()
            }
            .retrieve()
            .toEntity(Any::class.java)
            .doOnError(WebClientResponseException::class.java) {
                Logger.error("Error while fetching data from downstream service: ${it.message}")
            }
            .mapNotNull { it.body }
    }

    companion object {
        private val Logger = LoggerFactory.getLogger(DownStreamRequester::class.java.name)
    }
}