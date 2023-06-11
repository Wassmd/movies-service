package com.paxier.moviesservice.config

import com.paxier.moviesservice.header.DeviceIdWebFilter
import com.paxier.moviesservice.header.DeviceIdWebFilter.Companion.EXTERNAL_HEADER
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.annotation.Order
import org.springframework.security.web.server.util.matcher.ServerWebExchangeMatcher
import org.springframework.security.web.server.util.matcher.ServerWebExchangeMatchers
import org.springframework.web.server.ServerWebExchange
import org.springframework.web.server.WebFilter
import org.springframework.web.server.WebFilterChain
import reactor.core.publisher.Mono

@Configuration
class WebFilterConfiguration {

    private val matcher = ServerWebExchangeMatchers.pathMatchers("/api/**")

    @Bean
    @Order(20)
    fun validateDeviceIdHeader() = ExchangeMatchingWebFilter(matcher, DeviceIdWebFilter(EXTERNAL_HEADER))
}

class ExchangeMatchingWebFilter(
    private val matcher: ServerWebExchangeMatcher,
    private val delegate: WebFilter
) : WebFilter {
    override fun filter(exchange: ServerWebExchange, chain: WebFilterChain): Mono<Void> {
        return matcher.matches(exchange)
            .filter { it.isMatch }
            .switchIfEmpty(Mono.defer { chain.filter(exchange) }.then(Mono.empty()))
            .flatMap { delegate.filter(exchange, chain) }
    }
}

