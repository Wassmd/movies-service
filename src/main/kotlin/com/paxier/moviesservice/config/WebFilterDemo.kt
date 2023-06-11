package com.paxier.moviesservice.config

import org.springframework.core.Ordered
import org.springframework.core.annotation.Order
import org.springframework.stereotype.Component
import org.springframework.web.server.ServerWebExchange
import org.springframework.web.server.WebFilter
import org.springframework.web.server.WebFilterChain
import reactor.core.publisher.Mono

@Component
@Order(Ordered.LOWEST_PRECEDENCE)
class WebFilterDemo : WebFilter {

    override fun filter(exchange: ServerWebExchange, chain: WebFilterChain): Mono<Void> {
        println("WebFilter Executing pre-processing logic")

        // Continue the filter chain
        return chain.filter(exchange)
            .doOnSuccess { aVoid: Void? ->
                // Post-processing logic here
                println("WebFilter Executing post-processing logic")
            }
    }
}