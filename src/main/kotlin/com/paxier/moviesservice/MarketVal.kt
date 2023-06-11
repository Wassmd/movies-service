package com.paxier.moviesservice

import com.paxier.moviesservice.header.AttributeToHeaderDownstream
import com.paxier.moviesservice.header.DEVICE_ID_CONTEXT_KEY
import com.paxier.moviesservice.header.DeviceIdWebFilter.Companion.INTERNAL_HEADER
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.reactive.function.client.ExchangeFilterFunction

@Configuration
class MarketVal {

    @Bean
    fun marketValHeaderAppender(): ExchangeFilterFunction =
        AttributeToHeaderDownstream(
            mapOf(
                Pair(DEVICE_ID_CONTEXT_KEY, INTERNAL_HEADER)
            )
        )
}