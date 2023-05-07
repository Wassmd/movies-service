package com.paxier.moviesservice.downstream

import com.paxier.moviesservice.config.WebClientConfigurationProperties
import io.netty.channel.ChannelOption
import org.slf4j.LoggerFactory
import org.springframework.http.client.reactive.ReactorClientHttpConnector
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.reactive.function.client.WebClientResponseException
import reactor.core.publisher.Mono
import reactor.netty.transport.ProxyProvider
import reactor.netty.http.client.HttpClient


class ServiceEnpointDataFetcher(
    private val endpoint: ServiceEndpoint,
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
        private val Logger = LoggerFactory.getLogger(ServiceEnpointDataFetcher::class.java.name)
    }
}