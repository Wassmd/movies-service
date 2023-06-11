package com.paxier.moviesservice.header

import org.slf4j.LoggerFactory
import org.springframework.web.reactive.function.client.ClientRequest
import org.springframework.web.reactive.function.client.ClientResponse
import org.springframework.web.reactive.function.client.ExchangeFilterFunction
import org.springframework.web.reactive.function.client.ExchangeFunction
import reactor.core.publisher.Mono

class AttributeToHeaderDownstream(
    private val attributeToHeader: Map<String, String>
): ExchangeFilterFunction {
    override fun filter(request: ClientRequest, next: ExchangeFunction): Mono<ClientResponse> {

        Logger.info("AttributeToHeaderDownstream filter called")
        return next.exchange(request)
    }

    companion object {
        private val Logger = LoggerFactory.getLogger(AttributeToHeaderDownstream::class.java)
    }
}