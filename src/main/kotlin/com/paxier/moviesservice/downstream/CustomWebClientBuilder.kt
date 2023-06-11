package com.paxier.moviesservice.downstream

import org.springframework.beans.factory.BeanFactory
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.client.ExchangeFilterFunction
import org.springframework.web.reactive.function.client.WebClient

@Component
class CustomWebClientBuilder(
    private val beanFactory: BeanFactory,
    private val marketValHeaderAppender: ExchangeFilterFunction,
    ) {

    fun customBuilder(): WebClient.Builder =
        beanFactory.getBean(WebClient.Builder::class.java)
            .filter(marketValHeaderAppender)
}