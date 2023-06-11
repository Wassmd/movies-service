package com.paxier.moviesservice.client

import com.paxier.moviesservice.downstream.MicroServiceEndpoint
import com.paxier.moviesservice.downstream.DownStreamRequester
import com.paxier.moviesservice.downstream.CustomWebClientBuilder
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.HttpMethod
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.client.WebClient
import reactor.core.publisher.Mono

@Component
class UsersClient (
    @Value("\${restClient.usersUrl}") val usersURL: String,
    val customWebClientBuilder: CustomWebClientBuilder) {
    fun getAllUsers(): Mono<Any> {
        val endpoint = MicroServiceEndpoint(HttpMethod.GET, usersURL, "/all", "name=Wasim")
        return DownStreamRequester(endpoint, customWebClientBuilder.customBuilder().build()).get()
    }

    fun getUsersById(id: Int): Mono<Any> {
        val endpoint = MicroServiceEndpoint(HttpMethod.GET, usersURL, "/$id", null)
        return DownStreamRequester(endpoint, customWebClientBuilder.customBuilder().build()).get()
    }
    fun getError(): Mono<Any> {
        val endpoint = MicroServiceEndpoint(HttpMethod.GET, usersURL, "/error", null)
        val webClient = WebClient.builder().build()
        return DownStreamRequester(endpoint, webClient).get()
    }
}