package com.paxier.moviesservice.client

import com.paxier.moviesservice.downstream.ServiceEndpoint
import com.paxier.moviesservice.downstream.ServiceEnpointDataFetcher
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.HttpMethod
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.client.WebClient
import reactor.core.publisher.Mono

@Component
class UsersClient (
    @Value("\${restClient.usersUrl}") val usersURL: String,
    ) {
    fun getAllUsers(): Mono<Any> {
        val endpoint = ServiceEndpoint(HttpMethod.GET, usersURL, "/all", null)
        return ServiceEnpointDataFetcher(endpoint, WebClient.builder()).get()
    }
}