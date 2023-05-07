package com.paxier.moviesservice.client

import com.paxier.moviesservice.downstream.ServiceEndpoint
import com.paxier.moviesservice.downstream.ServiceEnpointDataFetcher
import org.springframework.beans.factory.BeanFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.HttpMethod
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.client.WebClient
import reactor.core.publisher.Mono

@Component
class UsersClient (
    @Value("\${restClient.usersUrl}") val usersURL: String,
    private val beanFactory: BeanFactory) {
    fun getAllUsers(): Mono<Any> {
        val endpoint = ServiceEndpoint(HttpMethod.GET, usersURL, "/all", "name=Wasim")
        val webClient = beanFactory.getBean(WebClient.Builder::class.java)
        return ServiceEnpointDataFetcher(endpoint, webClient.build()).get()
    }

    fun getError(): Mono<Any> {
        val endpoint = ServiceEndpoint(HttpMethod.GET, usersURL, "/error", null)
        val webClient = WebClient.builder().build()
        return ServiceEnpointDataFetcher(endpoint, webClient).get()
    }
}